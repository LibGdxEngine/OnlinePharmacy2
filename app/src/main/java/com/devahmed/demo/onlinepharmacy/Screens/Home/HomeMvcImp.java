package com.devahmed.demo.onlinepharmacy.Screens.Home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.devahmed.demo.onlinepharmacy.Common.MVC.BaseObservableMvcView;
import com.devahmed.demo.onlinepharmacy.Models.Category;
import com.devahmed.demo.onlinepharmacy.Models.SubCategory;
import com.devahmed.demo.onlinepharmacy.R;
import com.devahmed.demo.onlinepharmacy.Screens.Home.ProductsList.ProductOffersAdapter;
import com.devahmed.demo.onlinepharmacy.Screens.Home.categoryList.CategoryAdapter;
import com.devahmed.demo.onlinepharmacy.Screens.Home.categoryList.RecyclerTouchListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

public class HomeMvcImp extends BaseObservableMvcView<HomeMvc.Listener> implements
        HomeMvc, ProductOffersAdapter.OnOfferItemClickListener {

    private SliderView sliderView;
    private RecyclerView categoriesRecycler;
    private List<SubCategory>  offersProducstList;
    private ProductOffersAdapter offersAdapter;
    private CategoryAdapter categoryAdapter;
    private List<Category> categoryList;
    private FloatingActionButton addCategoryBtn;
    private ProgressBar progressBar;
    private EditText searchbarEditText;
    public HomeMvcImp(LayoutInflater inflater , ViewGroup parent) {

        setRootView(inflater.inflate(R.layout.fragment_home_classic , parent , false));
        addCategoryBtn = findViewById(R.id.addCategoryBtn);
        addCategoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(Listener listener : getmListeners()){
                    listener.onAddCategoryBtnClicked();
                }
            }
        });
        searchbarEditText = findViewById(R.id.searchbar);
        searchbarEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(Listener listener : getmListeners()){
                    listener.onSearchbarClicked();
                }
            }
        });
        //ProgressBar
        progressBar = findViewById(R.id.progressBar);
        //SliderView that shows offers
        sliderView = findViewById(R.id.sliderView);
        offersProducstList = new ArrayList<>();
        offersAdapter = new ProductOffersAdapter(offersProducstList , this);
        sliderView.setSliderAdapter(offersAdapter);
        sliderView.startAutoCycle();
        sliderView.setScrollTimeInSec(6);
        sliderView.setIndicatorAnimation(IndicatorAnimations.SLIDE);
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        //Categories RecyclerView
        categoriesRecycler = findViewById(R.id.categoriesRecycler);
        categoryList = new ArrayList<>();
        categoryAdapter = new CategoryAdapter(categoryList);
        categoriesRecycler.setLayoutManager(new GridLayoutManager(getContext() , 2));
        categoriesRecycler.setHasFixedSize(true);
        categoriesRecycler.setAdapter(categoryAdapter);

        categoriesRecycler.addOnItemTouchListener( new RecyclerTouchListener(getContext(), categoriesRecycler, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                String category = categoryList.get(position).getId();
                for(Listener listener : getmListeners()){
                    listener.onCategoryClicked(category);
                }
            }

            @Override
            public void onLongClick(View view, int position) {
                for(Listener listener : getmListeners()){
                    listener.onCtegoryLongClick(categoryList.get(position));
                }
            }
        }));
    }

    public void showCategoriesOptionsDialog(String title , String [] options , final Category category) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(title);
        builder.setCancelable(true);
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int index) {
                // the user clicked on colors[which]
                switch (index){
                    case 0:
                        //edit
                        for(Listener listener : getmListeners()){
                            listener.onChooseCategoryEdit(category);
                        }
                        break;
                    case 1:
                        //delete
                        for(Listener listener : getmListeners()){
                            listener.onChooseCategoryDelete(category);
                        }
                        break;
                }
            }
        });
        builder.show();
    }

    public void showOffersOptionsDialog(String title , String [] options , final SubCategory subCategory) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(title);
        builder.setCancelable(true);
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int index) {
                // the user clicked on colors[which]
                switch (index){
                    case 0:
                        //edit
                        for(Listener listener : getmListeners()){
                            listener.onChooseOfferEdit(subCategory);
                        }
                        break;
                    case 1:
                        //delete
                        for(Listener listener : getmListeners()){
                            listener.onChooseOfferDelete(subCategory);
                        }
                        break;
                }
            }
        });
        builder.show();
    }

    @Override
    public void bindSliderData(List<SubCategory> offersProducstList) {
        this.offersProducstList = offersProducstList;
        offersAdapter.setList(offersProducstList);
    }

    @Override
    public void bindCategoriesDataData(List<Category> categoryList) {
        this.categoryList = categoryList;
        categoryAdapter.setList(categoryList);
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
    public void OnItemClicked(SubCategory category) {
        for(Listener listener : getmListeners()){
            listener.onOfferCliked(category);
        }
    }

    @Override
    public void OnItemLongClicked(SubCategory subCategory) {
        for(Listener listener : getmListeners()){
            listener.onOffersLongClick(subCategory);
        }
    }
}
