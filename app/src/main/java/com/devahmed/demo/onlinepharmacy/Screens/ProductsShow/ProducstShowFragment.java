package com.devahmed.demo.onlinepharmacy.Screens.ProductsShow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.devahmed.demo.onlinepharmacy.Common.Navigator.Navigator;
import com.devahmed.demo.onlinepharmacy.Common.dependencyInjection.BaseFragment;
import com.devahmed.demo.onlinepharmacy.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ProducstShowFragment extends BaseFragment implements ProductsShowMvc.Listener {

    ProductsShowMvcImp mvcImp;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mvcImp = getCompositionRoot().getMvcFactory().getShowProductsImp(null);
        String data  = getArguments().getString("category");


        return mvcImp.getRootView();
    }

    @Override
    public void onStart() {
        super.onStart();
        mvcImp.registerListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        mvcImp.unregisterListener(this);
    }

    @Override
    public void onProductItemClicked() {
        Toast.makeText(requireContext(), "Item clicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAddNewProductBtnClicked() {
        Navigator.instance(requireActivity()).navigate(R.id.nav_diabetes);
    }
}