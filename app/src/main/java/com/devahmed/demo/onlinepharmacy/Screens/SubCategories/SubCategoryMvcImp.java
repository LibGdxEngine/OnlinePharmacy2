package com.devahmed.demo.onlinepharmacy.Screens.SubCategories;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.devahmed.demo.onlinepharmacy.Common.MVC.BaseObservableMvcView;
import com.devahmed.demo.onlinepharmacy.Models.SubCategory;
import com.devahmed.demo.onlinepharmacy.R;
import com.devahmed.demo.onlinepharmacy.Screens.Home.categoryList.RecyclerTouchListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class SubCategoryMvcImp extends BaseObservableMvcView<SubCategoriesMvc.Listener> implements SubCategoriesMvc {

    private RecyclerView subRecyclerView;
    private List<SubCategory>  subCategories;
    private SubcategoriesAdapter subcategoriesAdapter;
    private FloatingActionButton addSubCategoryBtn;

    public SubCategoryMvcImp(LayoutInflater inflater , ViewGroup parent) {
        setRootView(inflater.inflate(R.layout.subcategories_fragment , parent , false));
        addSubCategoryBtn = findViewById(R.id.addSubCategoryBtn);
        addSubCategoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(Listener listener : getmListeners()){
                    listener.onAddSubCategoryBtnClicked();
                }
            }
        });
        subRecyclerView = findViewById(R.id.subcategoryRecycler);
        subRecyclerView.setHasFixedSize(true);
        subRecyclerView.setLayoutManager(new GridLayoutManager(getContext() , 1));
        subCategories = new ArrayList<>();
        subcategoriesAdapter = new SubcategoriesAdapter(subCategories);
        subRecyclerView.setAdapter(subcategoriesAdapter);
        subRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), subRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                String title = subCategories.get(position).getTitle();
                for(Listener listener : getmListeners()){
                    listener.onSubCategoryClicked(title);
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }


    @Override
    public void bindSubCategoriesData(List<SubCategory> subCategoryList) {
        this.subCategories = subCategoryList;
        subcategoriesAdapter.setList(subCategoryList);
    }


}
