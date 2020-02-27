package com.devahmed.demo.onlinepharmacy.Screens.SubCategories;

import com.devahmed.demo.onlinepharmacy.Models.SubCategory;

import java.util.List;

public interface SubCategoriesMvc {

    interface  Listener{
        void onSubCategoryClicked(String subcategory);
        void onAddSubCategoryBtnClicked();
    }

    void bindSubCategoriesData(List<SubCategory> subCategoryList);
}
