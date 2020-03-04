package com.devahmed.demo.onlinepharmacy.Screens.Cart.CartItem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.devahmed.demo.onlinepharmacy.Models.Product;
import com.devahmed.demo.onlinepharmacy.Screens.ProductsShow.ProductsList.ShowProductsAdapter;
import com.devahmed.demo.onlinepharmacy.Utils.CartManager;

import java.util.List;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.ViewHolder> implements CartItemsMvc.Listener {

    private Listener mListener;
    List<Product> productList;
    private CartManager prodctsIDCartManager , productCountCartManagaer;
    private List<String> cartProductIdList , cartProductCountList;

    public interface Listener{
        void onIncreaseBtnClicked(Product product);
        void onDecreaseBtnCLicked(Product product);
        void onProductImageClicked(Product product);
    }

    public CartListAdapter(List<Product> productList, Listener listener , Context context) {
        this.productList = productList;
        this.mListener = listener;
        prodctsIDCartManager = new CartManager(context , "productsId");
        productCountCartManagaer = new CartManager(context , "productCount");
        cartProductIdList = prodctsIDCartManager.getStoredValues();
        cartProductCountList = productCountCartManagaer.getStoredValues();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CartItemMvcImp mvcImp = new CartItemMvcImp(LayoutInflater.from(parent.getContext()), parent);
        mvcImp.registerListener(this);
        return new ViewHolder(mvcImp);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product model = productList.get(position);
        //if we found this product id in the cart ... we show it in a different way
        if(cartProductIdList.contains(model.getId())){
            int index = cartProductIdList.indexOf(model.getId());
            int counter = Integer.parseInt(cartProductCountList.get(index));
            if( counter > 0) {
                //if the counter is more than 0 then show the number if not => show add to cart btn only
                holder.mvcImp.bindData(model
                        , Integer.valueOf(cartProductCountList.get(index)));
            }
        }
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public void setList(List<Product> newList) {
        productList = newList;
        notifyDataSetChanged();
    }


    @Override
    public void onIncreaseBtnClicked(Product product) {
        //if products is already exist in the cart => just increase its counter
        int index = cartProductIdList.indexOf(product.getId());
        System.out.println("index is " + index);
        int newKey = Integer.parseInt(cartProductCountList.get(index)) + 1;
        System.out.println("newKey key is " + newKey);
        productCountCartManagaer.replaceKey(index , "" + newKey);
        //update the lists of id's with the new data
        cartProductIdList = prodctsIDCartManager.getStoredValues();
        cartProductCountList = productCountCartManagaer.getStoredValues();
        mListener.onIncreaseBtnClicked(product);
    }

    @Override
    public void onDecreaseBtnCLicked(Product product) {
        //if products is already exist in the cart => just increase its counter
        int index = cartProductIdList.indexOf(product.getId());
        System.out.println("index is " + index);
        int newKey = Integer.parseInt(cartProductCountList.get(index)) - 1;
        System.out.println("newKey key is " + newKey);
        productCountCartManagaer.replaceKey(index , "" + newKey);
        //update the lists of id's with the new data
        cartProductIdList = prodctsIDCartManager.getStoredValues();
        cartProductCountList = productCountCartManagaer.getStoredValues();
        mListener.onDecreaseBtnCLicked(product);
    }

    @Override
    public void onProductImageClicked(Product product) {
        mListener.onProductImageClicked(product);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CartItemMvcImp mvcImp;
        public ViewHolder(@NonNull CartItemMvcImp itemView) {
            super(itemView.getRootView());
            mvcImp = itemView;
        }
    }
}
