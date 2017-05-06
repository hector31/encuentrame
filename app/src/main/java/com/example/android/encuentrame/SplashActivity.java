package com.example.android.encuentrame;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    private static final long SPLASH_DELAY = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//quita app bar

        setContentView(R.layout.activity_splash2);
        TimerTask task=new TimerTask() {
            @Override
            public void run() {
                Intent i=new Intent().setClass(SplashActivity.this,LoginActivity.class);
                startActivity(i);
                finish();
            }
        };
        Timer timer=new Timer();
        timer.schedule(task,SPLASH_DELAY);


    }

}
