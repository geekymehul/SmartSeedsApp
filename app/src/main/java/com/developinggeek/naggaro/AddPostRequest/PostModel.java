package com.developinggeek.naggaro.AddPostRequest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by gargc on 15-09-2018.
 */

public class PostModel {

    @SerializedName("value")
    @Expose
    private String value;

    @SerializedName("year")
    @Expose
    private String year;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
