package com.devahmed.techx.foxmart.Screens.Settings;

public interface SettingsMvc {
    interface Listener{
        void onChangLanguageBtnClicked(boolean isChecked);
    }
    void bindData(String language);
}
