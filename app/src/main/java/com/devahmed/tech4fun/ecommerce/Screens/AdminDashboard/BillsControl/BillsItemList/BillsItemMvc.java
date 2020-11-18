package com.devahmed.tech4fun.ecommerce.Screens.AdminDashboard.BillsControl.BillsItemList;

import com.devahmed.tech4fun.ecommerce.Models.Order;
import com.devahmed.tech4fun.ecommerce.Models.Product;
import com.devahmed.tech4fun.ecommerce.Models.User;
import com.devahmed.tech4fun.ecommerce.Utils.BillCanvasView;

import java.util.List;

public interface BillsItemMvc {
    interface Listener{
        void onBillItemClicked(Order order , BillCanvasView billCanvasView);
        void onDownloadItemClicked(Order order, BillCanvasView billCanvasView);
        void onShareBtnClicked(Order order, BillCanvasView billCanvasView);
    }

    void bindData(Order order , List<Product> productList , User user , int position);
}
