package com.devahmed.demo.onlinepharmacy.Screens.ProductsShow;

import com.devahmed.demo.onlinepharmacy.Models.Product;

import java.util.List;

public interface ProductsShowMvc {

    public interface Listener{

        public void onProductItemClicked();
        public void onAddNewProductBtnClicked();
    }

    public void bindProductsDataData(List<Product> productList);

}
