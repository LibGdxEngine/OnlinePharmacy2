package com.devahmed.demo.onlinepharmacy.Screens.AddProducts;

import android.net.Uri;

public interface AddProductMvc {

    public interface Listener {
        void onCameraBtnCLicked();
        void onGalleryImageClicked();
        void onPublishBtnClicked();
    }

    void showProgressBar();
    void hideProgressBar();

    void showAddBtn();
    void hideAddBtn();

    void bindFullImage(Uri image);

    void clearData();
}
