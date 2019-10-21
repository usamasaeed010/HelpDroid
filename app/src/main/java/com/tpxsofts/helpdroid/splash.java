package com.tpxsofts.helpdroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Timer().schedule(
                new TimerTask(){

                    @Override
                    public void run(){
finish();
startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    }

                }, 3000);
}}
