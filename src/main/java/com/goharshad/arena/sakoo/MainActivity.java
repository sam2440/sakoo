package com.goharshad.arena.sakoo;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import ir.huri.jcal.JalaliCalendar;

import static android.view.View.GONE;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, OnFragmentRemoved, OnFragmentLessonSelected, OnWeeklyScheduleEntered, TimePicker.OnTimeChangedListener, OnBazdehiUpdateListener {

    private TextView mainPageAdvice, barnameHaftegiVaredKonim, takalifMan, taklifManAfter, taklifManAfter1, motaleEmrooz, todayDatePersianDay, todayDatePersianMonth, notifCount,
            calendarToday, calendarNextday, calendarNextday2, calendarNextday3, inFlagTime, inProgressTime;
    private TextView bazdehiTxt, moadelMahaneTxt, rotbeDarMadreseTxt, rotbeDarClassTxt, avgMahaneTxt, majmooHaftegiTxt;
    private TextView moshaverName;
    private RelativeLayout darkOppacity;
    private LockableScrollView scrollMain;
    private LinearLayout scrollMainChild, fragmentContainer, taklifVaredKonidContainer, kadr_ha_dg_container;
    private RelativeLayout kadr_bazdehi;
    private RelativeLayout kadr_majmoo;
    private LinearLayout kadr_miangin;
    private PreferencesManager preferenceManager;
    private ExpandableListView takalifFardaContainer, takalifEmroozContainer, takalifJamondeContainer;
    private TaklifAdapter takalifFardaAdapter, takalifEmroozAdapter, takalifJamondeAdapter;
    private List<Taklif> takalifFardaList, takalifEmroozList, takalifJamondeList;
    private ImageView drawerToggler, ezafeKardaneTaklifeRooz, btnNotif, btnInfo, btnCalendar, motaleEmroozMoreAdd;
    private ImageView btnPickTime, moshaverImg;
    private ImageView btnPickNoTime;
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private TimePicker motaleDarsTimePicker;
    private CircularAnimatableProgressBar animatableProgressBar;
    private static String subjects[];

    private LessonDatabase lessonDatabase;
    private TaklifDatabase taklifDatabase;
    private PurposingDatabase purposingDatabase;
    private JalaliCalendar date;

    private int currentTime = 0, timePicked;
    private String weekdayStr = null;
    public boolean isToAddMoreStudy = false, backPressed, isToUpdateStudyTime = false, isToUndoTaklifDone = false;
    private String studiedLesson = null;

    public TaklifDatabase getTaklifDatabase() {
        backPressed = false;
        return taklifDatabase;
    }

    public LessonDatabase getLessonDatabase() {
        backPressed = false;
        return lessonDatabase;
    }

    public TextView getBazdehiTxt() {
        return bazdehiTxt;
    }

    public TextView getMajmooHaftegiTxt() {
        return majmooHaftegiTxt;
    }

    public TextView getAvgMahaneTxt() {
        return avgMahaneTxt;
    }

    public CircularAnimatableProgressBar getAnimatableProgressBar() {
        return animatableProgressBar;
    }

    public TextView getInProgressTime() {
        return inProgressTime;
    }

    @Override
    protected void onStart() {
        sendBroadCast();
        super.onStart();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        backPressed = false;
        date = new JalaliCalendar();
        weekdayStr = date.getDayOfWeekString();
        lessonDatabase = new LessonDatabase(this);
        taklifDatabase = new TaklifDatabase(MainActivity.this);
        purposingDatabase = new PurposingDatabase(MainActivity.this);
        preferenceManager = new PreferencesManager(this);
        findViews();
        initMenuItems();
        setFonts();
        initMoshaver();
        initVaredBarnameInfoDialog(getResources().getString(R.string.notif_varedkardane_barname));

        motaleDarsTimePicker.setIs24HourView(true);
        motaleDarsTimePicker.setOnTimeChangedListener(this);

        if (!preferenceManager.isEnteredSchedule()) {
            initIfNotEnteredSchedule();
        } else {
            initIfEnteredSchedule();
        }

        if (preferenceManager.isFilledPurposes()) {
            this.currentTime = purposingDatabase.getTimeOf(weekdayStr);
            inFlagTime.setText(TimeHandler.getValidated(currentTime));
        } else {
            inFlagTime.setText("00:00");
        }

        avgMahaneTxt.setText((preferenceManager.getMonthlyAvg() > 0 ? preferenceManager.getMonthlyAvg() : 0) + "");
        mainPageAdvice.setText(getRandomAdvice());
        todayDatePersianDay.setText(date.getDay() + "");
        todayDatePersianMonth.setText(date.getMonthString());
        calendarToday.setText(date.getDayOfWeekString());
        calendarNextday.setText(date.getTomorrow().getDayOfWeekString());
        calendarNextday2.setText(date.getTomorrow().getTomorrow().getDayOfWeekString());
        calendarNextday3.setText(date.getTomorrow().getTomorrow().getTomorrow().getDayOfWeekString());

        updateBazdehiTxt();
        updateWeeklySum();
        updateInprogressTime();

        initListeners();
        initToolbar();
        initDrawer();
        updateNotifs();
        preferenceManager.setAppInForground(false);
    }

    public void unDoTaklif() {
        updateBazdehiTxt();
        updateWeeklySum();
        updateInprogressTime();
        isToUndoTaklifDone = false;
    }

    private void initVaredBarnameInfoDialog(String msg) {
        if (!preferenceManager.isEnteredSchedule() && !preferenceManager.isCheckedInfoDialog() && preferenceManager.isAppInForground()) {
            View view = LayoutInflater.from(this).inflate(R.layout.layout_vared_barname_info, null);
            final AlertDialog dialog = new AlertDialog.Builder(this).setView(view).create();
            TextView tv1 = (TextView) view.findViewById(R.id.layout_vared_barname_title);
            TextView tv2 = (TextView) view.findViewById(R.id.layout_vared_barname_txt);
            TextView tv3 = (TextView) view.findViewById(R.id.layout_vared_barname_checkbox_txt);
            Button button = (Button) view.findViewById(R.id.layout_vared_barname_btn_ok);
            final CheckBox checkBox = (CheckBox) view.findViewById(R.id.layout_vared_barname_checkbox);
            tv2.setText(msg);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (checkBox.isChecked()) {
                        preferenceManager.setIsCheckedInfoDialog(true);
                    }
                    dialog.dismiss();
                }
            });

            Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/IRAN-SANS.TTF");
            tv1.setTypeface(typeface);
            tv2.setTypeface(typeface);
            tv3.setTypeface(typeface);
            button.setTypeface(typeface);
            dialog.show();
        }
    }

    private void initMoshaver() {
        if (preferenceManager.isAppInForground()) {
            Random random = new Random();
            int randNum = random.nextInt(2);
            if (randNum == 0) {
                moshaverName.setText("محمد نجانی");
                moshaverImg.setImageResource(R.drawable.najani);
            } else if (randNum == 1) {
                moshaverName.setText("رضا صادقیان پور");
                moshaverImg.setImageResource(R.drawable.reza_sadeqian_poor);
            }
        }
    }

    private void initListeners() {
        drawerToggler.setOnClickListener(this);
        barnameHaftegiVaredKonim.setOnClickListener(this);
        ezafeKardaneTaklifeRooz.setOnClickListener(this);
        taklifVaredKonidContainer.setOnClickListener(this);
        btnPickTime.setOnClickListener(this);
        btnPickNoTime.setOnClickListener(this);
        motaleEmroozMoreAdd.setOnClickListener(this);
        kadr_ha_dg_container.setOnClickListener(this);
        kadr_miangin.setOnClickListener(this);
        motaleEmroozMoreAdd.setOnClickListener(this);
    }

    private void updateInprogressTime() {
        int history = preferenceManager.getCurrentStudyTime();
        int res = 0;
        if (timePicked != 0 && isToUpdateStudyTime) {
            res = history + timePicked - taklifDatabase.getStudyTimeOf(TaklifAdapter.getCurrentLesson(), TaklifAdapter.getCurrentTaklif(), TaklifAdapter.getCurrentStatus());
            animateIntValue(history, res, inProgressTime);
            preferenceManager.setCurrentStudyTime(res);
        } else if (timePicked != 0 && !isToUpdateStudyTime) {
            res = history + timePicked;
            animateIntValue(history, res, inProgressTime);
            preferenceManager.setCurrentStudyTime(res);
        } else if (isToUndoTaklifDone) {
            res = history - taklifDatabase.getStudyTimeOf(TaklifAdapter.getCurrentLesson(), TaklifAdapter.getCurrentTaklif(), TaklifAdapter.getCurrentStatus());
            animateIntValue(history, res, inProgressTime);
            preferenceManager.setCurrentStudyTime(res);
        } else {
            animateIntValue(0, preferenceManager.getCurrentStudyTime(), inProgressTime);
        }

        animatableProgressBar.setProgressWithAnimation(getCurrentProgress());
    }

    private void updateWeeklySum() {
        int history = preferenceManager.getWeeklySum();
        int res = 0;
        if (timePicked != 0 && isToUpdateStudyTime) {
            res = history + timePicked - taklifDatabase.getStudyTimeOf(TaklifAdapter.getCurrentLesson(), TaklifAdapter.getCurrentTaklif(), TaklifAdapter.getCurrentStatus());
            animateIntValue(history, res, majmooHaftegiTxt);
            preferenceManager.setWeeklySum(res);
        } else if (timePicked != 0 && !isToUpdateStudyTime) {
            res = history + timePicked;
            animateIntValue(history, res, majmooHaftegiTxt);
            preferenceManager.setWeeklySum(res);
        } else if (isToUndoTaklifDone) {
            res = history - taklifDatabase.getStudyTimeOf(TaklifAdapter.getCurrentLesson(), TaklifAdapter.getCurrentTaklif(), TaklifAdapter.getCurrentStatus());
            /*update weekly sum animation*/
            animateIntValue(history, res, majmooHaftegiTxt);
            preferenceManager.setWeeklySum(res);
        } else {
            animateIntValue(0, history, majmooHaftegiTxt);
        }
    }

    private void updateBazdehiTxt() {
        int suggested = 0;
        float bazdehi = preferenceManager.getBazdehi();
        if (preferenceManager.isFilledPurposes()) {
            if ((suggested = purposingDatabase.getSuggestedOf(weekdayStr)) != 0) {
                if (bazdehi >= 100) {
                    bazdehiTxt.setText("100");
                } else {
                    double res = 0;
                    double history = preferenceManager.getCurrentStudyTime();
                    if (isToUpdateStudyTime) {
                        res = (history + timePicked - taklifDatabase.getStudyTimeOf(TaklifAdapter.getCurrentLesson(), TaklifAdapter.getCurrentTaklif(), TaklifAdapter.getCurrentStatus())) / (1.0 * suggested);
                    } else if (isToUndoTaklifDone) {
                        res = (history - taklifDatabase.getStudyTimeOf(TaklifAdapter.getCurrentLesson(), TaklifAdapter.getCurrentTaklif(), TaklifAdapter.getCurrentStatus())) / (1.0 * suggested);
                    } else {
                        res = (history + timePicked) / (1.0 * suggested);
                    }

                    res *= 100;
                    if (res >= 100) {
                        animateIntValue(((int) preferenceManager.getBazdehi()), 100, bazdehiTxt);
                    } else {
                        animateFloatValue(preferenceManager.getBazdehi(), (float) res, bazdehiTxt);
                        if (res <= .00) {
                            bazdehiTxt.setText("0");
                        }
                    }
                    preferenceManager.setBazdehi(((float) res));
                }
            } else if (bazdehi <= 0) {
                bazdehiTxt.setText("0");
            }
        }
    }

    public void animateIntValue(int from, int to, final TextView tv) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(from, ((int) (to * .95)));
        valueAnimator.setDuration(1100);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (!tv.equals(bazdehiTxt))
                    tv.setText(TimeHandler.getValidated(Integer.parseInt(animation.getAnimatedValue().toString())));
                else
                    tv.setText(animation.getAnimatedValue().toString());
            }
        });
        valueAnimator.start();

        ValueAnimator valueAnimator1 = ValueAnimator.ofInt(((int) (to * .95)), to);
        valueAnimator1.setDuration(500);
        valueAnimator1.setStartDelay(1000);
        valueAnimator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (!tv.equals(bazdehiTxt))
                    tv.setText(TimeHandler.getValidated(Integer.parseInt(animation.getAnimatedValue().toString())));
                else
                    tv.setText(animation.getAnimatedValue().toString());
            }
        });
        valueAnimator1.start();
    }

    public void animateFloatValue(float from, final float to, final TextView tv) {
        final ValueAnimator valueAnimator = ValueAnimator.ofFloat(from, to);
        valueAnimator.setDuration(1100);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Double d = Double.parseDouble(animation.getAnimatedValue().toString());
                DecimalFormat format = new DecimalFormat("#.00");
                tv.setText(format.format(d));
            }
        });
        valueAnimator.start();

        ValueAnimator valueAnimator1 = ValueAnimator.ofFloat(((float) (to * .95)), to);
        valueAnimator1.setDuration(500);
        valueAnimator1.setStartDelay(1000);
        valueAnimator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Double d = Double.parseDouble(animation.getAnimatedValue().toString());
                DecimalFormat format = new DecimalFormat("#.00");
                tv.setText(format.format(d));
            }
        });
        valueAnimator1.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (valueAnimator.getCurrentPlayTime() >= 499) {
                    if (bazdehiTxt != null) {
                        if (to == 0) {
                            bazdehiTxt.setText("0");
                        }
                    }
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {
            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        valueAnimator1.start();
    }


    private void setFonts() {
        Typeface iransansTF = Typeface.createFromAsset(getAssets(), "fonts/IRAN-SANS.TTF");
        Typeface koodakTF = Typeface.createFromAsset(getAssets(), "fonts/B Koodak Bold.TTF");

        barnameHaftegiVaredKonim.setTypeface(iransansTF);
        takalifMan.setTypeface(iransansTF);
        motaleEmrooz.setTypeface(iransansTF);
        mainPageAdvice.setTypeface(iransansTF);
        todayDatePersianMonth.setTypeface(iransansTF);
        taklifManAfter.setTypeface(iransansTF);
        taklifManAfter1.setTypeface(iransansTF);
        calendarToday.setTypeface(iransansTF);
        calendarNextday.setTypeface(iransansTF);
        calendarNextday2.setTypeface(iransansTF);
        calendarNextday3.setTypeface(iransansTF);

        todayDatePersianDay.setTypeface(koodakTF);
        inFlagTime.setTypeface(koodakTF);
        inProgressTime.setTypeface(koodakTF);
        bazdehiTxt.setTypeface(koodakTF);
        majmooHaftegiTxt.setTypeface(koodakTF);
        moadelMahaneTxt.setTypeface(koodakTF);
        rotbeDarMadreseTxt.setTypeface(koodakTF);
        rotbeDarClassTxt.setTypeface(koodakTF);
        avgMahaneTxt.setTypeface(koodakTF);
//        notifCount.setTypeface(koodakTF);

        setFont(R.id.main_page_moshaver_gender, iransansTF);
        setFont(R.id.main_page_moshaver_name, iransansTF);
        setFont(R.id.main_page_txt_1, iransansTF);
        setFont(R.id.main_page_txt_2, iransansTF);
        setFont(R.id.main_page_txt_3, iransansTF);
        setFont(R.id.main_page_txt_4, iransansTF);
        setFont(R.id.main_page_txt_5, iransansTF);
        setFont(R.id.main_page_txt_6, iransansTF);
        setFont(R.id.main_page_emroz_iv, iransansTF);
        setFont(R.id.main_page_farda_iv, iransansTF);
        setFont(R.id.main_page_jamande_iv, iransansTF);

    }

    private void setFont(@IdRes int id, Typeface tf) {
        TextView textView = (TextView) findViewById(id);
        textView.setTypeface(tf);
    }

    private void sendBroadCast() {
        IntentFilter filter = new IntentFilter("android.intent.action.TIME_TICK");
        DailyUpdateReciever reciever = new DailyUpdateReciever();
        this.registerReceiver(reciever, filter);
    }

    private void initMenuItems() {
        NavigationView drawerView = (NavigationView) findViewById(R.id.nav_view);
        TextView drawerUsername, drawerGrade, drawerSchool, drawerItemGroupTools, drawerItemGroupSpeedBox, drawerItemGroupTogether,
                drawerItemLightner, drawerItemPercent, drawerItemAnswersheet, drawerItemPurposing, drawerItemCharts, drawerItemGuide,
                drawerItemSuport, drawerItemSuggestions, drawerItemAbout;
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/IRAN-SANS.TTF");
        drawerGrade = (TextView) drawerView.findViewById(R.id.drawer_item_grade);
        drawerItemGroupTools = (TextView) drawerView.findViewById(R.id.drawer_item_group_tools);
        drawerItemGroupSpeedBox = (TextView) drawerView.findViewById(R.id.drawer_item_group_speedbox);
        drawerItemGroupTogether = (TextView) drawerView.findViewById(R.id.drawer_item_group_together);
        drawerItemLightner = (TextView) drawerView.findViewById(R.id.drawer_item_lightner);
        drawerItemPercent = (TextView) drawerView.findViewById(R.id.drawer_item_calc_percent);
        drawerItemAnswersheet = (TextView) drawerView.findViewById(R.id.drawer_item_answersheet);
        drawerItemPurposing = (TextView) drawerView.findViewById(R.id.drawer_item_purpose);
        drawerItemCharts = (TextView) drawerView.findViewById(R.id.drawer_item_chart);
        drawerItemGuide = (TextView) drawerView.findViewById(R.id.drawer_item_howto);
        drawerItemSuport = (TextView) drawerView.findViewById(R.id.drawer_item_support);
        drawerItemSuggestions = (TextView) drawerView.findViewById(R.id.drawer_item_comments);
        drawerItemAbout = (TextView) drawerView.findViewById(R.id.drawer_item_location);

        drawerGrade.setTypeface(typeface);
        drawerItemGroupTools.setTypeface(typeface);
        drawerItemGroupSpeedBox.setTypeface(typeface);
        drawerItemGroupTogether.setTypeface(typeface);
        drawerItemLightner.setTypeface(typeface);
        drawerItemPercent.setTypeface(typeface);
        drawerItemAnswersheet.setTypeface(typeface);
        drawerItemPurposing.setTypeface(typeface);
        drawerItemCharts.setTypeface(typeface);
        drawerItemGuide.setTypeface(typeface);
        drawerItemSuport.setTypeface(typeface);
        drawerItemSuggestions.setTypeface(typeface);
        drawerItemAbout.setTypeface(typeface);

        if (preferenceManager.isEnteredSchedule()) {
            drawerGrade.setText("  مقطع " + preferenceManager.getStudentGrade());
        } else {
            drawerGrade.setText("  مقطع تحصیلی انتخاب نشده");
        }
    }

    private float getCurrentProgress() {
        backPressed = false;
        int progress, studied = preferenceManager.getCurrentStudyTime();
        if (preferenceManager.isFilledPurposes() && currentTime != 0) {
            progress = (int) ((75 * studied) / (currentTime * 1.0));
            progress = progress > 75 ? 75 : progress;
        } else if (studied > 0) {
            progress = 75;
        } else {
            progress = 0;
        }
        return progress;
    }

    private void initIfNotEnteredSchedule() {
        barnameHaftegiVaredKonim.setPadding(10, 45, 10, 45);
    }

    private String getRandomAdvice() {
        backPressed = false;
        String[] advices = getResources().getStringArray(R.array.advises);
        Random random = new Random();
        int rand = random.nextInt(advices.length);
        return advices[rand];
    }

    private void getTaklifList(Map<String, String> map, List<Taklif> taklifList, TaklifStatus status) {
        backPressed = false;
        Taklif taklif;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            taklif = new Taklif(entry.getKey(), entry.getValue(), status);
            Log.d("TAKLIF STRING : ", taklif.toString());
            taklifList.add(taklif);
        }
        Log.d("TAG", "list size : " + taklifList.size());
    }

    private void initIfEnteredSchedule() {
        backPressed = false;
        taklifManAfter.setVisibility(View.VISIBLE);
        barnameHaftegiVaredKonim.setText(" برنامه فردا" + "\n" + lessonDatabase.getScheduleOf(date.getTomorrow().getDayOfWeekString()));
        barnameHaftegiVaredKonim.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
        barnameHaftegiVaredKonim.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        barnameHaftegiVaredKonim.setPadding(10, 10, 10, 10);

        takalifFardaList = new ArrayList<>();
        getTaklifList(taklifDatabase.getTaklifWithStatus(TaklifStatus.TOMORROWED), takalifFardaList, TaklifStatus.TOMORROWED);
        takalifEmroozList = new ArrayList<>();
        getTaklifList(taklifDatabase.getTaklifWithStatus(TaklifStatus.TODAY), takalifEmroozList, TaklifStatus.TODAY);
        getTaklifList(taklifDatabase.getTaklifWithStatus(TaklifStatus.TODAY_DONE), takalifEmroozList, TaklifStatus.TODAY_DONE);
        takalifJamondeList = new ArrayList<>();
        getTaklifList(taklifDatabase.getTaklifWithStatus(TaklifStatus.DELAYED), takalifJamondeList, TaklifStatus.DELAYED);

        takalifFardaAdapter = new TaklifAdapter(MainActivity.this, R.layout.lesson_taklif_list_item, takalifFardaList);
        takalifEmroozAdapter = new TaklifAdapter(MainActivity.this, R.layout.lesson_taklif_list_item, takalifEmroozList);
        takalifJamondeAdapter = new TaklifAdapter(MainActivity.this, R.layout.lesson_taklif_list_item, takalifJamondeList);

        takalifFardaContainer.setAdapter(takalifFardaAdapter);
        takalifEmroozContainer.setAdapter(takalifEmroozAdapter);
        takalifJamondeContainer.setAdapter(takalifJamondeAdapter);

        takalifFardaContainer.setDivider(null);
        takalifFardaContainer.setDividerHeight(0);

        takalifEmroozContainer.setDivider(null);
        takalifEmroozContainer.setDividerHeight(0);

        takalifJamondeContainer.setDivider(null);
        takalifJamondeContainer.setDividerHeight(0);
    }

    private void removeClickables() {
        backPressed = false;
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        barnameHaftegiVaredKonim.setClickable(false);
        barnameHaftegiVaredKonim.setLongClickable(false);
        ezafeKardaneTaklifeRooz.setClickable(false);
        toolbar.setClickable(false);
        drawerToggler.setClickable(false);
        kadr_ha_dg_container.setClickable(false);
        kadr_miangin.setClickable(false);
        taklifVaredKonidContainer.setClickable(false);
        motaleEmroozMoreAdd.setClickable(false);

        if (takalifFardaAdapter!=null){
            takalifFardaAdapter.setEnabled(false);
            takalifFardaAdapter.areAllItemsEnabled();
        }
        if (takalifEmroozAdapter!=null){
            takalifEmroozAdapter.setEnabled(false);
            takalifEmroozAdapter.areAllItemsEnabled();
        }
        if (takalifJamondeAdapter!=null){
            takalifJamondeAdapter.setEnabled(false);
            takalifJamondeAdapter.areAllItemsEnabled();
        }
//        takalifJamondeContainer.setFocusable(false);
//        takalifJamondeContainer.setFocusableInTouchMode(false);
//        takalifEmroozContainer.setFocusable(false);
//        takalifEmroozContainer.setFocusableInTouchMode(false);
//        takalifFardaContainer.setFocusable(false);
//        takalifFardaContainer.setFocusableInTouchMode(false);
    }

    private void initDrawer() {
        backPressed = false;
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void initToolbar() {
        toolbar.setNavigationOnClickListener(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(MainActivity.this, R.color.toolbar_bg)));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.toolbar_bg));
        }
    }

    private void updateNotifs() {
        backPressed = false;
//        notifCount.setText(NotificationHelper.getNotifsAmount() + "");
    }

    private void findViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggler = (ImageView) findViewById(R.id.drawer_toggler);
        barnameHaftegiVaredKonim = (TextView) findViewById(R.id.main_page_barname_haftegi_vared_konid);
        takalifMan = (TextView) findViewById(R.id.main_page_takalife_man);
        taklifManAfter1 = (TextView) findViewById(R.id.main_page_takalif_vared_konid);
        taklifManAfter = (TextView) findViewById(R.id.main_page_takalif_vared_konid_after);
        takalifFardaContainer = (ExpandableListView) findViewById(R.id.takalif_farda_container);
        takalifEmroozContainer = (ExpandableListView) findViewById(R.id.takalif_emrooz_container);
        takalifJamondeContainer = (ExpandableListView) findViewById(R.id.takalif_jaamoonde_container);
        motaleEmrooz = (TextView) findViewById(R.id.main_page_motalee_emroz);
        moshaverName = (TextView) findViewById(R.id.main_page_moshaver_name);
        moshaverImg = (ImageView) findViewById(R.id.main_page_moshaver_img);
        darkOppacity = (RelativeLayout) findViewById(R.id.main_page_make_bg_dark);
        scrollMain = (LockableScrollView) findViewById(R.id.main_page_scroll);
        scrollMainChild = (LinearLayout) findViewById(R.id.scrollview_child);
        taklifVaredKonidContainer = (LinearLayout) findViewById(R.id.main_page_takalif_vared_konid_container);
        todayDatePersianDay = (TextView) findViewById(R.id.main_page_day_of_month);
        todayDatePersianMonth = (TextView) findViewById(R.id.main_page_month_of_year);
        ezafeKardaneTaklifeRooz = (ImageView) findViewById(R.id.main_page_taklif_plus);
        fragmentContainer = (LinearLayout) findViewById(R.id.fragment_container);
