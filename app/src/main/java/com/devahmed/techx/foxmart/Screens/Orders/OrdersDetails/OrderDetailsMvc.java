package com.devahmed.techx.foxmart.Screens.Orders.OrdersDetails;

import com.devahmed.techx.foxmart.Models.Product;
import com.devahmed.techx.foxmart.Models.User;

import java.util.List;

public interface OrderDetailsMvc {

    interface Listener{
        void onCallUserBtnClicked(User user);
        void onCancelOrderBtnClicked();
        void onEditOrderBtnClicked();
    }
    void bindOrderedItemsData(List<Product> productList , List<String> productsCount );
    void bindUserData(User user);
    void bindPaymentsData(int totalItems, int deliveryCost, int totalPrice);
    void bindOrderState(String orderState);
    void showProgressBar();
    void hideProgressBar();
    void showAdminControls();
}
