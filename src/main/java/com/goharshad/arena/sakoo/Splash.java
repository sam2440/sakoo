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

        Intent intent = new Intent(this, FirstLaunchActivity.class);
        startActivity(intent);
        finish();

        //        setContentView(R.layout.activity_splash);
//        getSupportActionBar().hide();
//        handler.postDelayed(runnable,3550);
//        PreferencesManager manager=new PreferencesManager(this);
//        manager.setAppInForground(true);
    }

//    private static class MyHandler extends Handler {
//
//        private final WeakReference<Splash> mRefrence;
//
//        private MyHandler(Splash splash) {
//            this.mRefrence = new WeakReference<Splash>(splash);
//        }
//    }
//
//    private static class MyRunnable implements Runnable{
//
//        private Activity activity;
//
//        public MyRunnable(Activity activity) {
//            this.activity = activity;
//        }
//
//        @Override
//        public void run() {
//
//        }
//    }
//
//    @Override
//    public void onBackPressed() {
//        android.os.Process.killProcess(android.os.Process.myPid());
//    }
}
