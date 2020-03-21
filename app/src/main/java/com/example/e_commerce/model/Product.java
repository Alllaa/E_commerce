package com.example.e_commerce.model;

import java.util.List;

public class Product {
   int id;
    String name_en;
    String description_en;
    String name_ar;
    String description_ar;
    int stock;
   int price;
   String default_image;
   int total_rate;

    public int getTotal_rate() {
        return total_rate;
    }

    public void setTotal_rate(int total_rate) {
        this.total_rate = total_rate;
    }

    boolean is_fav;

    public boolean isIs_fav() {
        return is_fav;
    }

    public void setIs_fav(boolean is_fav) {
        this.is_fav = is_fav;
    }

    List<Colors> colors;
    List<Sizes> sizes;
    List<Images>  images;

    public String getName_ar() {
        return name_ar;
    }

    public String getDescription_ar() {
        return description_ar;
    }

    public List<Colors> getColors() {
        return colors;
    }

    public void setColors(List<Colors> colors) {
        this.colors = colors;
    }

    public List<Sizes> getSizes() {
        return sizes;
    }

    public void setSizes(List<Sizes> sizes) {
        this.sizes = sizes;
    }

    public List<Images> getImages() {
        return images;
    }

    public void setImages(List<Images> images) {
        this.images = images;
    }

    Brand brand;
    SubCategory subcategory;
    List<Offer> offer;


    public List<Offer> getOffer() {
        return offer;
    }

    public Brand getBrand() {
        return brand;
    }

    public SubCategory getSubcategory() {
        return subcategory;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName_en() {
        return name_en;
    }

    public void setName_en(String name_en) {
        this.name_en = name_en;
    }

    public String getDescription_en() {
        return description_en;
    }

    public void setDescription_en(String description_en) {
        this.description_en = description_en;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDefault_image() {
        return default_image;
    }

    public void setDefault_image(String default_image) {
        this.default_image = default_image;
    }
    public class Offer{
        int percentage;

        public int getPercentage() {
            return percentage;
        }

        public void setPercentage(int offer) {
            this.percentage = offer;
        }
    }
    public class Brand{
        String name_en;

        public String getName_en() {
            return name_en;
        }

        public void setName_en(String name_en) {
            this.name_en = name_en;
        }
    }
    public class SubCategory{
              Category category;

        public Category getCategory() {
            return category;
        }

        public void setCategory(Category category) {
            this.category = category;
        }
    }
   public class Category {
        String name_en;

       public String getName_en() {
           return name_en;
       }

       public void setName_en(String name_en) {
           this.name_en = name_en;
       }
   }
   public class Sizes{
        int id ;
        String size;

       public void setId(int id) {
           this.id = id;
       }

       public void setSize(String size) {
           this.size = size;
       }

       public String getSize() {
           return size;
       }
   }
   public class Colors{
        int id;
        String color;

       public void setId(int id) {
           this.id = id;
       }

       public void setColor(String color) {
           this.color = color;
       }

       public int getId() {
           return id;
       }

       public String getColor() {
           return color;
       }
   }
   public class Images{
        int id;
        String image;

       public void setId(int id) {
           this.id = id;
       }

       public void setImage(String image) {
           this.image = image;
       }

       public int getId() {
           return id;
       }

       public String getImage() {
           return image;
       }
   }
}
