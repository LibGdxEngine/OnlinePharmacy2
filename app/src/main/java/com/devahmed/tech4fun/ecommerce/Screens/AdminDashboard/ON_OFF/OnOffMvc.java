package com.devahmed.tech4fun.ecommerce.Screens.AdminDashboard.ON_OFF;

import com.devahmed.tech4fun.ecommerce.Models.Branch;

import java.util.List;

public interface OnOffMvc {
    interface Listener{
        void onItemClicked(Branch branch);
    }
    void bindData(List<Branch> branches);
    void showProgressbar();
    void hideProgressbar();
}
