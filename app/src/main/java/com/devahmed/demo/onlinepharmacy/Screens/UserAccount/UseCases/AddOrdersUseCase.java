package com.devahmed.demo.onlinepharmacy.Screens.UserAccount.UseCases;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.devahmed.demo.onlinepharmacy.Common.MVC.BaseObservableMvcView;
import com.devahmed.demo.onlinepharmacy.Models.Order;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AddOrdersUseCase extends BaseObservableMvcView<AddOrdersUseCase.Listener> {

    private final Activity context;

    private final String FIREBASE_PATH = "Orders";

    public interface Listener {
        void onOrderAddedSuccessfully();
        void onOrderFailedToAdd();
        void onOrderInputError(String message);
        void onOrderDataUpdated(Order order);
    }

    public AddOrdersUseCase(Activity context) {
        this.context = context;
    }

    public void addNewOrder(Order order){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(FIREBASE_PATH).push();
        //get post unique ID & update post key
        String key = myRef.getKey();
        order.setId(key);
        //add post data to firebase database
        myRef.setValue(order).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                notifySuccess();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                notifyFailure();
            }
        });

    }


    public void updateExistingOrder(Order order){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference(FIREBASE_PATH);
        Map<String, Object> postValues = order.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put(order.getId(), postValues);
        reference.updateChildren(childUpdates);
        notifyOrderUpdated(order);
    }

    private void notifyFailure() {
        for(Listener listener : getmListeners()){
            listener.onOrderFailedToAdd();
        }
    }

    private void notifySuccess(){
        for(Listener listener : getmListeners()){
            listener.onOrderAddedSuccessfully();
        }
    }

    private void notifyInputError(String message){
        for(Listener listener : getmListeners()){
            listener.onOrderInputError(message);
        }
    }
    private void notifyOrderUpdated(Order order){
        for(Listener listener : getmListeners()){
            listener.onOrderDataUpdated(order);
        }
    }

}
