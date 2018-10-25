package com.goharshad.arena.sakoo;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

public class PreferencesManager {
    private static final String PREF_NAME = "preffirstlaunch";
    private static final String IS_FIRST_LAUNCH = "firstlaunch";
    private static final String IS_APP_IN_FORGROUND= "isappinforground";
    private static final String IS_CHECKED_INFO_DIALOG= "checkedinfodialog";
    private static final String IS_CHECKED_ENTEKHAB_DARS_DIALOG= "checkedentekhabedarsdialog";
    private static final String IS_SEEN_PURPOSING_DIALOG= "isseenpurposingdialog";
    private static final String IS_ENTERED_SCHEDULE = "isenteredschedule";
    private static final String REMOVE_GOZARESH_AZMOON = "removegozareshazmoon";
    private static final String GOZARESH_AZMOON_IS_CHECKED_INFO_DIALOG= "gozareshazmooncheckedinfodialog";
    private static final String IS_FILLED_PURPOSES= "isfilledpurposes";
    private static final String CURRENT_STUDY_TIME= "currentstudytime";
    private static final String STUDENT_GRADE= "studentgrade";
    private static final String STUDENT_GRADE_POS= "studentgradepos";
    private static final String INSTALLATION_WEEK_NUMBER="installationweeknumber";
    private static final String INSTALLATION_MONTH_NUMBER="installationmonthnumber";
    private static final String BAZDEHI="bazdehi";
    private static final String WEEKLY_SUM="weeklysum";
    private static final String MONTHLY_AVG="monthlyavg";
    private static final String DATE="daaate";
    public static final String SAVED_WEEKLY_DATE="saveddateweekly";
    public static final String SAVED_MONTHLY_DATE="saveddatemonthly";
    private static final String ENTERED_LESSONS_SET="enteredlessonsset";
    private static final String EXIT_TO_ANSWERSHEET="exittoanswersheet";
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Context context;

