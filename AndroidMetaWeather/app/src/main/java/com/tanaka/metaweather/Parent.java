
package com.tanaka.metaweather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Parent {

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

public String getTitle() {
return title;
}

public void setTitle(String title) {
this.title = title;
}

public Parent withTitle(String title) {
this.title = title;
return this;
}

public String getLocationType() {
return locationType;
}

public void setLocationType(String locationType) {
this.locationType = locationType;
}

public Parent withLocationType(String locationType) {
this.locationType = locationType;
return this;
}

public Integer getWoeid() {
return woeid;
}

public void setWoeid(Integer woeid) {
this.woeid = woeid;
}

public Parent withWoeid(Integer woeid) {
this.woeid = woeid;
return this;
}

public String getLattLong() {
return lattLong;
}

public void setLattLong(String lattLong) {
this.lattLong = lattLong;
}

public Parent withLattLong(String lattLong) {
this.lattLong = lattLong;
return this;
}

}