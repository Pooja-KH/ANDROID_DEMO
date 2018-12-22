package com.example.microsoft.megha.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/****************************************************
 * Created by Dharam Shinde on 15-12-2018
 * Infoz Solution
 * vivekshinde31121996@gmail.com
 *****************************************************/
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    protected abstract void setWidgetIds();
    protected abstract void prepareData();
    protected abstract void setDataToWidgets();


}

