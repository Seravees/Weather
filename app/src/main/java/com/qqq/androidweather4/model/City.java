package com.qqq.androidweather4.model;

import java.util.List;

/**
 * Created by qqq on 2016-12-22.
 */


public class City {
   /* "city_info":[{"city_ID":"TV9JG0M1S9QU","name":"阿里","name_english":"Ali","country":"中国","country_code":"CN","ownership_1":"西藏","ownership_1_english":"Xizang","ownership_2":"阿里","ownership_2_english":"Ali","timezone":"Asia/Shanghai","level":"中国地级市"}]*/

    private List<City_info> city_info;

    public List<City_info> getCity_info() {
        return city_info;
    }

    public void setCity_info(List<City_info> city_info) {
        this.city_info = city_info;
    }

    public class City_info {
        private String city_ID;
        private String name;
        private String name_english;
        private String country;
        private String country_code;
        private String ownership_1;
        private String ownership_1_english;
        private String ownership_2;
        private String ownership_2_english;
        private String timezone;
        private String level;

        public String getCity_ID() {
            return city_ID;
        }

        public void setCity_ID(String city_ID) {
            this.city_ID = city_ID;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getCountry_code() {
            return country_code;
        }

        public void setCountry_code(String country_code) {
            this.country_code = country_code;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
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

        public String getOwnership_1() {
            return ownership_1;
        }

        public void setOwnership_1(String ownership_1) {
            this.ownership_1 = ownership_1;
        }

        public String getOwnership_1_english() {
            return ownership_1_english;
        }

        public void setOwnership_1_english(String ownership_1_english) {
            this.ownership_1_english = ownership_1_english;
        }

        public String getOwnership_2() {
            return ownership_2;
        }

        public void setOwnership_2(String ownership_2) {
            this.ownership_2 = ownership_2;
        }

        public String getOwnership_2_english() {
            return ownership_2_english;
        }

        public void setOwnership_2_english(String ownership_2_english) {
            this.ownership_2_english = ownership_2_english;
        }

        public String getTimezone() {
            return timezone;
        }

        public void setTimezone(String timezone) {
            this.timezone = timezone;
        }
    }
}
