package com.goharshad.arena.sakoo;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class GozareshAzmoon extends AppCompatActivity implements View.OnClickListener {

    private ScrollView scrollView;
    private LinearLayout linearLayout1,linearLayout2[],linearLayout3[],linearLayout4[];
    private ImageView imageView[];
    private TextView onvan[],date[],seeReport[];
    private LinearLayout.LayoutParams params1, params2, params3,params4, params5, params6,params7;
    private AzmoonDataBase azmoonDataBase;
    private Cursor cursor;
    private int lenght=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gozaresh_azmoon);
        initToolbar();
        findViews();
        azmoonDataBase=new AzmoonDataBase(GozareshAzmoon.this,"exam",null,1);
        makeChart();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.gozaresh_azmoon_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_37dp);

        TextView toolbar_txt = (TextView) toolbar.findViewById(R.id.gozaresh_azmoon_activity_name);
        toolbar_txt.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/IRAN-SANS.TTF"));
    }



    private void findViews(){
        scrollView= (ScrollView) findViewById(R.id.gozaresh_azmon_scroll);
        linearLayout1= (LinearLayout) findViewById(R.id.gozaresh_azmon_linear);
    }

    private void makeChart(){
        makeInitial();
        scrollView.removeAllViews();
        linearLayout1.removeAllViews();
        cursor=azmoonDataBase.view();
        for(int i=0;i<lenght;i++){
            cursor.moveToNext();
            params1.weight=3;
            linearLayout2[i]=new LinearLayout(this);
            linearLayout2[i].setOrientation(LinearLayout.HORIZONTAL);
            linearLayout2[i].setLayoutParams(params1);
            linearLayout2[i].setWeightSum(7);

            params2.weight=2;
            imageView[i]=new ImageView(this);
            imageView[i].setImageResource(R.drawable.growth);
            imageView[i].setLayoutParams(params2);

            params3.weight=4.5f;
            linearLayout3[i]=new LinearLayout(this);
            linearLayout3[i].setOrientation(LinearLayout.VERTICAL);
            linearLayout3[i].setLayoutParams(params3);
            linearLayout3[i].setWeightSum(2);

            params4.weight=1;
            onvan[i]=new TextView(this);
            onvan[i].setText(cursor.getString(1));
            onvan[i].setTextColor(Color.parseColor("#000000"));
            onvan[i].setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16.75f);
            onvan[i].setTypeface(Typeface.createFromAsset(getAssets(), "fonts/IRAN-SANS.TTF"));
            onvan[i].setBackgroundColor(Color.parseColor("#ffffff"));
            onvan[i].setGravity(Gravity.RIGHT|Gravity.CENTER_VERTICAL);
            onvan[i].setMaxLines(1);
            onvan[i].setEllipsize(TextUtils.TruncateAt.MARQUEE);
            onvan[i].setLayoutParams(params4);

            params5.weight=1;
            date[i]=new TextView(this);
            date[i].setText(cursor.getString(5));
            date[i].setTextColor(Color.parseColor("#000000"));
            date[i].setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16.75f);
            date[i].setTypeface(Typeface.createFromAsset(getAssets(), "fonts/IRAN-SANS.TTF"));
            date[i].setBackgroundColor(Color.parseColor("#ffffff"));
            date[i].setGravity(Gravity.RIGHT|Gravity.CENTER_VERTICAL);
            date[i].setMaxLines(1);
            date[i].setEllipsize(TextUtils.TruncateAt.MARQUEE);
            date[i].setLayoutParams(params5);


            linearLayout3[i].addView(onvan[i]);
            linearLayout3[i].addView(date[i]);
            linearLayout2[i].addView(imageView[i]);
            linearLayout2[i].addView(linearLayout3[i]);

            params6.weight=1;
            seeReport[i]=new TextView(this);
            seeReport[i].setText("مشاهده کارنامه");
            seeReport[i].setTextColor(Color.parseColor("#ffffff"));
            seeReport[i].setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16.75f);
            seeReport[i].setTypeface(Typeface.createFromAsset(getAssets(), "fonts/IRAN-SANS.TTF"));
            seeReport[i].setBackgroundColor(Color.parseColor("#0fe612"));
            seeReport[i].setGravity(Gravity.CENTER);
            seeReport[i].setMaxLines(1);
            seeReport[i].setLayoutParams(params6);
            seeReport[i].setId(i);
            seeReport[i].setOnClickListener(this);

            linearLayout4[i]=new LinearLayout(this);
            linearLayout4[i].setOrientation(LinearLayout.VERTICAL);
            linearLayout4[i].setLayoutParams(params7);
            linearLayout4[i].setWeightSum(4);
            linearLayout4[i].setBackgroundColor(Color.WHITE);
            linearLayout4[i].addView(linearLayout2[i]);
            linearLayout4[i].addView(seeReport[i]);
            linearLayout1.addView(linearLayout4[i]);
        }
        scrollView.addView(linearLayout1);
    }

    private void makeInitial(){
        cursor=azmoonDataBase.view();
        while(cursor.moveToNext()){
            lenght++;
        }
        linearLayout2=new LinearLayout[lenght];
        linearLayout3=new LinearLayout[lenght];
        linearLayout4=new LinearLayout[lenght];
        imageView=new ImageView[lenght];
        onvan=new TextView[lenght];
        date=new TextView[lenght];
        seeReport=new TextView[lenght];
        params1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300);
        params2 = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        params3 = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        params4 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,0);
        params5 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,0);
        params6 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params7 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params1.setMargins(0, 10, 0, 10);
        params2.setMargins(10, 10, 0, 10);
        params3.setMargins(0, 10, 10, 10);
        params4.setMargins(0, 10, 15, 10);
        params5.setMargins(0, 10, 20, 10);
        params6.setMargins(0, 10, 0, 0);
        params7.setMargins(0, 10, 0, 10);
    }

    @Override
    public void onClick(View v) {
        Intent intent=new Intent(GozareshAzmoon.this,ShowExamResult.class);
        intent.putExtra("option",(v.getId()+1)+"");
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
