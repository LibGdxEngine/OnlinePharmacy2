package com.devahmed.demo.onlinepharmacy.Screens.SubCategories;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.devahmed.demo.onlinepharmacy.Common.Navigator.Navigator;
import com.devahmed.demo.onlinepharmacy.Common.dependencyInjection.BaseFragment;
import com.devahmed.demo.onlinepharmacy.Models.SubCategory;
import com.devahmed.demo.onlinepharmacy.R;
import com.devahmed.demo.onlinepharmacy.Screens.SubCategories.UseCase.AddSubCategoryUseCase;
import com.devahmed.demo.onlinepharmacy.Screens.SubCategories.UseCase.FetchSubCategories;
import com.google.firebase.database.DatabaseError;

import java.util.List;

public class SubCategoriesFragment extends BaseFragment implements FetchSubCategories.Listener, SubCategoriesMvc.Listener {

    SubCategoryMvcImp mvcImp;
    FetchSubCategories useCase;
    String category;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mvcImp = getCompositionRoot().getMvcFactory().getSubCategoryMvcImp(null);
        category = getArguments().getString("category");
        useCase = new FetchSubCategories(getCompositionRoot().ConnectToFirebase());
        useCase.getSubCategories(category);



        return mvcImp.getRootView();
    }

    @Override
    public void onSubCategorySuccess(List<SubCategory> subCategories) {
        mvcImp.bindSubCategoriesData(subCategories);
    }

    @Override
    public void onSubCategoryCanceled(DatabaseError error) {
        Toast.makeText(requireActivity(), "SubCategories Error  " + error.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSubCategoryClicked(String title) {
        Bundle bundle = new Bundle();
        bundle.putString("category" , title);
        Toast.makeText(requireActivity(), ""+ title, Toast.LENGTH_SHORT).show();
        Navigator.instance(requireActivity()).navigate(R.id.nav_products, bundle);
    }

    @Override
    public void onAddSubCategoryBtnClicked() {
        Bundle bundle = new Bundle();
        bundle.putString("FN" , "ADD_SUBCATEGORY");
        bundle.putString("category" , category);
        Navigator.instance(requireActivity()).navigate(R.id.nav_add_item , bundle);
    }

    @Override
    public void onStart() {
        super.onStart();
        mvcImp.registerListener(this);
        useCase.registerListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        mvcImp.unregisterListener(this);
        useCase.unregisterListener(this);
    }

}
