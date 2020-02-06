package com.devahmed.demo.onlinepharmacy.Screens.Home;

import com.devahmed.demo.onlinepharmacy.Models.Product;

import java.util.List;

public interface HomeMvc {

    public interface Listener{

        void onCategoryClicked();
        void onProductClicked(Product product);

    }

    public void bindSliderData(List<Product> offersProductList);

    public void bindCategoriesDataData();

    public void bindPromotionProductsData(List<Product> productList);

    public void bindBestSellerProductsData(List<Product> productsList);

}
