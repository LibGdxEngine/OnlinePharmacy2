package com.devahmed.techx.mercadomart.Screens.AdminDashboard.MinChargeControl;

public interface MinChargeMvc {

    interface Listener{
        void onUpdateBtnClicked(int newValue);
    }

    void bindData(int value);

}
