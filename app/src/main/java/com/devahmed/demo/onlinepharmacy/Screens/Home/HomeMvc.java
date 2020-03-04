package com.devahmed.demo.onlinepharmacy.Screens.Home;

import com.devahmed.demo.onlinepharmacy.Models.Category;
import com.devahmed.demo.onlinepharmacy.Models.Product;
import com.devahmed.demo.onlinepharmacy.Models.SubCategory;

import java.util.List;

public interface HomeMvc {

    public interface Listener{


        void onOfferCliked(SubCategory subCategory);
        void onOffersLongClick(SubCategory subCategory);
        void onCategoryClicked(String title);
        void onCtegoryLongClick(Category category);
        void onChooseCategoryEdit(Category category);
        void onChooseCategoryDelete(Category category);
        void onChooseOfferEdit(SubCategory subCategory);
        void onChooseOfferDelete(SubCategory subCategory);
        void onAddCategoryBtnClicked();
        void onSearchbarClicked();
    }

    public void bindSliderData(List<SubCategory> offersProductList);

    public void bindCategoriesDataData(List<Category> categoryList);

    void showProgressbar();
    void hideProgressbar();
}
