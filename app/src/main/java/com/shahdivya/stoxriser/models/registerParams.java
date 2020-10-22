package com.shahdivya.stoxriser.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class registerParams {

    private Integer id;
    public registerParams() {
    }
    public registerParams(Integer id) {
        super();
        this.id = id;
    }
    public Integer getIdR() {
        return id;
    }
    public void setIdR(Integer id) {
        this.id = id;
    }
}
