package com.devahmed.techx.mercadomart.Screens.AdminDashboard.ON_OFF;

import com.devahmed.techx.mercadomart.Models.Branch;

import java.util.List;

public interface OnOffMvc {
    interface Listener{
        void onItemClicked(Branch branch);
    }
    void bindData(List<Branch> branches);
    void showProgressbar();
    void hideProgressbar();
}
