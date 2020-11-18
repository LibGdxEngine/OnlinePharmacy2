package com.devahmed.tech4fun.ecommerce.Screens.AdminDashboard.MinChargeControl;

public interface MinChargeMvc {

    interface Listener{
        void onUpdateBtnClicked(int newValue);
    }

    void bindData(int value);

}
