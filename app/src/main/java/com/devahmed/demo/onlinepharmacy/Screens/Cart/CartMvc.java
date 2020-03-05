package com.devahmed.demo.onlinepharmacy.Screens.Cart;

import com.devahmed.demo.onlinepharmacy.Models.Product;

import java.util.List;

public interface CartMvc {

    interface Listener{
        void onSubmitBtnClicked();
        void onGoToShoppingBtnClicked();
        void onCartItemImageClicked(Product product);
    }

    void bindCartData(List<Product> productList , List<String> productsCount);
    void showProgressbar();
    void hideProgressbar();
    void activateEmptyCartState();
    int getTotalPrice();
    boolean isCartEmpty();
}
