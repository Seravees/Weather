package com.qqq.androidweather4.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by qqq on 2016-12-22.
 */

public class DBHelper extends SQLiteOpenHelper {
    private static DBHelper sDBHelper;
    private static final String DBNAME = "weather";
    private static final int CURRENT_VERSION = 1;

    public static DBHelper getInstance(Context context) {
        if (sDBHelper == null) {
            synchronized (DBHelper.class) {
                if (sDBHelper == null) {
                    sDBHelper = new DBHelper(context, DBNAME, null, CURRENT_VERSION);
                }
            }
        }
        return sDBHelper;
    }

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        onUpgrade(db, 0, CURRENT_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        for (int version = oldVersion + 1; version <= newVersion; version++) {
            upgradeTo(db, version);
        }
    }

    private void upgradeTo(SQLiteDatabase db, int version) {
        switch (version) {
            case 1:
                db.execSQL(CityDao.SQL_CREATE_ENTRIES);
                break;
            default:
                break;
        }
    }
}
