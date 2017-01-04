package com.qqq.androidweather4.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.qqq.androidweather4.service.AutoUpdateWidgetService;

/**
 * Created by qqq on 2016-12-29.
 */

public class ScreenStatusReceiver extends BroadcastReceiver {

    String SCREEN_ON = "android.intent.action.SCREEN_ON";
    String SCREEN_OFF = "android.intent.action.SCREEN_OFF";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (SCREEN_ON.equals(intent.getAction())) {
            Log.d("ScreenStatusReceiver", "Screen_On");
            Intent i = new Intent(context, AutoUpdateWidgetService.class);
            context.startService(i);
        } else if (SCREEN_OFF.equals(intent.getAction())) {
            Log.d("ScreenStatusReceiver", "Screen_Off");
        }
    }
}
