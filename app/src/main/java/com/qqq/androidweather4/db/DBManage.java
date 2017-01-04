package com.qqq.androidweather4.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.qqq.androidweather4.WeatherApplication;
import com.qqq.androidweather4.model.City;
import com.qqq.androidweather4.model.CityInfo;
import com.qqq.androidweather4.util.FileUtil;
import com.qqq.androidweather4.util.SharePreferencesUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qqq on 2016-12-22.
 */

public class DBManage {
    private static DBManage sDBManage;
    private static DBHelper sDBHelper;
    private static final String CITY_INITED = "CITY_INITED";

    public static DBManage getInstance() {
        if (sDBManage == null) {
            synchronized (DBHelper.class) {
                if (sDBManage == null) {
                    sDBManage = new DBManage();
                    sDBHelper = DBHelper.getInstance(WeatherApplication.getContext());
                }
            }
        }
        return sDBManage;
    }

    public void copyCityToDB() {
        boolean cityInited = SharePreferencesUtil.get(CITY_INITED, false);
        Log.d("DBManage", "" + cityInited);
        if (cityInited) {
            return;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                String cities = FileUtil.assetFileString("city.txt", WeatherApplication.getContext());
                City city = WeatherApplication.getGson().fromJson(cities, City.class);

                SQLiteDatabase db = sDBHelper.getWritableDatabase();
                db.beginTransaction();
                try {
                    ContentValues values;
                    for (City.City_info city_info : city.getCity_info()) {
                        values = new ContentValues();
                        values.put(CityDao.CITY_ID, city_info.getCity_ID());
                        values.put(CityDao.NAME, city_info.getName());
                        values.put(CityDao.NAME_ENGLISH, city_info.getName_english());
                        values.put(CityDao.COUNTRY, city_info.getCountry());
                        values.put(CityDao.COUNTRY_CODE, city_info.getCountry_code());
                        values.put(CityDao.OWNERSHIP_1, city_info.getOwnership_1());
                        values.put(CityDao.OWNERSHIP_1_ENGLISH, city_info.getOwnership_1_english());
                        values.put(CityDao.OWNERSHIP_2, city_info.getOwnership_2());
                        values.put(CityDao.OWNERSHIP_2_ENGLISH, city_info.getOwnership_2_english());
                        values.put(CityDao.TIMEZONE, city_info.getTimezone());
                        values.put(CityDao.LEVEL, city_info.getLevel());
                        db.insert(CityDao.TABLE_NAME, null, values);
                    }
                    db.setTransactionSuccessful();
                    SharePreferencesUtil.put(CITY_INITED, true);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    db.endTransaction();
                }
            }
        }).start();
    }

    public List<CityInfo> getCities(String sql) {
        SQLiteDatabase db = sDBHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        List<CityInfo> result = new ArrayList<CityInfo>();
        CityInfo cityInfo;
        while (cursor.moveToNext()) {
            String city_id = cursor.getString(cursor.getColumnIndex(CityDao.CITY_ID));
            String name = cursor.getString(cursor.getColumnIndex(CityDao.NAME));
            String name_english = cursor.getString(cursor.getColumnIndex(CityDao.NAME_ENGLISH));
            cityInfo = new CityInfo(city_id, name, name_english);
            result.add(cityInfo);
        }
        cursor.close();
        db.close();
        return result;
    }
}
