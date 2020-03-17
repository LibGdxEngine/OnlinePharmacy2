package com.devahmed.techx.foxmart.Screens.AdminDashboard.BillsControl;

import com.devahmed.techx.foxmart.Models.Order;
import com.devahmed.techx.foxmart.Utils.BillCanvasView;

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
