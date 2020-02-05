package com.example.fumagalliproject.Class;

public class Product {

    private String name;
    private String source;
    private String typeQuantity;

    public Product(String name, String source, String typeQuantity){
        this.name = name;
        this.source = source;
        this.typeQuantity = typeQuantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTypeQuantity() {
        return typeQuantity;
    }

    public void setTypeQuantity(String typeQuantity) {
        this.typeQuantity = typeQuantity;
    }


}
