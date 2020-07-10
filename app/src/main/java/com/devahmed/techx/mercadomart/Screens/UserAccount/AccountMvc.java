package com.devahmed.techx.mercadomart.Screens.UserAccount;

import com.devahmed.techx.mercadomart.Models.User;

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