//        notifCount = (TextView) findViewById(R.id.notif_count);
//        btnNotif = (ImageView) findViewById(R.id.btn_notif);
//        btnInfo = (ImageView) findViewById(R.id.btn_info);
        motaleEmroozMoreAdd = (ImageView) findViewById(R.id.main_page_motalee_emroz_plus_more);
//        btnCalendar = (ImageView) findViewById(R.id.ic_calendar);
        calendarToday = (TextView) findViewById(R.id.main_page_to_day);
        calendarNextday = (TextView) findViewById(R.id.main_page_next_day1);
        calendarNextday2 = (TextView) findViewById(R.id.main_page_next_day2);
        calendarNextday3 = (TextView) findViewById(R.id.main_page_next_day3);
        mainPageAdvice = (TextView) findViewById(R.id.main_page_advice);
        inFlagTime = (TextView) findViewById(R.id.in_flag_time);
        inProgressTime = (TextView) findViewById(R.id.in_progressbar_time);

        kadr_miangin = (LinearLayout) findViewById(R.id.miangine_mahane);
        kadr_ha_dg_container = (LinearLayout) findViewById(R.id.kadr_ha_dg_container);

        motaleDarsTimePicker = (TimePicker) findViewById(R.id.motale_dars_time_picker);
        btnPickTime = (ImageView) findViewById(R.id.btn_pick_time);
        btnPickNoTime = (ImageView) findViewById(R.id.btn_pick_no_time);
        animatableProgressBar = (CircularAnimatableProgressBar) findViewById(R.id.circular_animatable_progressbar);

        bazdehiTxt = (TextView) findViewById(R.id.bazdehi_txt);
        moadelMahaneTxt = (TextView) findViewById(R.id.moadel_mahane_txt);
        avgMahaneTxt = (TextView) findViewById(R.id.miangine_mahane_txt);
        rotbeDarClassTxt = (TextView) findViewById(R.id.rotbe_dar_class_txt);
        rotbeDarMadreseTxt = (TextView) findViewById(R.id.rotbe_dar_madrese_txt);
        majmooHaftegiTxt = (TextView) findViewById(R.id.majmo_haftegi_txt);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(Gravity.RIGHT)) {
            drawer.closeDrawer(Gravity.RIGHT);
        } else if (btnPickTime.getVisibility() == View.VISIBLE) {
            setClickables();
            btnPickTime.setVisibility(GONE);
            btnPickNoTime.setVisibility(GONE);
            motaleDarsTimePicker.setVisibility(GONE);
        } else {
            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
            if (fragment != null) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .remove(fragment)
                        .commit();
                setClickables();
                return;
            }
            backPressed = !backPressed;
            if (backPressed == false) {
                super.onBackPressed();
            } else {
                AlertHelper.makeToast(this, "برای خروج یکبار دیگر دکمه ی بازگشت را فشار دهید");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        backPressed = false;
                    }
                }, 2000);
            }
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        backPressed = false;
        switch (item.getItemId()) {
            case R.id.drawer_item_purpose:
                Intent intent = new Intent(MainActivity.this, Purposing.class);
                startActivity(intent);
                break;
        }
        drawer.closeDrawer(GravityCompat.END);
        return true;
    }

    @Override

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.drawer_toggler: {
                if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                    drawer.closeDrawer(Gravity.RIGHT);
                } else {
                    drawer.openDrawer(Gravity.RIGHT);
                }
                break;
            }
            case R.id.miangine_mahane:
                AlertHelper.makeToast(this, getResources().getString(R.string.txt_updateeverymonth));
                break;
            case R.id.kadr_ha_dg_container:
                AlertHelper.makeToast(this, getResources().getString(R.string.txt_thisforschools));
                break;
            case R.id.main_page_barname_haftegi_vared_konid: {
                initBackgroundFeatures();
                removeClickables();
                setLayoutParams(fragmentContainer);
                openFragment(new VaredKardaneBarnameHaftegi());
                break;
            }
            case R.id.main_page_takalif_vared_konid_container:
            case R.id.main_page_taklif_plus: {
                if (preferenceManager.isEnteredSchedule()) {
                    if (lessonDatabase.hasTaklif(weekdayStr)) {
                        initBackgroundFeatures();
                        removeClickables();
                        setLayoutParams(fragmentContainer);
                        openFragment(new VaredKardaneTakalifeRoozane());
                    } else {
                        AlertHelper.makeDialog(this, R.string.txt_no_taklif_exists);
                    }
                } else {
                    initBackgroundFeatures();
                    removeClickables();
                    setLayoutParams(fragmentContainer);
                    openFragment(new VaredKardaneBarnameHaftegi());
                }
                break;
            }
