package com.devahmed.techx.foxmart.Screens.SubCategories;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;
import com.devahmed.techx.foxmart.Common.dependencyInjection.BaseFragment;
import com.devahmed.techx.foxmart.Models.SubCategory;
import com.devahmed.techx.foxmart.R;
import com.devahmed.techx.foxmart.Screens.SubCategories.UseCase.FetchSubCategories;
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
    public void onSubCategoryClicked(String title) {
        Bundle bundle = new Bundle();
        bundle.putString("subCategory" , title);
//        Toast.makeText(requireActivity(), ""+ title, Toast.LENGTH_SHORT).show();
        Navigation.findNavController(requireActivity() , R.id.nav_host_fragment).navigate(R.id.nav_products , bundle);
//        Navigator.instance(requireActivity()).navigate(R.id.nav_products, bundle);
    }


    @Override
    public void onAddSubCategoryBtnClicked() {
        Bundle bundle = new Bundle();
        bundle.putString("FN" , "ADD_SUBCATEGORY");
        bundle.putString("category" , category);
        Navigation.findNavController(requireActivity() , R.id.nav_host_fragment).navigate(R.id.nav_add_item , bundle);
//        Navigator.instance(requireActivity()).navigate(R.id.nav_add_item , bundle);
    }

    @Override
    public void onSubCategoryLongClicked(SubCategory subCategory) {
        String[] options = {"Edit", "Delete"};
        mvcImp.showCategoriesOptionsDialog("Choose option" , options , subCategory);
    }

    @Override
    public void onChooseSubCategoryEdit(SubCategory subCategory) {
        Bundle bundle = new Bundle();
        bundle.putString("FN" , "EDIT_SUBCATEGORY");
        bundle.putString("subCategoryId" , subCategory.getId());
        bundle.putString("subCategoryTitle" , subCategory.getTitle());
        bundle.putString("subCategoryImage" , subCategory.getImage());
        bundle.putString("subCategoryCategory" , subCategory.getCategory());
        bundle.putBoolean("subCategoryInOffer" , subCategory.isInOffer());
        Navigation.findNavController(requireActivity() , R.id.nav_host_fragment).navigate(R.id.nav_add_item ,bundle);
        //Navigator.instance(requireActivity()).navigate(R.id.nav_add_item,  bundle);
    }

    @Override
    public void onChooseSubCategoryDelete(SubCategory category) {
        useCase.deleteCategory(category.getId());
        Toast.makeText(requireContext(), "Deleted", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSubCategorySuccess(List<SubCategory> subCategories) {
        mvcImp.bindSubCategoriesData(subCategories);
        mvcImp.hideProgressbar();
    }

    @Override
    public void onSubCategoryCanceled(DatabaseError error) {
        Toast.makeText(requireActivity(), "SubCategories Error  " + error.getMessage(), Toast.LENGTH_SHORT).show();
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