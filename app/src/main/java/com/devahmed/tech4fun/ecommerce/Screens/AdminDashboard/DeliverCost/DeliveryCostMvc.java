package com.devahmed.tech4fun.ecommerce.Screens.AdminDashboard.DeliverCost;

import com.devahmed.tech4fun.ecommerce.Models.DeliverCost;

import java.util.List;

public interface DeliveryCostMvc {
    interface Listener{
        void onSubmitBtnClicked();
    }

    void bindData(List<DeliverCost>  deliverCosts);
    void showProgressBar();
    void hideProgressBar();

}
