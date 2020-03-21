package com.example.e_commerce.model;

import java.util.List;

public class ItemList {
    Item products;
    List<BestSeller> data;
    Product product;
    List<Product> favourites;

    public Item getItem()
    {
    return products;
    }

    public List<BestSeller> getBestSeller()
    {
        return data;
    }
    public Product get_product()
    {
        return product;
    }


}
