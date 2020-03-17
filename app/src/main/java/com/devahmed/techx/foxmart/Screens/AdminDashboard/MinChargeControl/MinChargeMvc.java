package com.devahmed.techx.foxmart.Screens.AdminDashboard.MinChargeControl;

public interface MinChargeMvc {

    interface Listener{
        void onUpdateBtnClicked(int newValue);
    }

    void bindData(int value);

}
