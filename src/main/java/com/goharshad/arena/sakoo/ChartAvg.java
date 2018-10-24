package com.goharshad.arena.sakoo;


import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.LimitLine;
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
public class ChartAvg extends Fragment {

    private final static int MAIN_BARS = 7;

    private Spinner spinner;
    private BarChart barchart_1, barchart_2;

    private float avg;

    public ChartAvg() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chart_avg, container, false);
    }

//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        findViews(view);
//        initSpinner();
//
//        setBarData(barchart_1, getDataSets(MAIN_BARS,false));
//        setBarData(barchart_2, getDataSets(MAIN_BARS,true));
//
//        initAxises(barchart_1, 0);
//        initAxises(barchart_2, 1);
//
//    }
//
//    public float getAvg() {
//        return avg;
//    }
//
//    private List<IBarDataSet> getDataSets(int index, boolean hasComparator) {
//        List<BarEntry> entries = new ArrayList<>();
//        avg=0;
//        Random random=new Random();
//        for (int i = 0; i < index; i++) {
//            int num=random.nextInt(200) + 1;
//            entries.add(new BarEntry((i + 1),num));
//            avg+=num;
//        }
//        avg=avg/index;
//        Toast.makeText(getContext(), "avg :" +avg, Toast.LENGTH_SHORT).show();
//
//        MyBarDataSet dataSet = new MyBarDataSet(entries, null);
//        if (hasComparator) {
//            dataSet.setColors(new int[]{
//                    ContextCompat.getColor(getContext(),R.color.colorGreenGrass),
//                    ContextCompat.getColor(getContext(),R.color.colorOrangeLight)
//            });
//        }else {
//            dataSet.setColor(getResources().getColor(R.color.colorGreenBlue));
//        }
//        dataSet.setValueTextColor(Color.parseColor("#303030"));
//        dataSet.setValueTextSize(14.5f);
//        dataSet.setValueTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/B Koodak Bold.TTF"));
//
//        List<IBarDataSet> dataSets = new ArrayList<>();
//        dataSets.add(dataSet);
//        return dataSets;
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
//        barchart_1 = (BarChart) view.findViewById(R.id.chart_avg_barchart);
//        barchart_2 = (BarChart) view.findViewById(R.id.chart_avg_barchart_comparator);
//    }
//
//    @Override
//    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//        switch (position) {
//            case 0:
//                initAxises(barchart_1, 0);
//                initAxises(barchart_2, 1);
//                setBarData(barchart_1, getDataSets(MAIN_BARS,false));
//                setBarData(barchart_2, getDataSets(MAIN_BARS,true));
//                break;
//            case 1:
//                initAxises(barchart_1, 2);
//                initAxises(barchart_2, 3);
//                setBarData(barchart_1, getDataSets(MAIN_BARS,false));
//                setBarData(barchart_2, getDataSets(4,true));
//                break;
//        }
//
//    }
//
//    @Override
//    public void onNothingSelected(AdapterView<?> parent) {
//
//    }
//
//    private void initAxises(BarChart barchart, int i) {
//        XAxis xAxis = barchart.getXAxis();
//        xAxis.setEnabled(true);
//        xAxis.setDrawAxisLine(false);
//        xAxis.setAxisLineColor(getResources().getColor(R.color.colorHotRed));
//        xAxis.setDrawGridLines(false);
//        xAxis.setDrawLabels(true);
//        xAxis.setLabelRotationAngle(i == 1 || i == 2 ? 45 : 0);
//        String[] labels = getLabels(i);
//        xAxis.setValueFormatter(new MyValueFormatter(labels));
//        xAxis.setLabelCount(labels.length);
//        xAxis.setYOffset(10);
//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setGranularity(1);
//        xAxis.setGranularityEnabled(true);
//        xAxis.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/IRAN-SANS.TTF"));
//        xAxis.setTextColor(getResources().getColor(R.color.colorOrangeLight));
//        xAxis.setTextSize(11.5f);
//
//        YAxis yAxisRight = barchart.getAxisRight();
//        yAxisRight.setEnabled(false);
//        yAxisRight.setDrawLabels(false);
//        yAxisRight.setDrawGridLines(false);
//
//        LimitLine ll2 = new LimitLine(0f,null);
//        ll2.setLineWidth(1f);
//        ll2.setLineColor(ContextCompat.getColor(getContext(),R.color.colorDark_1));
//        yAxisRight.addLimitLine(ll2);
//        Toast.makeText(getContext(), "calledddd", Toast.LENGTH_SHORT).show();
//
//        YAxis yAxisLeft = barchart.getAxisLeft();
//        yAxisLeft.setEnabled(false);
//        yAxisLeft.setDrawLabels(false);
//        yAxisLeft.setDrawGridLines(false);
//
//        if (i==1 || i==3){
//            LimitLine ll1 = new LimitLine(getAvg(),TimeHandler.getValidated((int) getAvg()));
//            ll1.setLineWidth(1f);
//            ll1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
//            ll1.setTextSize(10f);
//            ll1.setLineColor(ContextCompat.getColor(getContext(),R.color.colorOrangeLight));
//            ll1.setTextColor(ContextCompat.getColor(getContext(),R.color.colorHotRed));
//            ll1.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/B Koodak Bold.TTF"));
//            ll1.setLabelPosition(LimitLine.LimitLabelPosition.LEFT_TOP);
//            yAxisLeft.addLimitLine(ll1);
//        }
//
//    }
//
//    private void setBarData(BarChart barchart, List<IBarDataSet> dataSets) {
//        BarData barData = new BarData(dataSets);
//        barData.setBarWidth(.45f);
//        barData.setValueFormatter(new StudyTimeValueFormatter());
//        barchart.setData(barData);
//        barchart.setExtraOffsets(0, 0, 0, 40);
//        barchart.animateY(500);
//        barchart.setDescription(null);
//        barchart.getLegend().setEnabled(false);
//        barchart.setDrawGridBackground(false);
//        barchart.setDrawValueAboveBar(true);
//        barchart.invalidate();
//    }
//
//    private String[] getLabels(int i) {
//        int month = PersianCalendarHandler.getToday().getMonth();
//        int week = (PersianCalendarHandler.getToday().getMonth() * 31 / 7) + (PersianCalendarHandler.getToday().getDayOfMonth() / 7) + 1;
//        PreferencesManager manager = new PreferencesManager(getContext());
//        int weekInitial = manager.getInstallationWeekNumber();
//        int monthInitial = manager.getInstallationWeekNumber();
//        String[] label = new String[MAIN_BARS];
//        switch (i) {
//            case 0:
//                for (int j = 1; j <= MAIN_BARS; j++) {
//                    if (weekInitial <= week) {
//                        label[j-1] = getResources().getString(R.string.txt_hafte) + "\n" + (week - weekInitial + j);
//                    } else if (weekInitial > week) {
//                        label[j-1] = getResources().getString(R.string.txt_hafte) + "\n" + (52 - weekInitial + week + j);
//                    } else {
//                        label[j-1] = getResources().getString(R.string.txt_hafte);
//                    }
//                }
//                return label;
//            case 1:
//                int index = 0;
//                label[index++] = getResources().getString(R.string.weekday_saturday);
//                label[index++] = getResources().getString(R.string.weekday_sunday);
//                label[index++] = getResources().getString(R.string.weekday_monday);
//                label[index++] = getResources().getString(R.string.weekday_tuesday);
//                label[index++] = getResources().getString(R.string.weekday_wednesday);
//                label[index++] = getResources().getString(R.string.weekday_thursday);
//                label[index] = getResources().getString(R.string.weekday_friday);
//                index=0;
//                break;
//            case 2:
//                for (int j = 0; j < MAIN_BARS; j++) {
//                    label[j] = getMonthName(month + j <= 12 ? month + j : 12 - month - j);
//                }
//                break;
//            case 3:
//                int index1=0;
//                label[index1++] = "هفته اول";
//                label[index1++] = "هفته دوم";
//                label[index1++] = "هفته سوم";
//                label[index1++] = "هفته چهارم";
//                break;
//        }
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
//
//    private class MyBarDataSet extends BarDataSet{
//
//        public MyBarDataSet(List<BarEntry> yVals, String label) {
//            super(yVals, label);
//        }
//
//        @Override
//        public int getColor(int index) {
//            float val=getAvg();
//            if(getEntryForIndex(index).getY() < val) // less than 95 green
//                return mColors.get(0);
//            else if(getEntryForIndex(index).getY() >= val) // less than 100 orange
//                return mColors.get(1);
//            else
//                return 0;
//        }
//    }
}
