package com.devahmed.tech4fun.ecommerce.Screens.AdminDashboard.OrdersControl.OrderControlItemList;

import com.devahmed.tech4fun.ecommerce.Models.Order;

public interface OrderListItemViewMvc {
    interface Listener{
        void onItemClicked(Order order);
        void onShowBtnClicked(Order order);
        void onLocationBtnClicked(Order order);
        void onStatusBtnClicked(Order order);
    }

    void bindData(Order order);
}
