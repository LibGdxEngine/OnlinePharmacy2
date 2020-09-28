package com.devahmed.techx.aswaqmisr.Screens.AdminDashboard.DeliverCost;

import com.devahmed.techx.aswaqmisr.Models.DeliverCost;

import java.util.List;

public interface DeliveryCostMvc {
    interface Listener{
        void onSubmitBtnClicked();
    }

    void bindData(List<DeliverCost>  deliverCosts);
    void showProgressBar();
    void hideProgressBar();

}
