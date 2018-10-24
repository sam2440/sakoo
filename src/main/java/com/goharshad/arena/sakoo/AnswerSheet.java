package com.goharshad.arena.sakoo;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.support.annotation.IdRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AnswerSheet extends AppCompatActivity implements View.OnClickListener {

    private ImageButton back;
    private LinearLayout newTest;
    private LinearLayout gozaresh;
    private AzmoonDataBase azmoonDataBase;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_sheet);
        initToolbar();
        findViews();
    }
    
    private void findViews(){
        newTest= (LinearLayout) findViewById(R.id.answer_sheet_new_test);
        gozaresh= (LinearLayout) findViewById(R.id.answer_sheet_gozaresh);
        newTest.setOnClickListener(this);
        gozaresh.setOnClickListener(this);

        setFont(R.id.answer_sheet_new_test_title);
        setFont(R.id.answer_sheet_new_test_desc);
        setFont(R.id.answer_sheet_record_desc);
        setFont(R.id.answer_sheet_record_title);
        setFont(R.id.anstitle);

    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.anstoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_37dp);

        TextView toolbar_txt = (TextView) toolbar.findViewById(R.id.anstitle);
        toolbar_txt.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/IRAN-SANS.TTF"));
    }

    private void setFont(@IdRes int id) {
        Typeface typeface=Typeface.createFromAsset(getAssets(), "fonts/IRAN-SANS.TTF");
        TextView textView= (TextView) findViewById(id);
        textView.setTypeface(typeface);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
           case R.id.answer_sheet_new_test:
                intent=new Intent(AnswerSheet.this,AnswerSetup.class);
                startActivity(intent);
                break;
            case R.id.answer_sheet_gozaresh:
                azmoonDataBase=new AzmoonDataBase(AnswerSheet.this,"exam",null,1);
                cursor=azmoonDataBase.view();
                if(cursor.moveToNext()){
                    intent=new Intent(AnswerSheet.this,GozareshAzmoon.class);
                    startActivity(intent);
                }
                else{
                    AlertHelper.makeDialog(this,R.string.txt_no_gozaresh_exists);
                }
                break;
        }
    }
}
