package com.devahmed.demo.onlinepharmacy.Screens.Home;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.devahmed.demo.onlinepharmacy.Common.MVC.BaseObservableMvcView;
import com.devahmed.demo.onlinepharmacy.Models.Product;
import com.devahmed.demo.onlinepharmacy.R;
import com.devahmed.demo.onlinepharmacy.Screens.Home.ProductsList.ProductOffersAdapter;
import com.devahmed.demo.onlinepharmacy.Screens.Home.ProductsList.ProductsAdapter;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

public class HomeMvcImp extends BaseObservableMvcView<HomeMvc.Listener> implements HomeMvc, ProductsAdapter.OnItemClickListener, ProductOffersAdapter.OnOfferItemClickListener {

    private SliderView sliderView;
    private RecyclerView promotionProductsRecyclerView , bestSelletRecyclerView , categoriesRecycler;
    private ProductsAdapter promotionAdapter , bestSellerAdapter;
    private List<Product> promotionsList , bestSellerList , offersProducstList;
    private ProductOffersAdapter offersAdapter;


    public HomeMvcImp(LayoutInflater inflater , ViewGroup parent) {

        setRootView(inflater.inflate(R.layout.fragment_home , parent , false));
        //SliderView that shows offers
        sliderView = findViewById(R.id.sliderView);
        offersProducstList = new ArrayList<>();
        offersAdapter = new ProductOffersAdapter(offersProducstList , this);
        sliderView.setSliderAdapter(offersAdapter);
        sliderView.startAutoCycle();
        sliderView.setScrollTimeInSec(6);
        sliderView.setIndicatorAnimation(IndicatorAnimations.SLIDE);
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        //promotion recyclerView
        promotionProductsRecyclerView = findViewById(R.id.promotionProductsRecyclerView);
        promotionsList = new ArrayList<>();
        promotionAdapter = new ProductsAdapter(promotionsList , this);
        promotionProductsRecyclerView.setHasFixedSize(true);
        promotionProductsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()  , LinearLayoutManager.HORIZONTAL , false));
        promotionProductsRecyclerView.setAdapter(promotionAdapter);
        //bestSeller RecyclerView
        bestSelletRecyclerView = findViewById(R.id.bestSelletRecyclerView);
        bestSellerList = new ArrayList<>();
        bestSellerAdapter = new ProductsAdapter(bestSellerList , this);
        bestSelletRecyclerView.setHasFixedSize(true);
        bestSelletRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()  , LinearLayoutManager.HORIZONTAL , false));
        bestSelletRecyclerView.setAdapter(bestSellerAdapter);
        //Categories RecyclerView
        categoriesRecycler = findViewById(R.id.categoriesRecycler);
    }


    @Override
    public void bindSliderData(List<Product> offersProducstList) {
        offersAdapter.setList(offersProducstList);
    }

    @Override
    public void bindCategoriesDataData() {

    }

    @Override
    public void bindPromotionProductsData(List<Product> productList) {
        promotionAdapter.setList(productList);
    }

    @Override
    public void bindBestSellerProductsData(List<Product> productsList) {
        bestSellerAdapter.setList(productsList);
    }

    @Override
    public void OnItemClicked(Product product) {
        for(Listener listener : getmListeners()){
            listener.onProductClicked(product);
        }
    }
}
