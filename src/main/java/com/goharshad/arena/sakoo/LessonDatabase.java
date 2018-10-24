package com.goharshad.arena.sakoo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Home on 9/5/2018.
 */

public class LessonDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "lessons.db";
    private static final String TABLE_NAME = "lessonstable";
    private static final String COL_WEEK_DAY = "dayOfWeek";
    private static final String COL_LESSON = "lesson";
    private static final String COL_PRIORITY_INDEX="lessonindex";


    public LessonDatabase(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" +
                COL_WEEK_DAY + " TEXT ," +
                COL_LESSON + " TEXT ," +
                COL_PRIORITY_INDEX + " INTEGER" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + TABLE_NAME);
        this.onCreate(db);
    }

    public boolean insert(String weekDay ,int index , String lesson) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_WEEK_DAY, weekDay);
        values.put(COL_LESSON, lesson);
        values.put(COL_PRIORITY_INDEX, index);
        return database.insert(TABLE_NAME, null, values) != -1;
    }

    public boolean update(String weekDay ,int index , String lesson){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_LESSON, lesson);
        return database.update(TABLE_NAME,values,COL_WEEK_DAY+"=? AND "+COL_PRIORITY_INDEX+"=?"
                ,new String[]{weekDay,String.valueOf(index)}) != -1;
    }

    public boolean exists(String weekDay ,int index , String lesson){
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor=database.query(true,TABLE_NAME,null,COL_WEEK_DAY+" =? AND "+COL_PRIORITY_INDEX+" =? AND "+COL_LESSON+"=? "
                ,new String[]{weekDay,String.valueOf(index),lesson},null,null,null,null);
        return cursor.moveToNext();
    }

    public boolean exists(String weekDay ,int index){
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor=database.query(true,TABLE_NAME,null,COL_WEEK_DAY+" =? AND "+COL_PRIORITY_INDEX+" =?"
                ,new String[]{weekDay,String.valueOf(index)},null,null,null,null);
        return cursor.moveToNext();
    }

    public boolean exists(String weekDay,String lesson){
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor=database.query(true,TABLE_NAME,null,COL_WEEK_DAY+"=? AND "+COL_LESSON+"=?",new String[]{weekDay,lesson},null,null,null,null);
        if (cursor.moveToNext()){
            return true;
        }
        return false;
    }

    public String getLessonOf(String weekDay ,int index){
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor=database.query(true,TABLE_NAME,new String[]{COL_LESSON},COL_WEEK_DAY+" =? AND "+COL_PRIORITY_INDEX+" =?"
                ,new String[]{weekDay,String.valueOf(index)},null,null,null,null);
        if (cursor.moveToNext())
            return cursor.getString(0);
        return null;
    }

    public boolean delete(String weekDay,int index){
        SQLiteDatabase database = this.getWritableDatabase();
        return database.delete(TABLE_NAME,COL_WEEK_DAY+"=? AND "+COL_PRIORITY_INDEX+"= ?"
                ,new String[]{weekDay,String.valueOf(index)}) != -1;

    }

    public boolean hasTaklif(String weekDay) {
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.query(TABLE_NAME,null,COL_WEEK_DAY + "=?"
                , new String[]{weekDay}, null, null, null, null);
        return cursor.moveToNext();
    }


    public boolean deleteAll(){
        SQLiteDatabase database = this.getWritableDatabase();
        return database.delete(TABLE_NAME,"1",null) != -1;
    }

    public String getScheduleOf(String weekDay){
        int start=1;
        Log.d("TAG","weekday heeeeeeeeeeeeeee : "+weekDay);
        StringBuilder stringBuilder=new StringBuilder();
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.query(true,TABLE_NAME,new String[]{COL_PRIORITY_INDEX,COL_LESSON},COL_WEEK_DAY + "= ? "
                , new String[]{weekDay}, null,null,COL_PRIORITY_INDEX+" ASC ",null);
        while (cursor.moveToNext()){
            Log.d("TAG","weekday,index,lesson : "+weekDay+","+cursor.getInt(0)+","+cursor.getString(1));
            for (int i = start; i < cursor.getInt(0); i++) {
                stringBuilder.append((i!=1 ? "," : "")+" _ ");
            }
            stringBuilder.append((start!=1 ? "," : "")+" "+cursor.getString(1)+" ");
            start = cursor.getInt(0)+1;
        }
        for (int i=start; i<=6; i++) {
            stringBuilder.append((i!=1 ? "," : "")+" _ ");
        }
        return stringBuilder.toString();
    }

    public String map() {
        StringBuilder builder=new StringBuilder();
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.query(TABLE_NAME, null, "1", null, null, null, null);
        Log.d("TAG", "**********************");
        builder.append("**********************");
        while (cursor.moveToNext()) {
            builder.append("|| " + cursor.getString(0) + " , " + cursor.getString(1) + " , "+ cursor.getInt(2)+"\n");
            Log.d("TAG","|| " + cursor.getString(0) + " , " + cursor.getString(1) + " , "+ cursor.getInt(2)+"\n");
        }
        builder.append("**********************");
        return builder.toString();
    }

    public Cursor all(){
        SQLiteDatabase database=this.getWritableDatabase();
        Cursor cursor=database.query(TABLE_NAME,null,null,null,null,null,null,null);
        return cursor;
    }

    public List<String> lessons() {
        SQLiteDatabase database=this.getWritableDatabase();
        List<String> strings=new ArrayList<>();
        Cursor cursor=database.query(true,TABLE_NAME,new String[]{COL_LESSON},null,null,null,null,null,null);
        while (cursor.moveToNext())
            strings.add(cursor.getString(0));
        return strings;
    }

    public List<String> lessonsOf(String dayOfWeek) {
        SQLiteDatabase database=this.getWritableDatabase();
        List<String> strings=new ArrayList<>();
        Cursor cursor=database.query(true,TABLE_NAME,new String[]{COL_LESSON},COL_WEEK_DAY+"=?"
                ,new String[]{dayOfWeek},null,null,null,null);
        while (cursor.moveToNext())
            strings.add(cursor.getString(0));
        return strings;
    }
}
