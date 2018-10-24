package com.goharshad.arena.sakoo;

import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;

public class ChartMain extends AppCompatActivity implements TabHost.OnTabChangeListener {

    private FragmentTabHost chartsTabhost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_main);
        chartsTabhost= (FragmentTabHost) findViewById(R.id.charts_fragment_tabhost);
        chartsTabhost.setup(ChartMain.this,getSupportFragmentManager(),android.R.id.tabcontent);

        chartsTabhost.addTab(chartsTabhost.newTabSpec("tab1").setIndicator(getResources().getString(R.string.txt_today))
                ,ChartToday.class,null);
        chartsTabhost.addTab(chartsTabhost.newTabSpec("tab3").setIndicator(getResources().getString(R.string.txt_avg))
                ,ChartAvg.class,null);
        chartsTabhost.addTab(chartsTabhost.newTabSpec("tab2").setIndicator(getResources().getString(R.string.txt_dars_mehvar))
                ,ChartDarsMehvar.class,null);
        chartsTabhost.addTab(chartsTabhost.newTabSpec("tab4").setIndicator(getResources().getString(R.string.txt_madreseman))
                ,ChartMadreseMan.class,null);

        chartsTabhost.getTabWidget().setStripEnabled(false);
        chartsTabhost.getTabWidget().setRightStripDrawable(null);
        chartsTabhost.getTabWidget().setLeftStripDrawable(null);
        chartsTabhost.getTabWidget().setDividerDrawable(new ColorDrawable(getResources().getColor(R.color.colorOrangeNormal)));
        chartsTabhost.setCurrentTab(3);
        initTabWidget();
        chartsTabhost.setOnTabChangedListener(this);

    }


    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.chart_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_37dp);

        TextView toolbar_txt = (TextView) toolbar.findViewById(R.id.purposing_activity_name);
        toolbar_txt.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/IRAN-SANS.TTF"));
    }

    @Override
    public void onTabChanged(String tabId) {
        initTabWidget();
    }

    private void initTabWidget() {
        for (int i = 0; i < chartsTabhost.getTabWidget().getChildCount(); i++) {
            View view= chartsTabhost.getTabWidget().getChildAt(i);
            TextView textView= (TextView)view.findViewById(android.R.id.title);
            if(i == chartsTabhost.getCurrentTab()){
                textView.setTextColor(getResources().getColor(R.color.colorGreenBlue_2));
                textView.setShadowLayer(2.5f,.5f,.5f,getResources().getColor(R.color.colorDark));
            }else{
                textView.setTextColor(getResources().getColor(R.color.colorDark_1));
                textView.setShadowLayer(0,0,0,0);
            }
            textView.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/IRAN-SANS.TTF"));
            textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP,13);
        }
    }
}
