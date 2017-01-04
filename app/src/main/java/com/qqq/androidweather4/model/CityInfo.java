package com.qqq.androidweather4.model;

import com.qqq.androidweather4.util.SharePreferencesUtil;

/**
 * Created by qqq on 2016-12-22.
 */

public class CityInfo {
    private String city_id;
    private String name;
    private String name_english;

    public CityInfo(String city_id, String name, String name_english) {
        this.city_id = city_id;
        this.name = name;
        this.name_english = name_english;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName_english() {
        return name_english;
    }

    public void setName_english(String name_english) {
        this.name_english = name_english;
    }
}
