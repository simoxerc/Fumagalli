package com.example.fumagalli2020.Class;

public class Employee extends UnauthenticatedUser {
    private String name;
    private String surname;
    private String mobile;
    private String marketId;
    private String type;
    private String employeeId;

    public Employee(String name, String surname, String mobile, String marketId, String type, String employeeId) {
        this.name = name;
        this.surname = surname;
        this.mobile = mobile;
        this.marketId = marketId;
        this.type = type;
        this.employeeId = employeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getMarketId() {
        return marketId;
    }

    public void setMarketId(String marketId) {
        this.marketId = marketId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEmployeeId() { return employeeId; }

    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
