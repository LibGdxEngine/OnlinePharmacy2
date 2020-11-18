package com.devahmed.tech4fun.ecommerce.Screens.ContactUs;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.devahmed.tech4fun.ecommerce.Common.MVC.BaseObservableMvcView;
import com.devahmed.tech4fun.ecommerce.R;

public class ContactUsMvcImp extends BaseObservableMvcView<ContactUsMvc.Listener> implements ContactUsMvc {

    TextView phoneNumberTextView;
    public ContactUsMvcImp(LayoutInflater inflater , ViewGroup parent) {
        setRootView(inflater.inflate(R.layout.contact_us_fragment , parent , false));
        phoneNumberTextView = findViewById(R.id.phoneNumber);
        phoneNumberTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(Listener listener : getmListeners()){
                    listener.onPhoneNumberClicked(phoneNumberTextView.getText().toString());
                }
            }
        });
    }
}