//            case R.id.btn_info:
//                Intent intent = new Intent(MainActivity.this, Intro.class);
//                startActivity(intent);
//                break;
//                start
//            case R.id.btn_notif: {
//                if (NotificationHelper.getNotifsAmount() > 0) {
//                    Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
//                    if (fragment != null) {
//                        getSupportFragmentManager().beginTransaction().remove(fragment).commit();
//                        setClickabless();
//                    } else {
//                        initBackgroundFeatures();
//                        setLayoutParams(fragmentContainer);
//                        removeClickables();
//                        openFragment(new NotificationViewer());
//                    }
//                } else {
//                    Toast.makeText(this, "اعلانی برای نمایش وجود ندارد", Toast.LENGTH_SHORT).show();
//                }
//                break;
//            }
            case R.id.main_page_motalee_emroz_plus_more:
                if (preferenceManager.isEnteredSchedule()) {
                    isToAddMoreStudy = true;
                    initBackgroundFeatures();
                    setLayoutParams(fragmentContainer);
                    removeClickables();
                    openFragment(new EntekhabeDars());
                } else {
                    AlertHelper.makeToast(this, "برنامه درسی هنوز وارد نشده است");
                }
                break;
//            case R.id.ic_calendar:
//                Intent calendarIntent = new Intent(MainActivity.this, CalendarActivity.class);
//                startActivity(calendarIntent);
//                break;
            case R.id.btn_pick_no_time:
                isToUpdateStudyTime = false;
                isToAddMoreStudy = false;
                setClickables();
                motaleDarsTimePicker.setVisibility(GONE);
                btnPickTime.setVisibility(GONE);
                btnPickNoTime.setVisibility(GONE);
                break;
            case R.id.btn_pick_time:
                if (timePicked != 0) {
                    if (isToAddMoreStudy) {
                        isToAddMoreStudy = false;
                        updateBazdehiTxt();
                        updateWeeklySum();
                        updateInprogressTime();
//                    taklifDatabase.update(getDayOfWeek(weekDay),studiedLesson,TaklifStatus.DONE);
                    } else if (isToUpdateStudyTime) {
                        updateBazdehiTxt();
                        updateWeeklySum();
                        updateInprogressTime();
                        taklifDatabase.update(TaklifAdapter.getCurrentLesson(), TaklifAdapter.getCurrentTaklif(), timePicked);
                    } else {
                        taklifDatabase.update(TaklifAdapter.getCurrentLesson(), TaklifAdapter.getCurrentTaklif(), timePicked);
                        switch (TaklifAdapter.getCurrentStatus()) {
                            case TODAY:
                                taklifDatabase.update(TaklifAdapter.getCurrentLesson(), TaklifAdapter.getCurrentTaklif(), TaklifStatus.TODAY_DONE);
                                takalifEmroozList.clear();
                                getTaklifList(taklifDatabase.getTaklifWithStatus(TaklifStatus.TODAY), takalifEmroozList, TaklifStatus.TODAY);
                                getTaklifList(taklifDatabase.getTaklifWithStatus(TaklifStatus.TODAY_DONE), takalifEmroozList, TaklifStatus.TODAY_DONE);
                                takalifEmroozAdapter.notifyDataSetChanged();
                                break;
                            case TOMORROWED:
                                taklifDatabase.update(TaklifAdapter.getCurrentLesson(), TaklifAdapter.getCurrentTaklif(), TaklifStatus.TOMORROWED_DONE);
                                takalifFardaList.clear();
                                getTaklifList(taklifDatabase.getTaklifWithStatus(TaklifStatus.TOMORROWED), takalifFardaList, TaklifStatus.TOMORROWED);
                                getTaklifList(taklifDatabase.getTaklifWithStatus(TaklifStatus.TOMORROWED_DONE), takalifFardaList, TaklifStatus.TOMORROWED_DONE);
                                takalifFardaAdapter.notifyDataSetChanged();
                                break;
                            case DELAYED:
                                taklifDatabase.update(TaklifAdapter.getCurrentLesson(), TaklifAdapter.getCurrentTaklif(), TaklifStatus.DELAYED_DONE);
                                takalifJamondeList.clear();
                                getTaklifList(taklifDatabase.getTaklifWithStatus(TaklifStatus.DELAYED), takalifJamondeList, TaklifStatus.DELAYED);
                                getTaklifList(taklifDatabase.getTaklifWithStatus(TaklifStatus.DELAYED_DONE), takalifJamondeList, TaklifStatus.DELAYED_DONE);
                                takalifJamondeAdapter.notifyDataSetChanged();
                                break;
                            default:
                                break;
                        }
                        updateBazdehiTxt();
                        updateWeeklySum();
                        updateInprogressTime();
                    }
                    setClickables();
                    motaleDarsTimePicker.setVisibility(GONE);
                    btnPickTime.setVisibility(GONE);
                    btnPickNoTime.setVisibility(GONE);
//                    animatableProgressBar.setProgressWithAnimation(getCurrentProgress());
                    timePicked = 0;
                    isToAddMoreStudy = false;
                    isToUpdateStudyTime = false;

                } else {
                    AlertHelper.makeToast(this, getResources().getString(R.string.txt_timenotvalidated));
                }
                break;
        }
    }

    private void initBackgroundFeatures() {
        backPressed = false;
        darkOppacity.setBackgroundColor(Color.parseColor("#7f7f7f8f"));
        barnameHaftegiVaredKonim.setClickable(false);
        scrollMain.setScrollingEnabled(false);
    }

    private void openFragment(Fragment fragment) {
        backPressed = false;
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    private void setLayoutParams(ViewGroup layout) {
        backPressed = false;
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) layout.getLayoutParams();
        params.width = (int) (.82 * DimenHelper.getDeviceWidth(this));
        params.height = (int) (.85 * DimenHelper.getDeviceHeight(this));
        params.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        params.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
        layout.setLayoutParams(params);
    }

    public void makeNewFragmentLenght(ViewGroup fragment_container2, Activity activity) {
        backPressed = false;
        double width = DimenHelper.getDeviceWidth(activity), height = DimenHelper.getDeviceHeight(activity);
        double w1 = width * 0.64;
        double h1 = height * 0.7;
        double w = width * 0.82;
        double h = height * 0.85;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) w, (int) h);
        params.gravity = Gravity.CENTER;
