package com.devahmed.demo.onlinepharmacy.Screens.Cart.UseCase;

import androidx.annotation.NonNull;

import com.devahmed.demo.onlinepharmacy.Common.MVC.BaseObservableMvcView;
import com.devahmed.demo.onlinepharmacy.Models.DeliverCost;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FetchDeliveryCostesUseCase extends BaseObservableMvcView<FetchDeliveryCostesUseCase.Listener> {
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private List<DeliverCost> productList;

    private final String FIREBASE_PATH = "DeliveryCosts";

    public interface Listener {
        void onDeliveryCostDataChange(List<DeliverCost> dataList);

        void onDeliveryCostDataCancel(DatabaseError error);
    }

    public FetchDeliveryCostesUseCase(FirebaseDatabase database) {
        this.database = database;
    }

    public void getAllDeliveryCostes() {
        databaseReference = database.getReference(FIREBASE_PATH);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                productList = new ArrayList<>();
                for (DataSnapshot postSnap : dataSnapshot.getChildren()) {
                    DeliverCost model = postSnap.getValue(DeliverCost.class);
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
        for (Listener listener : getmListeners()) {
            listener.onDeliveryCostDataCancel(databaseError);
        }
    }

    private void notifyDataChange(List<DeliverCost> dataList) {
        for (Listener listener : getmListeners()) {
            listener.onDeliveryCostDataChange(dataList);
        }
    }
}
