package com.devahmed.techx.onlineshop.Screens.AddProducts;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;

import com.devahmed.techx.onlineshop.Common.dependencyInjection.BaseFragment;
import com.devahmed.techx.onlineshop.Models.Branch;
import com.devahmed.techx.onlineshop.Models.Category;
import com.devahmed.techx.onlineshop.Models.Product;
import com.devahmed.techx.onlineshop.Models.SubCategory;
import com.devahmed.techx.onlineshop.Screens.AddProducts.AddProductUseCase.AddProductUseCase;
import com.devahmed.techx.onlineshop.Screens.AddProducts.AddProductUseCase.GetImageUseCase;
import com.devahmed.techx.onlineshop.Screens.AdminDashboard.BranchesControl.UseCases.FetchBranchesUseCase;
import com.devahmed.techx.onlineshop.Screens.Home.UseCases.AddCategoryUseCase;
import com.devahmed.techx.onlineshop.Screens.SubCategories.UseCase.AddSubCategoryUseCase;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import static android.app.Activity.RESULT_OK;

public class AddProductFragment extends BaseFragment implements AddProductMvc.Listener, AddProductUseCase.Listener, AddSubCategoryUseCase.Listener, AddCategoryUseCase.Listener, FetchBranchesUseCase.Listener {
    @Override
    public void onBranchDataChange(List<Branch> dataList) {
        mvcImp.bindBranchedData(dataList);
    }

    @Override
    public void onBranchDataCancel(DatabaseError error) {

    }

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
    FetchBranchesUseCase branchesUseCase;
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
        branchesUseCase = new FetchBranchesUseCase(FirebaseDatabase.getInstance());
        branchesUseCase.getAllBranches();
        //get the mode of adding ( category or subcategory or product
        if(getArguments() != null)
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
            String categoryImage = getArguments().getString("categoryImage");
            String categoryTimeStamp = getArguments().getString("categoryTimeStamp");
            updatedCategory = new Category(categoryTitle ,categoryImage);
            updatedCategory.setId(categoryId);
            updatedCategory.setTimeStamp(Long.parseLong(categoryTimeStamp));
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
        } else if(function.equals("EDIT_PRODUCT")){
            selectedMode = MODE.EDIT_PRODUCT;
            String productId = getArguments().getString("productId");
            String productImage = getArguments().getString("productImage");
            String productTitle = getArguments().getString("productTitle");
            double productPrice = getArguments().getDouble("productPrice");
            String productSubCategory = getArguments().getString("productSubCategory");
            String productTimeStamp = getArguments().getString("productTimeStamp");
            int productCount = getArguments().getInt("productCount");
            int productPoints = getArguments().getInt("productPoints");
            String productBranch = getArguments().getString("productBranch");
            updatedProduct = new Product();
            updatedProduct.setPoints(productPoints);
            updatedProduct.setMaxCount(productCount);
            updatedProduct.setBranch(productBranch);
            updatedProduct.setTimeStamp(Long.parseLong(productTimeStamp));
            updatedProduct.setSubCategory(productSubCategory);
            updatedProduct.setId(productId);
            updatedProduct.setImage(productImage);
            updatedProduct.setPrice(productPrice);
            updatedProduct.setTitle(productTitle);
            mvcImp.activateProductMode(updatedProduct);
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
            addProductUseCase.addNewProduct(mvcImp.getTitle() , mvcImp.getPrice() , selectedImage
                    , subCategory , mvcImp.isOfferChecked() ,mvcImp.getCount() , mvcImp.getPoints() , mvcImp.getSelectedBranch());

        }else if( mode == MODE.ADD_SUBCATEGORY){
            addSubCategoryUseCase.addSubCategoryToFirebase(mvcImp.getTitle()
                    , selectedImage
                    , category
                    , mvcImp.isOfferChecked());
        }else if(mode == MODE.ADD_CATEGORY){
            addCategoryUseCase.addNewCategory(mvcImp.getTitle() , selectedImage);
        } else if(mode == MODE.EDIT_CATEGORY){
            updatedCategory.setTitle(mvcImp.getTitle());
            if(selectedImage != null){
                addCategoryUseCase.updateCategory(updatedCategory , selectedImage);
            }else{
                //use the same image like before
                addCategoryUseCase.updateCategoryWithOldImage(updatedCategory);
            }

        } else if(mode == MODE.EDIT_SUBCATEGORY){
            updatedSubCategory.setTitle(mvcImp.getTitle());
            updatedSubCategory.setInOffer(mvcImp.isOfferChecked());
            if(selectedImage != null){
                addSubCategoryUseCase.updateSubCategory(updatedSubCategory ,selectedImage);
            }else{
                addSubCategoryUseCase.updateSubCategoryWithOldImage(updatedSubCategory);
            }

        } else if(mode == MODE.EDIT_PRODUCT){
            updatedProduct.setTitle(mvcImp.getTitle());
            updatedProduct.setPrice(Double.parseDouble(mvcImp.getPrice()));
            updatedProduct.setBranch(mvcImp.getSelectedBranch());
            updatedProduct.setMaxCount(mvcImp.getCount());
            updatedProduct.setPoints(mvcImp.getPoints());
            if(selectedImage != null){
                addProductUseCase.updateProduct(updatedProduct , selectedImage);
            }else{
                addProductUseCase.updateProductWithOldImage(updatedProduct);
            }

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
        branchesUseCase.registerListener(this);

    }

    @Override
    public void onStop() {
        super.onStop();
        mvcImp.unregisterListener(this);
        addProductUseCase.unregisterListener(this);
        addSubCategoryUseCase.unregisterListener(this);
        addCategoryUseCase.unregisterListener(this);
        branchesUseCase.unregisterListener(this);
    }
}