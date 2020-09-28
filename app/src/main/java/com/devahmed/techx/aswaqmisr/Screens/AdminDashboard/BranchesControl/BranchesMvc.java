package com.devahmed.techx.aswaqmisr.Screens.AdminDashboard.BranchesControl;

import com.devahmed.techx.aswaqmisr.Models.Branch;

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
