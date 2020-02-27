package com.devahmed.demo.onlinepharmacy.Screens.ProductsShow.UseCases;

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
    private DatabaseReference databaseReference;
    private List<Product> productList;


    public interface Listener{
        void onProductsDataChange(List<Product> products);
        void onProductsDataCancel(DatabaseError error);
    }

    public FetchProductsUseCase(FirebaseDatabase database) {
        this.database = database;
    }
    //category
    /*
    * category has a name , image and List<SubCategory> subcategories;
    * sub-Category has - name , image and List<Product>
    * each category has some sub-categories
    * which means that
    * */
    public void getProductsOfCategory(String category){
        databaseReference = database.getReference("Products");
        databaseReference.orderByChild("category").equalTo( category ).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                productList = new ArrayList<>();
                for (DataSnapshot postSnap: dataSnapshot.getChildren()) {
                    Product model = postSnap.getValue(Product.class);
                    productList.add(model);
                }
                Collections.reverse(productList);
                notifyDataChange(productList);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                notifyDataCancelled(databaseError);
            }
        });
    }

    private void notifyDataCancelled(DatabaseError databaseError) {
        for(Listener listener : getmListeners()){
            listener.onProductsDataCancel(databaseError);
        }
    }

    private void notifyDataChange(List<Product> productList) {
        for(Listener listener : getmListeners()){
            listener.onProductsDataChange(productList);
        }
    }
}
