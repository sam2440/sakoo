package com.goharshad.arena.sakoo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.io.Serializable;
import ir.huri.jcal.JalaliCalendar;

public class AzmoonDataBase extends SQLiteOpenHelper implements Serializable {

    private final static String COL_ID="id";
    private final static String COL_ID_2="id2";
    private final static String TABLE_NAME="azmoon";
    private final static String COL_AZMOON_NAME="azmoon_name";
    private final static String COL_TOTALQ="azmoon_totalQ";
    private final static String COL_RIGHTS="azmoon_right";
    private final static String COL_WRONGS="azmoon_wrong";
    private final static String COL_DATE="azmoon_date";
    private static int id_2=0;
    private String today;

    public AzmoonDataBase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.today = new JalaliCalendar().toString();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+TABLE_NAME+" ("+COL_ID+" INT AUTO_INCREMENT PRIMARY KEY,"+
                " "+COL_AZMOON_NAME + " TEXT ,"+
                " "+COL_TOTALQ + " INT ,"+
                " "+COL_RIGHTS + " INT ,"+
                " "+COL_WRONGS + " INT ,"+
                " "+COL_DATE + " DATE ,"+
                " "+COL_ID_2+" INT"+")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db=this.getWritableDatabase();
        db.execSQL("DROP TABLE azmoon;");
        this.onCreate(db);
    }

    public boolean insert(String name,int t,int r,int w){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(COL_AZMOON_NAME,name);
        values.put(COL_ID_2,id_2);
        values.put(COL_TOTALQ,t);
        values.put(COL_RIGHTS,r);
        values.put(COL_WRONGS,w);
        values.put(COL_DATE,today);
        id_2++;
        return db.insert(TABLE_NAME,null,values) != -1;
    }

    public boolean remove(String tableName,String id){
        SQLiteDatabase db=getWritableDatabase();
        Log.d("TAG123",id+"");
        return  db.delete(tableName,COL_ID_2+"= ?",new String[]{id}) != -1;
    }

    public Cursor view(){
        SQLiteDatabase db=getWritableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM "+TABLE_NAME,null);
        return cursor;
    }
}
