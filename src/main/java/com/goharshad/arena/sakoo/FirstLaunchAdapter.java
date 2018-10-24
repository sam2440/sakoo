package com.goharshad.arena.sakoo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class FirstLaunchAdapter extends PagerAdapter{


    private Context context;
    private int[] layouts = {
            R.layout.layout_slide_first,
            R.layout.layout_slide_second,
            R.layout.layout_slide_third,
            R.layout.layout_slide_forth,
            R.layout.layout_slide_fifth,
            R.layout.layout_slide_sixth,
            R.layout.layout_slide_seventh
    };
    private int pos;

    public FirstLaunchAdapter(Context context) {
        this.context = context;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(layouts[position],container,false);

        RelativeLayout layout = (RelativeLayout) view.findViewById(R.id.first_launch_space_between);
        layout.getLayoutParams().height = (int) (.109 * DimenHelper.getDeviceHeight(((Activity) context)));

        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/IRAN-SANS.TTF");
        TextView title = (TextView) view.findViewById(R.id.first_launch_slide_title);
        TextView description = (TextView) view.findViewById(R.id.first_launch_slide_txt);
        switch (position){
            case 0:
                description.setText("مدیر و مشاور درسی خودتون باشید");
                break;
            case 1:
                description.setText("برای هر روزتون هدف داشته باشید" + "\n" + "و بهش برسید" + "\n" + "یه هدف منطفی");
                break;
            case 2:
                description.setText("خودتون رو با گذشته خودتون بسنجید"+"\n"+"نه دیگران !!!!");
                break;
            case 3 :
                description.setText("7 روز هفته"+"\n"+"24 ساعت شبانه روز"+"\n"+"با نت و بی نت");
                break;
            case 4 :
                description.setText("با ابزاری که بهتون می دیم "+"\n"+"راحت تر درس بخونین");
                break;
            case 5 :
                description.setText("از گذشته درسی خودتون مطلع باشید"+"\n"+"و تحلیلش کنید!");
                break;
            case 6 :
                description.setText("مدرسه خود را در گوشیتان مدیریت کنید");
                break;
        }
        title.setTypeface(typeface);
        description.setTypeface(typeface);

        container.addView(view);
        return view;
//        return super.instantiateItem(container, position);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    public int getPos(){
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    @Override
    public int getCount() {
        return layouts.length;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
