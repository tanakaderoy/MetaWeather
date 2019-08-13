package com.tanaka.metaweather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ConsolidatedWeather {

@SerializedName("id")
@Expose
private Integer id;
@SerializedName("weather_state_name")
@Expose
private String weatherStateName;
@SerializedName("weather_state_abbr")
@Expose
private String weatherStateAbbr;
@SerializedName("wind_direction_compass")
@Expose
private String windDirectionCompass;
@SerializedName("created")
@Expose
private String created;
@SerializedName("applicable_date")
@Expose
private String applicableDate;
@SerializedName("min_temp")
@Expose
private Double minTemp;
@SerializedName("max_temp")
@Expose
private Double maxTemp;
@SerializedName("the_temp")
@Expose
private Double theTemp;
@SerializedName("wind_speed")
@Expose
private Double windSpeed;
@SerializedName("wind_direction")
@Expose
private Double windDirection;
@SerializedName("air_pressure")
@Expose
private Double airPressure;
@SerializedName("humidity")
@Expose
private Integer humidity;
@SerializedName("visibility")
@Expose
private Double visibility;
@SerializedName("predictability")
@Expose
private Integer predictability;

public Integer getId() {
return id;
}

public void setId(Integer id) {
this.id = id;
}

public ConsolidatedWeather withId(Integer id) {
this.id = id;
return this;
}

public String getWeatherStateName() {
return weatherStateName;
}

public void setWeatherStateName(String weatherStateName) {
this.weatherStateName = weatherStateName;
}

public ConsolidatedWeather withWeatherStateName(String weatherStateName) {
this.weatherStateName = weatherStateName;
return this;
}

public String getWeatherStateAbbr() {
return weatherStateAbbr;
}

public void setWeatherStateAbbr(String weatherStateAbbr) {
this.weatherStateAbbr = weatherStateAbbr;
}

public ConsolidatedWeather withWeatherStateAbbr(String weatherStateAbbr) {
this.weatherStateAbbr = weatherStateAbbr;
return this;
}

public String getWindDirectionCompass() {
return windDirectionCompass;
}

public void setWindDirectionCompass(String windDirectionCompass) {
this.windDirectionCompass = windDirectionCompass;
}

public ConsolidatedWeather withWindDirectionCompass(String windDirectionCompass) {
this.windDirectionCompass = windDirectionCompass;
return this;
}

public String getCreated() {
return created;
}

public void setCreated(String created) {
this.created = created;
}

public ConsolidatedWeather withCreated(String created) {
this.created = created;
return this;
}

public String getApplicableDate() {
return applicableDate;
}

public void setApplicableDate(String applicableDate) {
this.applicableDate = applicableDate;
}

public ConsolidatedWeather withApplicableDate(String applicableDate) {
this.applicableDate = applicableDate;
return this;
}

public Double getMinTemp() {
return minTemp;
}

public void setMinTemp(Double minTemp) {
this.minTemp = minTemp;
}

public ConsolidatedWeather withMinTemp(Double minTemp) {
this.minTemp = minTemp;
return this;
}

public Double getMaxTemp() {
return maxTemp;
}

public void setMaxTemp(Double maxTemp) {
this.maxTemp = maxTemp;
}

public ConsolidatedWeather withMaxTemp(Double maxTemp) {
this.maxTemp = maxTemp;
return this;
}

public Double getTheTemp() {
return theTemp;
}

public void setTheTemp(Double theTemp) {
this.theTemp = theTemp;
}

public ConsolidatedWeather withTheTemp(Double theTemp) {
this.theTemp = theTemp;
return this;
}

public Double getWindSpeed() {
return windSpeed;
}

public void setWindSpeed(Double windSpeed) {
this.windSpeed = windSpeed;
}

public ConsolidatedWeather withWindSpeed(Double windSpeed) {
this.windSpeed = windSpeed;
return this;
}

public Double getWindDirection() {
return windDirection;
}

public void setWindDirection(Double windDirection) {
this.windDirection = windDirection;
}

public ConsolidatedWeather withWindDirection(Double windDirection) {
this.windDirection = windDirection;
return this;
}

public Double getAirPressure() {
return airPressure;
}

public void setAirPressure(Double airPressure) {
this.airPressure = airPressure;
}

public ConsolidatedWeather withAirPressure(Double airPressure) {
this.airPressure = airPressure;
return this;
}

public Integer getHumidity() {
return humidity;
}

public void setHumidity(Integer humidity) {
this.humidity = humidity;
}

public ConsolidatedWeather withHumidity(Integer humidity) {
this.humidity = humidity;
return this;
}

public Double getVisibility() {
return visibility;
}

public void setVisibility(Double visibility) {
this.visibility = visibility;
}

public ConsolidatedWeather withVisibility(Double visibility) {
this.visibility = visibility;
return this;
}

public Integer getPredictability() {
return predictability;
}

public void setPredictability(Integer predictability) {
this.predictability = predictability;
}

public ConsolidatedWeather withPredictability(Integer predictability) {
this.predictability = predictability;
return this;
}

}


