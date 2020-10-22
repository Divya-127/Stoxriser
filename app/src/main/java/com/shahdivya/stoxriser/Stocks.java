package com.shahdivya.stoxriser;

public class Stocks {
    private int id;
    private String shareName;
    private Double sharePrice;

    public Stocks(String shareName, Double sharePrice) {
        this.shareName= shareName ;
        this.sharePrice = sharePrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShareName() {
        return shareName;
    }

    public void setShareName(String shareName) {
        this.shareName = shareName;
    }

    public Double getSharePrice() {
        return sharePrice;
    }

    public void setSharePrice(Double sharePrice) {
        this.sharePrice = sharePrice;
    }
}
