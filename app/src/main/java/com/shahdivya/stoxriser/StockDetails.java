package com.shahdivya.stoxriser;
public class StockDetails {
    private String shareName;
    private Double sharePrice;
    private Integer noOfShares;
    public StockDetails(String shareName, Double sharePrice,Integer noOfShares) {
        this.shareName= shareName ;
        this.sharePrice = sharePrice;
        this.noOfShares = noOfShares;
    }

    public Integer getNoOfShares() {
        return noOfShares;
    }

    public void setNoOfShares(Integer noOfShares) {
        this.noOfShares = noOfShares;
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

