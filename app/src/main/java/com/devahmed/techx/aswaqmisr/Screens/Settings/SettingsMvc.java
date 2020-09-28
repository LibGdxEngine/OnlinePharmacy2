package com.devahmed.techx.aswaqmisr.Screens.Settings;

public interface SettingsMvc {
    interface Listener{
        void onChangLanguageBtnClicked(boolean isChecked);
    }
    void bindData(String language);
}
