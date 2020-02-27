package com.devahmed.demo.onlinepharmacy.Screens.ProductsShow.ProductsList;

import com.devahmed.demo.onlinepharmacy.Models.Product;

public interface ShowProductListItemView {

        public interface Listener{
            void onImageClicked(Product product);
            void onAddToCartBtnClicked(Product product);
        }

        public void bindData(Product product);

    }


