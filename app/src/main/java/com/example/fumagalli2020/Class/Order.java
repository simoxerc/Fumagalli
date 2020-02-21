package com.example.fumagalli2020.Class;

public class Order {
    private String orderId;
    private String creditcard;
    private String pickDate;
    private String pickTime;
    private String customerId;
    private String totalPayed;
    private String orderState;

    public Order(String orderId, String creditcard, String pickDate, String pickTime, String customerId, String totalPayed, String orderState) {
        this.orderId = orderId;
        this.creditcard = creditcard;
        this.pickDate = pickDate;
        this.pickTime = pickTime;
        this.customerId = customerId;
        this.totalPayed = totalPayed;
        this.orderState = orderState;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCreditcard() {
        return creditcard;
    }

    public void setCreditcard(String creditcard) {
        this.creditcard = creditcard;
    }

    public String getPickDate() {
        return pickDate;
    }

    public void setPickDate(String pickDate) {
        this.pickDate = pickDate;
    }

    public String getPickTime() {
        return pickTime;
    }

    public void setPickTime(String pickTime) {
        this.pickTime = pickTime;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getTotalPayed() {
        return totalPayed;
    }

    public void setTotalPayed(String totalPayed) {
        this.totalPayed = totalPayed;
    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }
}
