package com.devahmed.demo.onlinepharmacy.Screens.Orders.MyOrdersList;

import com.devahmed.demo.onlinepharmacy.Models.Order;
import com.devahmed.demo.onlinepharmacy.Models.User;

import java.util.List;

public interface OrdersListMvc {
    interface Listener{
        void onOrderItemClicked(Order order);
    }

    void bindData(List<Order> orderList , User  user);
    void showProgressbar();
    void hideProgressbar();
}
