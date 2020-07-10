package com.devahmed.techx.mercadomart.Screens.AdminDashboard.DeliverCost;

import com.devahmed.techx.mercadomart.Models.DeliverCost;

import java.util.List;

public interface DeliveryCostMvc {
    interface Listener{
        void onSubmitBtnClicked();
    }

    void bindData(List<DeliverCost>  deliverCosts);
    void showProgressBar();
    void hideProgressBar();

}
