package com.devahmed.techx.foxmart.Screens.Home.OffersSliderList;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.devahmed.techx.foxmart.Common.MVC.BaseObservableMvcView;
import com.devahmed.techx.foxmart.Models.ProductSubCategory;
import com.devahmed.techx.foxmart.R;
import com.devahmed.techx.foxmart.Utils.CartManager;

import java.util.List;

public class OffersListItemViewMvcImp extends BaseObservableMvcView<OffersListItemView.Listener>
        implements OffersListItemView {



    TextView mTitle , mPrice;
    ImageView mImage;
    ProductSubCategory productSubCategory;
    LinearLayout productControlContainerLinearLayout , itemsCountControls;
    Button addToCartBtn;
    Dialog Dialog;
    private CartManager prodctsIDCartManager , productCountCartManagaer;
    private List<String> cartProductIdList , cartProductCountList;
    private int counter = 1;

    public OffersListItemViewMvcImp(LayoutInflater inflater , ViewGroup parent) {

        setRootView(inflater.inflate(R.layout.row_sliderview , parent , false));
        prodctsIDCartManager = new CartManager(getContext() , "productsId");
        productCountCartManagaer = new CartManager(getContext() , "productCount");
        cartProductIdList = prodctsIDCartManager.getStoredValues();
        cartProductCountList = productCountCartManagaer.getStoredValues();
//        prodctsIDCartManager.deleteAllKeys();
//        productCountCartManagaer.deleteAllKeys();
        mTitle = findViewById(R.id.slideViewTitle);
        mImage = findViewById(R.id.slideViewImage);
        itemsCountControls = findViewById(R.id.cartControlsContainer);
        addToCartBtn = findViewById(R.id.add_to_cart_Btn);

        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCartBtn.setVisibility(View.GONE);
//                showAddDialog();
                if(!cartProductIdList.contains(productSubCategory.getId())){
                    //add a new product to cart manager
                    prodctsIDCartManager.addToCart(productSubCategory.getId());
                    productCountCartManagaer.addToCart("1");
                }else{
                    //if the id is exist but its count is zero => just increase the counter
                    //this case happen when user increase the counter then decrease it again
                    int index = cartProductIdList.indexOf(productSubCategory.getId());
                    productCountCartManagaer.replaceKey(index , "1");
                }
                //update the lists of id's with the new data
                cartProductIdList = prodctsIDCartManager.getStoredValues();
                cartProductCountList = productCountCartManagaer.getStoredValues();
                for(Listener listener : getmListeners()){
                    listener.onAddToCartBtnClicked(productSubCategory);
                }
            }


        });

        productControlContainerLinearLayout = findViewById(R.id.productControls);
        getRootView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(OffersListItemView.Listener listener : getmListeners()){
                    listener.onItemClicked(productSubCategory);
                }
            }
        });
        getRootView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(!getContext().getResources().getString(R.string.admin).equalsIgnoreCase("true")){
                    return false;
                }
                for(Listener listener : getmListeners()){
                    listener.onItemLongClicked(productSubCategory);
                }
                return false;
            }
        });
    }

    @Override
    public void bindData(ProductSubCategory product , boolean showAddToCartBtn) {
        this.productSubCategory = product;
        if(product.getType() == ProductSubCategory.TYPE_SUB_CATEGORY){
            productControlContainerLinearLayout.setVisibility(View.GONE);
            Glide.with(getContext()).load(product.getImage()).placeholder(R.drawable.images_placeholder).into(mImage);
            mTitle.setText("" + product.getTitle());

        }else if(product.getType() == ProductSubCategory.TYPE_PRODUCT){
            productControlContainerLinearLayout.setVisibility(View.VISIBLE);
            if(showAddToCartBtn == true){
                addToCartBtn.setVisibility(View.VISIBLE);
            }else{
                addToCartBtn.setVisibility(View.INVISIBLE);
            }
            Glide.with(getContext()).load(product.getImage()).placeholder(R.drawable.images_placeholder).into(mImage);
            mTitle.setText("" + product.getTitle());
        }

    }
    private void showAddDialog() {
        Dialog = new Dialog(getContext());
        Dialog.setContentView(R.layout.add_to_cart_pop_up_dialog);
        Dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        Dialog.findViewById(R.id.container).setAnimation(AnimationUtils.loadAnimation(getContext() , R.anim.products_image_fade_scale_animation));
        Dialog.setCancelable(true);
        ImageView increaseButton , decreaseButton;
        final TextView countTextView;
        Button okBtn = Dialog.findViewById(R.id.okBtn);
        increaseButton = Dialog.findViewById(R.id.increaseCounterBtn);
        decreaseButton = Dialog.findViewById(R.id.decreaseCounterBtn);
        countTextView = Dialog.findViewById(R.id.noOfItemsInCartText);
        Dialog.show();

        countTextView.setText("" + counter);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog.dismiss();
            }
        });

        increaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter++;
                countTextView.setText("" + counter);
                for(Listener listener : getmListeners()){
                    listener.onIncreaseBtnClicked(productSubCategory);
                }
                int newKey = counter;
                productCountCartManagaer.replaceKey(cartProductCountList.size() - 1 , "" + newKey);
                cartProductIdList = prodctsIDCartManager.getStoredValues();
                cartProductCountList = productCountCartManagaer.getStoredValues();
            }
        });

        decreaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter--;
                if(counter <= 0){
                    counter = 1;
                    Dialog.dismiss();
                }
                countTextView.setText("" + counter);
                for(Listener listener : getmListeners()){
                    listener.onDecreaseBtnClicked(productSubCategory);
                }
                int newKey = counter;
                productCountCartManagaer.replaceKey(cartProductCountList.size() - 1, "" + newKey);
                cartProductIdList = prodctsIDCartManager.getStoredValues();
                cartProductCountList = productCountCartManagaer.getStoredValues();
            }
        });
    }

}
