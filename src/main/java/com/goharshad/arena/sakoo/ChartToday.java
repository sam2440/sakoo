package com.goharshad.arena.sakoo;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChartToday extends Fragment {

    private TextView studyFraction, txt1,txt2,txt3,txt4,txt5,txt6,txt7,txt8,txt9,txt10,txt11, majmooDirooz, majmooHafte, majmooHaftePish;
    private BarChart barChart;
    private int weekDayIndex,year,month,day;
    private String weekDayName;

    private TaklifDatabase taklifDatabase;

    public ChartToday() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chart_today, container, false);
    }

//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        findViews(view);
//        weekDayIndex = PersianCalendarHandler.getToday().getDayOfWeek();
//        year = PersianCalendarHandler.getToday().getYear();
//        month = PersianCalendarHandler.getToday().getMonth();
//        day = PersianCalendarHandler.getToday().getDayOfMonth();
//        weekDayName = getDayOfWeek(weekDayIndex);
//
//        setFonts();
//        setWeekdayProgress(view);
//        initAxises();
//        List<BarEntry> entries = new ArrayList<>();
//        for (int i = 0; i < 4; i++) {
////            entries.add(new BarEntry((i+1),listener.getValueOfMonth(getLabels()[i])));
//            entries.add(new BarEntry((i + 1), new Random().nextInt(200)+1));
//        }
//
//        BarDataSet dataSet = new BarDataSet(entries, null);
//        dataSet.setColor(getResources().getColor(R.color.colorGreenBlue));
//        dataSet.setValueTextColor(Color.parseColor("#303030"));
//        dataSet.setValueTextSize(14.5f);
//        dataSet.setValueTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/B Koodak Bold.TTF"));
//
//        List<IBarDataSet> dataSets = new ArrayList<>();
//        dataSets.add(dataSet);
//        setBarData(dataSets);
//    }
//
//    private void setWeekdayProgress(View view) {
//        View v=view.findViewById(R.id.weekday_progress_view);
//        LinearLayout.LayoutParams params= (LinearLayout.LayoutParams) v.getLayoutParams();
//        params.weight=weekDayIndex;
//        v.setLayoutParams(params);
//    }
//
//    private void setBarData(List<IBarDataSet> dataSets) {
//        BarData barData = new BarData(dataSets);
//        barData.setBarWidth(.6f);
//        barData.setValueFormatter(new StudyTimeValueFormatter());
//        barChart.setData(barData);
//        barChart.setExtraOffsets(0,0,0,30);
//        barChart.animateY(1000);
//        barChart.setDescription(null);
//        barChart.getLegend().setEnabled(false);
//        barChart.setDrawGridBackground(false);
//        barChart.setDrawValueAboveBar(true);
//        barChart.invalidate();
//    }
//
//    private void initAxises() {
//        XAxis xAxis = barChart.getXAxis();
//        xAxis.setEnabled(true);
//        xAxis.setDrawAxisLine(true);
//        xAxis.setDrawGridLines(false);
//        xAxis.setDrawLabels(true);
//        String[] labels=getLabels();
//        xAxis.setValueFormatter(new MyValueFormatter(labels));
//        xAxis.setLabelCount(labels.length);
//        xAxis.setYOffset(10);
//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setGranularity(1);
//        xAxis.setGranularityEnabled(true);
//        xAxis.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/IRAN-SANS.TTF"));
//        xAxis.setTextSize(13f);
//
//        YAxis yAxisRight = barChart.getAxisRight();
//        yAxisRight.setEnabled(false);
//        yAxisRight.setDrawLabels(false);
//        yAxisRight.setDrawGridLines(false);
//        yAxisRight.setDrawLabels(false);
//
//        YAxis yAxisLeft = barChart.getAxisLeft();
//        yAxisLeft.setEnabled(false);
//        yAxisLeft.setDrawLabels(false);
//        yAxisLeft.setDrawGridLines(false);
//        yAxisLeft.setDrawLabels(false);
//    }
//
//    private String[] getLabels() {
////        List<String> lessons=taklifDatabase.lessonsOf(weekDayName,null);
//        List<String> lessons= Arrays.asList(getResources().getString(R.string.lesson_riazi1)
//                ,getResources().getString(R.string.lesson_arabi)
//                ,getResources().getString(R.string.lesson_savadresaneii)
//                ,getResources().getString(R.string.lesson_honar)
//        );
//        String[] labels = new String[lessons.size()];
//        for (int i = 0; i < lessons.size(); i++) {
//            labels[i] = lessons.get(i);
//        }
//        return labels;
//    }
//
//    private void setFonts() {
//        Typeface iransansTF = Typeface.createFromAsset(getContext().getAssets(), "fonts/IRAN-SANS.TTF");
//        Typeface koodakTF = Typeface.createFromAsset(getContext().getAssets(), "fonts/B Koodak Bold.TTF");
//
//        txt1.setTypeface(iransansTF);
//        txt2.setTypeface(iransansTF);
//        txt3.setTypeface(iransansTF);
//        txt4.setTypeface(iransansTF);
//        txt5.setTypeface(iransansTF);
//        txt6.setTypeface(iransansTF);
//        txt7.setTypeface(iransansTF);
//        txt8.setTypeface(iransansTF);
//        txt9.setTypeface(iransansTF);
//        txt10.setTypeface(iransansTF);
//        txt11.setTypeface(iransansTF);
//
//        studyFraction.setTypeface(koodakTF);
//        majmooDirooz.setTypeface(koodakTF);
//        majmooHafte.setTypeface(koodakTF);
//        majmooHaftePish.setTypeface(koodakTF);
//
//    }
//
//    private void findViews(View view) {
//        studyFraction = (TextView) view.findViewById(R.id.chart_today_txt_study_fraction);
//        txt1 = (TextView) view.findViewById(R.id.chart_today_txt_1);
//        txt2 = (TextView) view.findViewById(R.id.chart_today_txt2);
//        txt3 = (TextView) view.findViewById(R.id.chart_today_txt3);
//        txt4 = (TextView) view.findViewById(R.id.chart_today_txt4);
//        txt5 = (TextView) view.findViewById(R.id.chart_today_txt5);
//        txt6 = (TextView) view.findViewById(R.id.chart_today_txt6);
//        txt7 = (TextView) view.findViewById(R.id.chart_today_txt7);
//        txt8 = (TextView) view.findViewById(R.id.chart_today_txt8);
//        txt9 = (TextView) view.findViewById(R.id.chart_today_txt9);
//        txt10 = (TextView) view.findViewById(R.id.chart_today_txt10);
//        txt11 = (TextView) view.findViewById(R.id.chart_today_txt11);
//        majmooDirooz = (TextView) view.findViewById(R.id.chart_today_majmoo_dirooz);
//        majmooHafte = (TextView) view.findViewById(R.id.chart_today_majmoo_hafte);
//        majmooHaftePish = (TextView) view.findViewById(R.id.chart_today_majmoo_hafte_pish);
//        barChart = (BarChart) view.findViewById(R.id.chart_today_barchart);
//        taklifDatabase = new TaklifDatabase(getContext());
//    }
//
//    private String getDayOfWeek(int weekDay) {
//        switch (weekDay) {
//            case 1:
//                return getResources().getString(R.string.weekday_saturday);
//            case 2:
//                return getResources().getString(R.string.weekday_sunday);
//            case 3:
//                return getResources().getString(R.string.weekday_monday);
//            case 4:
//                return getResources().getString(R.string.weekday_tuesday);
//            case 5:
//                return getResources().getString(R.string.weekday_wednesday);
//            case 6:
//                return getResources().getString(R.string.weekday_thursday);
//            case 7:
//                return getResources().getString(R.string.weekday_friday);
//            default:
//                return null;
//        }
//    }
//
//    private class MyValueFormatter implements IAxisValueFormatter {
//        String[] strings;
//
//        public MyValueFormatter(String[] strings) {
//            this.strings = strings;
//        }
//
//        @Override
//        public String getFormattedValue(float v, AxisBase axisBase) {
//            return strings[(int) v - 1];
//        }
//    }
//
//    private class StudyTimeValueFormatter implements IValueFormatter{
//        @Override
//        public String getFormattedValue(float v, Entry entry, int i, ViewPortHandler viewPortHandler) {
//            return TimeHandler.getValidated(((int) v));
//        }
//    }
}