//        params.setMargins((int) (w - w1) / 2, (int) (h - h1) / 2, (int) (w - w1) / 2, (int) (h - h1) / 2);
        fragment_container2.setLayoutParams(params);
    }

    public String[] getSubjects() {
        backPressed = false;
        return subjects;
    }

    public void setSubjects(String[] s) {
        backPressed = false;
        subjects = s;
    }

    @Override
    protected void onPause() {
        backPressed = false;
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (inFlagTime != null && preferenceManager != null && preferenceManager.isFilledPurposes()) {
//            weekDay = PersianCalendarHandler.getToday().getDayOfWeek();
            this.currentTime = purposingDatabase.getTimeOf(weekdayStr);
            inFlagTime.setText(TimeHandler.getValidated(currentTime));
            animatableProgressBar.setProgressWithAnimation(getCurrentProgress());
        }
        if (bazdehiTxt != null) {
            updateBazdehiTxt();
        }
    }

    @Override
    public void setClickables() {
        backPressed = false;
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        barnameHaftegiVaredKonim.setClickable(true);
        barnameHaftegiVaredKonim.setLongClickable(true);
        ezafeKardaneTaklifeRooz.setClickable(true);
        drawerToggler.setClickable(true);
        taklifVaredKonidContainer.setClickable(true);
        motaleEmroozMoreAdd.setClickable(true);

        takalifJamondeContainer.setEnabled(true);
        takalifEmroozContainer.setEnabled(true);
        takalifFardaContainer.setEnabled(true);

        toolbar.setClickable(true);
        kadr_ha_dg_container.setClickable(true);
        kadr_miangin.setClickable(true);

        darkOppacity.setBackgroundColor(Color.parseColor("#00000000"));
        scrollMain.setScrollingEnabled(true);
        updateNotifs();
    }

    @Override
    public void onSelect(String lesson) {
        backPressed = false;
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (fragment != null) {
            if (!isToAddMoreStudy) {
                EntekhabeDarsBarayeZang fragment1 = (EntekhabeDarsBarayeZang) fragment;
                fragment1.updateTextView(lesson);
            } else if (isToAddMoreStudy) {
                this.studiedLesson = lesson;
                onPickTime();
            }
        }
    }

    @Override
    public void onSheduleEntered() {
        backPressed = false;
        initHadafGozariInfo(getResources().getString(R.string.notif_fill_in_purposing));
        preferenceManager.setIsEnteredSchedule(true);
        NavigationView drawerView = (NavigationView) findViewById(R.id.nav_view);
        TextView drawerGrade = (TextView) drawerView.findViewById(R.id.drawer_item_grade);
        drawerGrade.setText("  مقطع " + preferenceManager.getStudentGrade());

        taklifManAfter.setVisibility(View.VISIBLE);
        barnameHaftegiVaredKonim.setText(" برنامه فردا" + "\n" + lessonDatabase.getScheduleOf(date.getTomorrow().getDayOfWeekString()));
        barnameHaftegiVaredKonim.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
        barnameHaftegiVaredKonim.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        barnameHaftegiVaredKonim.setPadding(10, 10, 10, 10);
    }

    private void initHadafGozariInfo(String msg) {
        if (!preferenceManager.isSeenPurposingDialog()) {
            preferenceManager.setSeenPurposingDialog(true);
            View view = LayoutInflater.from(this).inflate(R.layout.layout_vared_barname_info, null);
            final AlertDialog dialog = new AlertDialog.Builder(this).setView(view).create();
            TextView tv1 = (TextView) view.findViewById(R.id.layout_vared_barname_title);
            TextView tv2 = (TextView) view.findViewById(R.id.layout_vared_barname_txt);
            TextView tv3 = (TextView) view.findViewById(R.id.layout_vared_barname_checkbox_txt);
            Button button = (Button) view.findViewById(R.id.layout_vared_barname_btn_ok);
            CheckBox checkBox = (CheckBox) view.findViewById(R.id.layout_vared_barname_checkbox);
            tv3.setVisibility(View.GONE);
            checkBox.setVisibility(View.GONE);
            tv2.setText(msg);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/IRAN-SANS.TTF");
            tv1.setTypeface(typeface);
            tv2.setTypeface(typeface);
            button.setTypeface(typeface);
            dialog.show();
        }
    }

    public void onMenuItemClick(View view) {
        backPressed = false;
        Intent intent;
        switch (view.getId()) {
            case R.id.drawer_item_purpose_container:
                intent = new Intent(MainActivity.this, Purposing.class);
                startActivity(intent);
                break;
            case R.id.drawer_item_support_container:
                intent = new Intent(MainActivity.this, Support.class);
                startActivity(intent);
                break;
            case R.id.drawer_item_lightner_container:
                intent = new Intent(MainActivity.this, Lightner.class);
                startActivity(intent);
                break;
            case R.id.drawer_item_calc_percent_container:
                intent = new Intent(MainActivity.this, CalcPercentage.class);
                startActivity(intent);
                break;
            case R.id.drawer_item_location_container:
                intent = new Intent(MainActivity.this, Team.class);
                startActivity(intent);
                break;
            case R.id.drawer_item_answersheet_container:
                intent = new Intent(MainActivity.this, AnswerSheet.class);
                startActivity(intent);
                break;
            case R.id.drawer_item_chart_container:
                intent = new Intent(MainActivity.this, DailyReport.class);
                startActivity(intent);
                break;
            case R.id.drawer_item_comments_container:
                String url = "http://support@saku.goharshad8.ir";
                Uri uriUrl = Uri.parse(url);
                intent = new Intent(Intent.ACTION_VIEW, uriUrl);
                startActivity(intent);
                break;
            case R.id.drawer_item_howto_container:
                intent = new Intent(MainActivity.this, Intro.class);
                startActivity(intent);
                break;
            case R.id.drawer_item_group_tools_description:
                AlertDialog builder = new AlertDialog.Builder(this)
                        .setMessage(
                                "نجاری بدون اره و گیره و سمباده و ... مگه میشه ؟!" + "\n" +
                                        "شک نکن درس خوندن هم ابزار لازم داره." + "\n" +
                                        "ابزارهایی که کارتو راحت کنن و سریع تر به پیشرفت برسوننت."
                        ).show();
                builder.getWindow().setBackgroundDrawableResource(R.drawable.rounded_rectangle_whitesmoke);
                TextView textView = (TextView) builder.findViewById(android.R.id.message);
                textView.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/IRAN-SANS.TTF"));
                textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 13f);
                textView.setGravity(Gravity.CENTER);
                textView.setPadding(10, 10, 10, 10);
                textView.setTextColor(ContextCompat.getColor(this, R.color.colorDark_1));
                break;
        }
    }

    public void onPickTime() {
        backPressed = false;
        initBackgroundFeatures();
        removeClickables();
        darkOppacity.setBackgroundColor(Color.parseColor("#eeeeeeff"));
        motaleDarsTimePicker.setVisibility(View.VISIBLE);
        motaleDarsTimePicker.setCurrentHour(0);
        motaleDarsTimePicker.setCurrentMinute(0);
        btnPickTime.setVisibility(View.VISIBLE);
        btnPickNoTime.setVisibility(View.VISIBLE);
    }

    public void updateTodayTaklif() {
        backPressed = false;
        if (takalifEmroozList != null)
            takalifEmroozList.clear();
        else
            takalifEmroozList = new ArrayList<>();

        getTaklifList(taklifDatabase.getTaklifWithStatus(TaklifStatus.TODAY), takalifEmroozList, TaklifStatus.TODAY);
        getTaklifList(taklifDatabase.getTaklifWithStatus(TaklifStatus.TODAY_DONE), takalifEmroozList, TaklifStatus.TODAY_DONE);

        if (takalifEmroozAdapter == null) {
            takalifEmroozAdapter = new TaklifAdapter(MainActivity.this, R.layout.lesson_taklif_list_item, takalifEmroozList);
            takalifEmroozContainer.setAdapter(takalifEmroozAdapter);
        }
        takalifEmroozAdapter.notifyDataSetChanged();

        takalifEmroozContainer.setDivider(null);
        takalifEmroozContainer.setDividerHeight(0);
    }

    public void updateTomorrowTaklif() {
        backPressed = false;
        if (takalifFardaList != null)
            takalifFardaList.clear();
        else
            takalifFardaList = new ArrayList<>();

        getTaklifList(taklifDatabase.getTaklifWithStatus(TaklifStatus.TOMORROWED), takalifFardaList, TaklifStatus.TOMORROWED);
        getTaklifList(taklifDatabase.getTaklifWithStatus(TaklifStatus.TOMORROWED_DONE), takalifFardaList, TaklifStatus.TOMORROWED_DONE);

        if (takalifFardaAdapter == null) {
            takalifFardaAdapter = new TaklifAdapter(MainActivity.this, R.layout.lesson_taklif_list_item, takalifFardaList);
            takalifFardaContainer.setAdapter(takalifFardaAdapter);
        }
        takalifFardaAdapter.notifyDataSetChanged();

        takalifFardaContainer.setDivider(null);
        takalifFardaContainer.setDividerHeight(0);
    }

    public void updateDelayedTaklif() {
        backPressed = false;
        if (takalifJamondeList != null)
            takalifJamondeList.clear();
        else
            takalifJamondeList = new ArrayList<>();

        getTaklifList(taklifDatabase.getTaklifWithStatus(TaklifStatus.DELAYED), takalifJamondeList, TaklifStatus.DELAYED);
        getTaklifList(taklifDatabase.getTaklifWithStatus(TaklifStatus.DELAYED_DONE), takalifJamondeList, TaklifStatus.DELAYED_DONE);

        if (takalifJamondeAdapter == null) {
            takalifJamondeAdapter = new TaklifAdapter(MainActivity.this, R.layout.lesson_taklif_list_item, takalifJamondeList);
            takalifJamondeContainer.setAdapter(takalifJamondeAdapter);
        }
        takalifJamondeAdapter.notifyDataSetChanged();

        takalifJamondeContainer.setDivider(null);
        takalifJamondeContainer.setDividerHeight(0);
    }


    @Override
    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
        backPressed = false;
        this.timePicked = (int) (TimeUnit.HOURS.toMinutes(hourOfDay) + minute);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBazdehiUpdate() {
        if (preferenceManager.isFilledPurposes()) {
            if (purposingDatabase.getSuggestedOf(weekdayStr) != 0) {
                double res = preferenceManager.getCurrentStudyTime() / (1.0 * purposingDatabase.getSuggestedOf(weekdayStr));
                res *= 100;
                DecimalFormat decimalFormat = new DecimalFormat("#.00");
                String resstr = decimalFormat.format(res);
                bazdehiTxt.setText(resstr);
                preferenceManager.setBazdehi(Float.parseFloat(resstr));
            }
        }
    }
}