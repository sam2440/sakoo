package com.goharshad.arena.sakoo;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Home on 8/29/2018.
 */

public class TaklifAdapter extends ArrayAdapter implements View.OnClickListener {

    private List<Taklif> taklifList;
    private Context context;
    private MainActivity main;
    private TaklifDatabase taklifDatabase;
    private int mPosition = 0;
    private static TaklifStatus status = null;
    private static String lesson, taklif;
    private boolean setEnabled=true;

    public TaklifAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List objects) {
        super(context, resource, objects);
        this.taklifList = objects;
        this.context = context;
        this.main= (MainActivity) context;
        taklifDatabase = new TaklifDatabase(context);
    }

    public int getCurrentPosition() {
        return mPosition;
    }

    public static TaklifStatus getCurrentStatus() {
        return status;
    }

    public static String getCurrentLesson() {
        return lesson;
    }

    public static String getCurrentTaklif() {
        return taklif;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lesson_taklif_list_item, parent, false);
        final Taklif taklif = taklifList.get(position);

        RelativeLayout taklifLayer = (RelativeLayout) view.findViewById(R.id.taklif_item_container);
        CheckBox isDoneCheck = (CheckBox) view.findViewById(R.id.taklif_checkbox);
        ImageView timeEditor = (ImageView) view.findViewById(R.id.taklif_edit_time_entered);
        ImageView lessonRemover = (ImageView) view.findViewById(R.id.taklif_erase_taklif_donity);

        isDoneCheck.setClickable(false);
        if (taklif.getStatus()==TaklifStatus.TODAY_DONE
                || taklif.getStatus()==TaklifStatus.DELAYED_DONE
                || taklif.getStatus()==TaklifStatus.TOMORROWED_DONE) {
            isDoneCheck.setButtonDrawable(R.drawable.checkbox_active);
            timeEditor.setVisibility(View.VISIBLE);
            lessonRemover.setVisibility(View.VISIBLE);
        }else{
            isDoneCheck.setButtonDrawable(R.drawable.checkbox_deactive);
        }
        TextView taklifText = (TextView) view.findViewById(R.id.taklif_text);
        final TextView lessonText = (TextView) view.findViewById(R.id.lesson_text);
        isDoneCheck.setChecked(false);
        taklifLayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPosition = position;
                if (taklif.getStatus()==TaklifStatus.TODAY_DONE
                        || taklif.getStatus()==TaklifStatus.DELAYED_DONE
                        || taklif.getStatus()==TaklifStatus.TOMORROWED_DONE) {
                    int time=taklifDatabase.getStudyTimeOf(taklif.getLesson(),taklif.getTaklif(),taklif.getStatus());
                    int hour=TimeHandler.getHour(time);
                    int min=TimeHandler.getMinute(time);
                    AlertHelper.makeToast(context,"زمان مطالعه : "+hour+" ساعت و "+min+" دقیقه ");
                    return;
                }
                TaklifAdapter.status = taklif.getStatus();
                TaklifAdapter.lesson = taklif.getLesson();
                TaklifAdapter.taklif = taklif.getTaklif();
                main.onPickTime();
            }
        });

        timeEditor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                main.isToUpdateStudyTime=true;
                TaklifAdapter.lesson = taklif.getLesson();
                TaklifAdapter.taklif = taklif.getTaklif();
                TaklifAdapter.status = taklif.getStatus();
                main.onPickTime();
            }
        });

        lessonRemover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TaklifAdapter.lesson = taklif.getLesson();
                TaklifAdapter.taklif = taklif.getTaklif();
                TaklifAdapter.status = taklif.getStatus();

                TaklifStatus to=null;
                switch (taklif.getStatus()){
                    case DELAYED_DONE:
                        to=TaklifStatus.DELAYED;
                        break;
                    case TOMORROWED_DONE:
                        to=TaklifStatus.TOMORROWED;
                        break;
                    case TODAY_DONE:
                        to=TaklifStatus.TODAY;
                        break;
                }
                main.isToUndoTaklifDone=true;
                main.unDoTaklif();
                taklifDatabase.undo(taklif.getLesson(),taklif.getTaklif(),taklif.getStatus(),to);
                main.updateTodayTaklif();
                main.updateDelayedTaklif();
                main.updateTomorrowTaklif();
            }
        });


        lessonText.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/IRAN-SANS.TTF"));
        lessonText.setTextColor(context.getResources().getColor(R.color.main_subject_text_color));
        lessonText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
        lessonText.setText(taklif.getLesson() + " : ");

        taklifText.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/IRAN-SANS.TTF"));
        taklifText.setTextColor(context.getResources().getColor(R.color.colorDark_1));
        taklifText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 13);
        taklifText.setText(taklif.getTaklif());

        return view;
    }

    @Override
    public int getCount() {
        return taklifList.size();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.taklif_erase_taklif_donity :
                break;
            case R.id.taklif_edit_time_entered :
                main.onPickTime();
                break;
        }
    }

    class MyOnClickListener implements View.OnClickListener {

        private int position;
        private TaklifStatus status;

        public MyOnClickListener(int position, TaklifStatus status) {
            this.position = position;
            this.status = status;
        }

        @Override
        public void onClick(View v) {

//            switch (status){
//                case TODAY:
//                    Log.d("TAG","today task");
//                    break;
//                case DELAYED:
//                    Log.d("TAG","delayed task");
//                    break;
//                case TOMORROWED:
//                    Log.d("TAG","tomorrowed task");
//                    break;
//            }
        }
    }

    public void setEnabled(boolean setEnabled) {
        this.setEnabled = setEnabled;
    }

    @Override
    public boolean isEnabled(int position) {
        return setEnabled;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return setEnabled;
    }
}
