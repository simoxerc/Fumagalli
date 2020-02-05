package com.example.fumagalliproject.Class;

public class Category {

    private String name;
    private String productTypeOne;
    private String productTypeTwo;
    private String productTypeThree;


    public Category(String name,String productOne, String productTwo, String productThree){
        this.name = name;
        this.productTypeOne = productOne;
        this.productTypeTwo = productTwo;
        this.productTypeThree = productThree;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProductTypeOne() {
        return productTypeOne;
    }

    public void setProductTypeOne(String productTypeOne) {
        this.productTypeOne = productTypeOne;
    }

    public String getProductTypeTwo() {
        return productTypeTwo;
    }

    public void setProductTypeTwo(String productTypeTwo) {
        this.productTypeTwo = productTypeTwo;
    }

    public String getProductTypeThree() {
        return productTypeThree;
    }

    public void setProductTypeThree(String productTypeThree) {
        this.productTypeThree = productTypeThree;
    }
}
