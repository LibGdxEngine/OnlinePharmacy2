package com.devahmed.tech4fun.ecommerce.Screens.Cart;

import com.devahmed.tech4fun.ecommerce.Models.DeliverCost;
import com.devahmed.tech4fun.ecommerce.Models.Product;

import java.util.ArrayList;
import java.util.List;

public interface CartMvc {

    interface Listener{
        void onSubmitBtnClicked(int totalEarnedPoints);
        void onGoToShoppingBtnClicked();
        void onCartItemImageClicked(Product product);
    }
    void bindDeliveryCost(List<DeliverCost> deliverCosts);
    void bindCartData(List<Product> productList , List<String> productsCount);
    void showProgressbar();
    void hideProgressbar();
    void activateEmptyCartState();
    ArrayList<String> getProductsIds();
    ArrayList<Integer> getProductsCounts();
    double getTotalPrice();
    double getDeliveryCost();
    boolean isCartEmpty();
}
