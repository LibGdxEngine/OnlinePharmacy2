package com.devahmed.demo.onlinepharmacy.Screens.Home.ProductsList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.devahmed.demo.onlinepharmacy.Common.MVC.BaseObservableMvcView;
import com.devahmed.demo.onlinepharmacy.Models.Product;
import com.devahmed.demo.onlinepharmacy.R;

public class OffersListItemViewMvcImp extends BaseObservableMvcView<ProductListItemView.Listener>
        implements ProductListItemView{



    TextView mTitle , mPrice;
    ImageView mImage;
    Product product;

    public OffersListItemViewMvcImp(LayoutInflater inflater , ViewGroup parent) {
        setRootView(inflater.inflate(R.layout.row_sliderview , parent , false));
        mTitle = findViewById(R.id.productTitle);
        mImage = findViewById(R.id.slideViewImage);
        getRootView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(ProductListItemView.Listener listener : getmListeners()){
                    listener.onItemClicked(product);
                }
            }
        });
    }

    @Override
    public void bindData(Product product) {
        System.out.println("the image is " + product.getImage());
        Glide.with(getContext()).load(product.getImage()).placeholder(R.drawable.ic_launcher_background).into(mImage);

    }
}
