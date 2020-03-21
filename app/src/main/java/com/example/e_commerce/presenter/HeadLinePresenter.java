package com.example.e_commerce.presenter;

import com.example.e_commerce.model.GetDataFromServer;
import com.example.e_commerce.iEcommerce.IGetDataFromServer;
import com.example.e_commerce.model.TopCategories;
import com.example.e_commerce.presenter.interface_presnter.IHeadLinePresenter;
import com.example.e_commerce.view.view_interface.IHeadLine;

import java.util.List;

public class HeadLinePresenter implements IHeadLinePresenter{
    //   IGetCategories iGetCategories;
    IGetDataFromServer iGetCategories;
    IHeadLine iHeadLine;
    private List<TopCategories> topCategoriesList;

    public HeadLinePresenter(IHeadLine newHeadLine) {
        iGetCategories = new GetDataFromServer();
        iHeadLine = newHeadLine;
    }

    @Override
    public void onSendData() {
        iGetCategories.getCategoriesData(this);
    }

    @Override
    public void sendCategories(List<TopCategories> topCategories) {
        topCategoriesList = topCategories;
        if (iHeadLine != null) {
            iHeadLine.onHeadLineRecieved(topCategoriesList);
        }
    }
}
