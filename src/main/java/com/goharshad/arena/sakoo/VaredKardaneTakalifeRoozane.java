package com.goharshad.arena.sakoo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import ir.huri.jcal.JalaliCalendar;

import static android.util.TypedValue.COMPLEX_UNIT_DIP;


public class VaredKardaneTakalifeRoozane extends Fragment implements View.OnClickListener {

    private LinearLayout scrollMainChild, layouts[];
    private TextView darsTitles[], headerText;
    private EditText darsTaklifs[];
    private MainActivity mainActivity;
    private Context context;
    private OnFragmentRemoved onFragmentRemoved;
    private List<String> lessons;
    private int size;
    private ImageView btnBack;

    private TaklifDatabase database;
    private JalaliCalendar calendar;

    public VaredKardaneTakalifeRoozane() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    closeFragment(R.id.fragment_container);
                    onFragmentRemoved.setClickables();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = (Activity) context;
        try {
            if (activity instanceof OnFragmentRemoved) {
                onFragmentRemoved = (OnFragmentRemoved) activity;
            }
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnItemClickedListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vared_kardane_takalife_roozane, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        this.context = view.getContext();
        this.mainActivity = (MainActivity) context;
        this.database = mainActivity.getTaklifDatabase();
        super.onViewCreated(view, savedInstanceState);
        calendar=new JalaliCalendar();
        findViews(view);
        btnBack.setOnClickListener(this);
        headerText.setText("وارد کردن تکالیف روزانه");
        headerText.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/IRAN-SANS.TTF"));

        lessons = mainActivity.getLessonDatabase().lessonsOf(calendar.getDayOfWeekString());
        size = lessons.size();
        Log.d("TAG", "lessons : " + lessons.size());

        RelativeLayout.LayoutParams params =
                new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);

        RelativeLayout.LayoutParams params1 =
                new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params1.addRule(RelativeLayout.BELOW, android.R.id.text1);

        LinearLayout.LayoutParams params2 =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (.3 * .85 * DimenHelper.getDeviceHeight(getActivity())));
        params2.setMargins(0, 0, 0, 5);

        darsTitles = new TextView[size];
        darsTaklifs = new EditText[size];
        layouts = new LinearLayout[size];
        Typeface typeface=Typeface.createFromAsset(context.getAssets(), "fonts/IRAN-SANS.TTF");
        for (int i = 0; i < size; i++) {

            layouts[i] = new LinearLayout(context);
            layouts[i].setOrientation(LinearLayout.VERTICAL);
            layouts[i].setPadding(0, 0, 0, 20);
            layouts[i].setLayoutParams(params2);
            scrollMainChild.addView(layouts[i]);

            darsTitles[i] = new TextView(context);
            darsTitles[i].setText(lessons.get(i));
            darsTitles[i].setId(android.R.id.text1);
            darsTitles[i].setTypeface(typeface);
            darsTitles[i].setBackgroundResource(R.color.colorGreenGrass);
            darsTitles[i].setTextColor(Color.WHITE);
            darsTitles[i].setTextSize(COMPLEX_UNIT_DIP,17.25f);
//            darsTitles[i].setPadding(0, 0, 0, 10);
            darsTitles[i].setGravity(Gravity.CENTER);
            darsTitles[i].setLayoutParams(params);
            layouts[i].addView(darsTitles[i]);

            darsTaklifs[i] = new EditText(context);
            darsTaklifs[i].setBackgroundColor(Color.parseColor("#ffffff"));
            darsTaklifs[i].setLayoutParams(params1);
            darsTaklifs[i].setGravity(Gravity.CENTER);
            darsTaklifs[i].setPadding(0, 50, 0, 50);
            darsTaklifs[i].setId((i * 10) + 1);
            darsTaklifs[i].setTypeface(typeface);
            darsTaklifs[i].setText(database.taklifOf(darsTitles[i].getText().toString()));
            if (database.isDone(darsTitles[i].getText().toString(), darsTaklifs[i].getText().toString())) {
                darsTaklifs[i].setEnabled(false);
            }
            layouts[i].addView(darsTaklifs[i]);
        }

    }

    private void findViews(View view) {
        scrollMainChild = (LinearLayout) view.findViewById(R.id.vared_kardane_taklif_scroll_child);
        btnBack = (ImageView) view.findViewById(R.id.vared_kardane_barname_haftegi_back);
        headerText = (TextView) view.findViewById(R.id.fragment_header_txt);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.vared_kardane_barname_haftegi_back:
                syncTakalif();
                Fragment fragments = getActivity().getSupportFragmentManager().findFragmentById(R.id.fragment_container);
                if (fragments != null) {
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .remove(fragments)
                            .commit();
                }
                onFragmentRemoved.setClickables();
                mainActivity.updateTodayTaklif();
                mainActivity.updateTomorrowTaklif();
                mainActivity.updateDelayedTaklif();
                break;
        }
    }

    private void syncTakalif() {
        String weekDay = calendar.getDayOfWeekString(), lesson, taklif;
        for (int i = 0; i < size; i++) {
            if(darsTaklifs[i].isEnabled()){
                lesson = darsTitles[i].getText().toString();
                taklif = darsTaklifs[i].getText().toString();
                if (!taklif.equals("")) {
                    if (!database.hasTaklifOfLesson(lesson)) {
                        Log.d("TAG", "inserted: " + weekDay + "," + lesson + "," + taklif + ",");
                        database.insert(weekDay, lesson, taklif);
                    } else {
                        Log.d("TAG", "updated: " + weekDay + "," + lesson + "," + taklif + ",");
                        database.update(lesson, taklif);
                    }
                }
            }
        }
    }

    private void closeFragment(@IdRes int fragment_id) {
        Fragment fragment = getActivity().getSupportFragmentManager().findFragmentById(fragment_id);
        if (fragment != null) {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .remove(fragment)
                    .commit();
        }
    }
}
