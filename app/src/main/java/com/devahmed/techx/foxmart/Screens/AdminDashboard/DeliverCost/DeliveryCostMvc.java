package com.devahmed.techx.foxmart.Screens.AdminDashboard.DeliverCost;

import com.devahmed.techx.foxmart.Models.DeliverCost;

import java.util.List;

public interface DeliveryCostMvc {
    interface Listener{
        void onSubmitBtnClicked();
    }

    void bindData(List<DeliverCost>  deliverCosts);
    void showProgressBar();
    void hideProgressBar();

}
