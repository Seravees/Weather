package com.qqq.androidweather4;

import android.app.Application;
import android.util.Log;

import com.google.gson.Gson;
import com.qqq.androidweather4.db.CityDao;
import com.qqq.androidweather4.db.DBManage;
import com.qqq.androidweather4.util.SharePreferencesUtil;

/**
 * Created by qqq on 2016-12-22.
 */

public class WeatherApplication extends Application {
    public static final String API = "t9m0okm78rdbudz1";
    private static Application sApplication;
    private static Gson sGson = new Gson();

    static String city_id;


    @Override
    public void onCreate() {
        super.onCreate();
        sApplication = this;

        DBManage.getInstance().copyCityToDB();


    }

    public static Gson getGson() {
        return sGson;
    }

    public static Application getContext() {
        return sApplication;
    }

    public static void setCity_id(String city_id) {
        WeatherApplication.city_id = city_id;
    }

    public static String getCity_id() {
        return city_id;
    }

    public static String getAddress1() {
        String address1 = "https://api.thinkpage.cn/v3/weather/now.json?key=" + WeatherApplication.API + "&location=" + city_id + "&language=zh-Hans&unit=c";
        return address1;
    }

    public static String getAddress2() {
        String address2 = "https://api.thinkpage.cn/v3/weather/daily.json?key=" + WeatherApplication.API + "&location=" + city_id + "&language=zh-Hans&unit=c&start=0&days=3";
        return address2;
    }

    public static String getAddress3() {
        String address3 = "https://api.thinkpage.cn/v3/life/suggestion.json?key=" + WeatherApplication.API + "&location=" + city_id + "&language=zh-Hans";
        return address3;
    }
}
