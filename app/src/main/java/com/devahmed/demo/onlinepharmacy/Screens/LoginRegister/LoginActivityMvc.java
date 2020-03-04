package com.devahmed.demo.onlinepharmacy.Screens.LoginRegister;

import android.app.Activity;

public interface LoginActivityMvc {

    public interface Listener{
        void onSubmitBtnCLicked();
        void onViewPagerPageChanged(int page);
        void onResendCodeBtnClicked();
        void onSkipBtnClicked();
    }

    void showProgressbar();
    void hideProgressbar();
    String getRegisterPhoneNumber();
    void startTimer(Activity activity);
    void changeViewPagerPageTo(int page);
    void hideLoginPage();
    void showLoginPage();
    void showCodeEnterencePage();
    void hideCodeEnterencePage();
}
