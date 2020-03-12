package com.devahmed.demo.onlinepharmacy.Screens.Orders.OrdersDetails;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.devahmed.demo.onlinepharmacy.Models.Product;
import com.devahmed.demo.onlinepharmacy.Models.User;
import com.devahmed.demo.onlinepharmacy.Screens.ProductsShow.UseCases.FetchProductsUseCase;
import com.devahmed.demo.onlinepharmacy.Screens.UserAccount.UseCases.FetchUserInfoFromFirebaseUseCase;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailsFragment extends Fragment implements OrderDetailsMvc.Listener, FetchProductsUseCase.Listener, FetchUserInfoFromFirebaseUseCase.Listener {


    OrderDetailsMvcImp mvcImp;
    FetchProductsUseCase productsUseCase;
    FetchUserInfoFromFirebaseUseCase userInfoFromFirebaseUseCase;
    ArrayList<String> ordersCounts;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        mvcImp = new OrderDetailsMvcImp(getLayoutInflater() , null);
        productsUseCase = new FetchProductsUseCase(FirebaseDatabase.getInstance());
        userInfoFromFirebaseUseCase = new FetchUserInfoFromFirebaseUseCase(FirebaseDatabase.getInstance());

        getOrderData();


        return mvcImp.getRootView();
    }

    private void getOrderData() {
        if(getArguments() != null){
            String userId = getArguments().getString("userId");
            String orderState = getArguments().getString("orderState");
            String orderId = getArguments().getString("orderId");
            int totalItems = getArguments().getInt("totalItems");
            int deliveryCost = getArguments().getInt("deliveryCost");
            int totalPrice = getArguments().getInt("totalPrice");
            ArrayList<String> orderedProducts = getArguments().getStringArrayList("orderedProducts");
            ordersCounts = getArguments().getStringArrayList("ordersCounts");
            String FN  = getArguments().getString("FN");
            if(FN != null){
                if(FN.equals("showForAdmin"))
                    mvcImp.showAdminControls();
            }
            mvcImp.bindOrderState(orderState);
            mvcImp.bindPaymentsData(totalItems , deliveryCost , totalPrice );
            productsUseCase.getCategoriesWithIDs(orderedProducts);
            userInfoFromFirebaseUseCase.getUserOfID(userId);
        }
    }

    @Override
    public void onCallUserBtnClicked(User user) {
        String phone = user.getPhone();
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
        startActivity(intent);
    }

    @Override
    public void onProductsDataChange(List<Product> products) {
        mvcImp.bindOrderedItemsData(products , ordersCounts);
        mvcImp.hideProgressBar();
    }

    @Override
    public void onProductsDataCancel(DatabaseError error) {
        mvcImp.hideProgressBar();
    }

    @Override
    public void onUserDataGotSuccessfully(List<User> userList) {
        User _user = null;
        for(User user : userList){
            _user = user;
        }
        mvcImp.bindUserData(_user);
        mvcImp.hideProgressBar();
    }

    @Override
    public void onUserDataCanceled(DatabaseError error) {
        mvcImp.hideProgressBar();
    }

    @Override
    public void onStart() {
        super.onStart();
        mvcImp.registerListener(this);
        productsUseCase.registerListener(this);
        userInfoFromFirebaseUseCase.registerListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        mvcImp.unregisterListener(this);
        productsUseCase.unregisterListener(this);
        userInfoFromFirebaseUseCase.unregisterListener(this);
    }


}
