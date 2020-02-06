package com.devahmed.demo.onlinepharmacy.Screens.AddProducts;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;

import com.devahmed.demo.onlinepharmacy.Common.dependencyInjection.BaseFragment;
import com.devahmed.demo.onlinepharmacy.Screens.AddProducts.AddProductUseCase.AddProductUseCase;
import com.devahmed.demo.onlinepharmacy.Screens.AddProducts.AddProductUseCase.GetImageUseCase;

import static android.app.Activity.RESULT_OK;

public class AddProductFragment extends BaseFragment implements AddProductMvc.Listener , AddProductUseCase.Listener{

    AddProductViewMvcImp mvcImp;
    GetImageUseCase getImageUseCase;
    AddProductUseCase addProductUseCase;
    Uri selectedImage;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mvcImp = getCompositionRoot().getMvcFactory().getAddProductImp(container);
        getImageUseCase = new GetImageUseCase(this);
        addProductUseCase = new AddProductUseCase(requireActivity());



        return mvcImp.getRootView();
    }


    @Override
    public void onCameraBtnCLicked() {
        getImageUseCase.openCamera();
    }

    @Override
    public void onGalleryImageClicked() {
        getImageUseCase.openGallery();

    }

    @Override
    public void onPublishBtnClicked() {
        mvcImp.showProgressBar();
        mvcImp.hideAddBtn();
        //add new product to the server with these info
        addProductUseCase.addNewProduct(mvcImp.getTitle() , mvcImp.getPrice() , selectedImage
                , mvcImp.isPromotionChecked() , mvcImp.isBestsellerChecked() , mvcImp.isOfferChecked());

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == getImageUseCase.GalleryREQUEST_CODE && data != null){
            // The user successfully picked an image
            System.out.println("Here");
            selectedImage = data.getData();
        }else if(resultCode == RESULT_OK && requestCode == getImageUseCase.CameraREQUEST_CODE && data != null){
            selectedImage = Uri.parse(getImageUseCase.getCapturedPhotoPath(data));
        }
        //bind this picked image uri to fullView imageView
        mvcImp.bindFullImage(selectedImage);
    }


    @Override
    public void onProductAddedSuccessfully() {
        Toast.makeText(requireActivity() , "Your post will be added soon \n Thank you ^_^", Toast.LENGTH_SHORT).show();
        mvcImp.hideProgressBar();
        mvcImp.showAddBtn();
        mvcImp.clearData();
        selectedImage = null;
    }

    @Override
    public void onProductFailedToAdd() {
        Toast.makeText(requireActivity(), "Failed to upload image" , Toast.LENGTH_SHORT).show();
        mvcImp.showAddBtn();
        mvcImp.hideProgressBar();
    }

    @Override
    public void onInputError(String message) {
        Toast.makeText(requireActivity(), "" + message, Toast.LENGTH_SHORT).show();
        mvcImp.hideProgressBar();
        mvcImp.showAddBtn();
    }

    @Override
    public void onStart() {
        super.onStart();
        mvcImp.registerListener(this);
        addProductUseCase.registerListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        mvcImp.unregisterListener(this);
        addProductUseCase.unregisterListener(this);
    }
}