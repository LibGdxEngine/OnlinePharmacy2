package com.devahmed.demo.onlinepharmacy.Screens.Cart.CartItem;

import com.devahmed.demo.onlinepharmacy.Models.Product;

public interface CartItemsMvc {

    interface Listener{
        void onIncreaseBtnClicked(Product product);
        void onDecreaseBtnCLicked(Product product);
        void onProductImageClicked(Product product);
    }

    void bindData(Product product , int count);
}
