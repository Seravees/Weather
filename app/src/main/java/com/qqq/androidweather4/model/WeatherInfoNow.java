package com.qqq.androidweather4.model;

import java.util.List;

/**
 * Created by qqq on 2016-12-21.
 */

public class WeatherInfoNow {
/*
{
	"results":[{
		"location":{
			"id":"WTTDPCGXTWUS",
			"name":"苏州",
			"country":"CN",
			"path":"苏州,苏州,江苏,中国",
			"timezone":"Asia/Shanghai",
			"timezone_offset":"+08:00"
		},
		"now":{
			"text":"中雨",
			"code":"14",
			"temperature":"17"
		},
		"last_update":"2016-12-21T14:50:00+08:00"
	}]
}
*/
    private List<Results> results;

    public List<Results> getResults() {
        return results;
    }

    public void setResults(List<Results> results) {
        this.results = results;
    }

    public class Results {
        private Location location;
        private Now now;
        private String last_update;

        public String getLast_update() {
            return last_update;
        }

        public void setLast_update(String last_update) {
            this.last_update = last_update;
        }

        public Location getLocation() {
            return location;
        }

        public void setLocation(Location location) {
            this.location = location;
        }

        public Now getNow() {
            return now;
        }

        public void setNow(Now now) {
            this.now = now;
        }

        public class Location {
            private String id;
            private String name;
            private String country;
            private String path;
            private String timezone;
            private String timezone_offset;

            public String getCountry() {
                return country;
            }

            public void setCountry(String country) {
                this.country = country;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPath() {
                return path;
            }

            public void setPath(String path) {
                this.path = path;
            }

            public String getTimezone() {
                return timezone;
            }

            public void setTimezone(String timezone) {
                this.timezone = timezone;
            }

            public String getTimezone_offset() {
                return timezone_offset;
            }

            public void setTimezone_offset(String timezone_offset) {
                this.timezone_offset = timezone_offset;
            }
        }

        public class Now {
            private String text;
            private String code;
            private String temperature;

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getTemperature() {
                return temperature;
            }

            public void setTemperature(String temperature) {
                this.temperature = temperature;
            }

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }
        }
    }


}
