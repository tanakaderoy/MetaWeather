

package com.tanaka.metaweather;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Weather {

@SerializedName("consolidated_weather")
@Expose
private List<ConsolidatedWeather> consolidatedWeather = null;
@SerializedName("time")
@Expose
private String time;
@SerializedName("sun_rise")
@Expose
private String sunRise;
@SerializedName("sun_set")
@Expose
private String sunSet;
@SerializedName("timezone_name")
@Expose
private String timezoneName;
@SerializedName("parent")
@Expose
private Parent parent;
@SerializedName("sources")
@Expose
private List<Source> sources = null;
@SerializedName("title")
@Expose
private String title;
@SerializedName("location_type")
@Expose
private String locationType;
@SerializedName("woeid")
@Expose
private Integer woeid;
@SerializedName("latt_long")
@Expose
private String lattLong;
@SerializedName("timezone")
@Expose
private String timezone;

public List<ConsolidatedWeather> getConsolidatedWeather() {
return consolidatedWeather;
}

public void setConsolidatedWeather(List<ConsolidatedWeather> consolidatedWeather) {
this.consolidatedWeather = consolidatedWeather;
}

public Weather withConsolidatedWeather(List<ConsolidatedWeather> consolidatedWeather) {
this.consolidatedWeather = consolidatedWeather;
return this;
}

public String getTime() {
return time;
}

public void setTime(String time) {
this.time = time;
}

public Weather withTime(String time) {
this.time = time;
return this;
}

public String getSunRise() {
return sunRise;
}

public void setSunRise(String sunRise) {
this.sunRise = sunRise;
}

public Weather withSunRise(String sunRise) {
this.sunRise = sunRise;
return this;
}

public String getSunSet() {
return sunSet;
}

public void setSunSet(String sunSet) {
this.sunSet = sunSet;
}

public Weather withSunSet(String sunSet) {
this.sunSet = sunSet;
return this;
}

public String getTimezoneName() {
return timezoneName;
}

public void setTimezoneName(String timezoneName) {
this.timezoneName = timezoneName;
}

public Weather withTimezoneName(String timezoneName) {
this.timezoneName = timezoneName;
return this;
}

public Parent getParent() {
return parent;
}

public void setParent(Parent parent) {
this.parent = parent;
}

public Weather withParent(Parent parent) {
this.parent = parent;
return this;
}

public List<Source> getSources() {
return sources;
}

public void setSources(List<Source> sources) {
this.sources = sources;
}

public Weather withSources(List<Source> sources) {
this.sources = sources;
return this;
}

public String getTitle() {
return title;
}

public void setTitle(String title) {
this.title = title;
}

public Weather withTitle(String title) {
this.title = title;
return this;
}

public String getLocationType() {
return locationType;
}

public void setLocationType(String locationType) {
this.locationType = locationType;
}

public Weather withLocationType(String locationType) {
this.locationType = locationType;
return this;
}

public Integer getWoeid() {
return woeid;
}

public void setWoeid(Integer woeid) {
this.woeid = woeid;
}

public Weather withWoeid(Integer woeid) {
this.woeid = woeid;
return this;
}

public String getLattLong() {
return lattLong;
}

public void setLattLong(String lattLong) {
this.lattLong = lattLong;
}

public Weather withLattLong(String lattLong) {
this.lattLong = lattLong;
return this;
}

public String getTimezone() {
return timezone;
}

public void setTimezone(String timezone) {
this.timezone = timezone;
}

public Weather withTimezone(String timezone) {
this.timezone = timezone;
return this;
}

}