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
import java.util.ArrayList;
import java.util.List;

public class CartMvcImp extends BaseObservableMvcView<CartMvc.Listener> implements CartMvc, CartListAdapter.Listener {

    private RecyclerView cartRecyclerView;
    private Button checkoutBtn , goToShoppingBtn;
    private TextView totalPriceText , emptyCartText;
    private List<Product> cartProductList;
    private CartListAdapter adapter;
    private int totalPrice = 0;
    private ProgressBar progressBar;
    private ImageView emptyCartPlaceHolder;

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
    }

    @Override
    public void onIncreaseBtnClicked(Product product) {
        totalPrice += product.getPrice();
        totalPriceText.setText("" + totalPrice + "-EP");
    }

    @Override
    public void onDecreaseBtnCLicked(Product product) {
        totalPrice -= product.getPrice();
        totalPriceText.setText("" + totalPrice + "-EP");
    }

    @Override
    public void onProductImageClicked(Product product) {
        Toast.makeText(getContext(), "Image Clicked", Toast.LENGTH_SHORT).show();
    }
}
