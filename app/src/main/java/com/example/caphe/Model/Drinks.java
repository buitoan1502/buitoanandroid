package com.example.caphe.Model;

public class Drinks {

    private int id;
    private String name;
    private int price;
    private String information;
    private String imageResource;

    // Constructor không cần ID, dùng khi thêm đồ uống mới
    public Drinks(String name, int price, String information, String imageResource) {
        this.name = name;
        this.price = price;
        this.information = information;
        this.imageResource = imageResource;
    }
    public Drinks(String name) {
        this.name = name;
    }

    // Constructor có ID, dùng khi lấy đồ uống từ database
    public Drinks(int id, String name, int price, String information, String imageResource) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.information = information;
        this.imageResource = imageResource;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getInformation() {
        return information;
    }

    public String getImageResource() {
        return imageResource;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public void setImageResource(String imageResource) {
        this.imageResource = imageResource;
    }
}
