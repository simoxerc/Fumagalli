package com.example.fumagalli2020.Class;

public class SystemAdministrator extends UnauthenticatedUser {



    private String name;
    private String surname;
    private String mobile;
    private String type;

    public SystemAdministrator(String name, String surname, String mobile, String type) {
        this.name = name;
        this.surname = surname;
        this.mobile = mobile;
        this.type = type;
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
