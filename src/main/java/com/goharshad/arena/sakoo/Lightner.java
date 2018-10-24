package com.goharshad.arena.sakoo;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Parcelable;
import android.support.annotation.IdRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import ir.huri.jcal.JalaliCalendar;


public class Lightner extends AppCompatActivity implements View.OnClickListener{

    private RelativeLayout everyday,every2day,every4day,every8day,every16day,baygani;
    private LinearLayout counterLayout;
    private TextView everyday_tv,every2day_tv,every4day_tv,every8day_tv,every16day_tv,baygani_tv,counterBarge1,counterBarge2,counterBarge3,counterBarge4,counterBarge5;
    private ImageButton back,info,plus,buy;
    private ImageView lock1,lock2,lock4,lock8,lock16,lock_baygani;
    private LightnerDatabase lightnerDatabase;
    private LinearLayout.LayoutParams params1,params2,params3,params4,params5;
    private int totalGroupWords[],availableGroupWords[];
    private int cur_day,cur_month,cur_year,day,month,year,next_day,next_month,next_year;
    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lightner);
        findViews();
        totalGroupWords=new int[6];
        availableGroupWords=new int[5];
        lightnerDatabase=new LightnerDatabase(Lightner.this,"laytner",null,1);
        date= new JalaliCalendar().toString();
        changeStringDateToInt();
        cur_day=day;
        cur_month=month;
        cur_year=year;
        setGroupWordNum();
        setLocksVisibility();
        setFonts();
    }

    private void setFonts() {
        Typeface typeface=Typeface.createFromAsset(getAssets(),"fonts/IRAN-SANS.TTF");
        setFont(typeface,R.id.laytner_group_words_bottom_num1);
        setFont(typeface,R.id.laytner_group_words_bottom_num2);
        setFont(typeface,R.id.laytner_4_day_bottom_container);
        setFont(typeface,R.id.laytner_8_day_bottom_container);
        setFont(typeface,R.id.laytner_16_day_bottom_container);
        setFont(typeface,R.id.laytner_group_bottom_num6);
        setFont(typeface,R.id.laytitle);
    }

    private void setFont(Typeface typeface,@IdRes int id) {
        TextView textView= (TextView) findViewById(id);
        textView.setTypeface(typeface);
    }

    @Override
    protected void onResume() {
        super.onResume();
        date= new JalaliCalendar().toString();
        changeStringDateToInt();
        cur_day=day;
        cur_month=month;
        cur_year=year;
        setGroupWordNum();
        setLocksVisibility();
    }

    private void findViews(){
        counterLayout= (LinearLayout) findViewById(R.id.laytner_barge_layout);
        everyday= (RelativeLayout) findViewById(R.id.laytner_everyday);
        every2day= (RelativeLayout) findViewById(R.id.laytner_2day);
        every4day= (RelativeLayout) findViewById(R.id.laytner_4day);
        every8day= (RelativeLayout) findViewById(R.id.laytner_8day);
        every16day= (RelativeLayout) findViewById(R.id.laytner_16day);
        baygani= (RelativeLayout) findViewById(R.id.laytner_baygani);
        everyday_tv= (TextView) findViewById(R.id.laytner_group_words_num1);
        every2day_tv= (TextView) findViewById(R.id.laytner_group_words_num2);
        every4day_tv= (TextView) findViewById(R.id.laytner_group_words_num3);
        every8day_tv= (TextView) findViewById(R.id.laytner_group_words_num4);
        every16day_tv= (TextView) findViewById(R.id.laytner_group_words_num5);
        baygani_tv= (TextView) findViewById(R.id.laytner_group_words_num6);
        lock1= (ImageView) findViewById(R.id.laytner_group_words_num1_lock);
        lock2= (ImageView) findViewById(R.id.laytner_group_words_num2_lock);
        lock4= (ImageView) findViewById(R.id.laytner_group_words_num3_lock);
        lock8= (ImageView) findViewById(R.id.laytner_group_words_num4_lock);
        lock16= (ImageView) findViewById(R.id.laytner_group_words_num5_lock);
        lock_baygani= (ImageView) findViewById(R.id.laytner_group_words_num6_lock);
        counterBarge1= (TextView) findViewById(R.id.laytner_barge_1);
        counterBarge2= (TextView) findViewById(R.id.laytner_barge_2);
        counterBarge3= (TextView) findViewById(R.id.laytner_barge_3);
        counterBarge4= (TextView) findViewById(R.id.laytner_barge_4);
        counterBarge5= (TextView) findViewById(R.id.laytner_barge_5);
        back= (ImageButton) findViewById(R.id.laytner_backimage);
        info= (ImageButton) findViewById(R.id.laytner_infoimage);
        plus= (ImageButton) findViewById(R.id.laytner_plusimage);
        buy= (ImageButton) findViewById(R.id.laytner_buycard);
        everyday.setOnClickListener(this);
        every2day.setOnClickListener(this);
        every4day.setOnClickListener(this);
        every8day.setOnClickListener(this);
        every16day.setOnClickListener(this);
        baygani.setOnClickListener(this);
        back.setOnClickListener(this);
        info.setOnClickListener(this);
        plus.setOnClickListener(this);
        buy.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent=new Intent(Lightner.this,LightnerWords.class);
        switch (v.getId()){
            case R.id.laytner_everyday:
                if(availableGroupWords[0]!=0){
                    intent.putExtra("table","1");
                    startActivity(intent);
                }
                else{
                    AlertHelper.makeToast(this, "هنوز برگه ای برای مرور در این بخش ندارید.");
                }
                break;
            case R.id.laytner_2day:
                if(availableGroupWords[1]!=0){
                    intent.putExtra("table","2");
                    startActivity(intent);
                }
                else{
                    AlertHelper.makeToast(this, "هنوز برگه ای برای مرور در این بخش ندارید.");
                }
                break;
            case R.id.laytner_4day:
                if(availableGroupWords[2]!=0){
                    intent.putExtra("table","3");
                    startActivity(intent);
                }
                else{
                    AlertHelper.makeToast(this, "هنوز برگه ای برای مرور در این بخش ندارید.");
                }
                break;
            case R.id.laytner_8day:
                if(availableGroupWords[3]!=0){
                    intent.putExtra("table","4");
                    startActivity(intent);
                }
                else{
                    AlertHelper.makeToast(this, "هنوز برگه ای برای مرور در این بخش ندارید.");
                }
                break;
            case R.id.laytner_16day:
                if(availableGroupWords[4]!=0){
                    intent.putExtra("table","5");
                    startActivity(intent);
                }
                else{
                    AlertHelper.makeToast(this, "هنوز برگه ای برای مرور در این بخش ندارید.");
                }
                break;
            case R.id.laytner_baygani:
                if(totalGroupWords[5]!=0){
                    intent.putExtra("table","6");
                    startActivity(intent);
                }
                else{
                    AlertHelper.makeToast(this, "هنوز برگه ای برای مرور در این بخش ندارید.");
                }
                break;
            case R.id.laytner_backimage:
                finish();
                break;
            case R.id.laytner_infoimage:
                intent=new Intent(Lightner.this,LightnerInfo.class);
                startActivity(intent);
//                AlertHelper.makeToast(this, "info", Toast.LENGTH_SHORT).show();
                break;
            case R.id.laytner_plusimage:
//                if (v_ezafe_kardane_word.getParent() != null)
//                    ((ViewGroup) v_ezafe_kardane_word.getParent()).removeView(v_ezafe_kardane_word);
//                dialogAddWord = new AlertDialog.Builder(this);
//                addWord = (Button) v_ezafe_kardane_word.findViewById(R.id.alart_laytner_add_word_btn);
//                word = (EditText) v_ezafe_kardane_word.findViewById(R.id.alart_laytner_add_word_et);
//                meaning = (EditText) v_ezafe_kardane_word.findViewById(R.id.alart_laytner_add_meaning_et);
//                word.setError(null);
//                meaning.setError(null);
//                addWord.setOnClickListener(this);
//                dialogAddWord.setCancelable(true);
//                dialogAddWord.setView(v_ezafe_kardane_word);
//                _dialogAddWord = dialogAddWord.create();
//                _dialogAddWord.show();
                intent=new Intent(Lightner.this,AddNewWord.class);
                startActivity(intent);
                break;
            case R.id.laytner_buycard :
                intent=new Intent(Lightner.this,LightnerBuyCard.class);
                startActivity(intent);
//                table1_num=lightnerDatabase.getTotalWordsNumTableEveryday();
//                setRemainPaperNum();
                break;
        }
    }

    public String getGrade(int i){
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
    private StringBuilder getPersianNum(int totalGroupWords){
        String s="";
        if(totalGroupWords==0){
            s="۰";
        }
        else{
            while(totalGroupWords!=0){
                s+=getGrade(totalGroupWords%10);
                totalGroupWords=totalGroupWords/10;
            }
        }
        StringBuilder sb=new StringBuilder();
        sb.append(s);
        sb=sb.reverse();
        return sb;
    }
    private void setGroupWordNum(){
        int sum=0;
        params1=new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        params2=new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        params2.weight=1;
        params3=new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        params3.weight=1;
        params4=new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        params4.weight=1;
        params5=new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        params5.weight=1;
        Cursor cursor=lightnerDatabase.view("lay_everyday");
        totalGroupWords[0]=0;
        availableGroupWords[0]=0;
        while (cursor.moveToNext()){
            totalGroupWords[0]++;
            date=cursor.getString(3);
            if(checkReviewDate(1))
                availableGroupWords[0]++;
        }
        sum+=totalGroupWords[0];
        params1.weight=totalGroupWords[0];
        everyday_tv.setText(getPersianNum(availableGroupWords[0]) +"/" +getPersianNum(totalGroupWords[0])+ " برگه");
        cursor=lightnerDatabase.view("lay_every2day");
        totalGroupWords[1]=0;
        availableGroupWords[1]=0;
        while (cursor.moveToNext()){
            totalGroupWords[1]++;
            date=cursor.getString(3);
            if(checkReviewDate(2))
                availableGroupWords[1]++;
        }
        sum+=totalGroupWords[1];
        params2.weight=totalGroupWords[1];
        every2day_tv.setText(getPersianNum(availableGroupWords[1]) +"/" +getPersianNum(totalGroupWords[1])+ " برگه");
        cursor=lightnerDatabase.view("lay_every4day");
        totalGroupWords[2]=0;
        availableGroupWords[2]=0;
        while (cursor.moveToNext()){
            totalGroupWords[2]++;
            date=cursor.getString(3);
            if(checkReviewDate(4))
                availableGroupWords[2]++;
        }
        sum+=totalGroupWords[2];
        params3.weight=totalGroupWords[2];
        every4day_tv.setText(getPersianNum(availableGroupWords[2]) +"/" +getPersianNum(totalGroupWords[2])+ " برگه");
        cursor=lightnerDatabase.view("lay_every8day");
        totalGroupWords[3]=0;
        availableGroupWords[3]=0;
        while (cursor.moveToNext()){
            totalGroupWords[3]++;
            date=cursor.getString(3);
            if(checkReviewDate(8))
                availableGroupWords[3]++;
        }
        sum+=totalGroupWords[3];
        params4.weight=totalGroupWords[3];
        every8day_tv.setText(getPersianNum(availableGroupWords[3]) +"/" +getPersianNum(totalGroupWords[3])+ " برگه");
        cursor=lightnerDatabase.view("lay_every16day");
        totalGroupWords[4]=0;
        availableGroupWords[4]=0;
        while (cursor.moveToNext()){
            totalGroupWords[4]++;
            date=cursor.getString(3);
            if(checkReviewDate(16))
                availableGroupWords[4]++;
        }
        sum+=totalGroupWords[4];
        params5.weight=totalGroupWords[4];
        every16day_tv.setText(getPersianNum(availableGroupWords[4]) +"/" +getPersianNum(totalGroupWords[4])+ " برگه");
        cursor=lightnerDatabase.view("lay_baygani");
        totalGroupWords[5]=0;
        while (cursor.moveToNext()){
            totalGroupWords[5]++;
        }
        baygani_tv.setText(getPersianNum(totalGroupWords[5])+ " برگه");
        counterLayout.setWeightSum(sum);
        counterBarge1.setLayoutParams(params1);
        counterBarge2.setLayoutParams(params2);
        counterBarge3.setLayoutParams(params3);
        counterBarge4.setLayoutParams(params4);
        counterBarge5.setLayoutParams(params5);
    }
    
    private void setLocksVisibility(){
        if(totalGroupWords[0]!=0)
            lock1.setVisibility(View.INVISIBLE);
        else
            lock1.setVisibility(View.VISIBLE);
        if(totalGroupWords[1]!=0)
            lock2.setVisibility(View.INVISIBLE);
        else
            lock2.setVisibility(View.VISIBLE);
        if(totalGroupWords[2]!=0)
            lock4.setVisibility(View.INVISIBLE);
        else
            lock4.setVisibility(View.VISIBLE);
        if(totalGroupWords[3]!=0)
            lock8.setVisibility(View.INVISIBLE);
        else
            lock8.setVisibility(View.VISIBLE);
        if(totalGroupWords[4]!=0)
            lock16.setVisibility(View.INVISIBLE);
        else
            lock16.setVisibility(View.VISIBLE);
        if(totalGroupWords[5]!=0)
            lock_baygani.setVisibility(View.INVISIBLE);
        else
            lock_baygani.setVisibility(View.VISIBLE);
    }

    private void changeStringDateToInt(){
        String hold="";
        for(int i=0;i<4;i++)
            hold+=date.charAt(i);
        year=Integer.parseInt(hold);
        hold="";
        int i;
        for(i=5;i<7;i++){
            if(date.charAt(i)!='-')
                hold+=date.charAt(i);
            else
                break;
        }
        month=Integer.parseInt(hold);
        hold="";
        i++;
        for(int j=i;j<date.length();j++){
            hold+=date.charAt(j);
        }
        day=Integer.parseInt(hold);
    }

    private void nextDay() {
        if (month == 12) {
            handleEsfand();
        } else if (day < 30) {
            next_day++;
        } else if (day == 30 && month < 7) {
            next_day++;
        } else {
            next_day = 1;
            next_month++;
        }
    }
    private void handleEsfand() {
        int boundaryDay = 29;
        if (isLeapYear(year)) {
            boundaryDay = 30;
        }
        if (day == boundaryDay) {
            next_year++;
            next_month = 1;
            next_day = 1;
        } else {
            next_day++;
        }
    }
    private boolean isLeapYear(int year) {
        double a = 0.025;
        double b = 266;
        double leapDays0 = 0, leapDays1 = 0;
        int frac0 = 0, frac1 = 0;
        if (year > 0) {
            leapDays0 = ((year + 38) % 2820)*0.24219 + a;  //0.24219 ~ extra days of one year
            leapDays1 = ((year + 39) % 2820)*0.24219 + a;  //38 days is the difference of epoch to
            //2820-year cycle
        } else if (year < 0) {
            leapDays0 = ((year + 39) % 2820)*0.24219 + a;
            leapDays1 = ((year + 40) % 2820)*0.24219 + a;
        } else {
            return false;
        }

        frac0 = (int)((leapDays0 - (int)(leapDays0))*1000);
        frac1 = (int)((leapDays1 - (int)(leapDays1))*1000);

        if (frac0 <= b && frac1 > b) {
            return true;
        } else {
            return false;
        }
    }

    private boolean checkReviewDate(int group){
        changeStringDateToInt();
        next_year=year;next_month=month;next_day=day;
        for(int i=1;i<=group;i++){
            if(cur_year==next_year && cur_month==next_month && cur_day==next_day)
                return false;
            nextDay();
            year=next_year;
            month=next_month;
            day=next_day;
            Log.d("TAG1234",next_day+" "+next_month+" "+next_year);
        }
        return true;
    }
}
