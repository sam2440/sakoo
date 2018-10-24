package com.goharshad.arena.sakoo;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by Home on 9/15/2018.
 */

public class VaredKardaneBarnameHaftegiSpinnerAdapter extends ArrayAdapter {

    private Context context;
    private boolean first=true;

    public VaredKardaneBarnameHaftegiSpinnerAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull Object[] objects) {
        super(context, resource, objects);
        this.context=context;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView view = (TextView) super.getView(position, convertView, parent);
//        String str;
//        if ((str=new PreferencesManager(context).getStudentGrade())!=null && first){
//            first=false;
//            view.setText(str);
//        }
        Typeface font=Typeface.createFromAsset(getContext().getAssets(), "fonts/IRAN-SANS.TTF");
        view.setTypeface(font);
        return view;
    }

    // Affects opened state of the spinner
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView view = (TextView) super.getDropDownView(position, convertView, parent);
        Typeface font=Typeface.createFromAsset(getContext().getAssets(), "fonts/IRAN-SANS.TTF");
        view.setTypeface(font);
        return view;
    }

}
