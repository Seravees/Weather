package com.qqq.androidweather4.util;

import android.util.Log;

import com.qqq.androidweather4.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by qqq on 2016-12-30.
 */

public class Utils {

    public static int getIcon(int code) {
        int icon = R.drawable.big0;
        switch (code) {
            case 0:
                icon = R.drawable.big0;
                break;
            case 1:
                icon = R.drawable.big1;
                break;
            case 2:
                icon = R.drawable.big2;
                break;
            case 3:
                icon = R.drawable.big3;
                break;
            case 4:
                icon = R.drawable.big4;
                break;
            case 5:
                icon = R.drawable.big5;
                break;
            case 6:
                icon = R.drawable.big6;
                break;
            case 7:
                icon = R.drawable.big7;
                break;
            case 8:
                icon = R.drawable.big8;
                break;
            case 9:
                icon = R.drawable.big9;
                break;
            case 10:
                icon = R.drawable.big10;
                break;
            case 11:
                icon = R.drawable.big11;
                break;
            case 12:
                icon = R.drawable.big12;
                break;
            case 13:
                icon = R.drawable.big13;
                break;
            case 14:
                icon = R.drawable.big14;
                break;
            case 15:
                icon = R.drawable.big15;
                break;
            case 16:
                icon = R.drawable.big16;
                break;
            case 17:
                icon = R.drawable.big17;
                break;
            case 18:
                icon = R.drawable.big18;
                break;
            case 19:
                icon = R.drawable.big19;
                break;
            case 20:
                icon = R.drawable.big20;
                break;
            case 21:
                icon = R.drawable.big21;
                break;
            case 22:
                icon = R.drawable.big22;
                break;
            case 23:
                icon = R.drawable.big23;
                break;
            case 24:
                icon = R.drawable.big24;
                break;
            case 25:
                icon = R.drawable.big25;
                break;
            case 26:
                icon = R.drawable.big26;
                break;
            case 27:
                icon = R.drawable.big27;
                break;
            case 28:
                icon = R.drawable.big28;
                break;
            case 29:
                icon = R.drawable.big29;
                break;
            case 30:
                icon = R.drawable.big30;
                break;
            case 31:
                icon = R.drawable.big31;
                break;
            case 32:
                icon = R.drawable.big32;
                break;
            case 33:
                icon = R.drawable.big33;
                break;
            case 34:
                icon = R.drawable.big34;
                break;
            case 35:
                icon = R.drawable.big35;
                break;
            case 36:
                icon = R.drawable.big36;
                break;
            case 37:
                icon = R.drawable.big37;
                break;
            case 38:
                icon = R.drawable.big38;
                break;
            case 99:
                icon = R.drawable.big99;
                break;
        }
        return icon;
    }

    public static String getUpdateTime(String updateDate) {
        String updateTime = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try {
            Date update = simpleDateFormat.parse(updateDate);
            Date now = new Date();
            Calendar calendarUpdate = Calendar.getInstance();
            calendarUpdate.setTime(update);
            Calendar calendarNow = Calendar.getInstance();
            calendarNow.setTime(now);
            long dTime = calendarNow.getTimeInMillis() - calendarUpdate.getTimeInMillis();
            int minutes = new Long(dTime / 1000 / 60).intValue();
            updateTime = minutes + "分钟前更新";
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return updateTime;
    }
}
