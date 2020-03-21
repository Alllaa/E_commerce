package com.example.e_commerce.model;

import java.util.List;

public class ListDataFromServer {
    List<NewArrivals> new_arrival ;
    List<NewArrivals> data ;


    Item item;
    List<BestSeller> best_seller;
    List<TopCategories> top_categories;
    List<TopCategories> top_brand;
    List<TopCategories> ads;

    public List<NewArrivals> getItems() {
        return new_arrival;
    }

    public List<BestSeller> getBest_seller()
    {
        return best_seller;
    }
    public List<TopCategories> getTopBrand()
    {
        return top_brand;
    }


    public List<TopCategories> getTop_categories()
    {
        return top_categories;
    }

  public List<NewArrivals>getNewArrival()
  {
      return data;
  }



    public List<TopCategories> getAds()
    {
        return ads;
    }
}
