package com.devahmed.tech4fun.ecommerce.Screens.AdminDashboard.NotificationsControl;

public interface NotificationsMvc {
    interface Listener{
        void onSendNotificationBtnClicked();
        void onNotificationImageClicked();
        void onSwitchModeChange(boolean isChecked);
    }
    void activatePhotoMode();
    void activateBodyMode();
}
