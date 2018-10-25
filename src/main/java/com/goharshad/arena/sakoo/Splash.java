package com.goharshad.arena.sakoo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import java.lang.ref.WeakReference;

public class Splash extends AppCompatActivity {

//    private final MyHandler handler=new MyHandler(this);
//    private final MyRunnable runnable=new MyRunnable(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PreferencesManager manager = new PreferencesManager(this);
        manager.setAppInForground(true);
        Intent intent = new Intent(this, FirstLaunchActivity.class);
        startActivity(intent);
        finish();
    }
}
