package com.devahmed.demo.onlinepharmacy.Screens.AdminDashboard.BranchesControl;

import com.devahmed.demo.onlinepharmacy.Models.Branch;

import java.util.List;

public interface BranchesMvc {
    interface Listener{
        void onAddBranchBtnClicked();
        void onBranchItemClicked(Branch branch);
        void onGetLocationBtnClicked(String branchName);
        void onCreateBranchBtnClicked();
        void onRangeProgressChanged(int progress);
    }

    void bindData(List<Branch> branches);
    void showAddBranchDialog();
    void showProgressbar();
    void hideProgressbar();
    void showLocationContainer(Branch branch);
}
