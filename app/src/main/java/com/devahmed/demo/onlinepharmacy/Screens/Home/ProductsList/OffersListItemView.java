package com.devahmed.demo.onlinepharmacy.Screens.Home.ProductsList;

import com.devahmed.demo.onlinepharmacy.Models.Category;
import com.devahmed.demo.onlinepharmacy.Models.Product;
import com.devahmed.demo.onlinepharmacy.Models.SubCategory;

public interface OffersListItemView {

    public interface Listener{
        void onItemClicked(SubCategory category);
        void onItemLongClicked(SubCategory subCategory);
    }

    public void bindData(SubCategory category);
}
