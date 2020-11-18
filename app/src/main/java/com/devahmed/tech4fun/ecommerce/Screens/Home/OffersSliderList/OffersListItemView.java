package com.devahmed.tech4fun.ecommerce.Screens.Home.OffersSliderList;

import com.devahmed.tech4fun.ecommerce.Models.ProductSubCategory;

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
