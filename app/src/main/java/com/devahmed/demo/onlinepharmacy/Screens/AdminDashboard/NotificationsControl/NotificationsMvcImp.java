package com.devahmed.demo.onlinepharmacy.Screens.AdminDashboard.NotificationsControl;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.devahmed.demo.onlinepharmacy.Common.MVC.BaseObservableMvcView;
import com.devahmed.demo.onlinepharmacy.R;

public class NotificationsMvcImp extends BaseObservableMvcView<NotificationsMvc.Listener> implements NotificationsMvc {


    public NotificationsMvcImp(LayoutInflater inflater , ViewGroup parent) {
        setRootView(inflater.inflate(R.layout.push_notifications_fragment , parent , false));


    }
}
