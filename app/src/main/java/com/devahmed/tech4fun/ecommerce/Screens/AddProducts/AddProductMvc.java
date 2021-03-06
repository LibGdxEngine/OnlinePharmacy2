package com.devahmed.tech4fun.ecommerce.Screens.AddProducts;

import android.net.Uri;

import com.devahmed.tech4fun.ecommerce.Models.Branch;

import java.util.List;

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
    String getSelectedBranch();
    int getCount();
    int getPoints();
    void bindFullImage(Uri image);
    void bindBranchedData(List<Branch> branchesList);
    void clearData();
}
