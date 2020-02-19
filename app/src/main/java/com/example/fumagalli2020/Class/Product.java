package com.example.fumagalli2020.Class;

public class Product {
    private String name;
    private String productId;
    private String categoryId;
    private String packagingDate;
    private String expireDate;
    private String origin;
    private String type;
    private String quantity;
    private String price;

    public Product(String name, String productId, String categoryId, String packagingDate, String expireDate, String origin, String type, String quantity, String price) {
        this.name = name;
        this.productId = productId;
        this.categoryId = categoryId;
        this.packagingDate = packagingDate;
        this.expireDate = expireDate;
        this.origin = origin;
        this.type = type;
        this.quantity = quantity;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getPackagingDate() {
        return packagingDate;
    }

    public void setPackagingDate(String packagingDate) {
        this.packagingDate = packagingDate;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
