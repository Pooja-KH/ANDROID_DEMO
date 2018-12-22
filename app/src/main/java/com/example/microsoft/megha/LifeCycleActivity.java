package com.example.microsoft.megha;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.microsoft.megha.model.LoginResponse;
import com.example.microsoft.megha.ui.HomeActivity;
import com.squareup.picasso.Picasso;;import org.json.JSONException;
import org.json.JSONObject;

public class LifeCycleActivity extends AppCompatActivity {

    EditText userNameEditText, passwordEditText;
    Button loginButton;
    boolean isBackPressed = false;
    MaterialDialog materialDialog = null;
    MySharedPreferences mySharedPreferences=null;
    String imageUri = "https://os.a2zapps.com/api/v4/core/common/orglogo?oid=759";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userNameEditText = findViewById(R.id.userNameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        ImageView ivBasicImage = (ImageView) findViewById(R.id.imageView);
        loginButton = findViewById(R.id.buttonsignin);
        mySharedPreferences=new MySharedPreferences();
        mySharedPreferences.init(this);

        Picasso.get().load(imageUri).fit().centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher_round)
                .into(ivBasicImage);


        materialDialog = new MaterialDialog.Builder(this).title("confirm").content("You Want to Exit").
                positiveText("Exit").negativeText("Cancle").onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                finishAffinity();

            }
        }).onNegative(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                dialog.dismiss();

            }
        }).build();


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RestApi.initialize(LifeCycleActivity.this);


                String username=userNameEditText.getText().toString();
                String password=passwordEditText.getText().toString();


                mySharedPreferences.saveUserName(username);
                mySharedPreferences.savePassword(password);

                RestApi.login(username,password, new ApiHandler() {
                    @Override
                    public void onApiSuccess(JSONObject successResponse) {
                        Toast.makeText(getApplicationContext(),"login success",  Toast.LENGTH_LONG).show();

                        mySharedPreferences.setUserActive(true);
                        LoginResponse loginResponseModel=new LoginResponse();
                        try
                        {
                            loginResponseModel.setEmail(successResponse.getJSONObject("login-result").getJSONObject("user").getString("email"));
                            loginResponseModel.setFirstname(successResponse.getJSONObject("login-result").getJSONObject("user").getString("name1"));
                            loginResponseModel.setLastname(successResponse.getJSONObject("login-result").getJSONObject("user").getString("name2"));
                            loginResponseModel.setOid(String.valueOf(successResponse.getJSONObject("login-result").getJSONObject("org").getInt("oid")));
                            loginResponseModel.setOrgname(successResponse.getJSONObject("login-result").getJSONObject("org").getString("org_name"));
                            loginResponseModel.setUrl("http://os.a2zapps.com/api/v4/core/common/orglogo?oid=760");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        mySharedPreferences.saveUserProfile(loginResponseModel);

                        Intent intent=new Intent(getApplicationContext(),HomeActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onApiFailure(JSONObject failureResponse) {
                        Toast.makeText(getApplicationContext(),"login failure",  Toast.LENGTH_LONG).show();


                    }
                });



//                if ((sysUserName.equals(userNameEditText.getText().toString())) && (sysPassword.equals(passwordEditText.getText().toString()))) {
//
//                    Intent intent = new Intent(LifeCycleActivity.this, MainActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(intent);
//
//
//                } else {
//                    Toast.makeText(getApplicationContext(), "Invalid msg", Toast.LENGTH_SHORT).show();
//
//
//                }

            }
        });
    }

    @Override
    protected void onStart() {
        ;
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        materialDialog.show();




/*
        if (isBackPressed)
        {
            finishAffinity();
        }
      isBackPressed=true;
        Toast.makeText(getApplicationContext(),"press again to exit",Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                isBackPressed = false;
            }

            }, 2000);*/
    }

}
