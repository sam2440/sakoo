package com.goharshad.arena.sakoo;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.List;

public class DailyReport extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DailyReportAdapter adapter;
    private List<DailyReportObject> list;
    private TaklifDatabase database;
    private TextView tv1,tv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_report);
        initToolbar();
        recyclerView = (RecyclerView) findViewById(R.id.daily_report_recycler);
        tv1= (TextView) findViewById(R.id.daily_report_no_report_txt);
        tv2= (TextView) findViewById(R.id.daily_report_header);

        database=new TaklifDatabase(this);
        list=database.getDailyReports();
        Typeface typeface=Typeface.createFromAsset(getAssets(),"fonts/IRAN-SANS.TTF");
        if (list.isEmpty()){
            tv2.setVisibility(View.GONE);
            tv1.setTypeface(typeface);
        }else{
            tv1.setVisibility(View.GONE);
            tv2.setTypeface(typeface);
            adapter = new DailyReportAdapter(this, list);

            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);
        }
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.dailyreporttoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_37dp);

        TextView toolbar_txt = (TextView) toolbar.findViewById(R.id.daily_report_activity_name);
        toolbar_txt.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/IRAN-SANS.TTF"));
    }
}
