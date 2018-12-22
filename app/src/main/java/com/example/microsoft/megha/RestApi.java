package com.example.microsoft.megha;

import android.content.Context;
import android.content.pm.PackageManager;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.logging.Handler;

import cz.msebera.android.httpclient.Header;

public class RestApi {
    protected static AsyncHttpClient client=null;
    protected static PersistentCookieStore myCookieStore=null;
   static Context apicontext=null;

    public static void initialize(Context context) {
        try {
            apicontext=context.createPackageContext("com.example.microsoft.megha",0);
            client=new  AsyncHttpClient();
            client.setUserAgent("Android");
            myCookieStore=new PersistentCookieStore(apicontext);
            client.setCookieStore(myCookieStore);

        }
        catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    public static void login(String username, String password, final ApiHandler apiHandler) {
        String url="http://infoz.os.a2zapps.com/api/v4/sso/login.json";
        if (username!=null&&!"".equals(username)&&password!=null&&!"".equals(password))
        {
            RequestParams params=new RequestParams();
            params.add("un",username);
            params.add("pw",password);



            client.post(url, params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    if (apiHandler!=null)
                    {
                        JSONObject JSONResponce=null;
                            try {
                                if(responseBody==null)return;

                                String strJSONResponce=new String(responseBody);
                                JSONResponce=new JSONObject(strJSONResponce);
                                apiHandler.onApiSuccess(JSONResponce);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                    }



                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    if (apiHandler != null) {
                        JSONObject JSONResponce = null;
                        try {
                            String strJSONResponce = new String(responseBody);
                            JSONResponce = new JSONObject(strJSONResponce);
                            if(JSONResponce.getJSONObject("error").getInt("code")==102513)
                            {
                                apiHandler.onApiSuccess(JSONResponce);
                            }
                            apiHandler.onApiFailure(JSONResponce);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }

            });


        }


    }
    public static void getAllSocialPost(final ApiHandler apiHandler){
        extendSession();
        String url="http://infoz.os.a2zapps.com/api/v4/core/chitchat/post/find.json";
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (apiHandler!=null)
                {
                    JSONObject JSONResponce=null;
                    try {
                        if(responseBody==null)return;

                        String strJSONResponce=new String(responseBody);
                        JSONResponce=new JSONObject(strJSONResponce);
                        apiHandler.onApiSuccess(JSONResponce);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                if (apiHandler != null) {
                    JSONObject JSONResponce = null;
                    try {
                        String strJSONResponce = new String(responseBody);
                        JSONResponce = new JSONObject(strJSONResponce);
                        if(JSONResponce.getJSONObject("error").getInt("code")==102513)
                        {
                            apiHandler.onApiSuccess(JSONResponce);
                        }
                        apiHandler.onApiFailure(JSONResponce);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

            }
        });
    }

    private static void extendSession( )
    {
        MySharedPreferences sharedPreferences=new MySharedPreferences();
        sharedPreferences.init(apicontext);

        login(sharedPreferences.getUserName(),sharedPreferences.getUserPassword(),null);
    }

}
