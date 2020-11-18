package com.devahmed.tech4fun.ecommerce.Screens.Settings;

public interface SettingsMvc {
    interface Listener{
        void onChangLanguageBtnClicked(boolean isChecked);
    }
    void bindData(String language);
}
