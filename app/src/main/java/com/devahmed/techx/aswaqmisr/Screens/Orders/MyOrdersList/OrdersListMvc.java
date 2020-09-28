package com.devahmed.techx.aswaqmisr.Screens.Orders.MyOrdersList;

import com.devahmed.techx.aswaqmisr.Models.Order;
import com.devahmed.techx.aswaqmisr.Models.User;

import java.util.List;

public interface OrdersListMvc {
    interface Listener{
        void onOrderItemClicked(Order order);
    }

    void bindData(List<Order> orderList , User  user);
    void showProgressbar();
    void hideProgressbar();
}
