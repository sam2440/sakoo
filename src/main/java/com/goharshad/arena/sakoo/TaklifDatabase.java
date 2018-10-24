package com.goharshad.arena.sakoo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ir.huri.jcal.JalaliCalendar;

/**
 * Created by Home on 8/27/2018.
 */

public class TaklifDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "takalif.db";
    private static final String TABLE_NAME = "takaiftable";
    private static final String COL_WEEK_DAY = "dayOfWeek";
    private static final String COL_LESSON = "lesson";
    private static final String COL_TAKLIF = "taklif";
    private static final String COL_STATUS = "status";
    private static final String COL_STUDY_TIME = "study";
    private static final String COL_DATE = "date";
    private String today;
    private Context context;

    public TaklifDatabase(Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.today = new JalaliCalendar().toString();
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" +
                COL_WEEK_DAY + " TEXT ," +
                COL_LESSON + " TEXT ," +
                COL_TAKLIF + " TEXT ," +
                COL_STATUS + " TEXT ," +
                COL_STUDY_TIME + " INT DEFAULT 0," +
                COL_DATE + " DATE " +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + TABLE_NAME);
        this.onCreate(db);
    }

    /* Only one insert method , for inserting new taklif of day */
    public boolean insert(String weekDay, String lesson, String taklif) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_WEEK_DAY, weekDay);
        values.put(COL_LESSON, lesson);
        values.put(COL_TAKLIF, taklif);
        values.put(COL_STATUS, TaklifStatus.TODAY.toString());
        values.put(COL_DATE, today);
        return database.insert(TABLE_NAME, null, values) != -1;
    }

    /*Multiple update methods
    * For updating current taklif
    * For updating taklif status
    * For updating taklif studytime
    * */

    /* Update taklif */
    public boolean update(String lesson, String taklif) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_TAKLIF, taklif);
        return database.update(TABLE_NAME, values, COL_LESSON + "=? AND " + COL_DATE + "=?"
                , new String[]{lesson, today}) != -1;
    }

    /*Update status on Date change today to delayed , today-done to done*/
    public boolean update(TaklifStatus from, TaklifStatus to) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_STATUS, to.toString());
        return database.update(TABLE_NAME, values, COL_STATUS + "=?", new String[]{from.toString()}) != -1;
    }

    public boolean update1(){
        LessonDatabase lessonDatabase=new LessonDatabase(context);
        int dayAfter=new JalaliCalendar().getTomorrow().getDayOfWeek();
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.query(true, TABLE_NAME,null,COL_STATUS+"=?",new String[]{TaklifStatus.DELAYED.toString()}
                , null, null, null, null);
        while (cursor.moveToNext()){
            if (lessonDatabase.exists(getDayOfWeek(dayAfter),cursor.getString(1))){
                update(cursor.getString(1),cursor.getString(2),TaklifStatus.DELAYED,TaklifStatus.TOMORROWED);
            }
        }
        return true;
    }

    /*Updates taklif status */
    public boolean update(String lesson, String taklif,TaklifStatus from,TaklifStatus status) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_STATUS, status.toString());
        return database.update(TABLE_NAME, values, COL_LESSON + "= ? AND " + COL_TAKLIF + "=? AND " + COL_STATUS + " = ?"
                , new String[]{lesson,taklif,from.toString()}) != -1;
    }

    /*Updates taklif status */
    public boolean update(String lesson, String taklif,TaklifStatus status) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_STATUS, status.toString());
        return database.update(TABLE_NAME, values, COL_LESSON + "= ? AND " + COL_TAKLIF + "=? AND " + COL_STATUS + " != ?"
                , new String[]{lesson, taklif, TaklifStatus.DONE.toString()}) != -1;
    }

    /*Updates Study Time */
    public boolean update(String lesson, String taklif, int studyTime) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_STUDY_TIME, studyTime);
        return database.update(TABLE_NAME, values, COL_LESSON + "=? AND " + COL_TAKLIF + "=? AND " + COL_STATUS + " != ?"
                , new String[]{lesson, taklif,TaklifStatus.DONE.toString()}) != -1;
    }

    public boolean undo(String lesson,String taklif,TaklifStatus from,TaklifStatus to){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_STUDY_TIME,0);
        values.put(COL_STATUS,to.toString());
        return database.update(TABLE_NAME , values ,COL_LESSON + "=? AND "+COL_TAKLIF + "=? AND "+COL_STATUS+" = ?"
                , new String[]{lesson,taklif,from.toString()}) != -1;
    }

    public List<String> lessonsOf(String weekDay, String date) {
        List<String> strings = new ArrayList<>();
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.query(TABLE_NAME, new String[]{COL_LESSON}, COL_WEEK_DAY + " = ? AND " + COL_DATE + "= ?"
                , new String[]{weekDay, date == null ? today : date}, null, null, null);
        while (cursor.moveToNext())
            strings.add(cursor.getString(0));

        return strings;
    }

    public String taklifOf(String lesson) {
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.query(true, TABLE_NAME, new String[]{COL_TAKLIF}
                , COL_DATE + " = ? AND " + COL_LESSON + " =?"
                , new String[]{today, lesson}, null, null, null, null);
        return cursor.moveToNext() ? cursor.getString(0) : "";
    }

    public int getStudyTimeOf(String lesson, String taklif, TaklifStatus status) {
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.query(true, TABLE_NAME, new String[]{COL_STUDY_TIME}
                , COL_DATE + " = ? AND " + COL_LESSON + " =? AND " + COL_TAKLIF + " = ? AND " + COL_STATUS + " =?"
                , new String[]{today, lesson, taklif, status.toString()}, null, null, null, null);
        if (cursor.moveToNext())
            return cursor.getInt(0);
        return 0;
    }

    public List<DailyReportObject> getDailyReports(){
        List<DailyReportObject> list=new ArrayList<>();
        SQLiteDatabase database=this.getWritableDatabase();
        Cursor cursor=database.query(true,TABLE_NAME,new String[]{COL_LESSON,COL_TAKLIF,COL_STUDY_TIME},
                COL_DATE+"=?",new String[]{today},null,null,null,null);
        if (cursor.moveToNext()){
            list.add(new DailyReportObject(cursor.getString(0),cursor.getString(1),cursor.getInt(2)));
        }
        return list;
    }

    public boolean hasTaklifOfLesson(String lesson) {
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.query(TABLE_NAME, null, COL_DATE + "=? AND " + COL_LESSON + "=?", new String[]{today, lesson}, null, null, null, null);
//        Log.d("TAG",""+cursor.moveToNext());
//        Log.d("TAG","heeeeeeeeee : "+cursor.getString(3));
        return cursor.moveToNext();
    }

    public Map<String, String> getTaklifWithStatus(TaklifStatus status) {
        Map<String, String> takalif = new HashMap<>();
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.query(true, TABLE_NAME, new String[]{COL_LESSON, COL_TAKLIF}
                , COL_STATUS + "= ?"
                , new String[]{status.toString()}, null, null, null, null);
        while (cursor.moveToNext()) {
            takalif.put(cursor.getString(0), cursor.getString(1));
        }
        return takalif;
    }

    private String getDayOfWeek(int weekDay) {
        switch (weekDay) {
            case 1:
                return context.getResources().getString(R.string.weekday_saturday);
            case 2:
                return context.getResources().getString(R.string.weekday_sunday);
            case 3:
                return context.getResources().getString(R.string.weekday_monday);
            case 4:
                return context.getResources().getString(R.string.weekday_tuesday);
            case 5:
                return context.getResources().getString(R.string.weekday_wednesday);
            case 6:
                return context.getResources().getString(R.string.weekday_thursday);
            case 7:
                return context.getResources().getString(R.string.weekday_friday);
            default:
                return null;
        }
    }

    public boolean deleteAll() {
        SQLiteDatabase database = this.getWritableDatabase();
        return database.delete(TABLE_NAME, "1", null) != -1;
    }

    public String map() {
        StringBuilder builder = new StringBuilder();
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.query(TABLE_NAME, null, "1", null, null, null, null);
        Log.d("TAG", "**********************");
        builder.append("**********************");
        while (cursor.moveToNext()) {
            builder.append("|| " + cursor.getString(0) + " , " + cursor.getString(1) + " , " + cursor.getString(2) + " , " + cursor.getString(3) + " , " + cursor.getInt(4) + " , " + cursor.getString(5) + "||");
        }
        builder.append("**********************");
        return builder.toString();
    }

    public boolean isDone(String lesson, String taklif) {
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.query(true, TABLE_NAME, new String[]{COL_STATUS}, COL_DATE + "=? AND " + COL_LESSON + "=? AND " + COL_TAKLIF + "=?"
                , new String[]{today, lesson, taklif}, null, null, null, null);
        if (cursor.moveToNext()) {
            if (cursor.getString(0).equals(TaklifStatus.TODAY_DONE.toString())
            || cursor.getString(0).equals(TaklifStatus.DELAYED_DONE.toString())
            || cursor.getString(0).equals(TaklifStatus.TOMORROWED_DONE.toString())
                    || cursor.getString(0).equals(TaklifStatus.DONE.toString())) {
                return true;
            }
        }
        return false;
    }
}
