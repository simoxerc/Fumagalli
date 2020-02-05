package com.example.fumagalliproject.Class;

import java.util.Map;

public class Cart {

    private String marketName;
    private String marketAddress;
    private String marketCity;
    private String marketPhone;
    private String totalprice;
    private Product cartProduct;
    private String productQnt;

    public Cart(String marketName, String marketAddress, String marketCity, String marketPhone, String totalprice, Product cartProduct, String productQnt){
        this.marketName = marketName;
        this.marketAddress = marketAddress;
        this.marketCity = marketCity;
        this.marketPhone = marketPhone;
        this.totalprice = totalprice;
        this.cartProduct = cartProduct;
        this.productQnt = productQnt;
    }

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public String getMarketAddress() {
        return marketAddress;
    }

    public void setMarketAddress(String marketAddress) {
        this.marketAddress = marketAddress;
    }

    public String getMarketCity() {
        return marketCity;
    }

    public void setMarketCity(String marketCity) {
        this.marketCity = marketCity;
    }

    public String getMarketPhone() {
        return marketPhone;
    }

    public void setMarketPhone(String marketPhone) {
        this.marketPhone = marketPhone;
    }

    public String getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(String totalprice) {
        this.totalprice = totalprice;
    }

    public Product getCartProduct() {
        return cartProduct;
    }

    public void setCartProduct(Product cartProduct) {
        this.cartProduct = cartProduct;
    }

    public String getProductQnt() {
        return productQnt;
    }

    public void setProductQnt(String productQnt) {
        this.productQnt = productQnt;
    }

}
