package com.example.ass_app;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2500);
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}