package com.devahmed.demo.onlinepharmacy.Screens.Orders.OrdersDetails;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.devahmed.demo.onlinepharmacy.Common.MVC.BaseObservableMvcView;
import com.devahmed.demo.onlinepharmacy.Models.Product;
import com.devahmed.demo.onlinepharmacy.Models.User;
import com.devahmed.demo.onlinepharmacy.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class OrderDetailsMvcImp extends BaseObservableMvcView<OrderDetailsMvc.Listener> implements OrderDetailsMvc {

    RecyclerView orderedItemsRecyclerView;
    List<Product> productList;
    List<String> ordersCounts;
    OrderedProducstAdapter adapter;
    CircleImageView inprogrressImageView , deliveringImageView , deliveredImage;
    View line1 , line2;
    TextView totalItemsText, itemsPriceText , deliveryCostPrice
            , totalPriceText , areaText , streetText
            , buildingText , uniqueSignText;
    TextView orderStateText1 ,orderStateText2 , orderStateText3;

    ProgressBar progressBar;
    Button callUser;
    User user;
    public OrderDetailsMvcImp(LayoutInflater inflater , ViewGroup parent) {
        setRootView(inflater.inflate(R.layout.order_details_fragment , parent ,false));
        initViews();

        callUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(Listener listener : getmListeners()){
                    listener.onCallUserBtnClicked(user);
                }
            }
        });


    }

    private void initViews() {
        orderedItemsRecyclerView = findViewById(R.id.myOrderedItemRecycler);
        progressBar = findViewById(R.id.progressBar);
        totalItemsText = findViewById(R.id.totalItemsText);
        itemsPriceText = findViewById(R.id.itemsPriceText);
        orderStateText1 = findViewById(R.id.orderStateText1);
        orderStateText2 = findViewById(R.id.orderStateText2);
        orderStateText3 = findViewById(R.id.orderStateText3);
        inprogrressImageView = findViewById(R.id.inprogressImage);
        callUser = findViewById(R.id.callUserBtnJustForAdmin);
        deliveringImageView = findViewById(R.id.deliveringImage);
        deliveredImage = findViewById(R.id.deliveredImage);
        line1 = findViewById(R.id.line1);
        line2 = findViewById(R.id.line2);
        deliveryCostPrice = findViewById(R.id.deliveryCostPrice);
        totalPriceText = findViewById(R.id.totalPriceText);
        areaText = findViewById(R.id.areaText);
        ordersCounts = new ArrayList<>();
        streetText = findViewById(R.id.streetText);
        buildingText = findViewById(R.id.buildingText);
        uniqueSignText = findViewById(R.id.uniqueSignText);
        productList = new ArrayList<>();
        adapter = new OrderedProducstAdapter(productList);
        orderedItemsRecyclerView.setHasFixedSize(true);
        orderedItemsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext() , LinearLayoutManager.HORIZONTAL , false));
        orderedItemsRecyclerView.setAdapter(adapter);
    }

    @Override
    public void bindOrderedItemsData(List<Product> productList , List<String> productCounts) {
        this.productList = productList;
        this.ordersCounts = productCounts;
        adapter.setList(productList , productCounts);
    }

    @Override
    public void bindUserData(User user) {
        this.user = user;
        areaText.setText("Area : " + user.getArea());
        streetText.setText("Street No : " + user.getStreet());
        buildingText.setText("Building No : " + user.getFlat());
        uniqueSignText.setText("Nearest Landmark : " + user.getUniqueSign());
    }

    @Override
    public void bindPaymentsData(int totalItems, int deliveryCost, int totalPrice) {
        totalItemsText.setText("" + totalItems);
        deliveryCostPrice.setText("" + deliveryCost);
        itemsPriceText.setText("" +totalPrice);
        totalPriceText.setText("" + (totalPrice + deliveryCost));
    }

    @Override
    public void bindOrderState(String orderState) {
        orderStateText1.setText("Your order is " + orderState);
        orderStateText2.setText("Order " + orderState);
        orderStateText3.setText("Your order is " + orderState);
        if(orderState.equals("In Progress")){
            inprogrressImageView.setImageResource(R.drawable.ic_inprogress_24dp);
            inprogrressImageView.setBorderColor(getContext().getResources().getColor(R.color.colorPrimary));
        }else if(orderState.equals("Delivering")){
            deliveringImageView.setImageResource(R.drawable.ic_delivering_24dp);
            inprogrressImageView.setImageResource(R.drawable.ic_delivering_24dp);
            deliveringImageView.setBorderColor(getContext().getResources().getColor(R.color.colorPrimary));
            inprogrressImageView.setBorderColor(getContext().getResources().getColor(R.color.colorPrimary));
            line1.setBackgroundColor(getContext().getResources().getColor(R.color.colorPrimary));
        }else if(orderState.equals("Done")){
            deliveringImageView.setBorderColor(getContext().getResources().getColor(R.color.colorPrimary));
            inprogrressImageView.setBorderColor(getContext().getResources().getColor(R.color.colorPrimary));
            deliveredImage.setBorderColor(getContext().getResources().getColor(R.color.colorPrimary));
            deliveredImage.setImageResource(R.drawable.ic_delivered_24dp);
            deliveringImageView.setImageResource(R.drawable.ic_delivering_24dp);
            inprogrressImageView.setImageResource(R.drawable.ic_inprogress_24dp);
            line1.setBackgroundColor(getContext().getResources().getColor(R.color.colorPrimary));
            line2.setBackgroundColor(getContext().getResources().getColor(R.color.colorPrimary));
        }

    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showAdminControls() {
        //show call user btn
        callUser.setVisibility(View.VISIBLE);
    }


}
