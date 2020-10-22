package com.shahdivya.stoxriser.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class shareHoldersParams {

    @SerializedName("status")
    @Expose
    private String status;

    public shareHoldersParams() {
    }
    public shareHoldersParams(String status) {
        super();
        this.status = status;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}