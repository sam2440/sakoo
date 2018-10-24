package com.goharshad.arena.sakoo;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class VaredKardaneBarnameHaftegi extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private Spinner spinner;
    private Context context;
    private String selsected;
    private String subjects[], subjects1[];
    private int holddars[], holddars1[];
    private LinearLayout ezafeKardaneDars, marhale2, linearLayout1, linearLayout2[], bg_dark;
    private ScrollView scrollView;
    private TextView cancle[], dars[], num[], dars_plus, dars_plus_txt, next_level_tv;
    private AlertDialog.Builder dialogAddDars;
    private AlertDialog _dialogAddDars;
    private View v_ezafe_kardane_dars;
    private ImageView btn_back;
    private ImageView next;
    private LinearLayout rahnama;
    private int listLength = 0;
    private LinearLayout.LayoutParams params1, params2, params3;
    public OnFragmentRemoved onFragmentRemoved;
    private PreferencesManager preferencesManager;
    private TextView add_dars_header;
    private ImageView add_dars_back;
    private EditText add_dars_et;
    private Button add_dars_btn;
    private boolean first = true;

    public VaredKardaneBarnameHaftegi() {
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_vared_kardane_barname_haftegi, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        this.context = view.getContext();
        preferencesManager = new PreferencesManager(context);

        findViews(view);
        initSpinner();

        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/IRAN-SANS.TTF");

        TextView tv = (TextView) view.findViewById(R.id.vared_barname_haftegi_txt_1);
        TextView tv1 = (TextView) view.findViewById(R.id.fragment_header_txt);
        TextView tv2 = (TextView) view.findViewById(R.id.vared_kardane_barname_haftegi_rahnama_txt);
        TextView tv3 = (TextView) view.findViewById(R.id.fragment_vared_barname_txt);

        add_dars_et.setTypeface(typeface);
        add_dars_btn.setTypeface(typeface);
        add_dars_header.setTypeface(typeface);
        dars_plus_txt.setTypeface(typeface);
        next_level_tv.setTypeface(typeface);
        tv.setTypeface(typeface);
        tv1.setTypeface(typeface);
        tv2.setTypeface(typeface);
        tv3.setTypeface(typeface);

        add_dars_btn.setOnClickListener(this);
        add_dars_back.setOnClickListener(this);
        dars_plus.setOnClickListener(this);
        btn_back.setOnClickListener(this);
        next.setOnClickListener(this);
        next_level_tv.setOnClickListener(this);
        dars_plus_txt.setOnClickListener(this);
    }

    private void initSpinner() {
        VaredKardaneBarnameHaftegiSpinnerAdapter adapter =
                new VaredKardaneBarnameHaftegiSpinnerAdapter(getContext(), R.layout.spinner_maghtae_tahsili, getResources().getStringArray(R.array.maghtae_tahsili_array));
        adapter.setDropDownViewResource(R.layout.spinner_maghtae_tahsili_dropdown);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    private void findViews(View view) {
        spinner = (Spinner) view.findViewById(R.id.vared_kardane_barname_haftegi_spinner);
        scrollView = (ScrollView) view.findViewById(R.id.vared_kardane_barname_haftegi_doros_scroll);
        linearLayout1 = (LinearLayout) view.findViewById(R.id.vared_kardane_barname_haftegi_doros);
        rahnama = (LinearLayout) view.findViewById(R.id.vared_kardane_barname_haftegi_rahnama);
        ezafeKardaneDars = (LinearLayout) view.findViewById(R.id.vared_kardane_barname_haftegi_ezafe_kardane_dars);
        dars_plus = (TextView) view.findViewById(R.id.vared_kardane_barname_haftegi_dars_plus);
        marhale2 = (LinearLayout) view.findViewById(R.id.vared_kardane_barname_haftegi_raftan_be_marhale_2);
        next = (ImageView) view.findViewById(R.id.vared_kardane_barname_haftegi_raftan_be_marhale_2_iv);
        bg_dark = (LinearLayout) view.findViewById(R.id.vared_kardane_barname_haftegi_make_bg_dark);
        btn_back = (ImageView) view.findViewById(R.id.vared_kardane_barname_haftegi_back);
        dars_plus_txt = (TextView) view.findViewById(R.id.vared_kardane_barname_haftegi_dars_text);
        next_level_tv = (TextView) view.findViewById(R.id.vared_kardane_barname_haftegi_raftan_be_marhale_2_tv);

        v_ezafe_kardane_dars = LayoutInflater.from(getContext()).inflate(R.layout.alart_ezafe_kardane_dars, null);
        add_dars_btn = (Button) v_ezafe_kardane_dars.findViewById(R.id.add_dars_btn_confirm);
        add_dars_et = (EditText) v_ezafe_kardane_dars.findViewById(R.id.add_dars_et);
        add_dars_back = (ImageView) v_ezafe_kardane_dars.findViewById(R.id.add_dars_back);
        add_dars_header = (TextView) v_ezafe_kardane_dars.findViewById(R.id.add_dars_header);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position != 0) {
            if (!first || !preferencesManager.isEnteredSchedule()) {
                selsected = parent.getItemAtPosition(position).toString();
                preferencesManager.setStudentGrade(selsected);
                preferencesManager.setStudentGradePos(position);
                linearLayout1.removeAllViews();
                getItems(selsected);
                makeChart();
            }
        } else if (preferencesManager.isEnteredSchedule() && first) {
            selsected = preferencesManager.getStudentGrade();
//            List<String> list = new LessonDatabase(getContext()).lessons();
            Set<String> strings=preferencesManager.getEnteredLessons();
            subjects = Arrays.copyOf(strings.toArray(),strings.size(),String[].class);
//            for (int i = 0; i < list.size(); i++) {
//                subjects[i] = list.get(i);
//            }
            rahnama.setVisibility(View.INVISIBLE);
            ezafeKardaneDars.setVisibility(View.VISIBLE);
            marhale2.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.VISIBLE);
            makeChart();
//            getItems(selsected);
//            getItems(selsected);
        } else {
            rahnama.setVisibility(View.VISIBLE);
            ezafeKardaneDars.setVisibility(View.INVISIBLE);
            marhale2.setVisibility(View.INVISIBLE);
            scrollView.setVisibility(View.INVISIBLE);
        }
        first = false;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public String getGrade(int i) {
        switch (i) {
            case 0:
                return "۰";
            case 1:
                return "۱";
            case 2:
                return "۲";
            case 3:
                return "۳";
            case 4:
                return "۴";
            case 5:
                return "۵";
            case 6:
                return "۶";
            case 7:
                return "۷";
            case 8:
                return "۸";
            case 9:
                return "۹";
        }
        return "۰";
    }

    private void getItems(String s) {

//        if (preferencesManager.isEnteredSchedule()){

//        }else
        if (s.equals(getResources().getString(R.string.grade_7))) {
            subjects = getResources().getStringArray(R.array.grade_7_lessons);

        } else if (s.equals(getResources().getString(R.string.grade_8))) {
            subjects = getResources().getStringArray(R.array.grade_8_lessons);

        } else if (s.equals(getResources().getString(R.string.grade_9))) {
            subjects = getResources().getStringArray(R.array.grade_9_lessons);

        } else if (s.equals(getResources().getString(R.string.grade_10_riazik))) {
            subjects = getResources().getStringArray(R.array.grade_10_riazik_lessons);

        } else if (s.equals(getResources().getString(R.string.grade_10_tajrobi))) {
            subjects = getResources().getStringArray(R.array.grade_10_tajrobi_lessons);

        } else if (s.equals(getResources().getString(R.string.grade_10_ensani))) {
            subjects = getResources().getStringArray(R.array.grade_10_ensani_lessons);

        } else if (s.equals(getResources().getString(R.string.grade_10_maaref))) {
            subjects = getResources().getStringArray(R.array.grade_10_maaref_lessons);

        } else if (s.equals(getResources().getString(R.string.grade_11_riazik))) {
            subjects = getResources().getStringArray(R.array.grade_11_riazik_lessons);

        } else if (s.equals(getResources().getString(R.string.grade_11_tajrobi))) {
            subjects = getResources().getStringArray(R.array.grade_11_tajrobi_lessons);

        } else if (s.equals(getResources().getString(R.string.grade_11_ensani))) {
            subjects = getResources().getStringArray(R.array.grade_11_ensani_lessons);

        } else if (s.equals(getResources().getString(R.string.grade_11_maaref))) {
            subjects = getResources().getStringArray(R.array.grade_11_maaref_lessons);
        }
    }

    private void makeChart() {
        listLength = subjects.length;
        holddars = new int[listLength];
        makeInitial(listLength);
        for (int i = 0; i < listLength; i++) {
            makeContents(i, subjects[i]);
        }
        scrollView.addView(linearLayout1);
    }

    private void makeNewChart() {
        linearLayout1.removeAllViews();
        scrollView.removeAllViews();
        listLength++;
        int x = 0;
        holddars1 = new int[listLength];
        subjects1 = new String[listLength];
        makeInitial(listLength);
        for (int i = 0; i < holddars.length; i++) {
            if (holddars[i] == 1) {
                holddars1[x] = holddars[i];
                subjects1[x] = subjects[i];
                makeContents(x, subjects[i]);
                x++;
            }
        }
        holddars1[x] = 1;
        holddars = new int[holddars1.length];
        holddars = holddars1;
        subjects1[x] = add_dars_et.getText().toString();
        subjects = new String[holddars1.length];
        subjects = subjects1;
        makeContents(x, add_dars_et.getText().toString());
        scrollView.addView(linearLayout1);
    }

    private void makeInitial(int l) {
        scrollView.removeAllViews();
        rahnama.setVisibility(View.INVISIBLE);
        ezafeKardaneDars.setVisibility(View.VISIBLE);
        marhale2.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.VISIBLE);
        linearLayout2 = new LinearLayout[l];
        cancle = new TextView[l];
        dars = new TextView[l];
        num = new TextView[l];
        params1 = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        params2 = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        params3 = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        params1.setMargins(0, 10, 0, 10);
        params2.setMargins(0, 10, 0, 10);
        params3.setMargins(0, 10, 0, 10);
    }

    private void makeContents(int i, String text) {
        linearLayout2[i] = new LinearLayout(getContext());
        linearLayout2[i].setOrientation(LinearLayout.HORIZONTAL);
        holddars[i] = 1;

        params1.weight = .75f;
        cancle[i] = new TextView(getContext());
        cancle[i].setText(Html.fromHtml("&times;"));
        cancle[i].setTextColor(Color.parseColor("#ffffff"));
        cancle[i].setTextSize(TypedValue.COMPLEX_UNIT_DIP, 32.5f);
//        cancle[i].setBackgroundColor(Color.parseColor("#594747"));
        cancle[i].setBackgroundResource(R.drawable.rounded_left_rectangle_dark);
        cancle[i].setPadding(25, 5, 0, 0);
        cancle[i].setLayoutParams(params1);
        cancle[i].setId(i);
        cancle[i].setOnClickListener(this);

        params2.weight = 5f;
        dars[i] = new TextView(getContext());
        dars[i].setText(text);
        dars[i].setTextColor(Color.parseColor("#dfc30f"));
        dars[i].setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16.75f);
        dars[i].setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/IRAN-SANS.TTF"));
        dars[i].setBackgroundColor(Color.parseColor("#594747"));
        dars[i].setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        dars[i].setMaxLines(1);
        dars[i].setEllipsize(TextUtils.TruncateAt.MARQUEE);
        float padding_dp = 16.75f;
        float density = getResources().getDisplayMetrics().density;
        int padding_px = (int) (padding_dp * density);
        dars[i].setPadding(0, padding_px, 30, padding_px);
        dars[i].setLayoutParams(params2);

        params3.weight = 1.25f;
        num[i] = new TextView(getContext());
        num[i].setText(getPersianNum(i));
        num[i].setTextColor(Color.parseColor("#594747"));
        num[i].setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25);
        num[i].setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/B Koodak Bold.TTF"));
