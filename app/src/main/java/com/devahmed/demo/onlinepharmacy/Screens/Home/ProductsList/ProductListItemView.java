package com.devahmed.demo.onlinepharmacy.Screens.Home.ProductsList;

import com.devahmed.demo.onlinepharmacy.Models.Product;

public interface ProductListItemView {

    public interface Listener{
        void onItemClicked(Product product);
    }

    public void bindData(Product product);
}
