package com.devahmed.techx.aswaqmisr.Screens.Cart.CartItem;

import com.devahmed.techx.aswaqmisr.Models.Product;

public interface CartItemsMvc {

    interface Listener{
        void onIncreaseBtnClicked(Product product);
        void onDecreaseBtnCLicked(Product product);
        void onProductImageClicked(Product product);
    }

    void bindData(Product product , int count);
}
