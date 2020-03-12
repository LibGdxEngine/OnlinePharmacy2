package com.devahmed.demo.onlinepharmacy.Screens.AdminDashboard.BillsControl.BillsItemList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.devahmed.demo.onlinepharmacy.Common.MVC.BaseObservableMvcView;
import com.devahmed.demo.onlinepharmacy.Models.Order;
import com.devahmed.demo.onlinepharmacy.Models.Product;
import com.devahmed.demo.onlinepharmacy.Models.User;
import com.devahmed.demo.onlinepharmacy.R;
import com.devahmed.demo.onlinepharmacy.Utils.CanvasView;

import java.util.List;

public class BillsItemMvcImp extends BaseObservableMvcView<BillsItemMvc.Listener> implements BillsItemMvc {

    ImageButton downloadBill , shareBill;
    TextView billNumberText;
    LinearLayout billContainerLinearLayout;
    Order order;
    CanvasView billCanvasView , temp;

    public BillsItemMvcImp(LayoutInflater inflater , ViewGroup parent) {
        setRootView(inflater.inflate(R.layout.bill_row_item , parent ,false));

        initViews();
        billContainerLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(Listener listener : getmListeners()){
                    listener.onBillItemClicked(order ,temp);
                }
            }
        });

        downloadBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(Listener listener : getmListeners()){
                    listener.onDownloadItemClicked(order , temp );
                }
            }
        });

        shareBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(Listener listener : getmListeners()){
                    listener.onShareBtnClicked(order , temp );
                }
            }
        });

    }

    private void initViews() {
        downloadBill = findViewById(R.id.billDownloadBtn);
        shareBill = findViewById(R.id.billShareBtn);
        billContainerLinearLayout = findViewById(R.id.billContainer);
        billNumberText = findViewById(R.id.billNumber);
    }


    @Override
    public void bindData(Order order , List<Product> productList , User user) {
        this.order = order;
        this.billCanvasView = new CanvasView(getContext() , order , productList , user);
        this.temp = new CanvasView(getContext() , order , productList , user);
        billContainerLinearLayout.addView(billCanvasView);

        billNumberText.setText("#1");
    }

}
