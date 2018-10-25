package com.goharshad.arena.sakoo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class CalcPercentage extends AppCompatActivity implements View.OnClickListener {

    private EditText total,right,wrong;
    private Button calcPercentage;
    private ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc_percentage);
        findViews();
        setFont(R.id.calcpercentagetitle);
        setFont(R.id.calc_percentage_txt_1);
        setFont(R.id.calc_percentage_txt_2);
        setFont(R.id.calc_percentage_txt_3);
        setFont(R.id.percent_calc_percent);
    }

    private void setFont(@IdRes int id) {
        Typeface typeface=Typeface.createFromAsset(getAssets(), "fonts/IRAN-SANS.TTF");
        View v=findViewById(id);
        if (id==R.id.percent_calc_percent)
            ((Button) v).setTypeface(typeface);
        else
            ((TextView) v).setTypeface(typeface);
    }

    private void findViews(){
        total= (EditText) findViewById(R.id.percent_total_questions);
        right= (EditText) findViewById(R.id.percent_corrects);
        wrong= (EditText) findViewById(R.id.percent_wrongs);
        calcPercentage= (Button) findViewById(R.id.percent_calc_percent);
        back= (ImageButton) findViewById(R.id.calc_percent_backimage);
        calcPercentage.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.percent_calc_percent:
                int totalNum=0,rightNum=0,wrongNum=0;
                if(total.getText().toString().isEmpty()){
                    total.setError("لطفا تعداد کل سوالات را وارد کنید!");
                }else{
                    totalNum=Integer.parseInt(total.getText().toString());
                }
                if(right.getText().toString().isEmpty()){
                    right.setError("لطفا تعداد سوالات صحیح را وارد کنید!");
                } else{
                    rightNum=Integer.parseInt(right.getText().toString());
                }
                if(wrong.getText().toString().isEmpty()){
                    wrong.setError("لطفا تعداد سوالات اشتباه را وارد کنید!");
                }else{
                    wrongNum=Integer.parseInt(wrong.getText().toString());
                }
                if(!total.getText().toString().isEmpty() && !right.getText().toString().isEmpty() && !wrong.getText().toString().isEmpty()){
                    int rightInt=Integer.parseInt(right.getText().toString());
                    int wrongInt=Integer.parseInt(wrong.getText().toString());
                    int totalInt=Integer.parseInt(total.getText().toString());
                    if (rightInt<0 || wrongInt<0 || totalInt<=0 || (rightInt+wrongInt)>totalInt){
                        hideKeyboard(this);
                        AlertHelper.makeDialog(this,R.string.txt_wrongdatacalcpercentage);
                    }else {
                        Intent intent = new Intent(CalcPercentage.this,ShowPercentage.class);
                        intent.putExtra("total", totalNum + "");
                        intent.putExtra("right", rightNum + "");
                        intent.putExtra("wrong", wrongNum + "");
                        startActivity(intent);
                    }
                }
                break;
            case R.id.calc_percent_backimage:
                finish();
        }
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
