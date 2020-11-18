package com.devahmed.tech4fun.ecommerce.Screens.ProductsShow;

import com.devahmed.tech4fun.ecommerce.Models.Product;

import java.util.List;

public interface ProductsShowMvc {

    public interface Listener{

        void onProductImageClicked(Product product);
        void onAddToCartBtnClicked();
        void onAddNewProductBtnClicked();
        void onProductLongClicked(Product product);

        void onChooseProductEdit(Product product);

        void onChooseProductDelete(Product product);
    }
    void showProductsOptionsDialog(String title , String [] options , final Product product);
    public void bindProductsDataData(List<Product> productList);

    void showProgressbar();
    void hideProgressbar();

}
