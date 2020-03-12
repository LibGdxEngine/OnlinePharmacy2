package com.devahmed.demo.onlinepharmacy.Screens.AdminDashboard.BillsControl;

import android.graphics.Bitmap;

import com.devahmed.demo.onlinepharmacy.Models.Order;
import com.devahmed.demo.onlinepharmacy.Utils.CanvasView;

import java.util.List;

public interface BillsMvc {

    interface Listener{
        void onBillItemClicked(Order order , CanvasView canvasView);
        void onDownloadItemClicked(Order order,CanvasView canvasView);
        void onShareBtnClicked(Order order,CanvasView canvasView);
    }

    void bindData(List<Order> orderList);
    void showProgressbar();
    void hideProgressbar();
    void activateDownloadMode(CanvasView canvasView);
    void activateNormalMode();

}
