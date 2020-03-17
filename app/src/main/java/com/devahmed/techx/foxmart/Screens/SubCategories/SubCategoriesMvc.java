package com.devahmed.techx.foxmart.Screens.SubCategories;

import com.devahmed.techx.foxmart.Models.SubCategory;

import java.util.List;

public interface SubCategoriesMvc {

    interface  Listener{
        void onSubCategoryClicked(String subcategory);
        void onAddSubCategoryBtnClicked();
        void onSubCategoryLongClicked(SubCategory category);

        void onChooseSubCategoryEdit(SubCategory category);

        void onChooseSubCategoryDelete(SubCategory category);
    }

    void bindSubCategoriesData(List<SubCategory> subCategoryList);
    void showProgressbar();
    void hideProgressbar();
}
