package com.goharshad.arena.sakoo;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import ir.huri.jcal.JalaliCalendar;

public class FirstLaunchActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener, ViewPager.PageTransformer {
    private ViewPager pager;
    private ImageView img_next;
    private PagerAdapter adapter;
    private TextView tv_finish, tv_start;
    private TextView[] dots;
    private LinearLayout dotsLayout;
    private PreferencesManager manager;
    private RelativeLayout sliderControllersContainer;
    private LightnerDatabase lightnerDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        manager = new PreferencesManager(FirstLaunchActivity.this);
        if(! manager.isFirstLaunch()){
            launchHomeScreen();
        }
        lightnerDatabase=new LightnerDatabase(FirstLaunchActivity.this,"laytner",null,1);
        lightnerDatabase.insertTotalWordsNumTableEveryday();
//        manager.setInstallationWeekNumber(new JalaliCalendar().get);
        manager.setSavedWeeklyDate(System.currentTimeMillis());
        manager.setSavedMonthlyDate(System.currentTimeMillis());
        manager.setDate(System.currentTimeMillis());
        manager.setFirstLaunch(false);
        setContentView(R.layout.activity_first_launch);
        getSupportActionBar().hide();
        findViews();

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) sliderControllersContainer.getLayoutParams();
        params.height = (int) (.109 * DimenHelper.getDeviceHeight(this));
        sliderControllersContainer.setLayoutParams(params);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/IRAN-SANS.TTF");
        tv_finish.setTypeface(typeface);
        tv_start.setTypeface(typeface);
        tv_finish.setOnClickListener(this);
        tv_start.setOnClickListener(this);
        img_next.setOnClickListener(this);

        if (Build.VERSION.SDK_INT >= 22) {
            adapter = new FirstLaunchFragmentPagerAdapter(getSupportFragmentManager(), this);
        } else {
            adapter = new FirstLaunchAdapter(this);
        }
        pager.setAdapter(adapter);
        pager.addOnPageChangeListener(this);
        addBottomDots(0);
    }

    private void launchHomeScreen() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void addBottomDots(int currentPage) {
        dots = new TextView[pager.getAdapter().getCount()];

        int[] colorsInactive = new int[]{
                Color.argb(150, 255, 255, 255), Color.argb(150, 255, 255, 255),
                Color.argb(150, 255, 255, 255), Color.argb(150, 255, 255, 255),
                Color.argb(150, 255, 255, 255), Color.argb(150, 255, 255, 255),
                Color.argb(150, 255, 255, 255)
        };
        int[] colorsActive = new int[]{
                Color.argb(255, 255, 255, 255), Color.argb(255, 255, 255, 255),
                Color.argb(255, 255, 255, 255), Color.argb(255, 255, 255, 255),
                Color.argb(255, 255, 255, 255), Color.argb(255, 255, 255, 255),
                Color.argb(255, 255, 255, 255)
        };

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorsInactive[currentPage]);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(colorsActive[currentPage]);
    }

    private void findViews() {
        pager = (ViewPager) findViewById(R.id.view_pager);
        img_next = (ImageView) findViewById(R.id.btn_next);
        dotsLayout = (LinearLayout) findViewById(R.id.layoutDots);
        tv_finish = (TextView) findViewById(R.id.btn_skip);
        tv_start = (TextView) findViewById(R.id.btn_start);
        sliderControllersContainer = (RelativeLayout) findViewById(R.id.slider_controllers_container);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_next:
                int pos = adapter instanceof FirstLaunchFragmentPagerAdapter ?
                        ((FirstLaunchFragmentPagerAdapter) adapter).getPos() :
                        ((FirstLaunchAdapter) adapter).getPos();
                if (pager.getAdapter().getCount() - 1 == pos) {
                    launchHomeScreen();
                } else {
                    if (adapter instanceof FirstLaunchFragmentPagerAdapter)
                        ((FirstLaunchFragmentPagerAdapter) adapter).setPos(++pos);
                    else
                        ((FirstLaunchAdapter) adapter).setPos(++pos);
                    pager.setCurrentItem(pos);
                }
                break;
            case R.id.btn_skip:
            case R.id.btn_start:
                launchHomeScreen();
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
//        adapter.setPos(position);

        if (adapter instanceof FirstLaunchFragmentPagerAdapter)
            ((FirstLaunchFragmentPagerAdapter) adapter).setPos(position);
        else
            ((FirstLaunchAdapter) adapter).setPos(position);

        addBottomDots(adapter instanceof FirstLaunchFragmentPagerAdapter ?
                ((FirstLaunchFragmentPagerAdapter) adapter).getPos() :
                ((FirstLaunchAdapter) adapter).getPos());

        if (position == adapter.getCount() - 1) {
            img_next.setVisibility(View.GONE);
            tv_finish.setVisibility(View.GONE);
            tv_start.setVisibility(View.VISIBLE);
        } else {
            // still pages are left;
            img_next.setVisibility(View.VISIBLE);
            tv_finish.setVisibility(View.VISIBLE);
            tv_start.setVisibility(View.GONE);
        }
//        if(adapter.getCount()-1 == position){
////            setParams(tv_start_program);
//            sliderControllersContainer.setVisibility(View.GONE);
//            tv_start_program.setVisibility(View.VISIBLE);
//        }
//        else{
//            sliderControllersContainer.setVisibility(View.VISIBLE);
//            tv_start_program.setVisibility(View.GONE);
//        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public void transformPage(View page, float position) {
        page.setAlpha(1 - Math.abs(position));
    }


//    private void setParams(TextView tv) {
//        RelativeLayout.LayoutParams params= (RelativeLayout.LayoutParams) tv.getLayoutParams();
//        params.width= (int) getDeviceWidth();
//        params.height= (int)getGridHeight();
//        params.bottomMargin= (int) - (.3 * params.height);
//        tv.setLayoutParams(params);
//
//        tv.setPadding(0,(int) (params.height * .035),(int) (params.height * .15),0);
//        tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP,29);
//        tv.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/iransans_bold.ttf"));
//    }

}

