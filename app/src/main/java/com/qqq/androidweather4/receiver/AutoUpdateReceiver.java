package com.qqq.androidweather4.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.qqq.androidweather4.service.AutoUpdateService;
import com.qqq.androidweather4.service.AutoUpdateWidgetService;

/**
 * Created by qqq on 2016-12-26.
 */

public class AutoUpdateReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, AutoUpdateWidgetService.class);
        context.startService(i);
    }
}
