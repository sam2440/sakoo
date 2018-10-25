package com.goharshad.arena.sakoo;

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

public class ShowPercentage extends AppCompatActivity implements View.OnClickListener {

    private TextView negetivePointPercent,withoutNegetivePointPercent,totalQ,right,wrong,notAnswered;
    private PieChart pieChart;
    private ArrayList<PieEntry> entries;
    private PieDataSet dataSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_percentage);
        initToolbar();
        findViews();
        setTextViews();
        setFonts();

        setIranSans(R.id.show_percentage_with_negetive_point);
        setIranSans(R.id.show_percentage_without_negetive_point);
        setIranSans(R.id.show_percentage_total_questions);
//        setIranSans(R.id.show_percent_title);
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.show_prercentage_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_37dp);

        TextView toolbar_txt = (TextView) toolbar.findViewById(R.id.show_percentage_activity_name);
        toolbar_txt.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/IRAN-SANS.TTF"));
    }

    private void setFonts() {
        Typeface typeface=Typeface.createFromAsset(getAssets(),"fonts/B Koodak Bold.TTF");
        negetivePointPercent.setTypeface(typeface);
        withoutNegetivePointPercent.setTypeface(typeface);
        totalQ.setTypeface(typeface);
        right.setTypeface(typeface);
        wrong.setTypeface(typeface);
        notAnswered.setTypeface(typeface);
    }

    private void setIranSans(@IdRes int id) {
        Typeface typeface=Typeface.createFromAsset(getAssets(), "fonts/IRAN-SANS.TTF");
        TextView textView= (TextView) findViewById(id);
        textView.setTypeface(typeface);
    }

    private void findViews(){
        negetivePointPercent= (TextView) findViewById(R.id.show_percent_with_negetive_point);
        withoutNegetivePointPercent= (TextView) findViewById(R.id.show_percent_without_negetive_point);
        totalQ= (TextView) findViewById(R.id.show_percent_total_questions);
        right= (TextView) findViewById(R.id.show_percent_right_answer);
        wrong= (TextView) findViewById(R.id.show_percent_wrong_answer);
        notAnswered= (TextView) findViewById(R.id.show_percent_not_answered);
        pieChart= (PieChart) findViewById(R.id.show_percent_pie_chart);
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
        pieChart.setDescription(null);
//        pieData.setValueFormatter(new PercentFormatter());
        pieChart.setHoleRadius(0);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }

    private void setTextViews(){
        int rightNum,wrongNum,totalNum;
        totalNum= Integer.parseInt(getIntent().getExtras().getString("total"));
        rightNum= Integer.parseInt(getIntent().getExtras().getString("right"));
        wrongNum= Integer.parseInt(getIntent().getExtras().getString("wrong"));
        if(rightNum<0 || wrongNum<0 || totalNum<=0 || (rightNum+wrongNum)>totalNum){
            negetivePointPercent.setText("اطلاعات اشتباه است!");
            withoutNegetivePointPercent.setText("اطلاعات اشتباه است!");
            totalQ.setText(getPersianNum(totalNum)+"");
            right.setText("ص : -");
            wrong.setText("غ : -");
            notAnswered.setText("ن : -");
        }
        else{
            makePieChart(rightNum,wrongNum,totalNum-(rightNum+wrongNum),totalNum);
            double x=((float)(rightNum-((float)wrongNum/3.0))/(float)totalNum)*100;
            double y=((float)rightNum/(float) totalNum)*100;
            DecimalFormat df=new DecimalFormat("##.##");
            x=Double.valueOf(df.format(x));
            y=Double.valueOf(df.format(y));
            negetivePointPercent.setText(getPersianNum((int)x)+"."+getPersianNum((int)((x-(int)x)*100))+"");
            withoutNegetivePointPercent.setText(getPersianNum((int)y)+"."+getPersianNum((int)((y-(int)y)*100))+"");
//            withoutNegetivePointPercent.setText(new DecimalFormat("##.##").format(y));
            totalQ.setText(getPersianNum(totalNum)+"");
            right.setText("ص : "+getPersianNum(rightNum));
            wrong.setText("غ : "+getPersianNum(wrongNum));
            notAnswered.setText("ن : "+getPersianNum(totalNum-rightNum-wrongNum));
        }
    }

    @Override
    public void onClick(View v) {
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
