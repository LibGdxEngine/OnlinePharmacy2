package com.devahmed.techx.mercadomart.Screens.Orders.MyOrdersList;

import com.devahmed.techx.mercadomart.Models.Order;
import com.devahmed.techx.mercadomart.Models.User;

import java.util.List;

public interface OrdersListMvc {
    interface Listener{
        void onOrderItemClicked(Order order);
    }

    void bindData(List<Order> orderList , User  user);
    void showProgressbar();
    void hideProgressbar();
}
