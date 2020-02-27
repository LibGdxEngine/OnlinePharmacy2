package com.devahmed.demo.onlinepharmacy.Screens.Home.ProductsList;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.devahmed.demo.onlinepharmacy.Models.SubCategory;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.List;

public class ProductOffersAdapter extends SliderViewAdapter<ProductOffersAdapter.ViewHolder> implements OffersListItemView.Listener  {

    private OnOfferItemClickListener mListener;

    @Override
    public int getCount() {
        return ProductsList.size();
    }


    public interface OnOfferItemClickListener {
        void OnItemClicked(SubCategory category);
        void OnItemLongClicked(SubCategory subCategory);
    }

    private List<SubCategory> ProductsList;

    public ProductOffersAdapter(List<SubCategory> ProductsList, OnOfferItemClickListener listener) {
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
        SubCategory model = ProductsList.get(position);
        holder.recyclerViewListItemMvcImp.bindData(model);
    }


    public void setList(List<SubCategory> newList) {
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
    public void onItemClicked(SubCategory model) {
        mListener.OnItemClicked(model);
    }

    @Override
    public void onItemLongClicked(SubCategory subCategory) {
        mListener.OnItemLongClicked(subCategory);
    }

}