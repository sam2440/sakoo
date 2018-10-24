package com.goharshad.arena.sakoo;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AlertHelper {

    public static void makeSnackbar(Context context, @StringRes int res, View container, int...sbColors){
        final Snackbar snackbar=Snackbar.make(container,res,Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("باشه", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                snackbar.dismiss();
            }
        });
        View snackView=snackbar.getView();
        if (sbColors.length>0) snackView.setBackgroundResource(sbColors[0]);
        TextView textView= (TextView) snackView.findViewById(android.support.design.R.id.snackbar_text);
        if (sbColors.length>1) textView.setTextColor(sbColors[1]);
        textView.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/IRAN-SANS.TTF"));
        snackbar.show();
    }

    public static void makeDialog(Context context,@StringRes int res){
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setMessage(res)
                .show();

        dialog.getWindow().setBackgroundDrawableResource(R.drawable.rounded_rectangle_white);
        TextView textView = (TextView) dialog.findViewById(android.R.id.message);
        textView.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/IRAN-SANS.TTF"));
        textView.setPadding(12, 12, 12, 12);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12f);
        textView.setTextColor(ContextCompat.getColor(context, R.color.colorDark_1));
    }

    public static void makeToast(Context context, String msg) {
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/IRAN-SANS.TTF");
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        LinearLayout toastView = (LinearLayout) toast.getView();
        TextView textView = (TextView) toastView.getChildAt(0);
        textView.setTypeface(typeface);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12f);
        toast.show();

    }}
