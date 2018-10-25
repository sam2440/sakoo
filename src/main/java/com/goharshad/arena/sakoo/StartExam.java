package com.goharshad.arena.sakoo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.icu.util.TimeUnit;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class StartExam extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private TextView time,title,testNumber[],testN1[],testN2[],testN3[],testN4[];
    private Button tamoom_shod;
    private ScrollView scrollView;
    private LinearLayout linearLayout,linearLayout1[];
    private int _time,totalQ,firstQ;
    private LinearLayout.LayoutParams params1,params2,params3,params4,params5,params6,params7,params8,params9;
    private CheckBox test1[],test2[],test3[],test4[];
    private String result,exam,answers,onvan;
    private AzmoonDataBase azmoonDataBase;
    private int rightNum=0,wrongNum=0;
    private CountDownTimer timer;
    private ImageButton back;
    private boolean check=true,finished=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_exam);
        initToolbar();
        findViews();
        back.setOnClickListener(this);
        tamoom_shod.setOnClickListener(this);
        tamoom_shod.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/IRAN-SANS.TTF"));
        getExtras();
        makeChart();
        scrollView.addView(linearLayout);
        if(exam.equals("true") && _time>0){
            timer=new CountDownTimer(_time*60000+1000,1000){
                @Override
                public void onTick(long millisUntilFinished) {
                    long h,m,s;
                    h=java.util.concurrent.TimeUnit.MILLISECONDS.toHours(millisUntilFinished);
                    m=java.util.concurrent.TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)%60;
                    s=java.util.concurrent.TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished)%60;
                    String _h=h+"",_m=m+"",_s=s+"";
                    if(h<10)
                        _h="0"+h;
                    if(m<10)
                        _m="0"+m;
                    if(s<10)
                        _s="0"+s;
                    time.setText(_h+":"+_m+":"+_s);
                }
                @Override
                public void onFinish() {
                    cancel();
                    time.setText("00:00:00");
                    time.setTextColor(Color.RED);
                    getAnswers();
                    if (!finished){
                        Intent intent=new Intent(StartExam.this,CheckExamWays.class);
                        intent.putExtra("totalQ",totalQ+"");
                        intent.putExtra("firstQ",firstQ+"");
                        intent.putExtra("result",result);
                        intent.putExtra("onvan",onvan);
                        startActivity(intent);
                        finish();
                    }
                }
            }.start();
        }
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.ansstartexamtoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void findViews(){
        time= (TextView) findViewById(R.id.start_test_time);
        title= (TextView) findViewById(R.id.start_test_title);
        tamoom_shod= (Button) findViewById(R.id.start_test_tamom_shod);
        scrollView= (ScrollView) findViewById(R.id.start_test_questions_scroll);
        linearLayout= (LinearLayout) findViewById(R.id.start_test_questions_linear);
        back= (ImageButton) findViewById(R.id.startexam_back);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.start_test_tamom_shod:
                if(exam.equals("true")){
                    getAnswers();
                    intent=new Intent(StartExam.this,CheckExamWays.class);
                    intent.putExtra("totalQ",totalQ+"");
                    intent.putExtra("firstQ",firstQ+"");
                    intent.putExtra("result",result);
                    intent.putExtra("onvan",onvan);
                    startActivity(intent);
                    finished=true;
                    if (timer != null) timer.cancel();
                    finish();
                }
                else{
                    if(getAnswers()){
                        intent=new Intent(StartExam.this,ShowExamResult.class);
                        addToGozareshDataBase();
                        intent.putExtra("option","result");
                        startActivity(intent);
                        finish();
                    }
                }
                break;
            case R.id.startexam_back :
                confirmExit();
                break;
        }
    }

    private void getExtras(){
        exam=getIntent().getExtras().getString("exam");
        onvan=getIntent().getExtras().getString("onvan");
        if(exam.equals("true")){
            _time=Integer.parseInt(getIntent().getExtras().getString("time"));
            title.setText("زمان آزمون : ");
            tamoom_shod.setText("تموم شد");
        }
        else {
            answers=getIntent().getExtras().getString("answers");
            title.setText("وارد کردن کلید ");
            tamoom_shod.setText("تصحیح کن");
        }
        totalQ=Integer.parseInt(getIntent().getExtras().getString("totalQ"));
        firstQ=Integer.parseInt(getIntent().getExtras().getString("firstQ"));
    }

    private void makeChart(){
        linearLayout.removeAllViews();
        scrollView.removeAllViews();

        linearLayout1=new LinearLayout[totalQ];
        testNumber=new TextView[totalQ];
        testN1=new TextView[totalQ];
        testN2=new TextView[totalQ];
        testN3=new TextView[totalQ];
        testN4=new TextView[totalQ];
        test1=new CheckBox[totalQ];
        test2=new CheckBox[totalQ];
        test3=new CheckBox[totalQ];
        test4=new CheckBox[totalQ];

        params1 = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        params2 = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        params3 = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        params4 = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        params5 = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        params6 = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        params7 = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        params8 = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        params9 = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        params1.setMargins(0, 10, 0, 10);
        params2.setMargins(0, 10, 0, 10);
        params3.setMargins(0, 10, 0, 10);
        params4.setMargins(0, 10, 0, 10);
        params5.setMargins(0, 10, 0, 10);
        params6.setMargins(0, 10, 0, 10);
        params7.setMargins(0, 10, 0, 10);
        params8.setMargins(0, 10, 0, 10);
        params9.setMargins(0, 10, 0, 10);

        int f=firstQ;
        int id=0;

        for (int i=0;i<totalQ;i++){
            linearLayout1[i] = new LinearLayout(this);
            linearLayout1[i].setOrientation(LinearLayout.HORIZONTAL);
            linearLayout1[i].setPadding(0,5,0,5);

            params1.weight=1f;
            testNumber[i]=new TextView(this);
            testNumber[i].setText(getPersianNum(f));
            testNumber[i].setTextColor(Color.parseColor("#000000"));
            testNumber[i].setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
            testNumber[i].setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/B Koodak Bold.TTF"));
            testNumber[i].setBackgroundColor(Color.parseColor("#ffffff"));
            testNumber[i].setLayoutParams(params1);
            testNumber[i].setGravity(Gravity.CENTER);

            params2.weight=0.5f;
            testN1[i]=new TextView(this);
            testN1[i].setText("1");
            testN1[i].setTextColor(Color.parseColor("#000000"));
            testN1[i].setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
            testN1[i].setBackgroundColor(Color.parseColor("#ffffff"));
            testN1[i].setLayoutParams(params2);
            testN1[i].setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);

            params3.weight=0.5f;
            test1[i]=new CheckBox(this);
            test1[i].setBackgroundColor(Color.WHITE);
            test1[i].setLayoutParams(params3);
            test1[i].setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
            test1[i].setId(id);
            id++;
            test1[i].setOnCheckedChangeListener(this);

            params4.weight=0.5f;
            testN2[i]=new TextView(this);
            testN2[i].setText("2");
            testN2[i].setTextColor(Color.parseColor("#000000"));
            testN2[i].setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
            testN2[i].setBackgroundColor(Color.parseColor("#ffffff"));
            testN2[i].setLayoutParams(params4);
            testN2[i].setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);

            params5.weight=0.5f;
            test2[i]=new CheckBox(this);
            test2[i].setBackgroundColor(Color.WHITE);
            test2[i].setLayoutParams(params5);
            test2[i].setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
            test2[i].setId(id);
            id++;
            test2[i].setOnCheckedChangeListener(this);

            params6.weight=0.5f;
            testN3[i]=new TextView(this);
            testN3[i].setText("3");
            testN3[i].setTextColor(Color.parseColor("#000000"));
            testN3[i].setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
            testN3[i].setBackgroundColor(Color.parseColor("#ffffff"));
            testN3[i].setLayoutParams(params6);
            testN3[i].setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);

            params7.weight=0.5f;
            test3[i]=new CheckBox(this);
            test3[i].setBackgroundColor(Color.WHITE);
            test3[i].setLayoutParams(params7);
            test3[i].setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
            test3[i].setId(id);
            id++;
            test3[i].setOnCheckedChangeListener(this);

            params8.weight=0.5f;
            testN4[i]=new TextView(this);
            testN4[i].setText("4");
            testN4[i].setTextColor(Color.parseColor("#000000"));
            testN4[i].setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
            testN4[i].setBackgroundColor(Color.parseColor("#ffffff"));
            testN4[i].setLayoutParams(params8);
            testN4[i].setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);

            params9.weight=0.5f;
            test4[i]=new CheckBox(this);
            test4[i].setBackgroundColor(Color.WHITE);
            test4[i].setLayoutParams(params9);
            test4[i].setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
            test4[i].setId(id);
            id++;
            test4[i].setOnCheckedChangeListener(this);

            linearLayout1[i].setWeightSum(5);
            linearLayout1[i].addView(testNumber[i]);
            linearLayout1[i].addView(testN1[i]);
            linearLayout1[i].addView(test1[i]);
            linearLayout1[i].addView(testN2[i]);
            linearLayout1[i].addView(test2[i]);
            linearLayout1[i].addView(testN3[i]);
            linearLayout1[i].addView(test3[i]);
            linearLayout1[i].addView(testN4[i]);
            linearLayout1[i].addView(test4[i]);
            linearLayout.addView(linearLayout1[i]);
            f++;
        }

    }

    private String getGrade(int i){
        switch (i){
            case 0:
                return "۰";
            case 1:
                return "۱";
            case 2:
                return "۲";
            case 3:
                return "۳";
            case 4:
                return "۴";
            case 5:
                return "۵";
            case 6:
                return "۶";
            case 7:
                return "۷";
            case 8:
                return "۸";
            case 9:
                return "۹";
        }
        return "۰";
    }
    private StringBuilder getPersianNum(int x){
        String s="";
        if(x==0){
            s="۰";
        }
        else{
            while(x!=0){
                s+=getGrade(x%10);
                x=x/10;
            }
        }
        StringBuilder sb=new StringBuilder();
        sb.append(s);
        sb=sb.reverse();
        return sb;
    }

    private boolean getAnswers(){
        result="";
        int x=0;
        for(int i=0;i<totalQ;i++){
            if(test1[i].isChecked())
                x=1;
            if(test2[i].isChecked())
                x=2;
            if(test3[i].isChecked())
                x=3;
            if(test4[i].isChecked())
                x=4;
            if(exam.equals("false") && x==0){
                makeToast(this,"جواب تمام گزینه ها را وارد کنید");
                return false;
            }
            result+=x;
            x=0;
        }
        return true;
    }

    private void makeToast(Context context, String msg){
        Typeface typeface=Typeface.createFromAsset(getAssets(),"fonts/IRAN-SANS.TTF");
        Toast toast=Toast.makeText(context,msg,Toast.LENGTH_SHORT);
        LinearLayout toastView= (LinearLayout) toast.getView();
        TextView textView= (TextView) toastView.getChildAt(0);
        textView.setTypeface(typeface);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP,12f);
        toast.show();
    }

    private void addToGozareshDataBase(){
        azmoonDataBase=new AzmoonDataBase(StartExam.this,"exam",null,1);
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

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int id=buttonView.getId();
        int group=id/4;
        if(isChecked){
            if(test1[group].getId() != id){
                test1[group].setChecked(false);
            }
            if(test2[group].getId() != id){
                test2[group].setChecked(false);
            }
            if(test3[group].getId() != id){
                test3[group].setChecked(false);
            }
            if(test4[group].getId() != id){
                test4[group].setChecked(false);
            }
        }
    }

    @Override
    public void onBackPressed() {
       confirmExit();
    }

    public void confirmExit(){
        final AlertDialog dialog=new AlertDialog.Builder(this)
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

        Typeface typeface=Typeface.createFromAsset(getAssets(), "fonts/IRAN-SANS.TTF");
        TextView textView = (TextView) dialog.findViewById(android.R.id.message);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12f);
        textView.setTypeface(typeface);
        textView.setTextColor(ContextCompat.getColor(this, R.color.colorDark_1));
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.rounded_rectangle_white1);
    }
}
