package com.devahmed.demo.onlinepharmacy.Screens.ProductsShow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.devahmed.demo.onlinepharmacy.Common.Navigator.Navigator;
import com.devahmed.demo.onlinepharmacy.Common.dependencyInjection.BaseFragment;
import com.devahmed.demo.onlinepharmacy.Models.Product;
import com.devahmed.demo.onlinepharmacy.R;
import com.devahmed.demo.onlinepharmacy.Screens.ProductsShow.UseCases.FetchProductsUseCase;
import com.devahmed.demo.onlinepharmacy.Utils.UtilsDialog;
import com.google.firebase.database.DatabaseError;

import java.util.List;

public class ProducstShowFragment extends BaseFragment
        implements ProductsShowMvc.Listener , FetchProductsUseCase.Listener{

    private ProductsShowMvcImp mvcImp;
    private FetchProductsUseCase fetchProductsUseCase;
    String subCategory;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        mvcImp = getCompositionRoot().getMvcFactory().getShowProductsImp(null);
        subCategory = getArguments().getString("subCategory");
        fetchProductsUseCase = new FetchProductsUseCase(getCompositionRoot().ConnectToFirebase());
        fetchProductsUseCase.getProductsOfCategory(subCategory);

        return mvcImp.getRootView();
    }

    @Override
    public void onProductImageClicked(Product product) {
        UtilsDialog dialog = new UtilsDialog(requireActivity());
        dialog.showFullImageDialog(product.getImage());
    }

    @Override
    public void onAddToCartBtnClicked() {
        Toast.makeText(requireContext(), "Add To Cart clicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAddNewProductBtnClicked() {
        Bundle bundle = new Bundle();
        bundle.putString("FN" , "ADD_PRODUCT");
        bundle.putString("subCategory" , subCategory);
        Navigator.instance(requireActivity()).navigate(R.id.nav_add_item, bundle);
    }

    @Override
    public void onProductLongClicked(Product product) {
        String[] options = {"Edit", "Delete"};
        mvcImp.showProductsOptionsDialog("Choose Option" , options , product);
    }

    @Override
    public void onChooseProductEdit(Product product) {
        Bundle bundle = new Bundle();
        bundle.putString("FN" , "EDIT_PRODUCT");
        bundle.putString("productId" , product.getId());
        bundle.putString("productImage" , product.getImage());
        bundle.putString("productTitle" , product.getTitle());
        bundle.putInt("productPrice" , product.getPrice());
        Navigator.instance(requireActivity()).navigate(R.id.nav_add_item, bundle);
    }

    @Override
    public void onChooseProductDelete(Product product) {
        fetchProductsUseCase.deleteCategory(product.getId());
    }

    @Override
    public void onProductsDataChange(List<Product> products) {
        mvcImp.bindProductsDataData(products);
        mvcImp.hideProgressbar();
    }

    @Override
    public void onProductsDataCancel(DatabaseError error) {
        Toast.makeText(requireActivity(), "" + error.getMessage(), Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onStart() {
        super.onStart();
        mvcImp.registerListener(this);
        fetchProductsUseCase.registerListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        mvcImp.unregisterListener(this);
        fetchProductsUseCase.unregisterListener(this);
    }

}