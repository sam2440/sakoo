package com.goharshad.arena.sakoo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class EntekhabeDarsBarayeZang extends Fragment implements View.OnClickListener, View.OnLongClickListener {

    private MainActivity mainActivity;
    private Context context;
    private TextView[] saturday, sunday, monday, tuesday, wednesday, thursday, friday;
    private LinearLayout fragment_container2, bg_dark;
    private TextView finish;
    private RelativeLayout roozZangContainer;
    private TextView roozZangContainerHeader;
    private Fragment fragment;
    private TextView currentView;
    private OnFragmentRemoved onFragmentRemoved;
    private OnWeeklyScheduleEntered onWeeklyScheduleEntered;
    private LessonDatabase database;
    private ImageView btnBack;
    private PreferencesManager manager;
    private String weekDay;
    private int index;

    public EntekhabeDarsBarayeZang() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = (Activity) context;
        try {
            if (activity instanceof OnFragmentRemoved) {
                onFragmentRemoved = (OnFragmentRemoved) activity;
                onWeeklyScheduleEntered = (OnWeeklyScheduleEntered) activity;
            }
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnItemClickedListener");
        }
    }


    public void updateTextView(String text) {
        bg_dark.setVisibility(View.GONE);
        if (currentView != null) {
            currentView.setText(text);
            currentView.setBackgroundResource(R.drawable.rounded_rectangle_yellow);
            if (database != null) {
                weekDay = ((TextView) ((RelativeLayout) currentView.getParent().getParent().getParent()).getChildAt(0)).getText().toString();
                index = Integer.parseInt(currentView.getTag().toString());
                Log.d("TAG", "has data " + database.exists(weekDay, index, text));
                if (!database.exists(weekDay, index)) {
                    database.insert(weekDay, index, text);
                } else {
                    database.update(weekDay, index, text);
                }
                database.map();
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_entekhabe_dars_baraye_zang, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        this.context = view.getContext();
        this.mainActivity = (MainActivity) context;
        this.database = mainActivity.getLessonDatabase();

        manager = new PreferencesManager(view.getContext());
        fragment = new EntekhabeDars();
        saturday = new TextView[6];
        sunday = new TextView[6];
        monday = new TextView[6];
        tuesday = new TextView[6];
        wednesday = new TextView[6];
        thursday = new TextView[6];
        friday = new TextView[6];

        initContainers(view, R.id.rooz_zang_container_saturday);
        initContainers(view, R.id.rooz_zang_container_sunday);
        initContainers(view, R.id.rooz_zang_container_monday);
        initContainers(view, R.id.rooz_zang_container_tuesday);
        initContainers(view, R.id.rooz_zang_container_wednesday);
        initContainers(view, R.id.rooz_zang_container_thursday);
        initContainers(view, R.id.rooz_zang_container_friday);

        initContainerHeader(view, R.id.zang_rooz_container_header_saturday);
        initContainerHeader(view, R.id.zang_rooz_container_header_sunday);
        initContainerHeader(view, R.id.zang_rooz_container_header_monday);
        initContainerHeader(view, R.id.zang_rooz_container_header_tuesday);
        initContainerHeader(view, R.id.zang_rooz_container_header_wednesday);
        initContainerHeader(view, R.id.zang_rooz_container_header_thursday);
        initContainerHeader(view, R.id.zang_rooz_container_header_friday);

        findViews(view);
        setListeners();
        setLongClickListeners();
        setFonts();
        initVaredBarnameInfoDialog();

        bg_dark.setVisibility(View.GONE);
        finish.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/IRAN-SANS.TTF"));

        mainActivity.makeNewFragmentLenght(fragment_container2, getActivity());

        if (manager.isEnteredSchedule()) {
            initIndexLessonsOf(saturday, getResources().getString(R.string.weekday_saturday));
            initIndexLessonsOf(sunday, getResources().getString(R.string.weekday_sunday));
            initIndexLessonsOf(monday, getResources().getString(R.string.weekday_monday));
            initIndexLessonsOf(tuesday, getResources().getString(R.string.weekday_tuesday));
            initIndexLessonsOf(wednesday, getResources().getString(R.string.weekday_wednesday));
            initIndexLessonsOf(thursday, getResources().getString(R.string.weekday_thursday));
            initIndexLessonsOf(friday, getResources().getString(R.string.weekday_friday));
        }

    }

    private void initVaredBarnameInfoDialog() {
        if (!manager.isCheckedEntekhabdeDarsDialog()) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_vared_barname_info, null);
            final AlertDialog dialog = new AlertDialog.Builder(getContext()).setView(view).create();
            TextView tv1 = (TextView) view.findViewById(R.id.layout_vared_barname_title);
            TextView tv2 = (TextView) view.findViewById(R.id.layout_vared_barname_txt);
            TextView tv3 = (TextView) view.findViewById(R.id.layout_vared_barname_checkbox_txt);
            Button button = (Button) view.findViewById(R.id.layout_vared_barname_btn_ok);
            final CheckBox checkBox = (CheckBox) view.findViewById(R.id.layout_vared_barname_checkbox);
            tv2.setText("برای حذف درسی که روز و زنگ اون رو مشخص کردین ، انگشت خودتون رو روی یکم روی اسم درس نگه دارین");

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (checkBox.isChecked()) {
                        manager.setIsCheckedEntekhabDarsialog(true);
                    }
                    dialog.dismiss();
                }
            });

            Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/IRAN-SANS.TTF");
            tv1.setTypeface(typeface);
            tv2.setTypeface(typeface);
            tv3.setTypeface(typeface);
            button.setTypeface(typeface);

            dialog.show();
        }
    }

    private void setFonts() {
        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/IRAN-SANS.TTF");
        TextView textView = (TextView) getView().findViewById(R.id.fragment_header_txt);
        textView.setTypeface(typeface);
        for (int i = 0; i < saturday.length; i++) {
            saturday[i].setTypeface(typeface);
            sunday[i].setTypeface(typeface);
            monday[i].setTypeface(typeface);
            tuesday[i].setTypeface(typeface);
            wednesday[i].setTypeface(typeface);
            thursday[i].setTypeface(typeface);
            friday[i].setTypeface(typeface);
        }
    }

    private void initIndexLessonsOf(TextView[] cols, String weekDay) {
        for (int i = 1; i <= cols.length; i++) {
            if (database.exists(weekDay, i)) {
                cols[i - 1].setText(database.getLessonOf(weekDay, i));
                cols[i - 1].setBackgroundResource(R.drawable.rounded_rectangle_yellow);
            }
        }
    }

    private void setLongClickListeners() {
        saturday[0].setOnLongClickListener(this);
        saturday[1].setOnLongClickListener(this);
        saturday[2].setOnLongClickListener(this);
        saturday[3].setOnLongClickListener(this);
        saturday[4].setOnLongClickListener(this);
        saturday[5].setOnLongClickListener(this);
        sunday[0].setOnLongClickListener(this);
        sunday[1].setOnLongClickListener(this);
        sunday[2].setOnLongClickListener(this);
        sunday[3].setOnLongClickListener(this);
        sunday[4].setOnLongClickListener(this);
        sunday[5].setOnLongClickListener(this);
        monday[0].setOnLongClickListener(this);
        monday[1].setOnLongClickListener(this);
        monday[2].setOnLongClickListener(this);
        monday[3].setOnLongClickListener(this);
        monday[4].setOnLongClickListener(this);
        monday[5].setOnLongClickListener(this);
        tuesday[0].setOnLongClickListener(this);
        tuesday[1].setOnLongClickListener(this);
        tuesday[2].setOnLongClickListener(this);
        tuesday[3].setOnLongClickListener(this);
        tuesday[4].setOnLongClickListener(this);
        tuesday[5].setOnLongClickListener(this);
        wednesday[0].setOnLongClickListener(this);
        wednesday[1].setOnLongClickListener(this);
        wednesday[2].setOnLongClickListener(this);
        wednesday[3].setOnLongClickListener(this);
        wednesday[4].setOnLongClickListener(this);
        wednesday[5].setOnLongClickListener(this);
        thursday[0].setOnLongClickListener(this);
        thursday[1].setOnLongClickListener(this);
        thursday[2].setOnLongClickListener(this);
        thursday[3].setOnLongClickListener(this);
        thursday[4].setOnLongClickListener(this);
        thursday[5].setOnLongClickListener(this);
        friday[0].setOnLongClickListener(this);
        friday[1].setOnLongClickListener(this);
        friday[2].setOnLongClickListener(this);
        friday[3].setOnLongClickListener(this);
        friday[4].setOnLongClickListener(this);
        friday[5].setOnLongClickListener(this);
    }

    private void setListeners() {
        saturday[0].setOnClickListener(this);
        saturday[1].setOnClickListener(this);
        saturday[2].setOnClickListener(this);
        saturday[3].setOnClickListener(this);
        saturday[4].setOnClickListener(this);
        saturday[5].setOnClickListener(this);
        sunday[0].setOnClickListener(this);
        sunday[1].setOnClickListener(this);
        sunday[2].setOnClickListener(this);
        sunday[3].setOnClickListener(this);
        sunday[4].setOnClickListener(this);
        sunday[5].setOnClickListener(this);
        monday[0].setOnClickListener(this);
        monday[1].setOnClickListener(this);
        monday[2].setOnClickListener(this);
        monday[3].setOnClickListener(this);
        monday[4].setOnClickListener(this);
        monday[5].setOnClickListener(this);
        tuesday[0].setOnClickListener(this);
        tuesday[1].setOnClickListener(this);
        tuesday[2].setOnClickListener(this);
        tuesday[3].setOnClickListener(this);
        tuesday[4].setOnClickListener(this);
        tuesday[5].setOnClickListener(this);
        wednesday[0].setOnClickListener(this);
        wednesday[1].setOnClickListener(this);
        wednesday[2].setOnClickListener(this);
        wednesday[3].setOnClickListener(this);
        wednesday[4].setOnClickListener(this);
        wednesday[5].setOnClickListener(this);
        thursday[0].setOnClickListener(this);
        thursday[1].setOnClickListener(this);
        thursday[2].setOnClickListener(this);
        thursday[3].setOnClickListener(this);
        thursday[4].setOnClickListener(this);
        thursday[5].setOnClickListener(this);
        friday[0].setOnClickListener(this);
        friday[1].setOnClickListener(this);
        friday[2].setOnClickListener(this);
        friday[3].setOnClickListener(this);
        friday[4].setOnClickListener(this);
        friday[5].setOnClickListener(this);
        finish.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        bg_dark.setOnClickListener(this);
    }

    private void findViews(View view) {
        fragment_container2 = (LinearLayout) view.findViewById(R.id.vared_kardane_barname_haftegi_fragment_container2);
        bg_dark = (LinearLayout) view.findViewById(R.id.entekhab_dars_make_bg_dark);
        saturday[0] = (TextView) view.findViewById(R.id.entekhab_dars_baraye_zang_saturday1);
        saturday[1] = (TextView) view.findViewById(R.id.entekhab_dars_baraye_zang_saturday2);
        saturday[2] = (TextView) view.findViewById(R.id.entekhab_dars_baraye_zang_saturday3);
        saturday[3] = (TextView) view.findViewById(R.id.entekhab_dars_baraye_zang_saturday4);
        saturday[4] = (TextView) view.findViewById(R.id.entekhab_dars_baraye_zang_saturday5);
        saturday[5] = (TextView) view.findViewById(R.id.entekhab_dars_baraye_zang_saturday6);
        sunday[0] = (TextView) view.findViewById(R.id.entekhab_dars_baraye_zang_sunday1);
        sunday[1] = (TextView) view.findViewById(R.id.entekhab_dars_baraye_zang_sunday2);
        sunday[2] = (TextView) view.findViewById(R.id.entekhab_dars_baraye_zang_sunday3);
        sunday[3] = (TextView) view.findViewById(R.id.entekhab_dars_baraye_zang_sunday4);
        sunday[4] = (TextView) view.findViewById(R.id.entekhab_dars_baraye_zang_sunday5);
        sunday[5] = (TextView) view.findViewById(R.id.entekhab_dars_baraye_zang_sunday6);
        monday[0] = (TextView) view.findViewById(R.id.entekhab_dars_baraye_zang_monday1);
        monday[1] = (TextView) view.findViewById(R.id.entekhab_dars_baraye_zang_monday2);
        monday[2] = (TextView) view.findViewById(R.id.entekhab_dars_baraye_zang_monday3);
        monday[3] = (TextView) view.findViewById(R.id.entekhab_dars_baraye_zang_monday4);
        monday[4] = (TextView) view.findViewById(R.id.entekhab_dars_baraye_zang_monday5);
        monday[5] = (TextView) view.findViewById(R.id.entekhab_dars_baraye_zang_monday6);
        tuesday[0] = (TextView) view.findViewById(R.id.entekhab_dars_baraye_zang_tuesday1);
        tuesday[1] = (TextView) view.findViewById(R.id.entekhab_dars_baraye_zang_tuesday2);
        tuesday[2] = (TextView) view.findViewById(R.id.entekhab_dars_baraye_zang_tuesday3);
        tuesday[3] = (TextView) view.findViewById(R.id.entekhab_dars_baraye_zang_tuesday4);
        tuesday[4] = (TextView) view.findViewById(R.id.entekhab_dars_baraye_zang_tuesday5);
        tuesday[5] = (TextView) view.findViewById(R.id.entekhab_dars_baraye_zang_tuesday6);
        wednesday[0] = (TextView) view.findViewById(R.id.entekhab_dars_baraye_zang_wednesday1);
        wednesday[1] = (TextView) view.findViewById(R.id.entekhab_dars_baraye_zang_wednesday2);
        wednesday[2] = (TextView) view.findViewById(R.id.entekhab_dars_baraye_zang_wednesday3);
        wednesday[3] = (TextView) view.findViewById(R.id.entekhab_dars_baraye_zang_wednesday4);
        wednesday[4] = (TextView) view.findViewById(R.id.entekhab_dars_baraye_zang_wednesday5);
        wednesday[5] = (TextView) view.findViewById(R.id.entekhab_dars_baraye_zang_wednesday6);
        thursday[0] = (TextView) view.findViewById(R.id.entekhab_dars_baraye_zang_thursday1);
        thursday[1] = (TextView) view.findViewById(R.id.entekhab_dars_baraye_zang_thursday2);
        thursday[2] = (TextView) view.findViewById(R.id.entekhab_dars_baraye_zang_thursday3);
        thursday[3] = (TextView) view.findViewById(R.id.entekhab_dars_baraye_zang_thursday4);
        thursday[4] = (TextView) view.findViewById(R.id.entekhab_dars_baraye_zang_thursday5);
        thursday[5] = (TextView) view.findViewById(R.id.entekhab_dars_baraye_zang_thursday6);
        friday[0] = (TextView) view.findViewById(R.id.entekhab_dars_baraye_zang_friday1);
        friday[1] = (TextView) view.findViewById(R.id.entekhab_dars_baraye_zang_friday2);
        friday[2] = (TextView) view.findViewById(R.id.entekhab_dars_baraye_zang_friday3);
        friday[3] = (TextView) view.findViewById(R.id.entekhab_dars_baraye_zang_friday4);
        friday[4] = (TextView) view.findViewById(R.id.entekhab_dars_baraye_zang_friday5);
        friday[5] = (TextView) view.findViewById(R.id.entekhab_dars_baraye_zang_friday6);
        finish = (TextView) view.findViewById(R.id.entekhab_dars_baraye_zang_finish);
        btnBack = (ImageView) view.findViewById(R.id.vared_kardane_barname_haftegi_back);
    }

    private void initContainerHeader(View view, @IdRes int zang_rooz_container_header_id) {
        roozZangContainerHeader = (TextView) view.findViewById(zang_rooz_container_header_id);
        roozZangContainerHeader.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/IRAN-SANS.TTF"));
    }

    private void initContainers(View view, @IdRes int rooz_zang_container_id) {
        roozZangContainer = (RelativeLayout) view.findViewById(rooz_zang_container_id);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) roozZangContainer.getLayoutParams();
        params.height = (int) (.275 * DimenHelper.getFragmentHeight(getActivity()));
        roozZangContainer.setLayoutParams(params);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.entekhab_dars_baraye_zang_finish:
                manager.setIsEnteredSchedule(true);
            case R.id.vared_kardane_barname_haftegi_back:
                closeFragment(R.id.fragment_container);
                if (v.getId() == R.id.vared_kardane_barname_haftegi_back) {
                    //
                } else {
                    onWeeklyScheduleEntered.onSheduleEntered();
                }
                onFragmentRemoved.setClickables();
                break;
            case R.id.entekhab_dars_make_bg_dark:
                closeFragment(R.id.vared_kardane_barname_haftegi_fragment_container2);
                bg_dark.setVisibility(View.GONE);
                break;
            case R.id.entekhab_dars_baraye_zang_saturday1:
            case R.id.entekhab_dars_baraye_zang_saturday2:
            case R.id.entekhab_dars_baraye_zang_saturday3:
            case R.id.entekhab_dars_baraye_zang_saturday4:
            case R.id.entekhab_dars_baraye_zang_saturday5:
            case R.id.entekhab_dars_baraye_zang_saturday6:
            case R.id.entekhab_dars_baraye_zang_sunday1:
            case R.id.entekhab_dars_baraye_zang_sunday2:
            case R.id.entekhab_dars_baraye_zang_sunday3:
            case R.id.entekhab_dars_baraye_zang_sunday4:
            case R.id.entekhab_dars_baraye_zang_sunday5:
            case R.id.entekhab_dars_baraye_zang_sunday6:
            case R.id.entekhab_dars_baraye_zang_monday1:
            case R.id.entekhab_dars_baraye_zang_monday2:
            case R.id.entekhab_dars_baraye_zang_monday3:
            case R.id.entekhab_dars_baraye_zang_monday4:
            case R.id.entekhab_dars_baraye_zang_monday5:
            case R.id.entekhab_dars_baraye_zang_monday6:
            case R.id.entekhab_dars_baraye_zang_tuesday1:
            case R.id.entekhab_dars_baraye_zang_tuesday2:
            case R.id.entekhab_dars_baraye_zang_tuesday3:
            case R.id.entekhab_dars_baraye_zang_tuesday4:
            case R.id.entekhab_dars_baraye_zang_tuesday5:
            case R.id.entekhab_dars_baraye_zang_tuesday6:
            case R.id.entekhab_dars_baraye_zang_wednesday1:
            case R.id.entekhab_dars_baraye_zang_wednesday2:
            case R.id.entekhab_dars_baraye_zang_wednesday3:
            case R.id.entekhab_dars_baraye_zang_wednesday4:
            case R.id.entekhab_dars_baraye_zang_wednesday5:
            case R.id.entekhab_dars_baraye_zang_wednesday6:
            case R.id.entekhab_dars_baraye_zang_thursday1:
            case R.id.entekhab_dars_baraye_zang_thursday2:
            case R.id.entekhab_dars_baraye_zang_thursday3:
            case R.id.entekhab_dars_baraye_zang_thursday4:
            case R.id.entekhab_dars_baraye_zang_thursday5:
            case R.id.entekhab_dars_baraye_zang_thursday6:
            case R.id.entekhab_dars_baraye_zang_friday1:
            case R.id.entekhab_dars_baraye_zang_friday2:
            case R.id.entekhab_dars_baraye_zang_friday3:
            case R.id.entekhab_dars_baraye_zang_friday4:
            case R.id.entekhab_dars_baraye_zang_friday5:
            case R.id.entekhab_dars_baraye_zang_friday6:

                bg_dark.setVisibility(View.VISIBLE);
                currentView = (TextView) v;
                this.getFragmentManager()
                        .beginTransaction()
                        .add(R.id.vared_kardane_barname_haftegi_fragment_container2, fragment)
                        .commit();
                break;
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

    @Override
    public boolean onLongClick(View v) {
        switch (v.getId()) {
            case R.id.entekhab_dars_baraye_zang_saturday1:
            case R.id.entekhab_dars_baraye_zang_saturday2:
            case R.id.entekhab_dars_baraye_zang_saturday3:
            case R.id.entekhab_dars_baraye_zang_saturday4:
            case R.id.entekhab_dars_baraye_zang_saturday5:
            case R.id.entekhab_dars_baraye_zang_saturday6:
            case R.id.entekhab_dars_baraye_zang_sunday1:
            case R.id.entekhab_dars_baraye_zang_sunday2:
            case R.id.entekhab_dars_baraye_zang_sunday3:
            case R.id.entekhab_dars_baraye_zang_sunday4:
            case R.id.entekhab_dars_baraye_zang_sunday5:
            case R.id.entekhab_dars_baraye_zang_sunday6:
            case R.id.entekhab_dars_baraye_zang_monday1:
            case R.id.entekhab_dars_baraye_zang_monday2:
            case R.id.entekhab_dars_baraye_zang_monday3:
            case R.id.entekhab_dars_baraye_zang_monday4:
            case R.id.entekhab_dars_baraye_zang_monday5:
            case R.id.entekhab_dars_baraye_zang_monday6:
            case R.id.entekhab_dars_baraye_zang_tuesday1:
            case R.id.entekhab_dars_baraye_zang_tuesday2:
            case R.id.entekhab_dars_baraye_zang_tuesday3:
            case R.id.entekhab_dars_baraye_zang_tuesday4:
            case R.id.entekhab_dars_baraye_zang_tuesday5:
            case R.id.entekhab_dars_baraye_zang_tuesday6:
            case R.id.entekhab_dars_baraye_zang_wednesday1:
            case R.id.entekhab_dars_baraye_zang_wednesday2:
            case R.id.entekhab_dars_baraye_zang_wednesday3:
            case R.id.entekhab_dars_baraye_zang_wednesday4:
            case R.id.entekhab_dars_baraye_zang_wednesday5:
            case R.id.entekhab_dars_baraye_zang_wednesday6:
            case R.id.entekhab_dars_baraye_zang_thursday1:
            case R.id.entekhab_dars_baraye_zang_thursday2:
            case R.id.entekhab_dars_baraye_zang_thursday3:
            case R.id.entekhab_dars_baraye_zang_thursday4:
            case R.id.entekhab_dars_baraye_zang_thursday5:
            case R.id.entekhab_dars_baraye_zang_thursday6:
            case R.id.entekhab_dars_baraye_zang_friday1:
            case R.id.entekhab_dars_baraye_zang_friday2:
            case R.id.entekhab_dars_baraye_zang_friday3:
            case R.id.entekhab_dars_baraye_zang_friday4:
            case R.id.entekhab_dars_baraye_zang_friday5:
            case R.id.entekhab_dars_baraye_zang_friday6:

                currentView = (TextView) v;
                String s = null;
                switch (currentView.getTag().toString()) {
                    case "1":
                        s = getResources().getString(R.string.index_first);
                        break;
                    case "2":
                        s = getResources().getString(R.string.index_second);
                        break;
                    case "3":
                        s = getResources().getString(R.string.index_third);
                        break;
                    case "4":
                        s = getResources().getString(R.string.index_forth);
                        break;
                    case "5":
                        s = getResources().getString(R.string.index_fifth);
                        break;
                    case "6":
                        s = getResources().getString(R.string.index_sixth);
                        break;
                }
                currentView.setBackgroundResource(R.drawable.rounded_rectangle_darkgray);
                currentView.setText(s);

                weekDay = ((TextView) ((RelativeLayout) currentView.getParent().getParent().getParent()).getChildAt(0)).getText().toString();
                index = Integer.parseInt(currentView.getTag().toString());
                if (database.delete(weekDay, index))
                    Log.d("TAG", "deleted : " + weekDay + "," + index);
                database.map();
                break;
        }
        return true;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                bg_dark.setVisibility(View.GONE);
                if (keyCode == KeyEvent.KEYCODE_BACK && !EntekhabeDars.b) {

                    bg_dark.setVisibility(View.GONE);
                    if (database == null) {
                        database = new LessonDatabase(getContext());
                        if (new PreferencesManager(mainActivity).isEnteredSchedule() == false) {
                            database.deleteAll();
                        }
                    }
                    back();
                    EntekhabeDars.b = false;
                    return true;
                }
                EntekhabeDars.b = false;
                return false;
            }
        });
    }

    private void back() {
        closeFragment(R.id.fragment_container);
        onFragmentRemoved.setClickables();
    }

}
