package com.devahmed.tech4fun.ecommerce.Screens.AdminDashboard.BillsControl;

import com.devahmed.tech4fun.ecommerce.Models.Order;
import com.devahmed.tech4fun.ecommerce.Utils.BillCanvasView;

import java.util.List;

public interface BillsMvc {

    interface Listener{
        void onBillItemClicked(Order order , BillCanvasView billCanvasView);
        void onDownloadItemClicked(Order order, BillCanvasView billCanvasView);
        void onShareBtnClicked(Order order, BillCanvasView billCanvasView);
    }

    void bindData(List<Order> orderList);
    void showProgressbar();
    void hideProgressbar();
    void activateDownloadMode(BillCanvasView billCanvasView);
    void activateNormalMode();

}
