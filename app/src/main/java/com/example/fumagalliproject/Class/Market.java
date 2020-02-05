package com.example.fumagalliproject.Class;

public class Market {

    private String address;
    private String city;
    private String phone;

    public Market(String address, String city, String phone){
        this.address = address;
        this.city = city;
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