    public PreferencesManager(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(PREF_NAME, context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public void setSeenPurposingDialog(boolean isSeen) {
        editor.putBoolean(IS_SEEN_PURPOSING_DIALOG, isSeen);
        editor.apply();
    }

    public boolean isSeenPurposingDialog() {
        return preferences.getBoolean(IS_SEEN_PURPOSING_DIALOG, false);
    }

    public void setFirstLaunch(boolean isFirst) {
        editor.putBoolean(IS_FIRST_LAUNCH, isFirst);
        editor.apply();
    }

    public boolean isFirstLaunch() {
        return preferences.getBoolean(IS_FIRST_LAUNCH, true);
    }

    public void setAppInForground(boolean isFirst) {
        editor.putBoolean(IS_APP_IN_FORGROUND, isFirst);
        editor.apply();
    }

    public boolean isAppInForground() {
        return preferences.getBoolean(IS_APP_IN_FORGROUND, false);
    }


    public void setIsEnteredSchedule(boolean isEnteredSchedule) {
        editor.putBoolean(IS_ENTERED_SCHEDULE, isEnteredSchedule);
        editor.apply();
    }

    public boolean isEnteredSchedule() {
        return preferences.getBoolean(IS_ENTERED_SCHEDULE, false);
    }

    public void setIsCheckedInfoDialog(boolean isCheckedInfoDialog) {
        editor.putBoolean(IS_CHECKED_INFO_DIALOG, isCheckedInfoDialog);
        editor.apply();
    }

    public boolean isCheckedInfoDialog() {
        return preferences.getBoolean(IS_CHECKED_INFO_DIALOG,false);
    }

    public void setGozareshAzmoonIsCheckedInfoDialog(boolean isCheckedInfoDialog) {
        editor.putBoolean(GOZARESH_AZMOON_IS_CHECKED_INFO_DIALOG, isCheckedInfoDialog);
        editor.apply();
    }

    public boolean gozareshAzmoonIsCheckedInfoDialog() {
        return preferences.getBoolean(GOZARESH_AZMOON_IS_CHECKED_INFO_DIALOG,false);
    }

    public void setIsCheckedEntekhabDarsialog(boolean isCheckedEntekhabDarsialog) {
        editor.putBoolean(IS_CHECKED_ENTEKHAB_DARS_DIALOG, isCheckedEntekhabDarsialog);
        editor.apply();
    }

    public boolean isCheckedEntekhabdeDarsDialog() {
        return preferences.getBoolean(IS_CHECKED_ENTEKHAB_DARS_DIALOG,false);
    }

    public void setCurrentStudyTime(int time){
        editor.putInt(CURRENT_STUDY_TIME,time);
        editor.apply();
    }

    public int getCurrentStudyTime(){
        return preferences.getInt(CURRENT_STUDY_TIME,0);
    }

    public boolean isFilledPurposes() {
        return preferences.getBoolean(IS_FILLED_PURPOSES,false);
    }

    public void setIsFilledPurposes(boolean isFilledPurposes) {
        editor.putBoolean(IS_FILLED_PURPOSES, isFilledPurposes);
        editor.apply();
    }

    public void setStudentGrade(String grade) {
        editor.putString(STUDENT_GRADE, grade);
        editor.apply();
    }

    public String getStudentGrade() {
        return preferences.getString(STUDENT_GRADE,null);
    }

    public void setStudentGradePos(int gradePoa) {
        editor.putInt(STUDENT_GRADE_POS, gradePoa);
        editor.apply();
    }

    public int getStudentGradePos() {
        return preferences.getInt(STUDENT_GRADE_POS,-1);
    }

    public void setInstallationWeekNumber(int weekNumber) {
        editor.putInt(INSTALLATION_WEEK_NUMBER, weekNumber);
        editor.apply();
    }

    public int getInstallationWeekNumber() {
        return preferences.getInt(INSTALLATION_WEEK_NUMBER,1);
    }

    public void setBazdehi(float bazdehi) {
        editor.putFloat(BAZDEHI,bazdehi);
        editor.apply();
    }

    public float getBazdehi() {
        return preferences.getFloat(BAZDEHI,0);
    }


    public void setWeeklySum(int avg) {
        editor.putInt(WEEKLY_SUM, avg);
        editor.apply();
    }

    public int getWeeklySum() {
        return preferences.getInt(WEEKLY_SUM,0);
    }

    public void setMonthlyAvg(float avg) {
        editor.putFloat(MONTHLY_AVG, avg);
        editor.apply();
    }

    public float getMonthlyAvg() {
        return preferences.getFloat(MONTHLY_AVG,-1);
    }

    public void setDate(long timeMillies) {
        editor.putLong(DATE,timeMillies);
        editor.apply();
    }

    public long getDate() {
        return preferences.getLong(DATE,0);
    }


    public void setSavedWeeklyDate(long timeMillies) {
        editor.putLong(SAVED_WEEKLY_DATE,timeMillies);
        editor.apply();
    }

    public long getSavedWeeklyDate() {
        return preferences.getLong(SAVED_WEEKLY_DATE,0);
    }

    public void setSavedMonthlyDate(long timeMillies) {
        editor.putLong(SAVED_MONTHLY_DATE,timeMillies);
        editor.apply();
    }

    public long getSavedMonthlyDate() {
        return preferences.getLong(SAVED_MONTHLY_DATE,0);
    }

    public void setEnteredLessons(Set<String> lessons){
        editor.putStringSet(ENTERED_LESSONS_SET,lessons);
        editor.apply();
    }

    public Set<String> getEnteredLessons(){
        return preferences.getStringSet(ENTERED_LESSONS_SET,null);
    }

    public void setExitToAnswersheet(boolean b){
        editor.putBoolean(EXIT_TO_ANSWERSHEET,b);
        editor.apply();
    }

    public boolean getExitToAnswersheet(){
        return preferences.getBoolean(EXIT_TO_ANSWERSHEET,false);
    }


}