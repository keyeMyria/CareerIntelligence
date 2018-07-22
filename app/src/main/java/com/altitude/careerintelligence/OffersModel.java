package com.altitude.careerintelligence;

/**
 * Created by Nandom on 5/11/2018.
 */

class OffersModel {

    private int savings;
    private String offer;

    public OffersModel(int savings, String offer) {
        this.savings = savings;
        this.offer = offer;
    }

    public int getSavings() {
        return savings;
    }

    public void setSavings(int savings) {
        this.savings = savings;
    }

    public String getOffer() {
        return offer;
    }

    public void setOffer(String offer) {
        this.offer = offer;
    }
}
