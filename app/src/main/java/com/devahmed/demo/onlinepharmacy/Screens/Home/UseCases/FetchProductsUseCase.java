package com.devahmed.demo.onlinepharmacy.Screens.Home.UseCases;

import androidx.annotation.NonNull;

import com.devahmed.demo.onlinepharmacy.Common.MVC.BaseObservableMvcView;
import com.devahmed.demo.onlinepharmacy.Models.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FetchProductsUseCase extends BaseObservableMvcView<FetchProductsUseCase.Listener> {

    private FirebaseDatabase database;
    private DatabaseReference promotionDatabaseReference , bestSellerDatabaseReference , offersDatabaseReference;
    private List<Product> mPromotionProductsList , mBestSellerProductsList , mOfferProductsList;

    public interface Listener{
        public void onPromotionProductChange(List<Product> productList);
        public void onPromotionProductCancelled(DatabaseError databaseError);
        public void onBestSellerProductChange(List<Product> productList);
        public void onBestSellerProductCancelled(DatabaseError databaseError);
        public void onOfferProductChange(List<Product> productList);
        public void onOfferProductCancelled(DatabaseError databaseError);
    }

    public FetchProductsUseCase(FirebaseDatabase database) {
        this.database = database;
    }

    public void getPromotionProducts(){
        promotionDatabaseReference = database.getReference("Products");
        promotionDatabaseReference.orderByChild("promotioned").equalTo(true).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mPromotionProductsList = new ArrayList<>();
                for (DataSnapshot postSnap: dataSnapshot.getChildren()) {
                    Product model = postSnap.getValue(Product.class);
                    mPromotionProductsList.add(model);
                }
                Collections.reverse(mPromotionProductsList);
                notifyPromotionChange(mPromotionProductsList);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                notifyPromotionCancelled(databaseError);
            }
        });
    }

    public void getBestSellerProducts(){
        bestSellerDatabaseReference = database.getReference("BestSellerProducts");
        bestSellerDatabaseReference.orderByChild("bestSeller").equalTo(true).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mBestSellerProductsList = new ArrayList<>();
                for (DataSnapshot postSnap: dataSnapshot.getChildren()) {
                    Product model = postSnap.getValue(Product.class);
                    mBestSellerProductsList.add(model);
                }
                Collections.reverse(mBestSellerProductsList);
                notifyBestSellerChange(mBestSellerProductsList);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
               notifyBestSellerCancelled(databaseError);
            }
        });
    }

    public void getOffersProducts(){
        offersDatabaseReference = database.getReference("OfferProducts");
        offersDatabaseReference.orderByChild("inOffer").equalTo(true).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mOfferProductsList = new ArrayList<>();
                for (DataSnapshot postSnap: dataSnapshot.getChildren()) {
                    Product model = postSnap.getValue(Product.class);
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


    public void notifyPromotionChange(List<Product> mProductsList){
        for(Listener listener : getmListeners()){
            listener.onPromotionProductChange(mProductsList);
        }
    }
    public void notifyPromotionCancelled(DatabaseError error){
        for(Listener listener : getmListeners()){
            listener.onPromotionProductCancelled(error);
        }
    }

    public void notifyBestSellerChange(List<Product> mProductsList){
        for(Listener listener : getmListeners()){
            listener.onBestSellerProductChange(mProductsList);
        }
    }
    public void notifyBestSellerCancelled(DatabaseError error){
        for(Listener listener : getmListeners()){
            listener.onBestSellerProductCancelled(error);
        }
    }

    public void notifyOffersChange(List<Product> mProductsList){
        for(Listener listener : getmListeners()){
            listener.onOfferProductChange(mProductsList);
        }
    }
    public void notifyOffersCancelled(DatabaseError error){
        for(Listener listener : getmListeners()){
            listener.onOfferProductCancelled(error);
        }
    }

}
