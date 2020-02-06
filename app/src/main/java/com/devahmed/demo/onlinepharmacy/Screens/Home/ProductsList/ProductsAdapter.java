package com.devahmed.demo.onlinepharmacy.Screens.Home.ProductsList;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.devahmed.demo.onlinepharmacy.Models.Product;
import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> implements ProductListItemView.Listener {

    private OnItemClickListener mListener;


    public interface OnItemClickListener {
        void OnItemClicked(Product Product);
    }

    private List<Product> ProductsList;

    public ProductsAdapter(List<Product> ProductsList, OnItemClickListener listener) {
        this.ProductsList = ProductsList;
        this.mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        PromotionListItemViewMvcImp mvcImp = new  PromotionListItemViewMvcImp(LayoutInflater.from(parent.getContext()), parent);

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

        private final PromotionListItemViewMvcImp recyclerViewListItemMvcImp;

        public ViewHolder(PromotionListItemViewMvcImp viewMvcImp) {
            super(viewMvcImp.getRootView());
            recyclerViewListItemMvcImp = viewMvcImp;
        }
    }


    @Override
    public void onItemClicked(Product model) {
        mListener.OnItemClicked(model);
    }

}