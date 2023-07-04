package com.example.barbershop.Domain;

public class CategoryDomain {
    private String title;
    private String imagePic;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImagePic() {
        return imagePic;
    }

    public void setImagePic(String imagePic) {
        this.imagePic = imagePic;
    }

    public CategoryDomain(String title, String imagePic){
        this.title = title;
        this.imagePic = imagePic;
    }
}