//        num[i].setBackgroundColor(Color.parseColor("#dfc30f"));
        num[i].setBackgroundResource(R.drawable.rounded_right_rectangle_yellow);
        num[i].setGravity(Gravity.CENTER);
        num[i].setLayoutParams(params3);

        linearLayout2[i].setWeightSum(7);
        linearLayout2[i].addView(cancle[i]);
        linearLayout2[i].addView(dars[i]);
        linearLayout2[i].addView(num[i]);
        linearLayout1.addView(linearLayout2[i]);
    }

    private StringBuilder getPersianNum(int i) {
        int x = i + 1;
        String s = "";
        while (x != 0) {
            s += getGrade(x % 10);
            x = x / 10;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(s);
        sb = sb.reverse();
        return sb;
    }

    private boolean checkDars() {
        if (add_dars_et.getText().toString().isEmpty()) {
            add_dars_et.setError("لطفا نام درس را وارد کنید!");
            return false;
        }
        for (int i = 0; i < holddars.length; i++) {
            if (holddars[i] == 1) {
                if (dars[i].getText().toString().equals(add_dars_et.getText().toString())) {
                    add_dars_et.setError("نام درس تکراری است!");
                    return false;
                }
                if (dars[i].getText().toString().trim().equals(add_dars_et.getText().toString().trim())) {
                    add_dars_et.setError("نام درس تکراری است!");
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.vared_kardane_barname_haftegi_dars_plus:
            case R.id.vared_kardane_barname_haftegi_dars_text:
//                bg_dark.setBackgroundColor(Color.parseColor("#2a101010"));
                dialogAddDars = new AlertDialog.Builder(getContext());
                dialogAddDars.setCancelable(false);
                dialogAddDars.setView(v_ezafe_kardane_dars);
                _dialogAddDars = dialogAddDars.create();
                _dialogAddDars.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                _dialogAddDars.show();
                break;

            case R.id.add_dars_btn_confirm:
                if (checkDars()) {
                    bg_dark.setBackgroundColor(Color.TRANSPARENT);
                    makeNewChart();
                    add_dars_et.setText("");
                    _dialogAddDars.dismiss();
                    if (v_ezafe_kardane_dars.getParent() != null)
                        ((ViewGroup) v_ezafe_kardane_dars.getParent()).removeView(v_ezafe_kardane_dars);
                }
                break;
            case R.id.add_dars_back:
                bg_dark.setBackgroundColor(Color.TRANSPARENT);
                add_dars_et.setText("");
                _dialogAddDars.dismiss();
                if (v_ezafe_kardane_dars.getParent() != null)
                    ((ViewGroup) v_ezafe_kardane_dars.getParent()).removeView(v_ezafe_kardane_dars);
                break;
            case R.id.vared_kardane_barname_haftegi_raftan_be_marhale_2_tv:
            case R.id.vared_kardane_barname_haftegi_raftan_be_marhale_2_iv:
                if (subjects.length > 0) {
                    Set<String> lessons=new HashSet<>(Arrays.asList(subjects));
                    preferencesManager.setEnteredLessons(lessons);
                    MainActivity m = (MainActivity) getContext();
                    m.setSubjects(subjects);
                    Fragment fragment = new EntekhabeDarsBarayeZang();
                    FragmentTransaction fragmentTransaction = this.getFragmentManager().beginTransaction();
                    fragmentTransaction.remove(this);
                    fragmentTransaction.add(R.id.fragment_container, fragment);
                    fragmentTransaction.commit();
                } else {
                    AlertHelper.makeDialog(context,R.string.txt_no_empty_lessons);
                }
                break;

            case R.id.vared_kardane_barname_haftegi_back:
                Fragment fragments = getActivity().getSupportFragmentManager().findFragmentById(R.id.fragment_container);
                if (fragments != null) {
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .remove(fragments)
                            .commit();
                }
                onFragmentRemoved.setClickables();
                break;

            default:
                int index = v.getId();
                int counter = 0;
                linearLayout1.removeView(linearLayout2[index]);
                holddars[index] = 0;
                for (int i = 0; i < holddars.length; i++) {
                    if (holddars[i] == 1) {
                        num[i].setText(getPersianNum(counter));
                        counter++;
                    }
                }
                scrollView.removeAllViews();
                scrollView.addView(linearLayout1);
                listLength--;
                subjects = new String[listLength];
                for (int i = 0; i < listLength; i++) {
                    subjects[i] = dars[i].getText().toString();
                }
        }
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
//                    if(!preferencesManager.isEnteredSchedule()){
//                        preferencesManager.setStudentGrade(null);
//                        preferencesManager.setStudentGradePos(-1);
//                    }
                    back();
                    return true;
                }
                return false;
            }
        });
    }

    private void back() {
        Fragment fragments = getActivity().getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (fragments != null) {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .remove(fragments)
                    .commit();
        }
        onFragmentRemoved.setClickables();
    }

}
