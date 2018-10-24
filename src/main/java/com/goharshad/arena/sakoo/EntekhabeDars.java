package com.goharshad.arena.sakoo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class EntekhabeDars extends Fragment implements View.OnClickListener {

    private TextView dars[];
    private String subjects[];
    private MainActivity instance;
    private ScrollView scrollView;
    private LinearLayout linearLayout;
    private LinearLayout.LayoutParams params;
    private String result;
    private OnFragmentLessonSelected onSelected;
    private OnFragmentRemoved onRemoved;
    private Context context;
    public static boolean b;

    public EntekhabeDars() {
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        b=false;
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode==KeyEvent.KEYCODE_BACK){
                    b=true;
                    back();
                    onRemoved.setClickables();
                    return true;
                }
                return false;
            }
        });
    }

    private void back(){
        FragmentTransaction fragmentTransaction=this.getFragmentManager().beginTransaction();
        fragmentTransaction.remove(this);
        fragmentTransaction.commit();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = (Activity) context;
        try {
            if (activity instanceof OnFragmentLessonSelected) {
                onSelected = (OnFragmentLessonSelected) activity;
            }
            if (activity instanceof OnFragmentRemoved) {
                onRemoved = (OnFragmentRemoved) activity;
            }
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnItemClickedListener");
        }
    }

    public void setSubjects(String[] subjects) {
        this.subjects = subjects;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_entekhabe_dars, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        this.context=view.getContext();
        scrollView = (ScrollView) view.findViewById(R.id.entekhabe_dars_scroll);
        linearLayout = (LinearLayout) view.findViewById(R.id.entekhabe_dars);
        instance = (MainActivity) this.context;
        if(instance.getSubjects() != null){
            subjects = instance.getSubjects();
        }else{
            Set<String> strings=new PreferencesManager(getContext()).getEnteredLessons();
            subjects = Arrays.copyOf(strings.toArray(),strings.size(),String[].class);
//            instance= (MainActivity) context;
//            List<String> list=instance.getLessonDatabase().lessons();
//            subjects=new String[list.size()];
//            for (int i = 0; i < list.size(); i++) {
//                subjects[i]=list.get(i);
//            }
        }
        ((TextView) view.findViewById(R.id.fragment_entekhab_dars_txt)).setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/IRAN-SANS.TTF"));
        makeChart();
    }

    private void makeChart() {
        int l = subjects.length;
        scrollView.removeAllViews();
        dars = new TextView[l];
        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
        params.setMargins(0, 10, 0, 10);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        for (int i = 0; i < l; i++) {
            params.weight = 6.25f;
            dars[i] = new TextView(getContext());
            dars[i].setText(subjects[i]);
            dars[i].setTextColor(getResources().getColor(R.color.colorDark));
            dars[i].setTextSize(TypedValue.COMPLEX_UNIT_DIP,15);
            dars[i].setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/IRAN-SANS.TTF"));
//            dars[i].setBackgroundColor(Color.parseColor("#ffc800"));
            dars[i].setBackgroundResource(R.drawable.rounded_rectangle_orangeyellow);
            dars[i].setGravity(Gravity.CENTER);
            dars[i].setPadding(0, 30,0, 30);
            dars[i].setLayoutParams(params);
            dars[i].setOnClickListener(this);
            dars[i].setId(i);
            linearLayout.addView(dars[i]);
        }
        scrollView.addView(linearLayout);
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onClick(View v) {
        result = subjects[v.getId()];
        onSelected.onSelect(result);
        FragmentTransaction fragmentTransaction=this.getFragmentManager().beginTransaction();
        fragmentTransaction.remove(this);
        fragmentTransaction.commit();
    }
}