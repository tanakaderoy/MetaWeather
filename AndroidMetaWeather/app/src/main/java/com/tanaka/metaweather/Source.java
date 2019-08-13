
package com.tanaka.metaweather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Source {

@SerializedName("title")
@Expose
private String title;
@SerializedName("slug")
@Expose
private String slug;
@SerializedName("url")
@Expose
private String url;
@SerializedName("crawl_rate")
@Expose
private Integer crawlRate;

public String getTitle() {
return title;
}

public void setTitle(String title) {
this.title = title;
}

public Source withTitle(String title) {
this.title = title;
return this;
}

public String getSlug() {
return slug;
}

public void setSlug(String slug) {
this.slug = slug;
}

public Source withSlug(String slug) {
this.slug = slug;
return this;
}

public String getUrl() {
return url;
}

public void setUrl(String url) {
this.url = url;
}

public Source withUrl(String url) {
this.url = url;
return this;
}

public Integer getCrawlRate() {
return crawlRate;
}

public void setCrawlRate(Integer crawlRate) {
this.crawlRate = crawlRate;
}

public Source withCrawlRate(Integer crawlRate) {
this.crawlRate = crawlRate;
return this;
}

}