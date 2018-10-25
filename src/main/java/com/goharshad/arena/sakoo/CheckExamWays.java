package com.goharshad.arena.sakoo;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.IdRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CheckExamWays extends AppCompatActivity implements View.OnClickListener {

    private ImageButton back;
    private LinearLayout key_checkBox, key_string;
    private String answers, onvan;
    private int totalQ, firstQ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_exam_ways);
        initToolbar();
        findViews();
        getExteras();

        back.setOnClickListener(this);
        key_checkBox.setOnClickListener(this);
        key_string.setOnClickListener(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.anscheckwaystoolbar);
        setSupportActionBar(toolbar);

        setFont(R.id.anscheckwaystitle);
        setFont(R.id.ans_checkways_key);
        setFont(R.id.ans_checkways_stringset);
    }

    @Override
    protected void onResume() {
        super.onResume();
        PreferencesManager manager = new PreferencesManager(this);
        boolean b = manager.getExitToAnswersheet();
        if (b) {
            manager.setExitToAnswersheet(false);
            finish();
        }
    }

    private void findViews() {
        key_checkBox = (LinearLayout) findViewById(R.id.check_exam_key_checkbox);
        key_string = (LinearLayout) findViewById(R.id.check_exam_key_string);
        back = (ImageButton) findViewById(R.id.checkexamway_back);
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.anscheckwaystoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        TextView toolbar_txt = (TextView) toolbar.findViewById(R.id.anscheckwaystitle);
        toolbar_txt.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/IRAN-SANS.TTF"));
    }


    private void setFont(@IdRes int id) {
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/IRAN-SANS.TTF");
        TextView textView = (TextView) findViewById(id);
        textView.setTypeface(typeface);
    }

    private void getExteras() {
        onvan = getIntent().getExtras().getString("onvan");
        answers = getIntent().getExtras().getString("result");
        totalQ = Integer.parseInt(getIntent().getExtras().getString("totalQ"));
        firstQ = Integer.parseInt(getIntent().getExtras().getString("firstQ"));
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.check_exam_key_checkbox:
                intent = new Intent(CheckExamWays.this, StartExam.class);
                intent.putExtra("answers", answers);
                intent.putExtra("totalQ", totalQ + "");
                intent.putExtra("firstQ", firstQ + "");
                intent.putExtra("onvan", onvan);
                intent.putExtra("exam", "false");
                startActivity(intent);
                break;
            case R.id.check_exam_key_string:
                intent = new Intent(CheckExamWays.this, CheckExamStringKey.class);
                intent.putExtra("answers", answers);
                intent.putExtra("totalQ", totalQ + "");
                intent.putExtra("firstQ", firstQ + "");
                intent.putExtra("onvan", onvan);
                startActivity(intent);
                break;
            case R.id.checkexamway_back:
                confirmExit();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        confirmExit();
    }

    public void confirmExit() {
        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setMessage("مطمئن هستی نمیخوای ادامه بدی ؟")
                .setPositiveButton("آره", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        finish();
                    }
                }).setNegativeButton("نه", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface di) {
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.RED);
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTypeface(Typeface.createFromAsset(getAssets(), "fonts/IRAN-SANS.TTF"));
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTypeface(Typeface.createFromAsset(getAssets(), "fonts/IRAN-SANS.TTF"));
            }
        });
        dialog.show();

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/IRAN-SANS.TTF");
        TextView textView = (TextView) dialog.findViewById(android.R.id.message);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12f);
        textView.setTypeface(typeface);
        textView.setTextColor(ContextCompat.getColor(this, R.color.colorDark_1));
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.rounded_rectangle_white1);
    }
}
