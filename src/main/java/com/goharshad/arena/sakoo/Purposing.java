package com.goharshad.arena.sakoo;

import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.concurrent.TimeUnit;

public class Purposing extends AppCompatActivity implements View.OnClickListener, TimePicker.OnTimeChangedListener {

    private TextView availableSaturday, availableSunday, availableMonday, availableTuesday, availableWednesday, availableThursday, availableFriday;
    private TextView suggestedSaturday, suggestedSunday, suggestedMonday, suggestedTuesday, suggestedWednesday, suggestedThursday, suggestedFriday;
    private TextView purposingSaturday, purposingSunday, purposingMonday, purposingTuesday, purposingWednesday, purposingThursday, purposingFriday;
    private TextView purposingIntroText;
    private ImageView btnBack1;
    private boolean isAvailable = false;

    private RelativeLayout timePickerContainer;
    private TimePicker timePicker;
    private Button finishBtn;
    private ImageView btnBack;
    private TextView currentView = null, currentSibling = null;

    PreferencesManager manager;
    private PurposingDatabase database;
    private long time = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purposing);
        database = new PurposingDatabase(Purposing.this);
        manager=new PreferencesManager(Purposing.this);
        findViews();
        setlisteners();
        initToolbar();
        database.map();
        if (manager.isFilledPurposes()) {
            setTimes(getResources().getString(R.string.weekday_saturday), availableSaturday, suggestedSaturday, purposingSaturday);
            setTimes(getResources().getString(R.string.weekday_sunday), availableSunday, suggestedSunday, purposingSunday);
            setTimes(getResources().getString(R.string.weekday_monday), availableMonday, suggestedMonday, purposingMonday);
            setTimes(getResources().getString(R.string.weekday_tuesday), availableTuesday, suggestedTuesday, purposingTuesday);
            setTimes(getResources().getString(R.string.weekday_wednesday), availableWednesday, suggestedWednesday, purposingWednesday);
            setTimes(getResources().getString(R.string.weekday_thursday), availableThursday, suggestedThursday, purposingThursday);
            setTimes(getResources().getString(R.string.weekday_friday), availableFriday, suggestedFriday, purposingFriday);
        }
        setFontFamily();
        timePicker.setIs24HourView(true);
        timePicker.setCurrentHour(0);
        timePicker.setCurrentMinute(0);
        timePicker.setOnTimeChangedListener(this);
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.purposing_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_37dp);

        TextView toolbar_txt = (TextView) toolbar.findViewById(R.id.purposing_activity_name);
        toolbar_txt.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/IRAN-SANS.TTF"));
    }

    private void setTimes(String string, TextView available, TextView suggested, TextView purposing) {
        Cursor cursor = database.getTimesOf(string);
        if (cursor.moveToNext()) {
            available.setText(cursor.getInt(0)!=0 ? TimeHandler.getValidated(cursor.getInt(0)) : null);
            suggested.setText(cursor.getInt(1)!=0 ? TimeHandler.getValidated(cursor.getInt(1)) : null);
            purposing.setText(cursor.getInt(2)!=0 ? TimeHandler.getValidated(cursor.getInt(2)) : null);
        }
    }

    @Override
    public void onBackPressed() {
        if (timePickerContainer!= null){
            if (timePickerContainer.getVisibility()==View.VISIBLE){
                timePickerContainer.setVisibility(View.GONE);
                isAvailable = false;
                timePicker.setCurrentHour(0);
                timePicker.setCurrentMinute(0);
            }else{
                finish();
            }
        }else{
            finish();
        }
    }

    private void setFontFamily() {

        TextView weekDay;
        Typeface iransansTF = Typeface.createFromAsset(getAssets(), "fonts/IRAN-SANS.TTF");
        Typeface koodakTF = Typeface.createFromAsset(getAssets(), "fonts/B Koodak Bold.TTF");

        weekDay = (TextView) findViewById(R.id.purposing_header_hadaf_shoma);
        weekDay.setTypeface(iransansTF);
        weekDay = (TextView) findViewById(R.id.purposing_header_pishnohad_ma);
        weekDay.setTypeface(iransansTF);
        weekDay = (TextView) findViewById(R.id.purposing_header_rooz_hafte);
        weekDay.setTypeface(iransansTF);
        weekDay = (TextView) findViewById(R.id.purposing_header_zaman_dar_dastress);
        weekDay.setTypeface(iransansTF);
        weekDay = (TextView) findViewById(R.id.purposing_weekday_saturday);
        weekDay.setTypeface(iransansTF);
        weekDay = (TextView) findViewById(R.id.purposing_weekday_sunday);
        weekDay.setTypeface(iransansTF);
        weekDay = (TextView) findViewById(R.id.purposing_weekday_monday);
        weekDay.setTypeface(iransansTF);
        weekDay = (TextView) findViewById(R.id.purposing_weekday_tuesday);
        weekDay.setTypeface(iransansTF);
        weekDay = (TextView) findViewById(R.id.purposing_weekday_wednesday);
        weekDay.setTypeface(iransansTF);
        weekDay = (TextView) findViewById(R.id.purposing_weekday_thursday);
        weekDay.setTypeface(iransansTF);
        weekDay = (TextView) findViewById(R.id.purposing_weekday_friday);
        weekDay.setTypeface(iransansTF);

        availableSaturday.setTypeface(koodakTF);
        availableSunday.setTypeface(koodakTF);
        availableMonday.setTypeface(koodakTF);
        availableTuesday.setTypeface(koodakTF);
        availableWednesday.setTypeface(koodakTF);
        availableThursday.setTypeface(koodakTF);
        availableFriday.setTypeface(koodakTF);

        suggestedSaturday.setTypeface(koodakTF);
        suggestedSunday.setTypeface(koodakTF);
        suggestedMonday.setTypeface(koodakTF);
        suggestedTuesday.setTypeface(koodakTF);
        suggestedWednesday.setTypeface(koodakTF);
        suggestedThursday.setTypeface(koodakTF);
        suggestedFriday.setTypeface(koodakTF);

        purposingSaturday.setTypeface(koodakTF);
        purposingSunday.setTypeface(koodakTF);
        purposingMonday.setTypeface(koodakTF);
        purposingTuesday.setTypeface(koodakTF);
        purposingWednesday.setTypeface(koodakTF);
        purposingThursday.setTypeface(koodakTF);
        purposingFriday.setTypeface(koodakTF);

        purposingIntroText.setTypeface(iransansTF);
        finishBtn.setTypeface(iransansTF);
//        btnBack.setTypeface(iransansTF);
//        btnBack1.setTypeface(iransansTF);
    }

    private void setlisteners() {
        availableSaturday.setOnClickListener(this);
        availableSunday.setOnClickListener(this);
        availableMonday.setOnClickListener(this);
        availableTuesday.setOnClickListener(this);
        availableWednesday.setOnClickListener(this);
        availableThursday.setOnClickListener(this);
        availableFriday.setOnClickListener(this);

        purposingSaturday.setOnClickListener(this);
        purposingSunday.setOnClickListener(this);
        purposingMonday.setOnClickListener(this);
        purposingTuesday.setOnClickListener(this);
        purposingWednesday.setOnClickListener(this);
        purposingThursday.setOnClickListener(this);
        purposingFriday.setOnClickListener(this);

        btnBack.setOnClickListener(this);
        btnBack1.setOnClickListener(this);
        finishBtn.setOnClickListener(this);
    }

    private void findViews() {
        availableSaturday = (TextView) findViewById(R.id.available_time_saturday);
        availableSunday = (TextView) findViewById(R.id.available_time_sunday);
        availableMonday = (TextView) findViewById(R.id.available_time_monday);
        availableTuesday = (TextView) findViewById(R.id.available_time_tuesday);
        availableWednesday = (TextView) findViewById(R.id.available_time_wednesday);
        availableThursday = (TextView) findViewById(R.id.available_time_thursday);
        availableFriday = (TextView) findViewById(R.id.available_time_friday);

        suggestedSaturday = (TextView) findViewById(R.id.suggested_time_saturday);
        suggestedSunday = (TextView) findViewById(R.id.suggested_time_sunday);
        suggestedMonday = (TextView) findViewById(R.id.suggested_time_monday);
        suggestedTuesday = (TextView) findViewById(R.id.suggested_time_tuesday);
        suggestedWednesday = (TextView) findViewById(R.id.suggested_time_wednesday);
        suggestedThursday = (TextView) findViewById(R.id.suggested_time_thursday);
        suggestedFriday = (TextView) findViewById(R.id.suggested_time_friday);

        purposingSaturday = (TextView) findViewById(R.id.your_purpose_time_saturday);
        purposingSunday = (TextView) findViewById(R.id.your_purpose_time_sunday);
        purposingMonday = (TextView) findViewById(R.id.your_purpose_time_monday);
        purposingTuesday = (TextView) findViewById(R.id.your_purpose_time_tuesday);
        purposingWednesday = (TextView) findViewById(R.id.your_purpose_time_wednesday);
        purposingThursday = (TextView) findViewById(R.id.your_purpose_time_thursday);
        purposingFriday = (TextView) findViewById(R.id.your_purpose_time_friday);

        timePickerContainer = (RelativeLayout) findViewById(R.id.time_picker_container);
        timePicker = (TimePicker) findViewById(R.id.time_picker);
        btnBack = (ImageView) findViewById(R.id.purposing_back_btn_ok);
        btnBack1 = (ImageView) findViewById(R.id.purposing_back_btn_nok);

        purposingIntroText = (TextView) findViewById(R.id.purposing_intro_text);
        finishBtn = (Button) findViewById(R.id.purposing_btn_finish);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.purposing_btn_finish:
                boolean savedOk;
                if (!manager.isFilledPurposes()) {
                    savedOk = insertData(getResources().getString(R.string.weekday_saturday), availableSaturday.getText().toString(), suggestedSaturday.getText().toString(), purposingSaturday.getText().toString());
                    savedOk = insertData(getResources().getString(R.string.weekday_sunday), availableSunday.getText().toString(), suggestedSunday.getText().toString(), purposingSunday.getText().toString());
                    savedOk = insertData(getResources().getString(R.string.weekday_monday), availableMonday.getText().toString(), suggestedMonday.getText().toString(), purposingMonday.getText().toString());
                    savedOk = insertData(getResources().getString(R.string.weekday_tuesday), availableTuesday.getText().toString(), suggestedTuesday.getText().toString(), purposingTuesday.getText().toString());
                    savedOk = insertData(getResources().getString(R.string.weekday_wednesday), availableWednesday.getText().toString(), suggestedWednesday.getText().toString(), purposingWednesday.getText().toString());
                    savedOk = insertData(getResources().getString(R.string.weekday_thursday), availableThursday.getText().toString(), suggestedThursday.getText().toString(), purposingThursday.getText().toString());
                    savedOk = insertData(getResources().getString(R.string.weekday_friday), availableFriday.getText().toString(), suggestedFriday.getText().toString(), purposingFriday.getText().toString());
                    manager.setIsFilledPurposes(savedOk);
                } else {
                    savedOk = updateData(getResources().getString(R.string.weekday_saturday), availableSaturday.getText().toString(), suggestedSaturday.getText().toString(), purposingSaturday.getText().toString());
                    savedOk = updateData(getResources().getString(R.string.weekday_sunday), availableSunday.getText().toString(), suggestedSunday.getText().toString(), purposingSunday.getText().toString());
                    savedOk = updateData(getResources().getString(R.string.weekday_monday), availableMonday.getText().toString(), suggestedMonday.getText().toString(), purposingMonday.getText().toString());
                    savedOk = updateData(getResources().getString(R.string.weekday_tuesday), availableTuesday.getText().toString(), suggestedTuesday.getText().toString(), purposingTuesday.getText().toString());
                    savedOk = updateData(getResources().getString(R.string.weekday_wednesday), availableWednesday.getText().toString(), suggestedWednesday.getText().toString(), purposingWednesday.getText().toString());
                    savedOk = updateData(getResources().getString(R.string.weekday_thursday), availableThursday.getText().toString(), suggestedThursday.getText().toString(), purposingThursday.getText().toString());
                    savedOk = updateData(getResources().getString(R.string.weekday_friday), availableFriday.getText().toString(), suggestedFriday.getText().toString(), purposingFriday.getText().toString());
                }
                if (savedOk) finish();
                break;
            case R.id.purposing_back_btn_ok:
                currentView.setText(TimeHandler.getValidated((int) time));
                currentSibling.setText(isAvailable == true ? TimeHandler.getValidated((int) (time * .7)) : currentSibling.getText());
            case R.id.purposing_back_btn_nok:
                timePickerContainer.setVisibility(View.GONE);
                isAvailable = false;
                timePicker.setCurrentHour(0);
                timePicker.setCurrentMinute(0);
                break;
            case R.id.available_time_saturday:
            case R.id.available_time_sunday:
            case R.id.available_time_monday:
            case R.id.available_time_tuesday:
            case R.id.available_time_wednesday:
            case R.id.available_time_thursday:
            case R.id.available_time_friday:
                isAvailable = true;
            case R.id.your_purpose_time_saturday:
            case R.id.your_purpose_time_sunday:
            case R.id.your_purpose_time_monday:
            case R.id.your_purpose_time_tuesday:
            case R.id.your_purpose_time_wednesday:
            case R.id.your_purpose_time_thursday:
            case R.id.your_purpose_time_friday:
                currentView = (TextView) v;
                currentSibling = ((TextView) ((LinearLayout) currentView.getParent()).getChildAt(1));
                timePickerContainer.setVisibility(View.VISIBLE);
                break;
        }
    }

    private boolean insertData(String weekDay, String availableStr, String suggestedStr, String purposingStr) {
        int availaibe, suggested, purposing;
        String[] str = availableStr.split(":");
        availaibe = str.length == 2 ? (int) (TimeUnit.HOURS.toMinutes(Integer.parseInt(str[0])) + Integer.parseInt(str[1])) : 0;
        Log.d("TAG", "weekday : " + weekDay + " , available : " + availaibe);
        str = suggestedStr.split(":");
        suggested = str.length == 2 ? (int) (TimeUnit.HOURS.toMinutes(Integer.parseInt(str[0])) + Integer.parseInt(str[1])) : 0;
        Log.d("TAG", "weekday : " + weekDay + " , suggested : " + suggested);

        str = purposingStr.split(":");
        purposing = str.length == 2 ? (int) (TimeUnit.HOURS.toMinutes(Integer.parseInt(str[0])) + Integer.parseInt(str[1])) : 0;
        Log.d("TAG", "weekday : " + weekDay + " , purposing : " + purposing);

        return database.insert(weekDay, availaibe, suggested, purposing);
    }

    private boolean updateData(String weekDay, String availableStr, String suggestedStr, String purposingStr) {
        int availaibe, suggested, purposing;
        String[] str = availableStr.split(":");
        availaibe = str.length == 2 ? (int) (TimeUnit.HOURS.toMinutes(Integer.parseInt(str[0])) + Integer.parseInt(str[1])) : 0;
        Log.d("TAG", "weekday : " + weekDay + " , available : " + availaibe);
        str = suggestedStr.split(":");
        suggested = str.length == 2 ? (int) (TimeUnit.HOURS.toMinutes(Integer.parseInt(str[0])) + Integer.parseInt(str[1])) : 0;
        Log.d("TAG", "weekday : " + weekDay + " , suggested : " + suggested);

        str = purposingStr.split(":");
        purposing = str.length == 2 ? (int) (TimeUnit.HOURS.toMinutes(Integer.parseInt(str[0])) + Integer.parseInt(str[1])) : 0;
        Log.d("TAG", "weekday : " + weekDay + " , purposing : " + purposing);

        return database.update(weekDay, availaibe, suggested, purposing);
    }

    @Override
    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
        time = TimeUnit.HOURS.toMinutes(hourOfDay) + minute;
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

}
