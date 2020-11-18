package com.devahmed.tech4fun.ecommerce.Screens.Orders.OrdersDetails;

import com.devahmed.tech4fun.ecommerce.Models.Product;
import com.devahmed.tech4fun.ecommerce.Models.User;

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
