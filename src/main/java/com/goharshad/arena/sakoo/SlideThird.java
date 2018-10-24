package com.goharshad.arena.sakoo;


import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class SlideThird extends Fragment {


    private View view;
    private Context context;

    public SlideThird() {
        // Required empty public constructor
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && view != null) {
            scaleView(R.id.slide_3_circle_1, 0, 1, 500, 5);
            fadeView(R.id.slide_3_balance,0,1,500,500);
            translateAlphaView(R.id.first_launch_slide_title,10,500,0);
            translateAlphaView(R.id.first_launch_slide_txt, 10,500,0);
        } else if (!isVisibleToUser && view != null) {
            descaleView(R.id.slide_3_circle_1);
            defadeView(R.id.slide_3_balance);
            deTranslateAlphaView(R.id.first_launch_slide_title,10);
            deTranslateAlphaView(R.id.first_launch_slide_txt,10);
        }
    }

    private void defadeView(int id) {
        ImageView imageView= (ImageView) view.findViewById(id);
        imageView.setAlpha(0f);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_slide_third, container, false);
    }

    private void deTranslateAlphaView(int id, int toY) {
        View view = this.view.findViewById(id);
        float density = getResources().getDisplayMetrics().density;
        view.setTranslationY(toY * density);
        view.setAlpha(0f);
    }

    private void translateAlphaView(int id, int fromY, int duration, int delay) {
        View view = this.view.findViewById(id);
        float density = getResources().getDisplayMetrics().density;

        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationY", fromY * density, 0);
        animator.setDuration(duration);
        animator.setStartDelay(delay);
        animator.start();

        ObjectAnimator animator1 = ObjectAnimator.ofFloat(view, "alpha", 0, 1);
        animator1.setDuration(duration);
        animator1.setStartDelay(delay);
        animator1.start();
    }

    private void descaleView(@IdRes int id) {
        ImageView imageView = (ImageView) view.findViewById(id);
        imageView.setScaleX(0);
        imageView.setScaleY(0);
    }

    private void fadeView(@IdRes int id, float from, float to,int duration,int delay){
        ImageView imageView= (ImageView) view.findViewById(id);
        ObjectAnimator fader=ObjectAnimator.ofFloat(imageView,"alpha",0,1);
        fader.setStartDelay(delay);
        fader.setDuration(duration);
        fader.start();
    }

    private void scaleView(@IdRes int id, float from, float to, int duration, int delay) {
        ImageView iv = (ImageView) view.findViewById(id);
        ObjectAnimator animator = ObjectAnimator.ofFloat(iv, "scaleX", from, to);
        animator.setDuration(duration);
        animator.setStartDelay(delay);
        animator.start();

        ObjectAnimator animator1 = ObjectAnimator.ofFloat(iv, "scaleY", from, to);
        animator1.setDuration(duration);
        animator1.setStartDelay(delay);
        animator1.start();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        this.view=view;
        this.context=view.getContext();
        RelativeLayout layout = (RelativeLayout) view.findViewById(R.id.first_launch_space_between);
        layout.getLayoutParams().height = (int) (.109 * DimenHelper.getDeviceHeight(((Activity) context)));

        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/IRAN-SANS.TTF");
        TextView title = (TextView) view.findViewById(R.id.first_launch_slide_title);
        TextView description = (TextView) view.findViewById(R.id.first_launch_slide_txt);

        title.setTypeface(typeface);
        description.setTypeface(typeface);
        description.setText("خودتون رو با گذشته خودتون بسنجید"+"\n"+"نه دیگران !!!!");
    }
}
