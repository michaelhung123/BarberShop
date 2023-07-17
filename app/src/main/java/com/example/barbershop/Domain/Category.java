package com.example.barbershop.Domain;

import androidx.annotation.NonNull;

public class Category {
    private int id;
    private String name;
    private String description;
    private String imagePic;

    public Category() {

    }

    public Category(int id, String name, String description) {
        this.setId(id);
        this.setName(name);
        this.setDescription(description);
    }

    public Category(int id, String name, String description, String imagePic) {
        this.setId(id);
        this.setName(name);
        this.setDescription(description);
        this.setImagePic(imagePic);
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImagePic() {
        return imagePic;
    }

    public void setImagePic(String imagePic) {
        this.imagePic = imagePic;
    }

    @NonNull
    @Override
    public String toString() {
        return this.name;
    }
}
