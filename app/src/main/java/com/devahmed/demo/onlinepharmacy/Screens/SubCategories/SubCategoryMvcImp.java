package com.devahmed.demo.onlinepharmacy.Screens.SubCategories;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.devahmed.demo.onlinepharmacy.Common.MVC.BaseObservableMvcView;
import com.devahmed.demo.onlinepharmacy.Models.Category;
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
    private ProgressBar progressBar;

    public SubCategoryMvcImp(LayoutInflater inflater , ViewGroup parent) {
        setRootView(inflater.inflate(R.layout.subcategories_fragment , parent , false));
        progressBar = findViewById(R.id.progressBar);
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
                for(Listener listener : getmListeners()){
                    listener.onSubCategoryLongClicked(subCategories.get(position));
                }
            }
        }));
    }

    public void showCategoriesOptionsDialog(String title , String [] options , final SubCategory category) {
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
                            listener.onChooseSubCategoryEdit(category);
                        }
                        break;
                    case 1:
                        //delete
                        for(Listener listener : getmListeners()){
                            listener.onChooseSubCategoryDelete(category);
                        }
                        break;
                }
            }
        });
        builder.show();
    }

    @Override
    public void bindSubCategoriesData(List<SubCategory> subCategoryList) {
        this.subCategories = subCategoryList;
        subcategoriesAdapter.setList(subCategoryList);
    }

    @Override
    public void showProgressbar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressbar() {
        progressBar.setVisibility(View.GONE);
    }


}
