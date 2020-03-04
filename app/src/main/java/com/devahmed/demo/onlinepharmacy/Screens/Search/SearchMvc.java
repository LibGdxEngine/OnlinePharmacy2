package com.devahmed.demo.onlinepharmacy.Screens.Search;

import com.devahmed.demo.onlinepharmacy.Models.Product;

import java.util.List;

public interface SearchMvc {
    public interface Listener{
       void onQueryTextFocusChangeListener(boolean isFocused);
       void onQueryTextSubmit(String query);
       void onQueryTextChange(String newText);
    }

    void applyQuery(String query);
    void bindData(List<Product> productList);
    void showProgressBar();
    void hideProgressBar();
}
