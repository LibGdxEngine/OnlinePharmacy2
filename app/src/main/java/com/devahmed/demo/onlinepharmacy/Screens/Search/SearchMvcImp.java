package com.devahmed.demo.onlinepharmacy.Screens.Search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;

import com.devahmed.demo.onlinepharmacy.Common.MVC.BaseObservableMvcView;
import com.devahmed.demo.onlinepharmacy.Models.Product;
import com.devahmed.demo.onlinepharmacy.R;
import com.devahmed.demo.onlinepharmacy.Screens.ProductsShow.ProductsList.ShowProductsAdapter;

import java.util.ArrayList;
import java.util.List;

public class SearchMvcImp extends BaseObservableMvcView<SearchMvc.Listener> implements SearchMvc, ShowProductsAdapter.OnItemClickListener {

    private SearchView searchView;
    private List<Product> productList , tempSearchList;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private ShowProductsAdapter adapter;

    public SearchMvcImp(LayoutInflater inflater , ViewGroup parent) {
        setRootView(inflater.inflate(R.layout.search_fragment , parent , false));
        initViews();
        searchView.setFocusable(true);
        searchView.setIconifiedByDefault(false);
        searchView.requestFocus();
        searchView.requestFocusFromTouch();
        searchView.onActionViewExpanded();
        searchView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                for(Listener listener : getmListeners()){
                    listener.onQueryTextFocusChangeListener(hasFocus);
                }
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                for(Listener listener : getmListeners()){
                    listener.onQueryTextSubmit(query);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                for(Listener listener : getmListeners()){
                    listener.onQueryTextChange(newText);
                }
                return false;
            }
        });
    }

    private void initViews() {
        searchView = findViewById(R.id.searchView);
        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.searchRecycler);
        adapter = new ShowProductsAdapter(tempSearchList , this , getContext());
        productList = new ArrayList<>();
        tempSearchList = new ArrayList<>();
    }

    @Override
    public void applyQuery(String query) {
        query = query.toLowerCase();
        tempSearchList = new ArrayList<>();
        for(Product product : productList){
            if(product.getTitle().toLowerCase().contains(query)){
                tempSearchList.add(product);
            }
        }
        adapter.setList(tempSearchList);
    }

    @Override
    public void bindData(List<Product> productList) {
        this.productList = productList;
        this.tempSearchList = productList;
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onImageClicked(Product Product) {

    }

    @Override
    public void onAddToCartBtnClicked(Product product) {

    }

    @Override
    public void onImageLongClicked(Product product) {

    }

    @Override
    public void onIncreaseItemsBtnClicked() {

    }

    @Override
    public void onDecreaseItemsBtnClicked() {

    }
}
