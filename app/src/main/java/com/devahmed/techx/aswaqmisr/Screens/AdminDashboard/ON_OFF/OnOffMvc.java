package com.devahmed.techx.aswaqmisr.Screens.AdminDashboard.ON_OFF;

import com.devahmed.techx.aswaqmisr.Models.Branch;

import java.util.List;

public interface OnOffMvc {
    interface Listener{
        void onItemClicked(Branch branch);
    }
    void bindData(List<Branch> branches);
    void showProgressbar();
    void hideProgressbar();
}
