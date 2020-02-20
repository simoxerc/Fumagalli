package com.example.fumagalli2020.Class;

public class Cart {

    private String cartId;
    private String totalPrice;
    private String marketId;

    public Cart(String cartId, String totalPrice, String marketId){
        this.cartId = cartId;
        this.totalPrice = totalPrice;
        this.marketId = marketId;
    }

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }


    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getMarketId() {
        return marketId;
    }

    public void setMarketId(String marketId) {
        this.marketId = marketId;
    }
}
