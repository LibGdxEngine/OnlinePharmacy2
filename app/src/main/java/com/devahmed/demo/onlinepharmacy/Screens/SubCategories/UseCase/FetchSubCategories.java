package com.devahmed.demo.onlinepharmacy.Screens.SubCategories.UseCase;

import androidx.annotation.NonNull;

import com.devahmed.demo.onlinepharmacy.Common.MVC.BaseObservableMvcView;
import com.devahmed.demo.onlinepharmacy.Models.Category;
import com.devahmed.demo.onlinepharmacy.Models.SubCategory;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FetchSubCategories extends BaseObservableMvcView<FetchSubCategories.Listener> {
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private List<SubCategory>  subCategoryList;

    public interface Listener{
        void onSubCategorySuccess(List<SubCategory> subCategories);
        void onSubCategoryCanceled(DatabaseError error);
    }

    public FetchSubCategories(FirebaseDatabase database) {
        this.database = database;
    }

    public void getSubCategories(String category){
        databaseReference = database.getReference("Sub-Categories");
        databaseReference.orderByChild("category").equalTo(category).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                subCategoryList = new ArrayList<>();
                for (DataSnapshot postSnap: dataSnapshot.getChildren()) {
                    SubCategory model = postSnap.getValue(SubCategory.class);
                    subCategoryList.add(model);
                }
                Collections.reverse(subCategoryList);
                notifyCategoryChange(subCategoryList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                notifyCategoryCancelled(databaseError);
            }
        });
    }



    private void notifyCategoryChange(List<SubCategory> subCategoryList) {
        for(Listener listener : getmListeners()){
            listener.onSubCategorySuccess(subCategoryList);
        }
    }

    private void notifyCategoryCancelled(DatabaseError error) {
        for(Listener listener : getmListeners()){
            listener.onSubCategoryCanceled(error);
        }
    }
}
