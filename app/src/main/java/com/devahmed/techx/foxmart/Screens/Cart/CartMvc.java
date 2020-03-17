package com.devahmed.techx.foxmart.Screens.Cart;

import com.devahmed.techx.foxmart.Models.DeliverCost;
import com.devahmed.techx.foxmart.Models.Product;

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
    int getTotalPrice();
    int getDeliveryCost();
    boolean isCartEmpty();
}
