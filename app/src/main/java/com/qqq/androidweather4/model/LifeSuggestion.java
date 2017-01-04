package com.qqq.androidweather4.model;

import java.util.List;

/**
 * Created by qqq on 2016-12-21.
 */

public class LifeSuggestion {
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
		"suggestion":{
			"car_washing":{		//洗车
				"brief":"不宜",
				"details":""
			},
			"dressing":{		//穿衣
				"brief":"较冷",
				"details":""
			},
			"flu":{			//感冒
				"brief":"易发",
				"details":""
			},
			"sport":{		//运动
				"brief":"较不宜",
				"details":""
			},
			"travel":{		//旅游
				"brief":"适宜",
				"details":""
			},
			"uv":{			//紫外线
				"brief":"最弱",
				"details":""
			}
		},
		"last_update":"2016-12-21T14:40:05+08:00"
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
        private Suggestion suggestion;
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

        public Suggestion getSuggestion() {
            return suggestion;
        }

        public void setSuggestion(Suggestion suggestion) {
            this.suggestion = suggestion;
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

        public class Suggestion {
            private Car_washing car_washing;
            private Dressing dressing;
            private Flu flu;
            private Sport sport;
            private Travel travel;
            private Uv uv;

            public Car_washing getCar_washing() {
                return car_washing;
            }

            public void setCar_washing(Car_washing car_washing) {
                this.car_washing = car_washing;
            }

            public Dressing getDressing() {
                return dressing;
            }

            public void setDressing(Dressing dressing) {
                this.dressing = dressing;
            }

            public Flu getFlu() {
                return flu;
            }

            public void setFlu(Flu flu) {
                this.flu = flu;
            }

            public Sport getSport() {
                return sport;
            }

            public void setSport(Sport sport) {
                this.sport = sport;
            }

            public Travel getTravel() {
                return travel;
            }

            public void setTravel(Travel travel) {
                this.travel = travel;
            }

            public Uv getUv() {
                return uv;
            }

            public void setUv(Uv uv) {
                this.uv = uv;
            }

            public class Car_washing {
                private String brief;
                private String details;

                public String getBrief() {
                    return brief;
                }

                public void setBrief(String brief) {
                    this.brief = brief;
                }

                public String getDetails() {
                    return details;
                }

                public void setDetails(String details) {
                    this.details = details;
                }
            }

            public class Dressing {
                private String brief;
                private String details;

                public String getBrief() {
                    return brief;
                }

                public void setBrief(String brief) {
                    this.brief = brief;
                }

                public String getDetails() {
                    return details;
                }

                public void setDetails(String details) {
                    this.details = details;
                }
            }

            public class Flu {
                private String brief;
                private String details;

                public String getBrief() {
                    return brief;
                }

                public void setBrief(String brief) {
                    this.brief = brief;
                }

                public String getDetails() {
                    return details;
                }

                public void setDetails(String details) {
                    this.details = details;
                }

            }

            public class Sport {
                private String brief;
                private String details;

                public String getBrief() {
                    return brief;
                }

                public void setBrief(String brief) {
                    this.brief = brief;
                }

                public String getDetails() {
                    return details;
                }

                public void setDetails(String details) {
                    this.details = details;
                }
            }

            public class Travel {
                private String brief;
                private String details;

                public String getBrief() {
                    return brief;
                }

                public void setBrief(String brief) {
                    this.brief = brief;
                }

                public String getDetails() {
                    return details;
                }

                public void setDetails(String details) {
                    this.details = details;
                }
            }

            public class Uv {
                private String brief;
                private String details;

                public String getBrief() {
                    return brief;
                }

                public void setBrief(String brief) {
                    this.brief = brief;
                }

                public String getDetails() {
                    return details;
                }

                public void setDetails(String details) {
                    this.details = details;
                }
            }
        }
    }
}
