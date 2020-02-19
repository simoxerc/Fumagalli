package com.example.fumagalli2020.Class;

public class Category {
    private String categoryId;
    private String name;
    private String desc;
    private String marketId;

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getMarketId() {
        return marketId;
    }

    public void setMarketId(String marketId) {
        this.marketId = marketId;
    }

    public Category(String categoryId, String name, String desc, String marketId) {
        this.categoryId = categoryId;
        this.name = name;
        this.desc = desc;
        this.marketId = marketId;
    }
}
