package com.example.fumagalli2020.Class;

public class Customer extends UnauthenticatedUser{

    private String name;
    private String surname;
    private String birthdate;
    private String mobile;
    private String fiscalcode;
    private String type;

    public Customer(String name, String surname, String birthdate, String mobile, String fiscalcode, String type){
        this.name = name;
        this.surname = surname;
        this.birthdate = birthdate;
        this.mobile = mobile;
        this.fiscalcode = fiscalcode;
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

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getFiscalcode() {
        return fiscalcode;
    }

    public void setFiscalcode(String fiscalcode) {
        this.fiscalcode = fiscalcode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
