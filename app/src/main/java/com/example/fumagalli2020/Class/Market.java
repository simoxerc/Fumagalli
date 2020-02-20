package com.example.fumagalli2020.Class;

import java.util.List;

public class Market {

    private String name;
    private String address;
    private String number;
    private String email;
    private String marketId;
    private String chain_Id;
    private List<String> businessHour;
    private List<Boolean> continuedSchedule;
    private List<Boolean> closedDays;

    public Market(String name, String address, String number, String email, String marketId, String chain_Id, List<String> businessHour, List<Boolean> continuedSchedule, List<Boolean> closedDays) {
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

    public Market(String name,String address,String number,String email,String marketId,String chain_Id){
        this.name = name;
        this.address = address;
        this.number = number;
        this.email = email;
        this.marketId = marketId;
        this.chain_Id = chain_Id;
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

    public List<String> getBusinessHour() {
        return businessHour;
    }

    public void setBusinessHour(List<String> businessHour) {
        this.businessHour = businessHour;
    }

    public List<Boolean> getContinuedSchedule() {
        return continuedSchedule;
    }

    public void setContinuedSchedule(List<Boolean> continuedSchedule) {
        this.continuedSchedule = continuedSchedule;
    }

    public List<Boolean> getClosedDays() {
        return closedDays;
    }

    public void setClosedDays(List<Boolean> closedDays) {
        this.closedDays = closedDays;
    }
}
