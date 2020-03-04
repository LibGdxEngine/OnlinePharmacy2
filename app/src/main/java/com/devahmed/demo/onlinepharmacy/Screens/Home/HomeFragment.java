package com.devahmed.demo.onlinepharmacy.Screens.Home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.devahmed.demo.onlinepharmacy.Common.Navigator.Navigator;
import com.devahmed.demo.onlinepharmacy.Models.Category;
import com.devahmed.demo.onlinepharmacy.Common.dependencyInjection.BaseFragment;
import com.devahmed.demo.onlinepharmacy.Models.SubCategory;
import com.devahmed.demo.onlinepharmacy.R;
import com.devahmed.demo.onlinepharmacy.Screens.Home.UseCases.FetchCategoryUseCase;
import com.google.firebase.database.DatabaseError;

import java.util.List;

public class HomeFragment extends BaseFragment implements FetchCategoryUseCase.Listener  , HomeMvc.Listener{


    private HomeMvcImp mvcImp;
    private FetchCategoryUseCase productsUseCase;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mvcImp = getCompositionRoot().getMvcFactory().getHomeMvc(container);

        productsUseCase = new FetchCategoryUseCase(getCompositionRoot().ConnectToFirebase());
        productsUseCase.getOffers();
        productsUseCase.getCategories();

        return mvcImp.getRootView();
    }


    @Override
    public void onOfferProductChange(List<SubCategory> productList) {
        mvcImp.bindSliderData(productList);
        mvcImp.hideProgressbar();
    }

    @Override
    public void onOfferProductCancelled(DatabaseError databaseError) {
        Toast.makeText(requireActivity()
                , "Error while getting Offers Products " + databaseError.getMessage()
                , Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onOfferCliked(SubCategory subCategory) {
        Toast.makeText(requireContext(), "" + subCategory.getTitle(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onOffersLongClick(SubCategory subCategory) {
        String[] options = {"Edit", "Delete"};
        mvcImp.showOffersOptionsDialog("Choose Option" , options , subCategory);
    }

    @Override
    public void onChooseOfferEdit(SubCategory subCategory) {

    }

    @Override
    public void onChooseOfferDelete(SubCategory subCategory) {
        productsUseCase.deleteOffer(subCategory.getId());
    }

    @Override
    public void onProductsChanged(List<Category> categoryList) {
        mvcImp.bindCategoriesDataData(categoryList);
    }

    @Override
    public void onProductsCancelled(DatabaseError databaseError) {
        Toast.makeText(requireActivity(), "Categoryies Cancelled in Home Fragment " + databaseError, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCategoryClicked(String category) {
        Bundle bundle = new Bundle();
        bundle.putString("category" , category);
        Navigator.instance(requireActivity()).navigate(R.id.subCategoriesFragment , bundle);
    }

    @Override
    public void onCtegoryLongClick(Category category) {
        String[] options = {"Edit", "Delete"};
        mvcImp.showCategoriesOptionsDialog("Choose option" , options , category);
    }


    @Override
    public void onChooseCategoryEdit(Category category) {
        Bundle bundle = new Bundle();
        bundle.putString("FN" , "EDIT_CATEGORY");
        bundle.putString("categoryId" , category.getId());
        bundle.putString("categoryTitle" , category.getTitle());
        bundle.putString("categoryImage" , category.getImage());
        Navigator.instance(requireActivity()).navigate(R.id.nav_add_item,  bundle);
    }

    @Override
    public void onChooseCategoryDelete(Category category) {
        productsUseCase.deleteCategory(category.getId());
    }

    @Override
    public void onAddCategoryBtnClicked() {
        Bundle bundle = new Bundle();
        bundle.putString("FN" , "ADD_CATEGORY");
        Navigator.instance(requireActivity()).navigate(R.id.nav_add_item,  bundle);
    }

    @Override
    public void onSearchbarClicked() {
        Navigator.instance(requireActivity()).navigate(R.id.searchFragment);
    }

    @Override
    public void onStart() {
        super.onStart();
        productsUseCase.registerListener(this);
        mvcImp.registerListener(this);

    }

    @Override
    public void onStop() {
        super.onStop();
        productsUseCase.unregisterListener(this);
        mvcImp.unregisterListener(this);
    }
}