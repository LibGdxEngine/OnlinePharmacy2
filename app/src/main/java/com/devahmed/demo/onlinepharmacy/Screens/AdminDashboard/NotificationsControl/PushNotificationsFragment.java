package com.devahmed.demo.onlinepharmacy.Screens.AdminDashboard.NotificationsControl;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;

public class PushNotificationsFragment extends Fragment {
    NotificationsMvcImp mvcImp;
    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        mvcImp = new NotificationsMvcImp(getLayoutInflater() , null);


        return mvcImp.getRootView();
    }
}
