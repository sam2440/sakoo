package com.goharshad.arena.sakoo;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.interfaces.datasets.IPieDataSet;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ShowExamResult extends AppCompatActivity{

    private String onvan,option;
    private int totalQ,rightNum=0,wrongNum=0;
    private TextView negetivePointPercent_tv,withoutNegetivePointPercent_tv,totalQ_tv,right_tv,wrong_tv,notAnswered_tv,title_tv;
    private PieChart pieChart;
    private ArrayList<PieEntry> entries;
    private PieDataSet dataSet;
    private AzmoonDataBase dataBase;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_exam_result);
        initToolbar();
        findViews();
        getFromDataBase();
        setTextViews();

        setKoodak();
        setIranSans(R.id.ans_showres_txt_1);
        setIranSans(R.id.ans_showres_txt_2);
        setIranSans(R.id.ans_showres_txt_3);
        setIranSans(R.id.ans_showres_txt_4);
        setIranSans(R.id.show_results_activity_name);
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.show_results_exam_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_37dp);
    }

    private void setKoodak() {
        Typeface typeface=Typeface.createFromAsset(getAssets(), "fonts/B Koodak Bold.TTF");
        negetivePointPercent_tv.setTypeface(typeface);
        notAnswered_tv.setTypeface(typeface);
        right_tv.setTypeface(typeface);
        title_tv.setTypeface(typeface);
        totalQ_tv.setTypeface(typeface);
        wrong_tv.setTypeface(typeface);
        withoutNegetivePointPercent_tv.setTypeface(typeface);
    }

    private void setIranSans(@IdRes int id) {
        Typeface typeface=Typeface.createFromAsset(getAssets(), "fonts/IRAN-SANS.TTF");
        TextView textView= (TextView) findViewById(id);
        textView.setTypeface(typeface);
    }

    private void findViews(){
        title_tv= (TextView) findViewById(R.id.show_exam_result_title);
        negetivePointPercent_tv= (TextView) findViewById(R.id.show_exam_result_with_negetive_point);
        withoutNegetivePointPercent_tv= (TextView) findViewById(R.id.show_exam_result_without_negetive_point);
        totalQ_tv= (TextView) findViewById(R.id.show_exam_result_total_questions);
        right_tv= (TextView) findViewById(R.id.show_exam_result_right_answer);
        wrong_tv= (TextView) findViewById(R.id.show_exam_result_wrong_answer);
        notAnswered_tv= (TextView) findViewById(R.id.show_exam_result_not_answered);
        pieChart= (PieChart) findViewById(R.id.show_exam_result_pie_chart);
    }

    private void getFromDataBase(){
        dataBase=new AzmoonDataBase(ShowExamResult.this,"exam",null,1);
        cursor=dataBase.view();
        option=getIntent().getExtras().getString("option");
        if(option.equals("result")){
            while(cursor.moveToNext()){
                if(cursor.isLast()){
                    onvan=cursor.getString(1);
                    totalQ=Integer.parseInt(cursor.getString(2));
                    rightNum=Integer.parseInt(cursor.getString(3));
                    wrongNum=Integer.parseInt(cursor.getString(4));
                    break;
                }
            }
        }
        else{
            int goal=Integer.parseInt(option);
            int current=0;
            while(cursor.moveToNext()){
                current++;
                if(current==goal){
                    onvan=cursor.getString(1);
                    totalQ=Integer.parseInt(cursor.getString(2));
                    rightNum=Integer.parseInt(cursor.getString(3));
                    wrongNum=Integer.parseInt(cursor.getString(4));
                    break;
                }
            }
        }
    }

    private void makePieChart(int r,int w,int n,int t){
        entries=new ArrayList<>();
        DecimalFormat df=new DecimalFormat("##.##");
        double x,y,z;
        x=((float)r/(float) t)*100;
        y=((float)w/(float) t)*100;
        z=((float)n/(float) t)*100;
        x=Double.valueOf(df.format(x));
        y=Double.valueOf(df.format(y));
        z=Double.valueOf(df.format(z));
        if(r!=0)
            entries.add(new PieEntry(r,getPersianNum((int)x)+"."+getPersianNum((int)((x-(int)x)*100))+"%"));
        if(w!=0)
            entries.add(new PieEntry(w,getPersianNum((int)y)+"."+getPersianNum((int)((y-(int)y)*100))+"%"));
        if(n!=0)
            entries.add(new PieEntry(n,getPersianNum((int)z)+"."+getPersianNum((int)((z-(int)z)*100))+"%"));

        dataSet=new PieDataSet(entries,"نتیجه مقایسه");
        if(r!=0){
            if(w!=0){
                if(n!=0)
                    dataSet.setColors(Color.parseColor("#298911"),Color.parseColor("#ea2d23"),Color.parseColor("#898585"));
                else
                    dataSet.setColors(Color.parseColor("#298911"),Color.parseColor("#ea2d23"));
            }
            else{
                if(n!=0)
                    dataSet.setColors(Color.parseColor("#298911"),Color.parseColor("#898585"));
                else
                    dataSet.setColors(Color.parseColor("#298911"));
            }
        }else{
            if(w!=0){
                if(n!=0)
                    dataSet.setColors(Color.parseColor("#ea2d23"),Color.parseColor("#898585"));
                else
                    dataSet.setColors(Color.parseColor("#ea2d23"));
            }
            else{
                if(n!=0)
                    dataSet.setColors(Color.parseColor("#898585"));
            }
        }
        dataSet.setDrawValues(false);
        dataSet.setValueTextColor(Color.WHITE);
        dataSet.setValueTextSize(13);
        List<IPieDataSet> dataSets=new ArrayList<>();
        dataSets.add(dataSet);
        PieData pieData=new PieData(dataSet);
//        pieData.setValueFormatter(new PercentFormatter());
        pieChart.setHoleRadius(0);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setData(pieData);
        pieChart.setDescription(null);
        pieChart.invalidate();
    }

    private void setTextViews(){
        title_tv.setText(onvan);
        makePieChart(rightNum,wrongNum,totalQ-(rightNum+wrongNum),totalQ);
        double x=((float)(rightNum-((float)wrongNum/3.0))/(float)totalQ)*100;
        double y=((float)rightNum/(float) totalQ)*100;
        DecimalFormat df=new DecimalFormat("##.##");
        x=Double.valueOf(df.format(x));
        y=Double.valueOf(df.format(y));
        negetivePointPercent_tv.setText(String.valueOf(x));
        withoutNegetivePointPercent_tv.setText(String.valueOf(y));
        totalQ_tv.setText(totalQ+"");
        right_tv.setText("ص : "+rightNum);
        wrong_tv.setText("غ : "+wrongNum);
        notAnswered_tv.setText("ن : "+(totalQ-rightNum-wrongNum));
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
}
