package com.devahmed.tech4fun.ecommerce.Common.dependencyInjection;


import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.devahmed.tech4fun.ecommerce.Screens.AddProducts.AddProductViewMvcImp;
import com.devahmed.tech4fun.ecommerce.Screens.Home.HomeMvcImp;
import com.devahmed.tech4fun.ecommerce.Screens.ProductsShow.ProductsShowMvcImp;
import com.devahmed.tech4fun.ecommerce.Screens.SubCategories.SubCategoryMvcImp;


public class MvcViewFactory {

    private final LayoutInflater inflater;

    public MvcViewFactory(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    public HomeMvcImp getHomeMvc(@Nullable ViewGroup parent){
        return new HomeMvcImp(inflater , parent);
    }

    public AddProductViewMvcImp getAddProductImp(@Nullable ViewGroup parent){
        return new AddProductViewMvcImp(inflater , parent);
    }

    public ProductsShowMvcImp getShowProductsImp(@Nullable ViewGroup parent){
        return new ProductsShowMvcImp(inflater , parent);
    }

    public SubCategoryMvcImp getSubCategoryMvcImp(@Nullable ViewGroup parent){
        return new SubCategoryMvcImp(inflater , parent);
    }

}

