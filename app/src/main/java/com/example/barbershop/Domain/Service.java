package com.example.barbershop.Domain;

public class Service {
    private int id;
    private String name;
    private double price;

    private String description;
    private String filePath;
    private int category_id;

    public Service() {

    }

    public Service(int id, String name, double price, String description, String file, int category_id) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.filePath = file;
        this.category_id = category_id;
    }

    public Service(String name, double price, String description, String filePath, int category_id) {
        this.name = name;
        this.setPrice(price);
        this.category_id = category_id;
        this.setDescription(description);
        this.setFilePath(filePath);
    }

    public Service(String name, double price){
        this.name = name;
        this.price = price;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    @Override
    public String toString() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
