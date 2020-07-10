package com.devahmed.techx.mercadomart.Screens.AdminDashboard.NotificationsControl;

public interface NotificationsMvc {
    interface Listener{
        void onSendNotificationBtnClicked();
        void onNotificationImageClicked();
        void onSwitchModeChange(boolean isChecked);
    }
    void activatePhotoMode();
    void activateBodyMode();
}
