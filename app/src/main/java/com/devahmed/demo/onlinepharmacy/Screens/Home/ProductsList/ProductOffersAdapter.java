package com.devahmed.demo.onlinepharmacy.Screens.Home.ProductsList;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.devahmed.demo.onlinepharmacy.Models.Product;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.List;

public class ProductOffersAdapter extends SliderViewAdapter<ProductOffersAdapter.ViewHolder> implements ProductListItemView.Listener  {

    private OnOfferItemClickListener mListener;

    @Override
    public int getCount() {
        return ProductsList.size();
    }


    public interface OnOfferItemClickListener {
        void OnItemClicked(Product Product);
    }

    private List<Product> ProductsList;

    public ProductOffersAdapter(List<Product> ProductsList, OnOfferItemClickListener listener) {
        this.ProductsList = ProductsList;
        this.mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        OffersListItemViewMvcImp mvcImp = new OffersListItemViewMvcImp(LayoutInflater.from(parent.getContext()), parent);
        mvcImp.registerListener(this);
        return new ViewHolder(mvcImp);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product model = ProductsList.get(position);
        holder.recyclerViewListItemMvcImp.bindData(model);
    }


    public void setList(List<Product> newList) {
        ProductsList = newList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends SliderViewAdapter.ViewHolder {

        private final OffersListItemViewMvcImp recyclerViewListItemMvcImp;

        public ViewHolder(OffersListItemViewMvcImp viewMvcImp) {
            super(viewMvcImp.getRootView());
            recyclerViewListItemMvcImp = viewMvcImp;
        }
    }


    @Override
    public void onItemClicked(Product model) {
        mListener.OnItemClicked(model);
    }

}