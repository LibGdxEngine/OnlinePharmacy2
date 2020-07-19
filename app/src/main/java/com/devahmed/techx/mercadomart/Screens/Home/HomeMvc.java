package com.devahmed.techx.mercadomart.Screens.Home;

import com.devahmed.techx.mercadomart.Models.Category;
import com.devahmed.techx.mercadomart.Models.ProductSubCategory;

import java.util.List;

public interface HomeMvc {

    public interface Listener{
        void onOfferCliked(ProductSubCategory subCategory);
        void onOffersLongClick(ProductSubCategory subCategory);
        void onCategoryClicked(String title);
        void onCtegoryLongClick(Category category);
        void onChooseCategoryEdit(Category category);
        void onChooseCategoryDelete(Category category);
        void onChooseOfferEdit(ProductSubCategory subCategory);
        void onChooseOfferDelete(ProductSubCategory subCategory);
        void onAddCategoryBtnClicked();
        void onSearchbarClicked();
        void onAddToCartBtnClicked(ProductSubCategory productSubCategory);
        void onIncreaseBtnClicked(ProductSubCategory productSubCategory);
        void onDecreaseBtnClicked(ProductSubCategory productSubCategory);
        void onOkBtnClicked(ProductSubCategory productSubCategory);
    }

    public void bindSliderData(List<ProductSubCategory> offersProductList);

    public void bindCategoriesDataData(List<Category> categoryList);

    void showProgressbar();
    void hideProgressbar();
}