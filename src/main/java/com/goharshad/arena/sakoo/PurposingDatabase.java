package com.goharshad.arena.sakoo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Home on 8/31/2018.
 */

public class PurposingDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "purposingggg.db";
    private static final String TABLE_NAME = "purposetableeee";
    private static final String COL_WEEK_DAY = "dayOfWeek";
    private static final String COL_AVAILABLE = "available";
    private static final String COL_SUGGESTED = "suggested";
    private static final String COL_YOUR_SUGGESTED = "yoursuggested";

    public PurposingDatabase(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" +
                COL_WEEK_DAY + " TEXT ," +
                COL_AVAILABLE + " INT ," +
                COL_SUGGESTED + " INT ," +
                COL_YOUR_SUGGESTED + " INT " +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + TABLE_NAME);
        this.onCreate(db);
    }

    public boolean insert(String weekDay, int available, int suggested, int yourSuggested) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_WEEK_DAY, weekDay);
        values.put(COL_AVAILABLE, available);
        values.put(COL_SUGGESTED, suggested);
        values.put(COL_YOUR_SUGGESTED, yourSuggested);
        return database.insert(TABLE_NAME, null, values) != -1;
    }

    public boolean update(String weekDay,int available,int suggested,int yourSuggested){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_AVAILABLE, available);
        values.put(COL_SUGGESTED, suggested);
        values.put(COL_YOUR_SUGGESTED, yourSuggested);
        return database.update(TABLE_NAME, values, COL_WEEK_DAY + "=?",new String[]{weekDay}) != -1;
    }

    public boolean isNull() {
        Cursor cursor = this.getWritableDatabase().query(TABLE_NAME,null,null,null,null,null,null);
        return cursor.moveToNext();
    }

    public Cursor getTimesOf(String weekDay) {
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.query(true, TABLE_NAME, new String[]{COL_AVAILABLE, COL_SUGGESTED, COL_YOUR_SUGGESTED}
                , COL_WEEK_DAY + "=?", new String[]{weekDay}, null, null, null, null);
        return cursor;
    }

    public int getTimeOf(String weekDay) {
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.query(true, TABLE_NAME, new String[]{COL_SUGGESTED, COL_YOUR_SUGGESTED}
                , COL_WEEK_DAY + "=?", new String[]{weekDay}, null, null, null, null);

        if (cursor.moveToNext())
            return cursor.getInt(1) != 0 ? cursor.getInt(1) : cursor.getInt(0);
        else
            return 0;
    }

    public int getSuggestedOf(String weekDay){
        SQLiteDatabase database=this.getWritableDatabase();
        Cursor cursor=database.query(true,TABLE_NAME,new String[]{COL_AVAILABLE},COL_WEEK_DAY+"=?"
                ,new String[]{weekDay},null,null,null,null);
        if (cursor.moveToNext())
            return cursor.getInt(0);
        return 0;
    }

    public void map() {
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.query(TABLE_NAME, null, "1", null, null, null, null);
        Log.d("TAG", "**********************");
        while (cursor.moveToNext()) {
            Log.d("TAG", "|| " + cursor.getString(0) + " , " + cursor.getInt(1) + " , " + cursor.getInt(2) + cursor.getInt(3) + " ||");
        }
        Log.d("TAG", "**********************");
    }

}
