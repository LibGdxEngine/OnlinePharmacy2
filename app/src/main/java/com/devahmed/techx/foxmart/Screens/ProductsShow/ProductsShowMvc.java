package com.devahmed.techx.foxmart.Screens.ProductsShow;

import com.devahmed.techx.foxmart.Models.Product;

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
