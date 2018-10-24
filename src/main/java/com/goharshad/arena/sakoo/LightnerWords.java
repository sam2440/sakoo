package com.goharshad.arena.sakoo;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.IdRes;
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

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lightner_words);
        initToolbar();
        findViews();
        lightnerDatabase=new LightnerDatabase(LightnerWords.this,"laytner",null,1);;
        setLayout();
        cursor=lightnerDatabase.view(findCurrentTable());
        setCounters();
        cursor=lightnerDatabase.view(findCurrentTable());
        makeWordMeaning();
        word.setText(_word);
        remember.setClickable(false);
        dontRemember.setClickable(false);


        setFont(R.id.lay_word_laytitle_word);
        setFont(R.id.lay_word_laytitle_word_group);
        setFont(R.id.laytner_word);
        setFont(R.id.laytner_meaning);
        setFont(R.id.lay_word_laytitle_word);
        setFont(R.id.laytner_dont_remember);
        setFont(R.id.laytner_hazf_baygani_btn);
        setFont(R.id.laytner_remember);
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.laytner_word_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_37dp);
    }


    private void setFont(@IdRes int id) {
        View textView = findViewById(id);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/IRAN-SANS.TTF");
        if (id==R.id.laytner_dont_remember || id==R.id.laytner_remember || id==R.id.laytner_hazf_baygani_btn)
            ((Button) textView).setTypeface(typeface);
        else
            ((TextView) textView).setTypeface(typeface);


    }

    private void setLayout(){
        table=getIntent().getExtras().getString("table");
//        lightnerDatabase=(lightnerDatabase) getIntent().getSerializableExtra("lightnerDatabase");
        makeWordGroupName((Integer.parseInt(table)));
        if(table.equals("6")){
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
            _word=cursor.getString(1);
            _meaning=cursor.getString(2);
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
        }
    }

    private void setCounters(){
        Cursor c=cursor;
        while (c.moveToNext()){
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
}
