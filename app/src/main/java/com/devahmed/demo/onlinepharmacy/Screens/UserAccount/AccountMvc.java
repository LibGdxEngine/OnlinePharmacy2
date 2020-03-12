package com.devahmed.demo.onlinepharmacy.Screens.UserAccount;

public interface AccountMvc {
    interface Listener{
        void onApplyBtnClicked();
        void onUseGPSBtnClicked();
        void onLogoutBtnClicked();
        void onCartConfirmBtnCLicked();
        void onEditBtnClicked();
    }

    void bindData(String userName,
                  String userPhone, String gpsAddress, String area,
                  String street, String name, String phone
                    , double getxPos, double v);
    void activateEditMode();
    void activateNormalShowMode();
    void activateCartConfirmationMode();
    void activateAdminShowMode();
    void showProgressbar();
    void hideProgressbar();
}
