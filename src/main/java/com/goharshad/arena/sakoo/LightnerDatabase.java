package com.goharshad.arena.sakoo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.io.Serializable;

import ir.huri.jcal.JalaliCalendar;

public class LightnerDatabase extends SQLiteOpenHelper implements Serializable{

    private final static String COL_ID="id";
    private final static String COL_WORD="laytner_word";
    private final static String COL_MEANING="laytner_meaning";
    private final static String COL_DATE="laytner_date";
    private final static String COL_TOTAL_WORDS_NUM_TABLE_EVERYDAY="laytner_total_words_num";
    private String today;

    public LightnerDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.today = new JalaliCalendar().toString();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE lay_everyday ("+COL_ID+" INT AUTO_INCREMENT ,"+
                " "+COL_WORD + " TEXT ,"+
                " "+COL_MEANING + " TEXT ,"+
                " "+COL_DATE+" TEXT"+")");
        db.execSQL("CREATE TABLE lay_every2day ("+COL_ID+" INT AUTO_INCREMENT ,"+
                " "+COL_WORD + " TEXT ,"+
                " "+COL_MEANING + " TEXT ,"+
                " "+COL_DATE+" TEXT"+")");
        db.execSQL("CREATE TABLE lay_every4day ("+COL_ID+" INT AUTO_INCREMENT ,"+
                " "+COL_WORD + " TEXT ,"+
                " "+COL_MEANING + " TEXT ,"+
                " "+COL_DATE+" TEXT"+")");
        db.execSQL("CREATE TABLE lay_every8day ("+COL_ID+" INT AUTO_INCREMENT ,"+
                " "+COL_WORD + " TEXT ,"+
                " "+COL_MEANING + " TEXT ,"+
                " "+COL_DATE+" TEXT"+")");
        db.execSQL("CREATE TABLE lay_every16day ("+COL_ID+" INT AUTO_INCREMENT ,"+
                " "+COL_WORD + " TEXT ,"+
                " "+COL_MEANING + " TEXT ,"+
                " "+COL_DATE+" TEXT"+")");
        db.execSQL("CREATE TABLE lay_baygani ("+COL_ID+" INT AUTO_INCREMENT ,"+
                " "+COL_WORD + " TEXT ,"+
                " "+COL_MEANING + " TEXT ,"+
                " "+COL_DATE+" TEXT"+")");
        db.execSQL("CREATE TABLE lay_total_papers ("+COL_ID+" INT AUTO_INCREMENT ,"+
                " "+COL_TOTAL_WORDS_NUM_TABLE_EVERYDAY+" TEXT"+")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db=this.getWritableDatabase();
        db.execSQL("DROP TABLE lay_everyday;");
        db.execSQL("DROP TABLE lay_every2day;");
        db.execSQL("DROP TABLE lay_every4day;");
        db.execSQL("DROP TABLE lay_every8day;");
        db.execSQL("DROP TABLE lay_every16day;");
        db.execSQL("DROP TABLE lay_baygani;");
        db.execSQL("DROP TABLE lay_total_papers;");
        this.onCreate(db);
    }

    public boolean insert(String tableName,String word,String meaning){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(COL_WORD,word);
        values.put(COL_MEANING,meaning);
        values.put(COL_DATE,today);
        return db.insert(tableName,null,values) != -1;
    }

    public boolean remove(String tableName,String word,String meaning){
        SQLiteDatabase db=getWritableDatabase();
        return  db.delete(tableName,COL_WORD+"= ? AND " + COL_MEANING + "= ?",new String[]{word, meaning}) != -1;
    }

    public boolean update(String tableName,String word,String meaning){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(COL_DATE,today);
        return db.update(tableName,values,COL_WORD+"= ? AND "+COL_MEANING+"= ? ",new String[]{word,meaning}) != -1;
    }

    public Cursor view(String tableName){
        SQLiteDatabase db=getWritableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM "+tableName,null);
        return cursor;
    }

    public int getTotalWordsNumTableEveryday(){
        SQLiteDatabase db=getWritableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM lay_total_papers",null);
        if(cursor.moveToNext()){
            return Integer.parseInt(cursor.getString(1));
        }
        return 50;
    }

    public boolean insertTotalWordsNumTableEveryday(){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(COL_TOTAL_WORDS_NUM_TABLE_EVERYDAY,"50");
        return db.insert("lay_total_papers",null,values) != -1;
    }

    public boolean updateTotalWordsNumTableEveryday(String lastNum,String newNum){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(COL_TOTAL_WORDS_NUM_TABLE_EVERYDAY,newNum);
        return db.update("lay_total_papers",values,COL_TOTAL_WORDS_NUM_TABLE_EVERYDAY+"= ? ",new String[]{lastNum}) != -1;
    }
}
