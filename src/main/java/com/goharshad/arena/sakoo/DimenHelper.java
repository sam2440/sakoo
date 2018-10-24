package com.goharshad.arena.sakoo;

import android.app.Activity;
import android.graphics.Point;
import android.view.Display;

/**
 * Created by Home on 8/27/2018.
 */

public class DimenHelper {

    public static double getDeviceWidth(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        return point.x;
    }

    public static double getDeviceHeight(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        return point.y;
    }

    public static double getFragmentHeight(Activity activity){
        return .82 * getDeviceHeight(activity);
    }

    public static double getFragmentWidth(Activity activity){
        return .85 * getDeviceHeight(activity);
    }

}
