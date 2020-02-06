package com.devahmed.demo.onlinepharmacy.Screens.Home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.devahmed.demo.onlinepharmacy.Models.Product;
import com.devahmed.demo.onlinepharmacy.Common.dependencyInjection.BaseFragment;
import com.devahmed.demo.onlinepharmacy.Screens.Home.UseCases.FetchProductsUseCase;
import com.google.firebase.database.DatabaseError;

import java.util.List;

public class HomeFragment extends BaseFragment implements FetchProductsUseCase.Listener  , HomeMvc.Listener{


    private HomeMvcImp mvcImp;
    private FetchProductsUseCase productsUseCase;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mvcImp = getCompositionRoot().getMvcFactory().getHomeMvc(container);

        productsUseCase = new FetchProductsUseCase(getCompositionRoot().ConnectToFirebase());
        productsUseCase.getPromotionProducts();
        productsUseCase.getOffersProducts();
        productsUseCase.getBestSellerProducts();


        return mvcImp.getRootView();
    }


    @Override
    public void onPromotionProductChange(List<Product> productList) {
        mvcImp.bindPromotionProductsData(productList);
    }

    @Override
    public void onPromotionProductCancelled(DatabaseError databaseError) {
        Toast.makeText(requireActivity()
                , "Error while getting Promotion Products " + databaseError.getMessage()
                , Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBestSellerProductChange(List<Product> productList) {
        mvcImp.bindBestSellerProductsData(productList);
    }

    @Override
    public void onBestSellerProductCancelled(DatabaseError databaseError) {
        Toast.makeText(requireActivity()
                , "Error while getting Bestseller Products " + databaseError.getMessage()
                , Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onOfferProductChange(List<Product> productList) {
        mvcImp.bindSliderData(productList);
    }

    @Override
    public void onOfferProductCancelled(DatabaseError databaseError) {
        Toast.makeText(requireActivity()
                , "Error while getting Offers Products " + databaseError.getMessage()
                , Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onCategoryClicked() {

    }

    @Override
    public void onProductClicked(Product product) {
        Toast.makeText(requireActivity(), "yeah " + product.getTitle() , Toast.LENGTH_SHORT).show();
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