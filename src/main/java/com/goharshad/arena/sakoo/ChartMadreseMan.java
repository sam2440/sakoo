package com.goharshad.arena.sakoo;


import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
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
public class ChartMadreseMan extends Fragment {

    private Spinner spinner;
    private BarChart barChart;

    public ChartMadreseMan() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chart_madrese_man, container, false);
    }

//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        findViews(view);
//        initSpinner();
//        setFont(view,R.id.chart_madreseman_header_dars);
//        setFont(view,R.id.chart_madreseman_header_miangin_class);
//        setFont(view,R.id.chart_madreseman_header_miangin_paye);
//        setFont(view,R.id.chart_madreseman_header_nomre_shoma);
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
//    private void setFont(View view, @IdRes int id) {
//        Typeface typeface=Typeface.createFromAsset(getContext().getAssets(), "fonts/IRAN-SANS.TTF");
//        TextView textView= (TextView) view.findViewById(id);
//        textView.setTypeface(typeface);
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
//
//    }
//
//    private String[] getLabels() {
//        String[] label=new String[7];
//        int month= PersianCalendarHandler.getToday().getMonth();
//        label[0]=getMonthName(month-3 > 0 ? month-3 : 9+month);
//        label[1]=getMonthName(month-2 > 0 ? month-2 : 10+month);
//        label[2]=getMonthName(month-1 > 0 ? month-1 : 11+month);
//        label[3]=getMonthName(month);
//        label[4]=getMonthName(month+1 <= 12 ? month+1 : month-11);
//        label[5]=getMonthName(month+2 <= 12 ? month+2 : month-10);
//        label[6]=getMonthName(month+3 <= 12 ? month+3 : month-9);
//        return label;
//    }
//
//    private String getMonthName(int monthIndex) {
//        switch (monthIndex) {
//            case 1:
//                return getResources().getString(R.string.month_farvardin);
//            case 2:
//                return getResources().getString(R.string.month_ordibehesht);
//            case 3:
//                return getResources().getString(R.string.month_khordad);
//            case 4:
//                return getResources().getString(R.string.month_tir);
//            case 5:
//                return getResources().getString(R.string.month_mordad);
//            case 6:
//                return getResources().getString(R.string.month_shahrivar);
//            case 7:
//                return getResources().getString(R.string.month_mehr);
//            case 8:
//                return getResources().getString(R.string.month_abaan);
//            case 9:
//                return getResources().getString(R.string.month_azar);
//            case 10:
//                return getResources().getString(R.string.month_dey);
//            case 11:
//                return getResources().getString(R.string.month_bahman);
//            case 12:
//                return getResources().getString(R.string.month_esfand);
//            default:
//                return null;
//        }
//
//    }
//
//    private void initSpinner() {
//        List<String> spinnerOptions = Arrays.asList(
//                getResources().getString(R.string.txt_haftegi)
//                , getResources().getString(R.string.txt_mahane));
//        ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, spinnerOptions);
//        spinner.setAdapter(adapter);
//        for (int i = 0; i < spinner.getChildCount(); i++) {
//            TextView textView = (TextView) spinner.getChildAt(i).findViewById(android.R.id.text1);
//            textView.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/IRAN-SANS.TTF"));
//        }
//        spinner.setOnItemSelectedListener(this);
//        spinner.setSelection(0);
//    }
//
//    private void findViews(View view) {
//        spinner = (Spinner) view.findViewById(R.id.chart_avg_spinner);
//        barChart = (BarChart) view.findViewById(R.id.chart_avg_barchart);
//    }
//
//    @Override
//    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//    }
//
//    @Override
//    public void onNothingSelected(AdapterView<?> parent) {
//
//    }
//
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
//    private class StudyTimeValueFormatter implements IValueFormatter {
//        @Override
//        public String getFormattedValue(float v, Entry entry, int i, ViewPortHandler viewPortHandler) {
//            return TimeHandler.getValidated(((int) v));
//        }
//    }
}
