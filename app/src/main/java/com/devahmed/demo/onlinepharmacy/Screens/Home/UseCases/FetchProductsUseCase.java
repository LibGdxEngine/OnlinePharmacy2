package com.devahmed.demo.onlinepharmacy.Screens.Home.UseCases;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.devahmed.demo.onlinepharmacy.Common.MVC.BaseObservableMvcView;
import com.devahmed.demo.onlinepharmacy.Models.Category;
import com.devahmed.demo.onlinepharmacy.Models.Product;
import com.devahmed.demo.onlinepharmacy.Models.SubCategory;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.security.auth.Subject;

public class FetchProductsUseCase extends BaseObservableMvcView<FetchProductsUseCase.Listener> {

    private FirebaseDatabase database;
    private DatabaseReference  offersDatabaseReference , cateegoriesDatabaseReference;
    private List<SubCategory>  mOfferProductsList;
    private List<Category> mCategoriesList;

    public interface Listener{
        public void onOfferProductChange(List<SubCategory> productList);
        public void onOfferProductCancelled(DatabaseError databaseError);
        public void onCategoryChange(List<Category> categoryList);
        public void onCategoryCancelled(DatabaseError databaseError);
    }

    public FetchProductsUseCase(FirebaseDatabase database) {
        this.database = database;
    }


    public void getCategories(){
        cateegoriesDatabaseReference = database.getReference("Categories");
        cateegoriesDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mCategoriesList = new ArrayList<>();
                for (DataSnapshot postSnap: dataSnapshot.getChildren()) {
                    Category model = postSnap.getValue(Category.class);
                    System.out.println("here id is  " + model.getId());
                    mCategoriesList.add(model);
                }
                Collections.reverse(mCategoriesList);
                notifyCategoryChange(mCategoriesList);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                notifyCategoryCancelled(databaseError);
            }
        });

    }


    public void getOffers(){
        offersDatabaseReference = database.getReference("Sub-Categories");
        offersDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mOfferProductsList = new ArrayList<>();
                for (DataSnapshot postSnap: dataSnapshot.getChildren()) {
                    SubCategory model = postSnap.getValue(SubCategory.class);
                    mOfferProductsList.add(model);
                }
                Collections.reverse(mOfferProductsList);
                notifyOffersChange(mOfferProductsList);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                notifyOffersCancelled(databaseError);
            }
        });
    }

    public void deleteOffer(String offerID){
        offersDatabaseReference = database.getReference("Sub-Categories");
        offersDatabaseReference.child(offerID).removeValue();
    }

    public void notifyOffersChange(List<SubCategory> mProductsList){
        for(Listener listener : getmListeners()){
            listener.onOfferProductChange(mProductsList);
        }
    }
    public void notifyOffersCancelled(DatabaseError error){
        for(Listener listener : getmListeners()){
            listener.onOfferProductCancelled(error);
        }
    }

    public void notifyCategoryChange(List<Category> mCategoriesList){
        for(Listener listener : getmListeners()){
            listener.onCategoryChange(mCategoriesList);
        }
    }
    public void notifyCategoryCancelled(DatabaseError error){
        for(Listener listener : getmListeners()){
            listener.onCategoryCancelled(error);
        }
    }
}
