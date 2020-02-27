package com.devahmed.demo.onlinepharmacy.Screens.Home.ProductsList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.devahmed.demo.onlinepharmacy.Common.MVC.BaseObservableMvcView;
import com.devahmed.demo.onlinepharmacy.Models.SubCategory;
import com.devahmed.demo.onlinepharmacy.R;

public class OffersListItemViewMvcImp extends BaseObservableMvcView<OffersListItemView.Listener>
        implements OffersListItemView {



    TextView mTitle , mPrice;
    ImageView mImage;
    SubCategory product;

    public OffersListItemViewMvcImp(LayoutInflater inflater , ViewGroup parent) {
        setRootView(inflater.inflate(R.layout.row_sliderview , parent , false));
        mTitle = findViewById(R.id.slideViewTitle);
        mImage = findViewById(R.id.slideViewImage);
        getRootView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(OffersListItemView.Listener listener : getmListeners()){
                    listener.onItemClicked(product);
                }
            }
        });
        getRootView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                for(Listener listener : getmListeners()){
                    listener.onItemLongClicked(product);
                }
                return false;
            }
        });
    }

    @Override
    public void bindData(SubCategory product) {
        this.product = product;
        System.out.println("the image is " + product.getImage());
        Glide.with(getContext()).load(product.getImage()).placeholder(R.drawable.ic_launcher_background).into(mImage);
        mTitle.setText("" + product.getTitle());
    }


}
