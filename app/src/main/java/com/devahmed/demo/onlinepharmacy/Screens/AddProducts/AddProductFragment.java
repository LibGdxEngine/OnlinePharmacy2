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
import com.devahmed.demo.onlinepharmacy.Models.Category;
import com.devahmed.demo.onlinepharmacy.Models.Product;
import com.devahmed.demo.onlinepharmacy.Models.SubCategory;
import com.devahmed.demo.onlinepharmacy.Screens.AddProducts.AddProductUseCase.AddProductUseCase;
import com.devahmed.demo.onlinepharmacy.Screens.AddProducts.AddProductUseCase.GetImageUseCase;
import com.devahmed.demo.onlinepharmacy.Screens.Home.UseCases.AddCategoryUseCase;
import com.devahmed.demo.onlinepharmacy.Screens.SubCategories.UseCase.AddSubCategoryUseCase;

import static android.app.Activity.RESULT_OK;

public class AddProductFragment extends BaseFragment implements AddProductMvc.Listener, AddProductUseCase.Listener, AddSubCategoryUseCase.Listener, AddCategoryUseCase.Listener {
    enum MODE  {
        ADD_CATEGORY,
        ADD_SUBCATEGORY,
        ADD_PRODUCT,
        EDIT_CATEGORY,
        EDIT_SUBCATEGORY,
        EDIT_PRODUCT,
    }
    AddProductViewMvcImp mvcImp;
    GetImageUseCase getImageUseCase;
    AddProductUseCase addProductUseCase;
    AddSubCategoryUseCase addSubCategoryUseCase;
    AddCategoryUseCase addCategoryUseCase;
    Uri selectedImage;
    String category , subCategory;
    String FN;
    Category updatedCategory;
    SubCategory updatedSubCategory;
    Product updatedProduct;
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
        //initialize views according to required function and its related data
        mode  = initAddingPage(FN);


        return mvcImp.getRootView();
    }

    private MODE initAddingPage(String function) {
        MODE selectedMode = null;
        if(function.equals("ADD_CATEGORY")){
            selectedMode = MODE.ADD_CATEGORY;
            mvcImp.activateCategoryMode(null);
        }else if(function.equals("ADD_SUBCATEGORY")){
            selectedMode = MODE.ADD_SUBCATEGORY;
            mvcImp.activateSubCategoryMode(null);
            //if we adding sub-category we need to know at which category it is
            category = getArguments().getString("category");
        }else if(function.equals("ADD_PRODUCT")){
            selectedMode = MODE.ADD_PRODUCT;
            mvcImp.activateProductMode(null);
            //if we adding product we need to know at which sub-category it is
            subCategory = getArguments().getString("subCategory");
        }else if(function.equals("EDIT_CATEGORY")){
            selectedMode = MODE.EDIT_CATEGORY;
            String categoryId = getArguments().getString("categoryId");
            String categoryTitle = getArguments().getString("categoryTitle");
            String categoryImage= getArguments().getString("categoryImage");
            updatedCategory = new Category(categoryTitle ,categoryImage);
            updatedCategory.setId(categoryId);
            mvcImp.activateCategoryMode(updatedCategory);
        } else if(function.equals("EDIT_SUBCATEGORY")){
            selectedMode = MODE.EDIT_SUBCATEGORY;
            String subcategoryId = getArguments().getString("subCategoryId");
            String subcategoryTitle = getArguments().getString("subCategoryTitle");
            String subcategoryImage= getArguments().getString("subCategoryImage");
            Boolean subcategoryInOffer= getArguments().getBoolean("subCategoryInOffer");
            String subcategoryCategory = getArguments().getString("subCategoryCategory");
            updatedSubCategory = new SubCategory(subcategoryTitle , subcategoryImage);
            updatedSubCategory.setId(subcategoryId);
            updatedSubCategory.setTitle(subcategoryTitle);
            updatedSubCategory.setInOffer(subcategoryInOffer);
            mvcImp.activateSubCategoryMode(updatedSubCategory);
            updatedSubCategory.setCategory(subcategoryCategory);
            Toast.makeText(requireActivity(), "" + updatedSubCategory.getTitle(), Toast.LENGTH_SHORT).show();
        } else if(function.equals("EDIT_PRODUCT")){
            selectedMode = MODE.EDIT_PRODUCT;
            String productId = getArguments().getString("productId");
            String productImage = getArguments().getString("productImage");
            String productTitle = getArguments().getString("productTitle");
            int productPrice= getArguments().getInt("productPrice");
            updatedProduct = new Product();
            updatedProduct.setId(productId);
            updatedProduct.setImage(productImage);
            updatedProduct.setPrice(productPrice);
            updatedProduct.setTitle(productTitle);
            mvcImp.activateProductMode(null);
        }

        return selectedMode;
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
            addProductUseCase.addNewProduct(mvcImp.getTitle() , mvcImp.getPrice() , selectedImage , subCategory);
        }else if( mode == MODE.ADD_SUBCATEGORY){
            addSubCategoryUseCase.addSubCategoryToFirebase(mvcImp.getTitle()
                    , selectedImage
                    , category
                    , mvcImp.isOfferChecked());
        }else if(mode == MODE.ADD_CATEGORY){
            addCategoryUseCase.addNewCategory(mvcImp.getTitle() , selectedImage);
        } else if(mode == MODE.EDIT_CATEGORY){
            updatedCategory.setTitle(mvcImp.getTitle());
            addCategoryUseCase.updateCategory(updatedCategory , selectedImage);
        } else if(mode == MODE.EDIT_SUBCATEGORY){
            updatedSubCategory.setTitle(mvcImp.getTitle());
            updatedSubCategory.setInOffer(mvcImp.isOfferChecked());
            addSubCategoryUseCase.updateSubCategory(updatedSubCategory ,selectedImage);
        } else if(mode == MODE.EDIT_PRODUCT){
            addProductUseCase.updateExistingProduct(updatedProduct);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        selectedImage = null;
        if(resultCode == RESULT_OK && requestCode == getImageUseCase.GalleryREQUEST_CODE && data != null){
            // The user successfully picked an image
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
    public void onSubCategoryAddedSuccessfully() {
        AddedSuccessfullyAction();
    }

    @Override
    public void onSubCategoryFailedToAdd() {
        failedToAddAction();
    }

    @Override
    public void onSubCategoryInputError(String message) {
        inputErrorAction(message);
    }

    @Override
    public void onCategoryAddedSuccessfully() {
        AddedSuccessfullyAction();
    }

    @Override
    public void onCategoryFailedToAdd() {
        failedToAddAction();
    }

    @Override
    public void onCategoryInputError(String message) {
        inputErrorAction(message);
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