package com.goharshad.arena.sakoo;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class GozareshAzmoon extends AppCompatActivity implements View.OnClickListener ,View.OnLongClickListener{

    private ScrollView scrollView;
    private LinearLayout linearLayout1, linearLayout2[], linearLayout3[], linearLayout4[];
    private ImageView imageView[];
    private TextView onvan[], date[], seeReport[];
    private LinearLayout.LayoutParams params1, params2, params3, params4, params5, params6, params7;
    private AzmoonDataBase azmoonDataBase;
    private Cursor cursor;
    private int lenght = 0,remove_id;
    private PreferencesManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gozaresh_azmoon);
        preferenceManager = new PreferencesManager(this);
        initVaredBarnameInfoDialog(getString(R.string.notif_gozaresh_azmoon_remove));
        initToolbar();
        findViews();
        azmoonDataBase = new AzmoonDataBase(GozareshAzmoon.this, "exam", null, 1);
        makeChart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        lenght=0;
        scrollView.removeAllViews();
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

    private void findViews() {
        scrollView = (ScrollView) findViewById(R.id.gozaresh_azmon_scroll);
        linearLayout1 = (LinearLayout) findViewById(R.id.gozaresh_azmon_linear);
    }

    private void makeChart() {
        makeInitial();
        scrollView.removeAllViews();
        linearLayout1.removeAllViews();
        cursor = azmoonDataBase.view();
        Log.d("TAG1234",lenght+"");
        for (int i = 0; i < lenght; i++) {
            cursor.moveToNext();
            params1.weight = 3;
            linearLayout2[i] = new LinearLayout(this);
            linearLayout2[i].setOrientation(LinearLayout.HORIZONTAL);
            linearLayout2[i].setLayoutParams(params1);
            linearLayout2[i].setWeightSum(7);

            params2.weight = 2;
            imageView[i] = new ImageView(this);
            imageView[i].setImageResource(R.drawable.growth);
            imageView[i].setLayoutParams(params2);

            params3.weight = 4.5f;
            linearLayout3[i] = new LinearLayout(this);
            linearLayout3[i].setOrientation(LinearLayout.VERTICAL);
            linearLayout3[i].setLayoutParams(params3);
            linearLayout3[i].setWeightSum(2);

            params4.weight = 1;
            onvan[i] = new TextView(this);
            onvan[i].setText(cursor.getString(1));
            onvan[i].setTextColor(Color.parseColor("#000000"));
            onvan[i].setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16.75f);
            onvan[i].setTypeface(Typeface.createFromAsset(getAssets(), "fonts/IRAN-SANS.TTF"));
            onvan[i].setBackgroundColor(Color.parseColor("#ffffff"));
            onvan[i].setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
            onvan[i].setMaxLines(1);
            onvan[i].setEllipsize(TextUtils.TruncateAt.MARQUEE);
            onvan[i].setLayoutParams(params4);

            params5.weight = 1;
            date[i] = new TextView(this);
            date[i].setText(cursor.getString(5));
            date[i].setTextColor(Color.parseColor("#000000"));
            date[i].setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16.75f);
            date[i].setTypeface(Typeface.createFromAsset(getAssets(), "fonts/B Koodak Bold.TTF"));
            date[i].setBackgroundColor(Color.parseColor("#ffffff"));
            date[i].setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
            date[i].setMaxLines(1);
            date[i].setEllipsize(TextUtils.TruncateAt.MARQUEE);
            date[i].setLayoutParams(params5);


            linearLayout3[i].addView(onvan[i]);
            linearLayout3[i].addView(date[i]);
            linearLayout2[i].addView(imageView[i]);
            linearLayout2[i].addView(linearLayout3[i]);

            params6.weight = 1;
            seeReport[i] = new TextView(this);
            seeReport[i].setText("مشاهده کارنامه");
            seeReport[i].setTextColor(Color.parseColor("#ffffff"));
            seeReport[i].setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16.75f);
            seeReport[i].setPadding(0,6,0,6);
            seeReport[i].setTypeface(Typeface.createFromAsset(getAssets(), "fonts/IRAN-SANS.TTF"));
            seeReport[i].setBackgroundColor(Color.parseColor("#0fe612"));
            seeReport[i].setGravity(Gravity.CENTER);
            seeReport[i].setMaxLines(1);
            seeReport[i].setLayoutParams(params6);
            seeReport[i].setId(i);
            seeReport[i].setOnClickListener(this);

            linearLayout4[i] = new LinearLayout(this);
            linearLayout4[i].setOrientation(LinearLayout.VERTICAL);
            linearLayout4[i].setLayoutParams(params7);
            linearLayout4[i].setWeightSum(4);
            linearLayout4[i].setBackgroundColor(Color.WHITE);
            linearLayout4[i].addView(linearLayout2[i]);
            linearLayout4[i].addView(seeReport[i]);
            linearLayout4[i].setId(i);
            linearLayout4[i].setOnLongClickListener(this);
            linearLayout1.addView(linearLayout4[i]);
        }
        scrollView.addView(linearLayout1);
    }

    private void makeInitial() {
        cursor = azmoonDataBase.view();
        while (cursor.moveToNext()) {
            lenght++;
        }
        if(lenght==0) {
            Log.d("TAG123","1-");
            finish();
        }
        linearLayout2 = new LinearLayout[lenght];
        linearLayout3 = new LinearLayout[lenght];
        linearLayout4 = new LinearLayout[lenght];
        imageView = new ImageView[lenght];
        onvan = new TextView[lenght];
        date = new TextView[lenght];
        seeReport = new TextView[lenght];
        params1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300);
        params2 = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        params3 = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        params4 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
        params5 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
        params6 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params7 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params1.setMargins(0, 10, 0, 10);
        params2.setMargins(10, 10, 0, 10);
        params3.setMargins(0, 10, 10, 10);
        params4.setMargins(0, 10, 15, 10);
        params5.setMargins(0, 10, 20, 10);
        params6.setMargins(0, 10, 0, 0);
        params7.setMargins(0, 10, 0, 10);
        Log.d("TAG123","2-");
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(GozareshAzmoon.this, ShowExamResult.class);
        intent.putExtra("option", (v.getId() + 1) + "");
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        finish();
    }


    private void initVaredBarnameInfoDialog(String msg) {
        if (!preferenceManager.gozareshAzmoonIsCheckedInfoDialog()) {
            View view = LayoutInflater.from(this).inflate(R.layout.layout_vared_barname_info, null);
            final AlertDialog dialog = new AlertDialog.Builder(this).setView(view).create();
            TextView tv1 = (TextView) view.findViewById(R.id.layout_vared_barname_title);
            TextView tv2 = (TextView) view.findViewById(R.id.layout_vared_barname_txt);
            TextView tv3 = (TextView) view.findViewById(R.id.layout_vared_barname_checkbox_txt);
            Button button = (Button) view.findViewById(R.id.layout_vared_barname_btn_ok);
            final CheckBox checkBox = (CheckBox) view.findViewById(R.id.layout_vared_barname_checkbox);
            tv2.setText(msg);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (checkBox.isChecked()) {
                        preferenceManager.setGozareshAzmoonIsCheckedInfoDialog(true);
                    }
                    dialog.dismiss();
                }
            });

            Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/IRAN-SANS.TTF");
            tv1.setTypeface(typeface);
            tv2.setTypeface(typeface);
            tv3.setTypeface(typeface);
            button.setTypeface(typeface);
            dialog.show();
        }
    }

    @Override
    public boolean onLongClick(View v) {
        remove_id=v.getId();
        alartRemove();
        return false;
    }

    private void alartRemove() {
        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setMessage(getResources().getString(R.string.activity_gozaresh_azmoon_remove_alart)).create();
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "بله", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                remove();
                dialog.dismiss();
                dialog.cancel();
            }
        });
        dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "خیر", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                dialog.cancel();
            }
        });
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface di) {
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTypeface(Typeface.createFromAsset(getAssets(), "fonts/IRAN-SANS.TTF"));
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTypeface(Typeface.createFromAsset(getAssets(), "fonts/IRAN-SANS.TTF"));

            }
        });
        dialog.show();
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.rounded_rectangle_white1);
        TextView textView = (TextView) dialog.findViewById(android.R.id.message);
        textView.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/IRAN-SANS.TTF"));
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12f);
        textView.setTextColor(ContextCompat.getColor(this, R.color.colorDark_1));
    }

    private void remove(){
        cursor=azmoonDataBase.view();
        while(cursor.moveToNext()){
            if(remove_id==0)
                break;
            remove_id--;
        }
        if(azmoonDataBase.remove("azmoon",cursor.getString(6))) {
            Log.d("TAG1234","aa");
            lenght=0;
            scrollView.removeAllViews();
            makeChart();
        }
    }
}