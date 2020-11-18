package com.devahmed.tech4fun.ecommerce.Screens.Cart.CartItem;

import com.devahmed.tech4fun.ecommerce.Models.Product;

public interface CartItemsMvc {

    interface Listener{
        void onIncreaseBtnClicked(Product product);
        void onDecreaseBtnCLicked(Product product);
        void onProductImageClicked(Product product);
    }

    void bindData(Product product , int count);
}
