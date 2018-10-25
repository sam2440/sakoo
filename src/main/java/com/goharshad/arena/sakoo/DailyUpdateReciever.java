package com.goharshad.arena.sakoo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;

public class DailyUpdateReciever extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
//
//        MainActivity mainActivity = (MainActivity) context;
//        PreferencesManager preferencesManager = new PreferencesManager(context);
//        if ((System.currentTimeMillis() - preferencesManager.getDate()) / (60 * 1000) == 4) {
//
//            preferencesManager.setDate(System.currentTimeMillis());
//            if (mainActivity.getAnimatableProgressBar() != null) {
//                mainActivity.getAnimatableProgressBar().setProgressWithAnimation(0);
//            }
//
//            TextView tv;
//            if ((tv = mainActivity.getInProgressTime()) != null) {
//                mainActivity.animateIntValue(preferencesManager.getCurrentStudyTime(), 0, tv);
//            }
//            preferencesManager.setCurrentStudyTime(0);
//
//            if ((tv = mainActivity.getMajmooHaftegiTxt()) != null) {
//                mainActivity.animateIntValue(preferencesManager.getWeeklySum(), 0, tv);
//            }
//            preferencesManager.setSavedWeeklyDate((int) System.currentTimeMillis());
//            preferencesManager.setWeeklySum(0);
//
//            if (preferencesManager.isFilledPurposes() && (tv = mainActivity.getBazdehiTxt()) != null) {
//                mainActivity.animateFloatValue(preferencesManager.getBazdehi(),0, tv);
//            }
//            preferencesManager.setBazdehi(0);
//
//            TaklifDatabase database1 = new TaklifDatabase(context);
//            database1.update(TaklifStatus.TODAY_DONE, TaklifStatus.DONE);
//            database1.update(TaklifStatus.DELAYED_DONE, TaklifStatus.DONE);
//            database1.update(TaklifStatus.TOMORROWED_DONE, TaklifStatus.DONE);
//            database1.update(TaklifStatus.TODAY, TaklifStatus.DELAYED);
//            database1.update1();
//
//            if (preferencesManager.isEnteredSchedule()) {
//                if (mainActivity != null) {
//                    mainActivity = (MainActivity) context;
//                    mainActivity.updateTodayTaklif();
//                    mainActivity.updateTomorrowTaklif();
//                    mainActivity.updateDelayedTaklif();
//                }
//            }
//
//            Toast.makeText(mainActivity, "up to 4 minutes", Toast.LENGTH_SHORT).show();
//        }
//
//        if ((System.currentTimeMillis() - preferencesManager.getSavedWeeklyDate()) / (60 * 1000) == 4) {
//            TextView tv;
//        }
    }
}
