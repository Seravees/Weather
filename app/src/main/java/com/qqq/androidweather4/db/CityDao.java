package com.qqq.androidweather4.db;

/**
 * Created by qqq on 2016-12-22.
 */

public class CityDao {

    public static final String TABLE_NAME = "city";

    static final String CITY_ID = "city_ID";
    public static final String NAME = "name";
    public static final String NAME_ENGLISH = "name_english";
    static final String COUNTRY = "country";
    static final String COUNTRY_CODE = "country_code";
    static final String OWNERSHIP_1 = "ownership_1";
    static final String OWNERSHIP_1_ENGLISH = "ownership_1_english";
    static final String OWNERSHIP_2 = "ownership_2";
    static final String OWNERSHIP_2_ENGLISH = "ownership_2_english";
    static final String TIMEZONE = "timezone";
    static final String LEVEL = "level";


    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";

    public static final String SQL_CREATE_ENTRIES = "create table if not exists " + TABLE_NAME + "(" +
            CITY_ID + TEXT_TYPE + COMMA_SEP +
            NAME + TEXT_TYPE + COMMA_SEP +
            NAME_ENGLISH + TEXT_TYPE + COMMA_SEP +
            COUNTRY + TEXT_TYPE + COMMA_SEP +
            COUNTRY_CODE + TEXT_TYPE + COMMA_SEP +
            OWNERSHIP_1 + TEXT_TYPE + COMMA_SEP +
            OWNERSHIP_1_ENGLISH + TEXT_TYPE + COMMA_SEP +
            OWNERSHIP_2 + TEXT_TYPE + COMMA_SEP +
            OWNERSHIP_2_ENGLISH + TEXT_TYPE + COMMA_SEP +
            TIMEZONE + TEXT_TYPE + COMMA_SEP +
            LEVEL + TEXT_TYPE +
            ")";
}
