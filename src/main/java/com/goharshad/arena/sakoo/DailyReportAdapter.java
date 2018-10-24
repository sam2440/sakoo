package com.goharshad.arena.sakoo;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class DailyReportAdapter extends RecyclerView.Adapter<DailyReportAdapter.ViewHolder>{

    private Context context;
    private List<DailyReportObject> list;

    public DailyReportAdapter(Context context, List<DailyReportObject> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_daily_report,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DailyReportObject reportObject=list.get(position);
        Typeface typeface=Typeface.createFromAsset(context.getAssets(), "fonts/IRAN-SANS.TTF");
        holder.lesson.setText(reportObject.getLesson());
        holder.taklif.setText(reportObject.getTaklif());
        holder.studyTime.setText(TimeHandler.getValidated(reportObject.getStudyTime()));

        holder.lesson.setTypeface(typeface);
        holder.taklif.setTypeface(typeface);
        holder.studyTime.setTypeface(typeface);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView lesson,taklif,studyTime;

        public ViewHolder(View itemView) {
            super(itemView);
            lesson= (TextView) itemView.findViewById(R.id.daily_report_lesson);
            taklif= (TextView) itemView.findViewById(R.id.daily_report_taklif);
            studyTime= (TextView) itemView.findViewById(R.id.daily_report_study_time);
        }
    }
}
