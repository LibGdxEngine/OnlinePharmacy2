package com.devahmed.demo.onlinepharmacy.Screens.ProductsShow.ProductsList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.devahmed.demo.onlinepharmacy.Common.MVC.BaseObservableMvcView;
import com.devahmed.demo.onlinepharmacy.Models.Product;
import com.devahmed.demo.onlinepharmacy.R;

public class ShowProductsListItemViewMvcImp extends BaseObservableMvcView<ShowProductListItemView.Listener>
        implements ShowProductListItemView {


    private TextView titleTextView , priceTextView;
    private ImageButton AddToCartBtn;
    private ImageView imageView;
    private Product product;

    public ShowProductsListItemViewMvcImp(LayoutInflater inflater , ViewGroup parent) {
        setRootView(inflater.inflate(R.layout.row_product , parent , false));

        titleTextView = findViewById(R.id.productTitle);
        priceTextView = findViewById(R.id.productPrice);
        AddToCartBtn = findViewById(R.id.productAddToCartBtn);
        imageView = findViewById(R.id.productImage);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(Listener listener : getmListeners()){
                    listener.onImageClicked(product);
                }
            }
        });

        AddToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(Listener listener : getmListeners()){
                    listener.onAddToCartBtnClicked(product);
                }
            }
        });


    }

    @Override
    public void bindData(Product product) {
        this.product = product;
        titleTextView.setText(product.getTitle());
        priceTextView.setText("" + product.getPrice());
        Glide.with(getContext())
                .load(product.getImage())
                .placeholder(R.drawable.ic_launcher_background)
                .into(imageView);

    }
}
