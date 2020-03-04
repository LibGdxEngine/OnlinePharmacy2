package com.devahmed.demo.onlinepharmacy.Screens.ProductsShow.ProductsList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.devahmed.demo.onlinepharmacy.Common.MVC.BaseObservableMvcView;
import com.devahmed.demo.onlinepharmacy.Models.Product;
import com.devahmed.demo.onlinepharmacy.R;

public class ShowProductsListItemViewMvcImp extends BaseObservableMvcView<ShowProductListItemView.Listener>
        implements ShowProductListItemView {


    private TextView titleTextView , priceTextView;
    private Button AddToCartBtn;
    private ImageView imageView;
    private Product product;
    private ImageView increaseItemsBtn , decreaseItemsBtn;
    private TextView noOfItemsInCartText;
    private LinearLayout cartControlsContainer;
    private int counter = 1;

    public ShowProductsListItemViewMvcImp(LayoutInflater inflater , ViewGroup parent) {
        setRootView(inflater.inflate(R.layout.row_product , parent , false));

        titleTextView = findViewById(R.id.productTitle);
        priceTextView = findViewById(R.id.productPrice);
        AddToCartBtn = findViewById(R.id.productAddToCartBtn);
        imageView = findViewById(R.id.productImage);
        increaseItemsBtn = findViewById(R.id.increaseCounterBtn);
        decreaseItemsBtn = findViewById(R.id.decreaseCounterBtn);
        noOfItemsInCartText = findViewById(R.id.noOfItemsInCartText);
        cartControlsContainer = findViewById(R.id.cartControlsContainer);

        increaseItemsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter += 1;
                noOfItemsInCartText.setText("" + counter);
                for(Listener listener : getmListeners()){
                    listener.onIncreaseItemsBtnClicked(product);
                }
            }
        });

        decreaseItemsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter -= 1;
                if(counter <= 0){
                    cartControlsContainer.setVisibility(View.GONE);
                    AddToCartBtn.setVisibility(View.VISIBLE);
                    //return the counter to its default value => 1
                    counter = 1;
                }else{
                    noOfItemsInCartText.setText("" + counter);
                }
                for(Listener listener : getmListeners()){
                    listener.onDecreaseItemsBtnClicked(product);
                }
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(Listener listener : getmListeners()){
                    listener.onImageClicked(product);
                }
            }
        });
        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                for(Listener listener : getmListeners()){
                    listener.onImageLongClicked(product);
                }
                return false;
            }
        });

        AddToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(Listener listener : getmListeners()){
                    listener.onAddToCartBtnClicked(product);
                }
                cartControlsContainer.setVisibility(View.VISIBLE);
                AddToCartBtn.setVisibility(View.GONE);
            }
        });


    }

    @Override
    public void bindData(Product product) {
        this.product = product;
        titleTextView.setText(product.getTitle());
        priceTextView.setText("" + product.getPrice() + "-EP");
        Glide.with(getContext())
                .load(product.getImage())
                .placeholder(R.drawable.images_placeholder)
                .into(imageView);
    }

    @Override
    public void bindDataForAddedToCartProduct(Product product , int noOfItemsInCart) {
        this.product = product;
        counter = noOfItemsInCart;
        titleTextView.setText(product.getTitle());
        priceTextView.setText("" + product.getPrice() + "-EP");
        Glide.with(getContext())
                .load(product.getImage())
                .placeholder(R.drawable.images_placeholder)
                .into(imageView);
        noOfItemsInCartText.setText("" + noOfItemsInCart);

        cartControlsContainer.setVisibility(View.VISIBLE);
        AddToCartBtn.setVisibility(View.GONE);
    }


}
