package com.example.microsoft.megha.ui;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.microsoft.megha.ApiHandler;
import com.example.microsoft.megha.LifeCycleActivity;
import com.example.microsoft.megha.MySharedPreferences;
import com.example.microsoft.megha.R;
import com.example.microsoft.megha.RestApi;
import com.example.microsoft.megha.common.BaseActivity;
import com.example.microsoft.megha.model.LoginResponse;
import com.example.microsoft.megha.model.PostModel;
import com.example.microsoft.megha.ui.adapter.SocialPostAdapter;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends BaseActivity {
    //Data variables
    LoginResponse loginResponse = null;
    MySharedPreferences mySharedPreferences = null;
    //UI widgets
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    View drawerContentLayout, layoutToolBar;
    Toolbar toolbar;
    RecyclerView socialPostRecyclerView;

    ImageView navDrawerImageview, orgLogoImageView;
    TextView usernameTextView, userEmailTextView, oidTextview, orgNameTextview;


    List<PostModel> postModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mySharedPreferences = new MySharedPreferences();
        mySharedPreferences.init(this);
        setWidgetIds();


    }

    @Override
    protected void setWidgetIds() {
        if (!mySharedPreferences.isUserActive()) {
            startActivity(new Intent(this, LifeCycleActivity.class));
            return;
        }


        drawerContentLayout = findViewById(R.id.drawerContentLayout);
        usernameTextView = drawerContentLayout.findViewById(R.id.usernameTextView);
        userEmailTextView = drawerContentLayout.findViewById(R.id.userEmailTextview);
        oidTextview = drawerContentLayout.findViewById(R.id.oidTextview);
        orgNameTextview = drawerContentLayout.findViewById(R.id.orgNameTextview);
        orgLogoImageView = drawerContentLayout.findViewById(R.id.orgLogoImageView);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        navigationView = (NavigationView) findViewById(R.id.navigationview);
        layoutToolBar = findViewById(R.id.layoutToolBar);
        navDrawerImageview = layoutToolBar.findViewById(R.id.navDrawerImageview);
        toolbar = layoutToolBar.findViewById(R.id.toolbar);

        socialPostRecyclerView = findViewById(R.id.socialPostRecyclerView);

        setSupportActionBar(toolbar);

        navDrawerImageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(Gravity.START);

            }
        });

        prepareData();
    }

    @Override
    protected void prepareData() {
        loginResponse = mySharedPreferences.getUserProfile();
        RestApi.initialize(this);
        RestApi.getAllSocialPost(new ApiHandler() {
            @Override
            public void onApiSuccess(JSONObject successResponse) {

                postModelList = new ArrayList<PostModel>();
                PostModel objPostModel = null;
                try {
                    JSONArray objJsonArray = successResponse.getJSONObject("page-result").getJSONObject("records").getJSONArray("record");
                    objPostModel = null;
                    JSONObject postItemJsonObject;
                    for (int i = 0; i < objJsonArray.length(); i++) {
                        objPostModel = new PostModel();
                        postItemJsonObject = objJsonArray.getJSONObject(i);
                        objPostModel.setFirstName(postItemJsonObject.getJSONObject("posted_by").getString("name1"));
                        objPostModel.setLastName(postItemJsonObject.getJSONObject("posted_by").getString("name2"));
                        objPostModel.setPost(postItemJsonObject.getString("message"));
                        objPostModel.setImgUrl("http://infoz.os.a2zapps.com/api/v4/core/chitchat/user/profilephoto?id=" + postItemJsonObject.getJSONObject("posted_by").getString("gid"));
                        postModelList.add(objPostModel);

                    }

                    setDataToWidgets();

                } catch (JSONException e)
                {
                    e.printStackTrace();
                }


            }

            @Override
            public void onApiFailure(JSONObject failureResponse)
            {
                Log.d("TAG", "onApiFailure: "+failureResponse.toString());

            }
        });
    }

    @Override
    protected void setDataToWidgets() {
        usernameTextView.setText(loginResponse.getFirstname().toString());
        userEmailTextView.setText(loginResponse.getEmail().toString());
        oidTextview.setText(loginResponse.getOid().toString());
        orgNameTextview.setText(loginResponse.getOrgname().toString());
        String imageUri = "http://os.a2zapps.com/api/v4/core/common/orglogo?oid=760";
        Picasso.get().load(imageUri).fit().centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher_round)
                .into(orgLogoImageView);

        SocialPostAdapter socialPostAdapter = new SocialPostAdapter(this, postModelList);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        socialPostRecyclerView.setLayoutManager(mLayoutManager);

        socialPostRecyclerView.setAdapter(socialPostAdapter);
    }
}
