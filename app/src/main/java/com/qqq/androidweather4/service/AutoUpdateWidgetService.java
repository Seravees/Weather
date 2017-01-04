package com.qqq.androidweather4.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.RemoteViews;

import com.qqq.androidweather4.R;
import com.qqq.androidweather4.WeatherApplication;
import com.qqq.androidweather4.activity.MainActivity;
import com.qqq.androidweather4.model.WeatherInfoNow;
import com.qqq.androidweather4.util.HttpCallbackListener;
import com.qqq.androidweather4.util.HttpUtil;
import com.qqq.androidweather4.util.SharePreferencesUtil;
import com.qqq.androidweather4.util.Utils;
import com.qqq.androidweather4.widget.WeatherWidget;

import java.util.Date;

/**
 * Created by qqq on 2016-12-26.
 */

public class AutoUpdateWidgetService extends Service {
    AlarmManager alarmManager;
    PendingIntent pendingIntent;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("AutoUpdateWidgetService", "onStart");

        final RemoteViews views = WeatherWidget.getWeatherWidget(this);
        final AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        final int[] appids = appWidgetManager.getAppWidgetIds(new ComponentName(this, WeatherWidget.class));


        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpUtil.sendHttpRequest(WeatherApplication.getAddress1(), new HttpCallbackListener() {
                    @Override
                    public void onFinish(String response) {
                        WeatherInfoNow weatherInfoNow = WeatherApplication.getGson().fromJson(response, WeatherInfoNow.class);
                        SharePreferencesUtil.put("currentWeather", weatherInfoNow.getResults().get(0).getNow().getText());
                        SharePreferencesUtil.put("todayTemp", weatherInfoNow.getResults().get(0).getNow().getTemperature() + "℃");
                        SharePreferencesUtil.put("weatherCode", weatherInfoNow.getResults().get(0).getNow().getCode());
                        SharePreferencesUtil.put("cityField", weatherInfoNow.getResults().get(0).getLocation().getName());

                        // RemoteViews views = new RemoteViews(AutoUpdateWidgetService.this.getPackageName(), R.layout.weather_widget);
                        views.setImageViewResource(R.id.widget_status_icon, Utils.getIcon(Integer.parseInt(SharePreferencesUtil.get("weatherCode", "0"))));
                        views.setTextViewText(R.id.widget_city, SharePreferencesUtil.get("cityField", ""));
                        views.setTextViewText(R.id.widget_weather_status, SharePreferencesUtil.get("currentWeather", "") + String.valueOf(new Date()));
                        views.setTextViewText(R.id.widget_temp, SharePreferencesUtil.get("todayTemp", ""));

                        // AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(AutoUpdateWidgetService.this);
                        appWidgetManager.updateAppWidget(appids, views);
                        //appWidgetManager.updateAppWidget(new ComponentName(AutoUpdateWidgetService.this, WeatherWidget.class), views);
                        Log.d("AutoUpdateWidgetService", String.valueOf(new Date()));

                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });
            }
        }).start();


        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        int hours = 60 * 60 * 1000;
        long triggerAtTime = SystemClock.elapsedRealtime() + hours;

        Intent i = new Intent(this, AutoUpdateWidgetService.class);
        pendingIntent = PendingIntent.getService(this, 0, i, 0);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pendingIntent);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.d("AutoUpdateWidgetService", "onDestroy");
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
        super.onDestroy();
    }
}
