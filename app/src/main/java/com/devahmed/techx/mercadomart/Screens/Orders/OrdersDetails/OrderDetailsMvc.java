package com.devahmed.techx.mercadomart.Screens.Orders.OrdersDetails;

import com.devahmed.techx.mercadomart.Models.Product;
import com.devahmed.techx.mercadomart.Models.User;

import java.util.List;

public interface OrderDetailsMvc {

    interface Listener{
        void onCallUserBtnClicked(User user);
        void onShowLocationBtnClicked(User user);
        void onCancelOrderBtnClicked();
        void onEditOrderBtnClicked();
    }
    void bindOrderedItemsData(List<Product> productList , List<String> productsCount );
    void bindUserData(User user);
    void bindPaymentsData(int totalItems, double deliveryCost, double totalPrice);
    void bindOrderState(String orderState);
    void showProgressBar();
    void hideProgressBar();
    void showAdminControls();
}