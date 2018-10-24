package com.goharshad.arena.sakoo;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class FirstLaunchFragmentPagerAdapter extends android.support.v4.app.FragmentPagerAdapter {
    private Context context;
//    private int[] layouts={
//            R.layout.layout_first,
//            R.layout.layout_second,
//            R.layout.layout_third,
//            R.layout.layout_fourth,
//            R.layout.layout_fifth,
//            R.layout.layout_sixth,
//            R.layout.layout_seventh
//    };
    private int pos;

    public FirstLaunchFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context=context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0 :
                return new SlideFirst();
            case 1 :
                return new SlideSecond();
            case 2 :
                return new SlideThird();
            case 3 :
                return new SlideForth();
            case 4 :
                return new SlideFifth();
            case 5 :
                return new SlideSixth();
            case 6 :
                return new SlideSeventh();
        }
        return null;
    }

    private void scaleView(View view, @IdRes int id,float from,float to,int duration,int delay) {
        ImageView iv= (ImageView) view.findViewById(id);
        ObjectAnimator animator=ObjectAnimator.ofFloat(iv,"scaleX",from,to);
        animator.setDuration(duration);
        animator.setStartDelay(delay);
        animator.start();

        ObjectAnimator animator1=ObjectAnimator.ofFloat(iv,"scaleY",from,to);
        animator1.setDuration(duration);
        animator1.setStartDelay(delay);
        animator1.start();
    }

    public int getPos(){
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    @Override
    public int getCount() {
        return 7;
    }
}
