package com.goharshad.arena.sakoo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Home on 9/17/2018.
 */

public class BazdehiUpdateReciever extends BroadcastReceiver {

    private OnBazdehiUpdateListener listener;

    public BazdehiUpdateReciever(OnBazdehiUpdateListener listener) {
        this.listener = listener;
    }

    public BazdehiUpdateReciever() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        listener.onBazdehiUpdate();
    }
}
