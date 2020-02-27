package com.devahmed.demo.onlinepharmacy.Screens.ProductsShow.ProductsList;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.devahmed.demo.onlinepharmacy.Models.Product;

import java.util.List;

public class ShowProductsAdapter extends RecyclerView.Adapter<ShowProductsAdapter.ViewHolder>
        implements ShowProductListItemView.Listener {

    private OnItemClickListener mListener;

    @Override
    public void onImageClicked(Product product) {
        mListener.onImageClicked(product);
    }

    @Override
    public void onAddToCartBtnClicked(Product product) {
        mListener.onAddToCartBtnClicked(product);
    }


    public interface OnItemClickListener {
        void onImageClicked(Product Product);
        void onAddToCartBtnClicked(Product product);
    }

    private List<Product> ProductsList;

    public ShowProductsAdapter(List<Product> ProductsList, OnItemClickListener listener) {
        this.ProductsList = ProductsList;
        this.mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ShowProductsListItemViewMvcImp mvcImp = new ShowProductsListItemViewMvcImp(LayoutInflater.from(parent.getContext()), parent);
        mvcImp.registerListener(this);
        return new ViewHolder(mvcImp);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Product model = ProductsList.get(position);
        holder.recyclerViewListItemMvcImp.bindData(model);
    }

    @Override
    public int getItemCount() {
        return ProductsList.size();
    }

    public void setList(List<Product> newList) {
        ProductsList = newList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ShowProductsListItemViewMvcImp recyclerViewListItemMvcImp;

        public ViewHolder(ShowProductsListItemViewMvcImp viewMvcImp) {
            super(viewMvcImp.getRootView());
            recyclerViewListItemMvcImp = viewMvcImp;
        }
    }

}