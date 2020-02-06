package com.devahmed.demo.onlinepharmacy.Screens.Home.ProductsList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.devahmed.demo.onlinepharmacy.Common.MVC.BaseObservableMvcView;
import com.devahmed.demo.onlinepharmacy.Models.Product;
import com.devahmed.demo.onlinepharmacy.R;

public class PromotionListItemViewMvcImp extends BaseObservableMvcView<ProductListItemView.Listener>
        implements ProductListItemView{

    TextView textView;
    Product product;
    public PromotionListItemViewMvcImp(LayoutInflater inflater , ViewGroup parent) {
        setRootView(inflater.inflate(R.layout.row_product , parent , false));
        textView = findViewById(R.id.productTitle);
        getRootView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(Listener listener : getmListeners()){
                    listener.onItemClicked(product);
                }
            }
        });
    }

    @Override
    public void bindData(Product product) {
        this.product = product;
        textView.setText(product.getCategory());
    }
}
