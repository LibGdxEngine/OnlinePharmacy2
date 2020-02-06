package com.devahmed.demo.onlinepharmacy.Screens.ProductsShow;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.devahmed.demo.onlinepharmacy.Common.MVC.BaseObservableMvcView;
import com.devahmed.demo.onlinepharmacy.Models.Product;
import com.devahmed.demo.onlinepharmacy.R;
import com.devahmed.demo.onlinepharmacy.Screens.Home.ProductsList.ProductsAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ProductsShowMvcImp extends BaseObservableMvcView<ProductsShowMvc.Listener> implements ProductsShowMvc, ProductsAdapter.OnItemClickListener {

    private FloatingActionButton addNewProductBtn;
    private RecyclerView showProducstRecyclerView;
    private ProductsAdapter productsAdapter;
    private List<Product> mProductsList;
    public ProductsShowMvcImp(LayoutInflater inflater , ViewGroup parent) {

        setRootView(inflater.inflate(R.layout.fragment_show_products , parent , false));
        addNewProductBtn = findViewById(R.id.addNewProductBtn);
        showProducstRecyclerView = findViewById(R.id.showProductsRecycler);
        showProducstRecyclerView.setLayoutManager(new GridLayoutManager(getContext() , 2));
        showProducstRecyclerView.setHasFixedSize(true);
        mProductsList = new ArrayList<>();
        productsAdapter = new ProductsAdapter(mProductsList , this);
        showProducstRecyclerView.setAdapter(productsAdapter);

        addNewProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(Listener listener : getmListeners()){
                    listener.onAddNewProductBtnClicked();
                }
            }
        });

    }

    @Override
    public void bindProductsDataData(List<Product> productList) {
        productsAdapter.setList(productList);
    }

    @Override
    public void OnItemClicked(Product product) {
        Toast.makeText(getContext(), "" + product.getTitle() , Toast.LENGTH_SHORT).show();
    }
}
