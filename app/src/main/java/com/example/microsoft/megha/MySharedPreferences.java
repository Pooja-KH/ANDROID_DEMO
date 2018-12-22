package com.example.microsoft.megha;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;

import com.example.microsoft.megha.model.LoginResponse;

public class MySharedPreferences {

    private static SharedPreferences sharedPreferences = null;
    Context context = null;
    final String PRE_SHARED_RESOURCE_NAME = "infoz_shared_resources";
    final String KEY_IS_USER_ACTIVE = "is_user_active";
    final String KEY_FIRST_NAME = "firstname";
    final String KEY_LAST_NAME = "lastname";
    final String KEY_URL_NAME = "url";
    final String KEY_EMAIL_NAME = "email";
    final String KEY_OID = "oid";
    final String KEY_ORGNAME_NAME = "orgname";

    final String KEY_USER_NAME = "username";
    final String KEY_USER_PASSWORD = "password";


    public MySharedPreferences() {

    }

    public void init(Context ctx) {
        try {
            context = ctx.createPackageContext("com.example.microsoft.megha", 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();

        }
        if (context == null) {
            context = ctx;

        }
        sharedPreferences = context.getSharedPreferences("PRE_SHARED_RESOURCE_NAME", Context.MODE_PRIVATE);

    }

    public void setUserActive(boolean isActive) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_IS_USER_ACTIVE, isActive);
        editor.apply();

    }

    public boolean isUserActive() {
        return sharedPreferences.getBoolean(KEY_IS_USER_ACTIVE, false);

    }

    public void saveUserProfile(LoginResponse loginResponseModel) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_FIRST_NAME, loginResponseModel.getFirstname());
        editor.putString(KEY_LAST_NAME, loginResponseModel.getLastname());
        editor.putString(KEY_EMAIL_NAME, loginResponseModel.getEmail());
        editor.putString(KEY_URL_NAME, loginResponseModel.getUrl());
        editor.putString(KEY_OID, loginResponseModel.getOid());
        editor.putString(KEY_ORGNAME_NAME, loginResponseModel.getOrgname());
        editor.apply();


    }

    public LoginResponse getUserProfile() {
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setFirstname(sharedPreferences.getString(KEY_FIRST_NAME, ""));
        loginResponse.setLastname(sharedPreferences.getString(KEY_LAST_NAME, ""));
        loginResponse.setEmail(sharedPreferences.getString(KEY_EMAIL_NAME, ""));
        loginResponse.setUrl(sharedPreferences.getString(KEY_URL_NAME, ""));
        loginResponse.setOid(sharedPreferences.getString(KEY_OID, ""));
        loginResponse.setOrgname(sharedPreferences.getString(KEY_ORGNAME_NAME, ""));

        return loginResponse;
    }

    public void saveUserName(String username) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USER_NAME, username);
        editor.apply();
    }

    public void savePassword(String password) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USER_PASSWORD, password);
        editor.apply();
    }

    public String getUserName() {
        return sharedPreferences.getString(KEY_USER_NAME, "");

    }

    public String getUserPassword() {
        return sharedPreferences.getString(KEY_USER_PASSWORD, "");
    }

}
