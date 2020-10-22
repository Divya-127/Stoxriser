package com.shahdivya.stoxriser.models;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class userDetails
{
        @SerializedName("user_id")
        @Expose
        private Integer userId;
        @SerializedName("u_shares")
        @Expose
        private Integer uShares;
        @SerializedName("balance")
        @Expose
        private Double balance;

        public userDetails() {
        }

        public userDetails(Integer userId, Integer uShares, Double balance) {
            super();
            this.userId = userId;
            this.uShares = uShares;
            this.balance = balance;
        }
        public Integer getUserId() {
            return userId;
        }
        public void setUserId(Integer userId) {
            this.userId = userId;
        }
        public Integer getUShares() {
            return uShares;
        }
        public void setUShares(Integer uShares) {
            this.uShares = uShares;
        }
        public Double getBalance() {
            return balance;
        }
        public void setBalance(Double balance) {
            this.balance = balance;
        }
}
