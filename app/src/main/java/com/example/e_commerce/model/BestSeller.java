package com.example.e_commerce.model;

public class BestSeller {

    String default_image;
    String name_en;
    String name_ar;
    int price;
    int id;
    boolean is_fav;

    public boolean isIs_fav() {
        return is_fav;
    }

    public void setIs_fav(boolean is_fav) {
        this.is_fav = is_fav;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName_ar() {
        return name_ar;
    }

    public BestSeller(String default_image, String name_en, int price, int id) {
        this.default_image = default_image;
        this.name_en = name_en;
        this.price = price;
        this.id = id;

    }

    public String getName_en() {
        return name_en;
    }

    public void setName_en(String name_en) {
        this.name_en = name_en;
    }

    public String getDefault_image() {
        return default_image;
    }

    public void setDefault_image(String default_image) {
        this.default_image = default_image;
    }


    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}

