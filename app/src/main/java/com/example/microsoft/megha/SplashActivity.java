package com.example.microsoft.megha;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.microsoft.megha.ui.HomeActivity;

public class SplashActivity extends AppCompatActivity {
    MySharedPreferences mySharedPreferences=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mySharedPreferences=new MySharedPreferences();
        mySharedPreferences.init(this);


        Toast.makeText(getApplicationContext(),"press again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mySharedPreferences.isUserActive())
                {
                    startActivity(new Intent(SplashActivity.this,HomeActivity.class));
                }
                else
                {
                    startActivity(new Intent(SplashActivity.this,LifeCycleActivity.class));
                }
            }

        }, 4000);

    }

}
