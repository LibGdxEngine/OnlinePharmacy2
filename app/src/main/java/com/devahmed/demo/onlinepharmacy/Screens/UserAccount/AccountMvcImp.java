package com.devahmed.demo.onlinepharmacy.Screens.UserAccount;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.devahmed.demo.onlinepharmacy.Common.MVC.BaseObservableMvcView;
import com.devahmed.demo.onlinepharmacy.R;

public class AccountMvcImp extends BaseObservableMvcView<AccountMvc.Listener> implements AccountMvc{


    public AccountMvcImp(LayoutInflater inflater , ViewGroup parent) {
        setRootView(inflater.inflate(R.layout.account_fragment , parent , false));
    }

    @Override
    public void bindData(String name, String phone, String address) {

    }
}
