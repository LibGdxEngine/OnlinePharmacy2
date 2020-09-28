package com.devahmed.techx.aswaqmisr.Screens.ContactUs;

public interface ContactUsMvc {
    interface Listener{
        void onFaceBookBtnClicked();
        void onWhatsAppBtnClicked();
        void onInstaGramBtnClicked();
        void onTwitterBtnClicked();
        void onPhoneNumberClicked(String phone);
    }
}
