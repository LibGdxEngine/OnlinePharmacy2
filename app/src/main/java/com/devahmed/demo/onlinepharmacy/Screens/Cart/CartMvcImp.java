package com.devahmed.demo.onlinepharmacy.Screens.Cart;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.devahmed.demo.onlinepharmacy.Common.MVC.BaseObservableMvcView;
import com.devahmed.demo.onlinepharmacy.Models.Product;
import com.devahmed.demo.onlinepharmacy.R;
import com.devahmed.demo.onlinepharmacy.Screens.Cart.CartItem.CartListAdapter;
import com.devahmed.demo.onlinepharmacy.Utils.UtilsDialog;

import java.util.ArrayList;
import java.util.List;

public class CartMvcImp extends BaseObservableMvcView<CartMvc.Listener> implements CartMvc, CartListAdapter.Listener {

    private RecyclerView cartRecyclerView;
    private Button checkoutBtn , goToShoppingBtn;
    private TextView totalPriceText , emptyCartText , deliveryCostText;
    private List<Product> cartProductList;
    private List<String> cartProductsCountList;
    private CartListAdapter adapter;
    private ProgressBar progressBar;
    private ImageView emptyCartPlaceHolder;
    private int totalPrice = 0;
    private int deliveryCost = 5;

    public CartMvcImp(LayoutInflater inflater , ViewGroup group) {
        setRootView(inflater.inflate(R.layout.cart_fragment , group , false));

        initViews();

        checkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(Listener listener : getmListeners()){
                    listener.onSubmitBtnClicked();
                }
            }
        });
        goToShoppingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(Listener listener : getmListeners()){
                    listener.onGoToShoppingBtnClicked();
                }
            }
        });
    }

    private void initViews() {
        cartRecyclerView = findViewById(R.id.cartRecycler);
        checkoutBtn = findViewById(R.id.cartCheckoutBtn);
        totalPriceText = findViewById(R.id.cartTotalPriceText);
        progressBar = findViewById(R.id.progressBar);
        deliveryCostText = findViewById(R.id.deliveryCostText);
        emptyCartText = findViewById(R.id.emptyCartText);
        emptyCartPlaceHolder = findViewById(R.id.emptycartplaceholder);
        goToShoppingBtn = findViewById(R.id.cartGoToShoppingBtn);
        cartProductList = new ArrayList<>();
        adapter = new CartListAdapter(cartProductList , this , getContext());
        cartRecyclerView.setHasFixedSize(true);
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        cartRecyclerView.setAdapter(adapter);
    }

    @Override
    public void bindCartData(List<Product> productList ,  List<String> productsCount) {
        totalPrice = deliveryCost;
        this.cartProductList = productList;
        this.cartProductsCountList = productsCount;
        adapter.setList(productList);
        for(int i = 0; i < productList.size(); i++){
            totalPrice += productList.get(i).getPrice() * Integer.parseInt(productsCount.get(i));
        }
        totalPriceText.setText("" + totalPrice + "-EP");
        if(productList.isEmpty()){
            activateEmptyCartState();
        }
    }

    @Override
    public void showProgressbar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressbar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void activateEmptyCartState() {
        emptyCartPlaceHolder.setVisibility(View.VISIBLE);
        goToShoppingBtn.setVisibility(View.VISIBLE);
        emptyCartText.setVisibility(View.VISIBLE);
        deliveryCostText.setVisibility(View.GONE);
        checkoutBtn.setEnabled(false);
        totalPriceText.setText("0-EP");
    }

    @Override
    public void onIncreaseBtnClicked(Product product) {
        totalPrice += product.getPrice();
        totalPriceText.setText("" + totalPrice + "-EP");
        int index = cartProductList.indexOf(product);
        int newValue = Integer.parseInt(cartProductsCountList.get(index)) + 1;
        cartProductsCountList.set(index , String.valueOf(newValue));
    }

    @Override
    public void onDecreaseBtnCLicked(Product product) {
        totalPrice -= product.getPrice();
        totalPriceText.setText("" + totalPrice + "-EP");
        int index = cartProductList.indexOf(product);
        int newValue = Integer.parseInt(cartProductsCountList.get(index)) - 1;
        cartProductsCountList.set(index , String.valueOf(newValue));
        if(totalPrice == deliveryCost){
            activateEmptyCartState();
        }
    }

    @Override
    public void onProductImageClicked(Product product) {
        for(Listener listener : getmListeners()){
            listener.onCartItemImageClicked(product);
        }
    }

    @Override
    public int getTotalPrice() {
        return totalPrice;
    }

    @Override
    public boolean isCartEmpty() {
        if(totalPrice == deliveryCost){
            return true;
        }
        return false;
    }
}
