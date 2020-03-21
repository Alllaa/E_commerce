package com.example.e_commerce.model;

public class TopCategories {
    String image;
    String name_en;
    String name_ar;
    int id;


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName_en() {
        return name_en;
    }

    public TopCategories(String image, String name_en,int id) {
        this.image = image;
        this.name_en = name_en;
        this.id = id;
    }

    public String getName_ar() {
        return name_ar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName_en(String name_en) {
        this.name_en = name_en;
    }
}
