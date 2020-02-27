package com.devahmed.demo.onlinepharmacy.Screens.AddProducts;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import com.devahmed.demo.onlinepharmacy.Common.MVC.BaseObservableMvcView;
import com.devahmed.demo.onlinepharmacy.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AddProductViewMvcImp extends BaseObservableMvcView<AddProductMvc.Listener> implements AddProductMvc {


    private FloatingActionButton _addBtn;
    private TextView addpost_pickfromgallerytxtview , addpost_pickfromcameratxtview , addpost_dialogtitle , priceTextView;
    private EditText _productName, _productPrice;
    private ProgressBar _progressbar;
    private ImageView _pickfromgallery , _pickfromcamera , addpost_fullviewImageView;
    private CheckBox checkbox_offer ;
    public AddProductViewMvcImp(LayoutInflater inflater , ViewGroup parent) {

        setRootView(inflater.inflate(R.layout.fragment_add_product, parent , false ));
        addpost_dialogtitle = findViewById(R.id.addpost_dialogtitle);
        priceTextView = findViewById(R.id.priceTextView);
        //Change the text of dialog title
        addpost_dialogtitle.setText("Add New Product");
        _progressbar = findViewById(R.id.addpost_progressbar);
        addpost_pickfromgallerytxtview = findViewById(R.id.addpost_pickfromgallerytxtview);
        addpost_pickfromgallerytxtview.setVisibility(View.VISIBLE);
        addpost_pickfromcameratxtview = findViewById(R.id.addpost_pickfromcameratxtview);
        addpost_pickfromcameratxtview.setVisibility(View.VISIBLE);
        _addBtn = findViewById(R.id.addpost_addbtn);
        _productName = findViewById(R.id.addpost_recipename);
        _productPrice = findViewById(R.id.addpost_recipequantities);
        _pickfromcamera = findViewById(R.id.addpost_pickfromcameraimage);
        _pickfromgallery = findViewById(R.id.addpost_pickfromgalleryimage);
        addpost_fullviewImageView = findViewById(R.id.addpost_fullviewImageView);
        checkbox_offer = findViewById(R.id.checkbox_offer);

        _addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(Listener listener : getmListeners()){
                    listener.onPublishBtnClicked();
                }
            }
        });

        _pickfromcamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(Listener listener : getmListeners()){
                    listener.onCameraBtnCLicked();
                }
            }
        });

        _pickfromgallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(Listener listener : getmListeners()){
                    listener.onGalleryImageClicked();
                }
            }
        });
    }

    public boolean isOfferChecked(){
        return checkbox_offer.isChecked();
    }

    public String getTitle(){
        return _productName.getText().toString();
    }
    public String getPrice(){
        return _productPrice.getText().toString();
    }

    @Override
    public void showProgressBar() {
        _progressbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        _progressbar.setVisibility(View.GONE);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void showAddBtn() {
        _addBtn.setVisibility(View.VISIBLE);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void hideAddBtn() {
        _addBtn.setVisibility(View.INVISIBLE);
        System.out.println("Hide");
    }

    @Override
    public void bindFullImage(Uri image) {
        addpost_fullviewImageView.setVisibility(View.VISIBLE);
        addpost_fullviewImageView.setImageURI(image);
    }

    @Override
    public void clearData() {
        addpost_fullviewImageView.setImageURI(null);
        _productName.setText("");
        _productPrice.setText("");
        checkbox_offer.setChecked(false);
        addpost_fullviewImageView.setVisibility(View.GONE);
    }

    public void activateCategoryMode(){
        priceTextView.setVisibility(View.GONE);
        _productPrice.setVisibility(View.GONE);
        checkbox_offer.setVisibility(View.GONE);
    }
    public void activateSubCategoryMode(){
        priceTextView.setVisibility(View.GONE);
        _productPrice.setVisibility(View.GONE);
        checkbox_offer.setVisibility(View.GONE);
    }
    public void activateProductMode(){
        //DO NOTHING
    }
}
