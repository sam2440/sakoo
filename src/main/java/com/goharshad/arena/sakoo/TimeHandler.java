package com.goharshad.arena.sakoo;

import java.util.concurrent.TimeUnit;

/**
 * Created by Home on 9/3/2018.
 */

public class TimeHandler {

    public static int getHour(long time){
        return (int) TimeUnit.MINUTES.toHours(time);
    }

    public static int getMinute(long time){
        return (int) (time - TimeUnit.MINUTES.toHours(time)*60);
    }

    public static String getValidated(long time){
        long hour,minute;
        hour=TimeUnit.MINUTES.toHours(time);
        minute=time - hour*60 ;
        return (hour<10 ? "0"+hour : hour )+":"+ (minute<10 ? "0"+minute : minute) ;
    }

    public static int getIntOf(String time){
        String[] splits=time.split(":");
        if (splits.length==2){
            return (int) (TimeUnit.HOURS.toMinutes(Long.parseLong(splits[0]))
                                +Integer.parseInt(splits[1]));
        }
        return 0;
    }
}
