package com.example.fumagalli2020.Class;

public class Chain {
    private String name;
    private String chain_Id;
    private String imgLink;

    public Chain(String name, String chain_Id, String imgLink) {
        this.name = name;
        this.chain_Id = chain_Id;
        this.imgLink = imgLink;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChain_Id() {
        return chain_Id;
    }

    public void setChain_Id(String chain_Id) {
        this.chain_Id = chain_Id;
    }

    public String getImgLink() {
        return imgLink;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }
}
