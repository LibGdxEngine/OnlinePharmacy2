package com.devahmed.tech4fun.ecommerce.Screens.UserAccount;

import com.devahmed.tech4fun.ecommerce.Models.User;

public interface AccountMvc {
    interface Listener{
        void onApplyBtnClicked();
        void onUseGPSBtnClicked();
        void onLogoutBtnClicked();
        void onCartConfirmBtnCLicked();
        void onEditBtnClicked();
    }

    void bindData(User user);
    void activateEditMode();
    void activateNormalShowMode();
    void activateCartConfirmationMode();
    void activateAdminShowMode();
    void showProgressbar();
    void hideProgressbar();
}
