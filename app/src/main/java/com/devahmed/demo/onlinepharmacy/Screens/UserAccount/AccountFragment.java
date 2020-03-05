package com.devahmed.demo.onlinepharmacy.Screens.UserAccount;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class AccountFragment extends Fragment implements AccountMvc.Listener {


    AccountMvcImp mvcImp;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        mvcImp = new AccountMvcImp(getLayoutInflater(), null);

        return mvcImp.getRootView();
    }

    @Override
    public void onApplyBtnClicked() {

    }

    @Override
    public void onStart() {
        super.onStart();
        mvcImp.registerListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        mvcImp.unregisterListener(this);
    }
}
