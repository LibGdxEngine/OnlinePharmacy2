package com.devahmed.demo.onlinepharmacy.Screens.AdminDashboard.BillsControl.BillsItemList;

import android.graphics.Bitmap;

import com.devahmed.demo.onlinepharmacy.Models.Order;
import com.devahmed.demo.onlinepharmacy.Models.Product;
import com.devahmed.demo.onlinepharmacy.Models.User;
import com.devahmed.demo.onlinepharmacy.Utils.CanvasView;

import java.util.List;

public interface BillsItemMvc {
    interface Listener{
        void onBillItemClicked(Order order , CanvasView canvasView);
        void onDownloadItemClicked(Order order,CanvasView canvasView);
        void onShareBtnClicked(Order order,CanvasView canvasView);
    }

    void bindData(Order order , List<Product> productList , User user);
}
