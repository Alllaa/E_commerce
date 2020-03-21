package com.example.e_commerce.model;

import java.util.List;

public class Item {

    List<NewArrivals> data;

    public Item(List<NewArrivals> data) {
        this.data = data;
    }

    public List<NewArrivals> getData() {
        return data;
    }

    public void setData(List<NewArrivals> data) {
        this.data = data;
    }
}
