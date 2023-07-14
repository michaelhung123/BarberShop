package com.example.barbershop.Domain;

import androidx.annotation.NonNull;

public class Service {
    private int id;
    private String name;
    private double price;

    private String description;
    private String filePath;
    private int category_id;

    public Service() {

    }

    public Service(int id, String name, double price, int category_id) {

    }

    public Service(String name, double price, String description, String filePath, int category_id) {
        this.name = name;
        this.price = price;
        this.category_id = category_id;
        this.description = description;
        this.filePath = filePath;
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
}
