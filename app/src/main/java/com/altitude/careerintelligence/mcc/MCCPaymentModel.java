package com.altitude.careerintelligence.mcc;

/**
 * Created by Nandom on 5/11/2018.
 */

public class MCCPaymentModel {
    private String mccTitle, mccAuthor, mccStatus;
    private String mccAmount;

    public MCCPaymentModel(){

    }

    public MCCPaymentModel(String mccTitle, String mccAuthor, String mccStatus, String mccAmount) {
        this.mccTitle = mccTitle;
        this.mccAuthor = mccAuthor;
        this.mccStatus = mccStatus;
        this.mccAmount = mccAmount;
    }

    public String getMccTitle() {
        return mccTitle;
    }

    public String getMccAuthor() {
        return mccAuthor;
    }

    public String getMccStatus() {
        return mccStatus;
    }

    public String getMccAmount() {
        return mccAmount;
    }
}
