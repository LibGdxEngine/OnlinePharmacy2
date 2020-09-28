package com.devahmed.techx.aswaqmisr.Screens.AdminDashboard.BillsControl.BillsItemList;

import com.devahmed.techx.aswaqmisr.Models.Order;
import com.devahmed.techx.aswaqmisr.Models.Product;
import com.devahmed.techx.aswaqmisr.Models.User;
import com.devahmed.techx.aswaqmisr.Utils.BillCanvasView;

import java.util.List;

public interface BillsItemMvc {
    interface Listener{
        void onBillItemClicked(Order order , BillCanvasView billCanvasView);
        void onDownloadItemClicked(Order order, BillCanvasView billCanvasView);
        void onShareBtnClicked(Order order, BillCanvasView billCanvasView);
    }

    void bindData(Order order , List<Product> productList , User user , int position);
}
