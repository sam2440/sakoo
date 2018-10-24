package com.goharshad.arena.sakoo;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CheckExamWays extends AppCompatActivity implements View.OnClickListener {

    private ImageButton back;
    private LinearLayout key_checkBox,key_string;
    private String answers,onvan;
    private int totalQ,firstQ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_exam_ways);
        initToolbar();
        findViews();
        getExteras();

        key_checkBox.setOnClickListener(this);
        key_string.setOnClickListener(this);

        Toolbar toolbar= (Toolbar) findViewById(R.id.anscheckwaystoolbar);
        setSupportActionBar(toolbar);

        setFont(R.id.anscheckwaystitle);
        setFont(R.id.ans_checkways_key);
        setFont(R.id.ans_checkways_stringset);
    }

    private void findViews(){
        key_checkBox= (LinearLayout) findViewById(R.id.check_exam_key_checkbox);
        key_string= (LinearLayout) findViewById(R.id.check_exam_key_string);
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.anscheckwaystoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_37dp);

        TextView toolbar_txt = (TextView) toolbar.findViewById(R.id.anscheckwaystitle);
        toolbar_txt.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/IRAN-SANS.TTF"));
    }


    private void setFont(@IdRes int id) {
        Typeface typeface=Typeface.createFromAsset(getAssets(), "fonts/IRAN-SANS.TTF");
        TextView textView= (TextView) findViewById(id);
        textView.setTypeface(typeface);
    }

    private void getExteras(){
        onvan=getIntent().getExtras().getString("onvan");
        answers=getIntent().getExtras().getString("result");
        totalQ=Integer.parseInt(getIntent().getExtras().getString("totalQ"));
        firstQ=Integer.parseInt(getIntent().getExtras().getString("firstQ"));
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.check_exam_key_checkbox:
                intent=new Intent(CheckExamWays.this,StartExam.class);
                intent.putExtra("answers",answers);
                intent.putExtra("totalQ",totalQ+"");
                intent.putExtra("firstQ",firstQ+"");
                intent.putExtra("onvan",onvan);
                intent.putExtra("exam","false");
                startActivity(intent);
                break;
            case R.id.check_exam_key_string:
                intent=new Intent(CheckExamWays.this,CheckExamStringKey.class);
                intent.putExtra("answers",answers);
                intent.putExtra("totalQ",totalQ+"");
                intent.putExtra("firstQ",firstQ+"");
                intent.putExtra("onvan",onvan);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        finish();
    }
}
