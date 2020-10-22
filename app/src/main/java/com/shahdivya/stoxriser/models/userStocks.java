package com.shahdivya.stoxriser.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class userStocks {
    @SerializedName("S_ID")
    @Expose
    private Integer sID;
    @SerializedName("S_NAME")
    @Expose
    private String sNAME;
    @SerializedName("COST_PRICE")
    @Expose
    private Double cOSTPRICE;
    @SerializedName("NOOFSHARES")
    @Expose
    private Integer nOOFSHARES;
    public userStocks(Integer sID,String sNAME, Double cOSTPRICE, Integer nOOFSHARES) {
        super();
        this.sID = sID;
        this.sNAME = sNAME;
        this.cOSTPRICE = cOSTPRICE;
        this.nOOFSHARES = nOOFSHARES;
    }
    public String getSNAME() {
        return sNAME;
    }
    public void setSNAME(String sNAME) {
        this.sNAME = sNAME;
    }
    public Double getCOSTPRICE() {
        return cOSTPRICE;
    }
    public void setCOSTPRICE(Double cOSTPRICE) {
        this.cOSTPRICE = cOSTPRICE;
    }
    public Integer getNOOFSHARES() {
        return nOOFSHARES;
    }
    public void setNOOFSHARES(Integer nOOFSHARES) {
        this.nOOFSHARES = nOOFSHARES;
    }
    public Integer getsID() { return sID; }
    public void setsID(Integer sID) { this.sID = sID; }
}