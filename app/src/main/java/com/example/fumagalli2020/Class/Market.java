package com.example.fumagalli2020.Class;

public class Market {

    private String name;
    private String address;
    private String number;
    private String email;
    private String marketId;
    private String chain_Id;
    private String[][] businessHour;
    private boolean[] continuedSchedule;
    private boolean[] closedDays;

    public Market(String name, String address, String number, String email, String marketId, String chain_Id, String[][] businessHour, boolean[] continuedSchedule, boolean[] closedDays) {
        this.name = name;
        this.address = address;
        this.number = number;
        this.email = email;
        this.marketId = marketId;
        this.chain_Id = chain_Id;
        this.businessHour = businessHour;
        this.continuedSchedule = continuedSchedule;
        this.closedDays = closedDays;
    }

    public String getChain_Id() {
        return chain_Id;
    }

    public void setChain_Id(String chain_Id) {
        this.chain_Id = chain_Id;
    }

    public String getMarketId() {
        return marketId;
    }

    public void setMarketId(String marketId) {
        this.marketId = marketId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String[][] getBusinessHour() {
        return businessHour;
    }

    public void setBusinessHour(String[][] businessHour) {
        this.businessHour = businessHour;
    }

    public boolean[] getContinuedSchedule() {
        return continuedSchedule;
    }

    public void setContinuedSchedule(boolean[] continuedSchedule) {
        this.continuedSchedule = continuedSchedule;
    }

    public boolean[] getClosedDays() {
        return closedDays;
    }

    public void setClosedDays(boolean[] closedDays) {
        this.closedDays = closedDays;
    }
}
