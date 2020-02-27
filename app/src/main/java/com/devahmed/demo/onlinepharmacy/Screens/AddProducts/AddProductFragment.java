package com.devahmed.demo.onlinepharmacy.Screens.AddProducts;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;

import com.devahmed.demo.onlinepharmacy.Common.dependencyInjection.BaseFragment;
import com.devahmed.demo.onlinepharmacy.Screens.AddProducts.AddProductUseCase.AddProductUseCase;
import com.devahmed.demo.onlinepharmacy.Screens.AddProducts.AddProductUseCase.GetImageUseCase;
import com.devahmed.demo.onlinepharmacy.Screens.Home.UseCases.AddCategoryUseCase;
import com.devahmed.demo.onlinepharmacy.Screens.SubCategories.UseCase.AddSubCategoryUseCase;

import static android.app.Activity.RESULT_OK;

public class AddProductFragment extends BaseFragment implements AddProductMvc.Listener, AddProductUseCase.Listener, AddSubCategoryUseCase.Listener, AddCategoryUseCase.Listener {

    AddProductViewMvcImp mvcImp;
    GetImageUseCase getImageUseCase;
    AddProductUseCase addProductUseCase;
    AddSubCategoryUseCase addSubCategoryUseCase;
    AddCategoryUseCase addCategoryUseCase;
    Uri selectedImage;
    String category , subCategory;
    String FN;

    @Override
    public void onSubCategoryAddedSuccessfully() {

    }

    @Override
    public void onSubCategoryFailedToAdd() {

    }

    @Override
    public void onSubCategoryInputError(String message) {

    }

    @Override
    public void onCategoryAddedSuccessfully() {

    }

    @Override
    public void onCategoryFailedToAdd() {

    }

    @Override
    public void onCategoryInputError(String message) {

    }

    enum MODE  {
        ADD_CATEGORY,
        ADD_SUBCATEGORY,
        ADD_PRODUCT;
    }
    MODE mode;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mvcImp = getCompositionRoot().getMvcFactory().getAddProductImp(container);
        getImageUseCase = new GetImageUseCase(this);
        addProductUseCase = new AddProductUseCase(requireActivity());
        addSubCategoryUseCase = new AddSubCategoryUseCase(requireActivity());
        addCategoryUseCase = new AddCategoryUseCase(requireActivity());

        //get the mode of adding ( category or subcategory or product
        FN = getArguments().getString("FN");

        if(FN.equals("ADD_CATEGORY")){
            mode = MODE.ADD_CATEGORY;
            mvcImp.activateCategoryMode();
        }else if(FN.equals("ADD_SUBCATEGORY")){
            mode = MODE.ADD_SUBCATEGORY;
            mvcImp.activateSubCategoryMode();
            //if we adding sub-category we need to know at which category it is
            category = getArguments().getString("category");
        }else if(FN.equals("ADD_PRODUCT")){
            mode = MODE.ADD_PRODUCT;
            mvcImp.activateProductMode();
            //if we adding product we need to know at which sub-category it is
            subCategory = getArguments().getString("subcategory");
        }


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
        if(mode == MODE.ADD_PRODUCT){
            //add new product to the server with these info
            addProductUseCase.addNewProduct(mvcImp.getTitle() , mvcImp.getPrice() , selectedImage , category, mvcImp.isOfferChecked());
        }else if( mode == MODE.ADD_SUBCATEGORY){
            addSubCategoryUseCase.addSubCategoryToFirebase(mvcImp.getTitle()
                    , selectedImage
                    , category
                    , mvcImp.isOfferChecked());
            System.out.println("ssss");
        }else if(mode == MODE.ADD_CATEGORY){
            addCategoryUseCase.addNewCategory(mvcImp.getTitle() , selectedImage);
        }
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
        AddedSuccessfullyAction();
    }

    @Override
    public void onProductFailedToAdd() {
        failedToAddAction();
    }

    @Override
    public void onInputError(String message) {
        inputErrorAction(message);
    }

    private void AddedSuccessfullyAction() {
        Toast.makeText(requireActivity() , "Your post will be added soon \n Thank you ^_^", Toast.LENGTH_SHORT).show();
        mvcImp.hideProgressBar();
        mvcImp.showAddBtn();
        mvcImp.clearData();
        selectedImage = null;
    }

    private void failedToAddAction() {
        Toast.makeText(requireActivity(), "Failed to upload image" , Toast.LENGTH_SHORT).show();
        mvcImp.showAddBtn();
        mvcImp.hideProgressBar();
    }

    private void inputErrorAction(String message) {
        Toast.makeText(requireActivity(), "" + message, Toast.LENGTH_SHORT).show();
        mvcImp.hideProgressBar();
        mvcImp.showAddBtn();
    }

    @Override
    public void onStart() {
        super.onStart();
        mvcImp.registerListener(this);
        addProductUseCase.registerListener(this);
        addSubCategoryUseCase.registerListener(this);
        addCategoryUseCase.registerListener(this);

    }

    @Override
    public void onStop() {
        super.onStop();
        mvcImp.unregisterListener(this);
        addProductUseCase.unregisterListener(this);
        addSubCategoryUseCase.unregisterListener(this);
        addCategoryUseCase.unregisterListener(this);
    }
}