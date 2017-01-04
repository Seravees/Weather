package com.qqq.androidweather4.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.qqq.androidweather4.WeatherApplication;

/**
 * Created by qqq on 2016-12-22.
 */

public class SharePreferencesUtil {
    private static String defaultName = SharePreferencesUtil.class.getSimpleName();


    private static SharedPreferences getSharePreferences(String name) {
        return WeatherApplication.getContext().getSharedPreferences(name, Context.MODE_PRIVATE);
    }


    public static boolean get(String key, boolean defValue) {
        return get(defaultName, key, defValue);
    }

    public static boolean get(String name, String key, boolean defValue) {
        return getSharePreferences(name).getBoolean(key, defValue);
    }

    public static void put(String key, boolean value) {
        put(defaultName, key, value);
    }

    public static void put(String name, String key, boolean value) {
        getSharePreferences(name).edit().putBoolean(key, value).apply();
    }

    public static String get(String key, String defValue) {
        return get(defaultName, key, defValue);
    }

    public static String get(String name, String key, String defValue) {
        return getSharePreferences(name).getString(key, defValue);
    }

    public static void put(String key, String value) {
        put(defaultName, key, value);
    }

    public static void put(String name, String key, String value) {
        getSharePreferences(name).edit().putString(key, value).apply();
    }
}
