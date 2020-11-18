package com.devahmed.tech4fun.ecommerce.Screens.Orders.MyOrdersList;

import com.devahmed.tech4fun.ecommerce.Models.Order;
import com.devahmed.tech4fun.ecommerce.Models.User;

import java.util.List;

public interface OrdersListMvc {
    interface Listener{
        void onOrderItemClicked(Order order);
    }

    void bindData(List<Order> orderList , User  user);
    void showProgressbar();
    void hideProgressbar();
}
