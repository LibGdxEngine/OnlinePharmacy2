package com.devahmed.techx.foxmart.Screens.ContactUs;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.devahmed.techx.foxmart.Common.MVC.BaseObservableMvcView;
import com.devahmed.techx.foxmart.R;

public class ContactUsMvcImp extends BaseObservableMvcView<ContactUsMvc.Listener> implements ContactUsMvc {


    public ContactUsMvcImp(LayoutInflater inflater , ViewGroup parent) {
        setRootView(inflater.inflate(R.layout.contact_us_fragment , parent , false));
    }
}
