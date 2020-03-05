package com.devahmed.demo.onlinepharmacy.Screens.Cart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.devahmed.demo.onlinepharmacy.Common.Navigator.Navigator;
import com.devahmed.demo.onlinepharmacy.Models.Product;
import com.devahmed.demo.onlinepharmacy.R;
import com.devahmed.demo.onlinepharmacy.Screens.Cart.CartItem.CartItemsMvc;
import com.devahmed.demo.onlinepharmacy.Screens.Cart.UseCase.FetchCartDataUseCase;
import com.devahmed.demo.onlinepharmacy.Utils.UtilsDialog;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class CartFragment extends Fragment implements CartMvc.Listener, FetchCartDataUseCase.Listener{

    CartMvcImp mvcImp;
    FetchCartDataUseCase cartDataUseCase;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        mvcImp = new CartMvcImp(getLayoutInflater() , null);
        cartDataUseCase = new FetchCartDataUseCase(requireActivity() , FirebaseDatabase.getInstance());


        return mvcImp.getRootView();
    }

    @Override
    public void onSubmitBtnClicked() {
        Toast.makeText(requireContext(), "Checkout clicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGoToShoppingBtnClicked() {
        Navigator.instance(requireActivity()).navigate(R.id.nav_home);
    }

    @Override
    public void onCartItemImageClicked(Product product) {
        UtilsDialog dialog = new UtilsDialog(requireActivity());
        dialog.showFullImageDialog(product.getImage());
    }

    @Override
    public void onProductsChanged(List<Product> productList , List<String> productsCountList) {
        mvcImp.bindCartData(productList , productsCountList);
        mvcImp.hideProgressbar();
    }

    @Override
    public void onProductsCanceled(DatabaseError error) {
        Toast.makeText(requireContext(), "CartFragment " + error.getMessage(), Toast.LENGTH_SHORT).show();
        mvcImp.hideProgressbar();
    }

    @Override
    public void onStart() {
        super.onStart();
        mvcImp.registerListener(this);
        cartDataUseCase.registerListener(this);
        cartDataUseCase.getProductsAtCart();
        mvcImp.showProgressbar();
    }


    @Override
    public void onStop() {
        super.onStop();
        mvcImp.unregisterListener(this);
        cartDataUseCase.unregisterListener(this);
    }

}
