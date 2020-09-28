package com.devahmed.techx.aswaqmisr.Screens.Home.OffersSliderList;

import com.devahmed.techx.aswaqmisr.Models.ProductSubCategory;

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
