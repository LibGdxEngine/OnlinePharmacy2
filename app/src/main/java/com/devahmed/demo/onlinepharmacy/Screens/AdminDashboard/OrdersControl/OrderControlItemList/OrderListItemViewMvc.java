package com.devahmed.demo.onlinepharmacy.Screens.AdminDashboard.OrdersControl.OrderControlItemList;

import com.devahmed.demo.onlinepharmacy.Models.Order;

public interface OrderListItemViewMvc {
    interface Listener{
        void onItemClicked(Order order);
        void onShowBtnClicked(Order order);
        void onShareBtnClicked(Order order);
        void onStatusBtnClicked(Order order);
    }

    void bindData(Order order);
}
