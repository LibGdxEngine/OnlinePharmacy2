package com.devahmed.techx.mercadomart.Screens.Home.OffersSliderList;

import com.devahmed.techx.mercadomart.Models.ProductSubCategory;

public interface OffersListItemView {

    public interface Listener{
        void onItemClicked(ProductSubCategory category);
        void onItemLongClicked(ProductSubCategory subCategory);
        void onAddToCartBtnClicked(ProductSubCategory productSubCategory);
        void onIncreaseBtnClicked(ProductSubCategory productSubCategory);
        void onDecreaseBtnClicked(ProductSubCategory productSubCategory);
        void onOkBtnClicked(ProductSubCategory productSubCategory);
    }

    public void bindData(ProductSubCategory category,boolean showAddToCartBtn);
}
