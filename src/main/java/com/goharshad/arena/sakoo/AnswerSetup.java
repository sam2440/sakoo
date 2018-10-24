package com.goharshad.arena.sakoo;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class AnswerSetup extends AppCompatActivity implements View.OnClickListener {

    private ImageButton back;
    private EditText onvan, azmoondahande, time, totalQ, firstQ;
    private CheckBox haveTime;
    private Button sakhte_pasokhname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_setup);
        initToolbar();
        findViews();
        sakhte_pasokhname.setOnClickListener(this);
        sakhte_pasokhname.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/IRAN-SANS.TTF"));

        setFont(R.id.ans_has_deadline_q);
        setFont(R.id.ans_holder);
        setFont(R.id.ans_subject);
        setFont(R.id.ans_first_q_number);
        setFont(R.id.ans_q_size);
        setFont(R.id.txt_minutes);
        setFont(R.id.anssetup);
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.anssetuptoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_37dp);

        TextView toolbar_txt = (TextView) toolbar.findViewById(R.id.anssetup);
        toolbar_txt.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/IRAN-SANS.TTF"));
    }

    private void findViews() {
        onvan = (EditText) findViewById(R.id.answer_setup_onvan);
        azmoondahande = (EditText) findViewById(R.id.answer_setup_azmoondahande);
        time = (EditText) findViewById(R.id.answer_setup_time);
        totalQ = (EditText) findViewById(R.id.answer_setup_total_q);
        firstQ = (EditText) findViewById(R.id.answer_setup_first_q);
        haveTime = (CheckBox) findViewById(R.id.answer_setup_have_time);
        sakhte_pasokhname = (Button) findViewById(R.id.answer_setup_sakhte_pasokhname);
    }

    private void setFont(@IdRes int id) {
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/IRAN-SANS.TTF");
        TextView textView = (TextView) findViewById(id);
        textView.setTypeface(typeface);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        String s = "0";
        switch (v.getId()) {
            case R.id.answer_setup_sakhte_pasokhname:
                intent = new Intent(AnswerSetup.this, StartExam.class);
                if (haveTime.isChecked()) {
                    if (time.getText().toString().isEmpty()) {
                        time.setError("زمان آزمون را وارد کنید.");
                        break;
                    } else if (Integer.parseInt(time.getText().toString()) == 0) {
                        time.setError("زمان وارد شده نمیتواند صفر باشد..");
                        break;
                    } else {
                        s = time.getText().toString();
                    }
                }
                if (onvan.getText().toString().isEmpty()) {
                    onvan.setError("عنوان آزمون را وارد کنید.");
                } else if (azmoondahande.getText().toString().isEmpty()) {
                    azmoondahande.setError("نام آزمون دهنده را وارد کنید.");
                } else if (totalQ.getText().toString().isEmpty()) {
                    totalQ.setError("تعداد کل آزمون را وارد کنید.");
                } else if (firstQ.getText().toString().isEmpty()) {
                    firstQ.setError("شماره اولین سوال آزمون را وارد کنید.");
                } else {
                    intent.putExtra("exam", "true");
                    intent.putExtra("time", s);
                    intent.putExtra("onvan", onvan.getText().toString());
                    intent.putExtra("azmoondahande", azmoondahande.getText().toString());
                    intent.putExtra("totalQ", totalQ.getText().toString());
                    intent.putExtra("firstQ", firstQ.getText().toString());
                    startActivity(intent);
                    finish();
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
