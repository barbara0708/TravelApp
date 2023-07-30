package com.example.travelapp;

public class Attractions {
    private String name;
    private Integer image;
    private String address;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getImage() {
        return image;
    }

    public void setImage(Integer image) {
        this.image = image;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }



    public Attractions(String name, Integer image, String address) {
        this.name = name;
        this.image = image;
        this.address = address;
    }
}
