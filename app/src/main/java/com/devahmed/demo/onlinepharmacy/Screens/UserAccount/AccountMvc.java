package com.devahmed.demo.onlinepharmacy.Screens.UserAccount;

public interface AccountMvc {
    interface Listener{
        void onApplyBtnClicked();
    }

    void bindData(String name , String phone , String address);
}
