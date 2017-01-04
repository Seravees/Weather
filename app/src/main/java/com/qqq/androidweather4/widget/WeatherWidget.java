package com.qqq.androidweather4.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.widget.RemoteViews;

import com.qqq.androidweather4.R;
import com.qqq.androidweather4.WeatherApplication;
import com.qqq.androidweather4.activity.MainActivity;
import com.qqq.androidweather4.model.WeatherInfoNow;
import com.qqq.androidweather4.receiver.ScreenStatusReceiver;
import com.qqq.androidweather4.service.AutoUpdateWidgetService;
import com.qqq.androidweather4.util.HttpCallbackListener;
import com.qqq.androidweather4.util.HttpUtil;
import com.qqq.androidweather4.util.SharePreferencesUtil;
import com.qqq.androidweather4.util.Utils;

/**
 * Implementation of App Widget functionality.
 */
public class WeatherWidget extends AppWidgetProvider {
    public static final String UPDATE_ACTION = "main_activity_update_ui";
    private static ScreenStatusReceiver screenStatusReceiver;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.weather_widget);


        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    public static RemoteViews getWeatherWidget(Context context) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.weather_widget);

        return views;

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        Log.d("WeatherWidget", "onUpdate");
        for (int j = 0; j < appWidgetIds.length; j++) {
            Log.d("WeatherWidget", "" + appWidgetIds[j]);
        }

        final RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.weather_widget);

        HttpUtil.sendHttpRequest(WeatherApplication.getAddress1(), new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                WeatherInfoNow weatherInfoNow = WeatherApplication.getGson().fromJson(response, WeatherInfoNow.class);
                SharePreferencesUtil.put("weatherCode", weatherInfoNow.getResults().get(0).getNow().getCode());
                SharePreferencesUtil.put("cityField", weatherInfoNow.getResults().get(0).getLocation().getName());
                SharePreferencesUtil.put("currentWeather", weatherInfoNow.getResults().get(0).getNow().getText());
                SharePreferencesUtil.put("todayTemp", weatherInfoNow.getResults().get(0).getNow().getTemperature());

                Log.d("WeatherWidget", "onFinish");
            }

            @Override
            public void onError(Exception e) {


            }
        });
        views.setImageViewResource(R.id.widget_status_icon, Utils.getIcon(Integer.parseInt(SharePreferencesUtil.get("weatherCode", "0"))));
        views.setTextViewText(R.id.widget_city, SharePreferencesUtil.get("cityField", ""));
        views.setTextViewText(R.id.widget_weather_status, SharePreferencesUtil.get("currentWeather", "") + "onUpdate");
        views.setTextViewText(R.id.widget_temp, SharePreferencesUtil.get("todayTemp", ""));

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.widget, pendingIntent);

        appWidgetManager.updateAppWidget(new ComponentName(context, WeatherWidget.class), views);

        Intent i = new Intent(context, AutoUpdateWidgetService.class);
        context.startService(i);

        screenStatusReceiver = new ScreenStatusReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_SCREEN_ON);
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
        context.getApplicationContext().registerReceiver(screenStatusReceiver, intentFilter);
       /* for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }*/
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
        Intent intent = new Intent(context, AutoUpdateWidgetService.class);
        context.stopService(intent);

        context.getApplicationContext().unregisterReceiver(screenStatusReceiver);
    }
}

