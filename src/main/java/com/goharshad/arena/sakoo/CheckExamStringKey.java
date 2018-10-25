package com.goharshad.arena.sakoo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CheckExamStringKey extends AppCompatActivity implements View.OnClickListener {

    private EditText key;
    private Button check;
    private ImageButton back;
    private String answers,result,onvan;
    private int totalQ;
    private AzmoonDataBase azmoonDataBase;
    private int rightNum=0,wrongNum=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_exam_string_key);
        initToolbar();
        fidViews();
        getExtras();

        check.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/IRAN-SANS.TTF"));
        check.setOnClickListener(this);
        back.setOnClickListener(this);

        setFont(R.id.ans_checkstr_txt_1);
        setFont(R.id.ans_checkstr_txt_2);
        setFont(R.id.anscheckstrtitle);

    }

    private void makeToast(Context context, String msg) {
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/IRAN-SANS.TTF");
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        LinearLayout toastView = (LinearLayout) toast.getView();
        TextView textView = (TextView) toastView.getChildAt(0);
        textView.setTypeface(typeface);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12f);
        toast.show();

    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.anscheckstrtoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
//        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_37dp);

        TextView toolbar_txt = (TextView) toolbar.findViewById(R.id.anscheckstrtitle);
        toolbar_txt.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/IRAN-SANS.TTF"));
    }

    private void fidViews(){
        back= (ImageButton) findViewById(R.id.checkexamwaystr_back);
        key= (EditText) findViewById(R.id.check_exam_string_key_et);
        check= (Button) findViewById(R.id.check_exam_string_key_btn);
    }

    private void setFont(@IdRes int id) {
        Typeface typeface=Typeface.createFromAsset(getAssets(), "fonts/IRAN-SANS.TTF");
        TextView textView= (TextView) findViewById(id);
        textView.setTypeface(typeface);
    }


    private void getExtras(){
        onvan=getIntent().getExtras().getString("onvan");
        answers=getIntent().getExtras().getString("answers");
        totalQ=Integer.parseInt(getIntent().getExtras().getString("totalQ"));
    }

    private void checkKey(){
        if(key.getText().toString().isEmpty()){
            key.setError("کلید تصحیح را وارد کنید!");
        }
        else{
            result=key.getText().toString();
            if(result.length()!= totalQ){
                makeToast(this, "طول رشته ی وارد شده اشتباه است");
                return;
            }
            for(int i=0;i<result.length();i++){
                if(!(result.charAt(i)=='1' || result.charAt(i)=='2' || result.charAt(i)=='3' || result.charAt(i)=='4')){
                    makeToast(this, result.charAt(i)+"رشته ی وارد شده اشتباه است.");
                    return;
                }
            }
            new PreferencesManager(this).setExitToAnswersheet(true);
            Intent intent=new Intent(CheckExamStringKey.this,ShowExamResult.class);
            addToGozareshDataBase();
            intent.putExtra("option","result");
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.check_exam_string_key_btn:
                checkKey();
                break;
            case R.id.checkexamwaystr_back :
                finish();
                break;
        }
    }

    private void addToGozareshDataBase(){
        azmoonDataBase=new AzmoonDataBase(CheckExamStringKey.this,"exam",null,1);
        checkExam();
        azmoonDataBase.insert(onvan,totalQ,rightNum,wrongNum);
    }
    private void checkExam(){
        for (int i=0;i<totalQ;i++){
            if(answers.charAt(i)==result.charAt(i))
                rightNum++;
            else {
                if(answers.charAt(i)!='0')
                    wrongNum++;
            }
        }
    }
}
