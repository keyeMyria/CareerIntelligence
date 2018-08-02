package com.altitude.careerintelligence.mcc.classes;

/**
 * Created by Nandom on 7/23/2018.
 */

public class PaymentHistoryModel {

    private String orderTitle;
    private String paymentStatus;
    private String paymentDate;
    private String paymentDay;
    private String paymentMonth;
    private String referenceCode;
    private String paymentAmount;

    public PaymentHistoryModel() {
    }

    public PaymentHistoryModel(String orderTitle, String paymentStatus, String paymentDate, String paymemtDay, String paymentMonth, String referenceCode, String paymentAmount) {
        this.orderTitle = orderTitle;
        this.paymentStatus = paymentStatus;
        this.paymentDate = paymentDate;
        this.paymentDay = paymemtDay;
        this.paymentMonth = paymentMonth;
        this.referenceCode = referenceCode;
        this.paymentAmount = paymentAmount;
    }

    public String getOrderTitle() {
        return orderTitle;
    }

    public void setOrderTitle(String orderTitle) {
        this.orderTitle = orderTitle;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPaymentDay() {
        return paymentDay;
    }

    public void setPaymentDay(String paymentDay) {
        this.paymentDay = paymentDay;
    }

    public String getPaymentMonth() {
        return paymentMonth;
    }

    public void setPaymentMonth(String paymentMonth) {
        this.paymentMonth = paymentMonth;
    }

    public void setReferenceCode(String referenceCode){
        this.referenceCode = referenceCode;
    }

    public String getReferenceCode() {
        return referenceCode;
    }

    public String getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(String paymentAmount) {
        this.paymentAmount = paymentAmount;
    }
}
