package com.goharshad.arena.sakoo;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;
import android.widget.ImageButton;

import ir.huri.jcal.JalaliCalendar;

public class LightnerWords extends AppCompatActivity implements View.OnClickListener{

    private LinearLayout layoutCounter;
    private TextView tvCounterShekl,tvCounter,tvTotalCounter,tvWordGroup,word,meaning;
    private ImageView hideMeaning,share,copy;
    private ImageButton back;
    private Button remember,dontRemember,hazf;
    private String table,_word,_meaning;
    private LightnerDatabase lightnerDatabase;
    private Cursor cursor;
    private int totalWordsNum=0;
    private LinearLayout.LayoutParams params;
    private int cur_day,cur_month,cur_year,day,month,year,next_day,next_month,next_year,group;
    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lightner_words);
        findViews();
        lightnerDatabase=new LightnerDatabase(LightnerWords.this,"laytner",null,1);
        date= new JalaliCalendar().toString();
        changeStringDateToInt();
        cur_day=day;
        cur_month=month;
        cur_year=year;
        setLayout();
        cursor=lightnerDatabase.view(findCurrentTable());
        setCounters();
        cursor=lightnerDatabase.view(findCurrentTable());
        makeWordMeaning();
        word.setText(_word);
        remember.setClickable(false);
        dontRemember.setClickable(false);
    }

    private void setLayout(){
        table=getIntent().getExtras().getString("table");
        group = (Integer.parseInt(table));
//        lightnerDatabase=(lightnerDatabase) getIntent().getSerializableExtra("lightnerDatabase");
        makeWordGroupName((Integer.parseInt(table)));
        if(group==6){
            remember.setVisibility(View.INVISIBLE);
            dontRemember.setVisibility(View.INVISIBLE);
            hazf.setVisibility(View.VISIBLE);
            hazf.setOnClickListener(this);
        }
        else{
            remember.setVisibility(View.VISIBLE);
            dontRemember.setVisibility(View.VISIBLE);
            hazf.setVisibility(View.INVISIBLE);
            remember.setOnClickListener(this);
            dontRemember.setOnClickListener(this);
        }
    }

    private void findViews(){
        layoutCounter= (LinearLayout) findViewById(R.id.laytner_layout_counter);
        tvCounterShekl= (TextView) findViewById(R.id.laytner_tv_counter);
        tvCounter= (TextView) findViewById(R.id.laytner_counter);
        word= (TextView) findViewById(R.id.laytner_word);
        meaning= (TextView) findViewById(R.id.laytner_meaning);
        tvTotalCounter= (TextView) findViewById(R.id.laytner_counter_total);
        tvWordGroup= (TextView) findViewById(R.id.lay_word_laytitle_word_group);
//        back= (ImageButton) findViewById(R.id.lay_word_backimage);
        hideMeaning= (ImageView) findViewById(R.id.laytner_hide_image);
        share= (ImageView) findViewById(R.id.laytner_share);
        copy= (ImageView) findViewById(R.id.laytner_copy);
        remember= (Button) findViewById(R.id.laytner_remember);
        dontRemember= (Button) findViewById(R.id.laytner_dont_remember);
        hazf= (Button) findViewById(R.id.laytner_hazf_baygani_btn);
        share.setOnClickListener(this);
        copy.setOnClickListener(this);
        hideMeaning.setOnClickListener(this);
//        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.laytner_remember:
                nextWord();
                lightnerDatabase.insert(findNextTable(),_word,_meaning);
                lightnerDatabase.remove(findCurrentTable(),_word,_meaning);
                break;
            case R.id.laytner_dont_remember:
//                Log.d("TAG1234",cursor.getString(3));
                if(lightnerDatabase.update(findCurrentTable(),_word,_meaning))
//                    Toast.makeText(this, cursor.getString(1)+cursor.getString(2)+cursor.getString(3), Toast.LENGTH_SHORT).show();
//                  Log.d("TAG123",cursor.getString(1)+cursor.getString(2)+cursor.getString(3));
                nextWord();
                break;
            case R.id.laytner_hazf_baygani_btn:
                lightnerDatabase.remove("lay_baygani",_word,_meaning);
                break;
            case R.id.laytner_hide_image:
                hideMeaning.setVisibility(View.INVISIBLE);
                remember.setBackgroundColor(Color.parseColor("#1ad10d"));
                dontRemember.setBackgroundColor(Color.parseColor("#f53408"));
                remember.setClickable(true);
                dontRemember.setClickable(true);
                meaning.setText(_meaning);
                break;
            case R.id.laytner_share:
                Intent intent=new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String s="کلمه: "+_word+" ,معنی: "+_meaning;
                intent.putExtra(Intent.EXTRA_TEXT,s);
                startActivity(intent.createChooser(intent,"Share this text via"));
                break;
            case R.id.laytner_copy:
                if(Build.VERSION.SDK_INT< Build.VERSION_CODES.HONEYCOMB){
                    android.text.ClipboardManager clipboard= (android.text.ClipboardManager) this.getSystemService(Context.CLIPBOARD_SERVICE);
                    clipboard.setText("کلمه: "+_word+" ,معنی: "+_meaning);
                }
                else{
                    ClipboardManager clipboard= (ClipboardManager) this.getSystemService(Context.CLIPBOARD_SERVICE);
                    android.content.ClipData clip=android.content.ClipData.newPlainText("Copid Text","کلمه: "+_word+" ,معنی: "+_meaning);
                    clipboard.setPrimaryClip(clip);
                }
                break;
//            case R.id.lay_word_backimage:
//                finish();
        }
    }

    private String findCurrentTable(){
        switch (table){
            case "1":
                return "lay_everyday";
            case "2":
                return "lay_every2day";
            case "3":
                return "lay_every4day";
            case "4":
                return "lay_every8day";
            case "5":
                return "lay_every16day";
        }
        return "lay_baygani";
    }
    private String findNextTable(){
        switch (table){
            case "1":
                return "lay_every2day";
            case "2":
                return "lay_every4day";
            case "3":
                return "lay_every8day";
            case "4":
                return "lay_every16day";
        }
        return "lay_baygani";
    }

    private void makeWordMeaning(){
        if(cursor.moveToNext()){
            date=cursor.getString(3);
            if ((checkReviewDate(group))){
                _word=cursor.getString(1);
                _meaning=cursor.getString(2);
            }
            if(group==6){
                _word=cursor.getString(1);
                _meaning=cursor.getString(2);
            }
        }
    }
    private void nextWord(){
        if((int)params.weight != totalWordsNum){
            makeWordMeaning();
            remember.setBackgroundColor(Color.parseColor("#393838"));
            dontRemember.setBackgroundColor(Color.parseColor("#393838"));
            remember.setClickable(false);
            dontRemember.setClickable(false);
            hideMeaning.setVisibility(View.VISIBLE);
            meaning.setText("");
            word.setText(_word);
            params.weight++;
            tvCounter.setText(getPersianNum((int) params.weight));
            tvCounterShekl.setLayoutParams(params);
        }
        else{
            Toast.makeText(this, "پایان", Toast.LENGTH_SHORT).show();
            remember.setBackgroundColor(Color.parseColor("#393838"));
            dontRemember.setBackgroundColor(Color.parseColor("#393838"));
            remember.setClickable(false);
            dontRemember.setClickable(false);
        }
    }

    private void setCounters(){
        Cursor c=cursor;
        while (c.moveToNext()){
            date=cursor.getString(3);
            if(group==6)
                totalWordsNum++;
            else if(checkReviewDate(group))
                totalWordsNum++;
        }
        tvCounter.setText(getPersianNum(1));
        tvTotalCounter.setText(getPersianNum(totalWordsNum));
        layoutCounter.setWeightSum(totalWordsNum);
        params=new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        params.weight=1;
        tvCounterShekl.setLayoutParams(params);
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
    private StringBuilder getPersianNum(int x){
        String s="";
        while(x!=0){
            s+=getGrade(x%10);
            x=x/10;
        }
        StringBuilder sb=new StringBuilder();
        sb.append(s);
        sb=sb.reverse();
        return sb;
    }

    private void makeWordGroupName(int i){
        switch (i){
            case 1:
                tvWordGroup.setText("مرور هر روز");
                break;
            case 2:
                tvWordGroup.setText("مرور هر دو روز");
                break;
            case 3:
                tvWordGroup.setText("مرور هر چهار روز");
                break;
            case 4:
                tvWordGroup.setText("مرور هر هشت روز");
                break;
            case 5:
                tvWordGroup.setText("مرور هر شانزده روز");
                break;
            case 6:
                tvWordGroup.setText("بایگانی");
                break;
        }
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
            if(cur_year==next_year && cur_month==next_month && cur_day==next_day) {
//                Toast.makeText(this, cur_year+cur_month+cur_day+" "+next_year+next_month+next_day, Toast.LENGTH_SHORT).show();
                return false;
            }
            nextDay();
            year=next_year;
            month=next_month;
            day=next_day;
        }
        return true;
    }
}
