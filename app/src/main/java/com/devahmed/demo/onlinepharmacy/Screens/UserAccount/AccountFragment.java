package com.devahmed.demo.onlinepharmacy.Screens.UserAccount;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.devahmed.demo.onlinepharmacy.Common.Navigator.Navigator;
import com.devahmed.demo.onlinepharmacy.Models.Order;
import com.devahmed.demo.onlinepharmacy.Models.User;
import com.devahmed.demo.onlinepharmacy.R;
import com.devahmed.demo.onlinepharmacy.Screens.LoginRegister.LoginActivity;
import com.devahmed.demo.onlinepharmacy.Screens.UserAccount.UseCases.AddOrdersUseCase;
import com.devahmed.demo.onlinepharmacy.Screens.UserAccount.UseCases.AddUserUseCase;
import com.devahmed.demo.onlinepharmacy.Screens.UserAccount.UseCases.FetchUserInfoFromFirebaseUseCase;
import com.devahmed.demo.onlinepharmacy.Utils.CartManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountFragment extends Fragment implements AccountMvc.Listener, AddUserUseCase.Listener
        , FetchUserInfoFromFirebaseUseCase.Listener, AddOrdersUseCase.Listener {


    AccountMvcImp mvcImp;
    FetchUserInfoFromFirebaseUseCase userInfoFromFirebaseUseCase;
    AddUserUseCase addUserUseCase;
    AddOrdersUseCase addOrdersUseCase;
    String FN;
    User user;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        mvcImp = new AccountMvcImp(getLayoutInflater(), null);
        addUserUseCase = new AddUserUseCase(requireActivity());
        userInfoFromFirebaseUseCase = new FetchUserInfoFromFirebaseUseCase(FirebaseDatabase.getInstance());
        addOrdersUseCase = new AddOrdersUseCase(requireActivity());

        return mvcImp.getRootView();
    }

    @Override
    public void onApplyBtnClicked() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        User user = mvcImp.getUserData();
        user.setUserId(currentUser.getUid());
        addUserUseCase.updateExistingUser(user);
    }

    @Override
    public void onUserAddedSuccessfully() {

    }

    @Override
    public void onUserFailedToAdd() {

    }

    @Override
    public void onInputError(String message) {

    }

    @Override
    public void onUserDataUpdated(User user) {
        mvcImp.activateNormalShowMode();
    }

    @Override
    public void onUseGPSBtnClicked() {

    }

    @Override
    public void onLogoutBtnClicked() {

    }

    @Override
    public void onCartConfirmBtnCLicked() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        user = mvcImp.getUserData();
        if(user != null && user.getIsBlocked() != true){
            user.setUserId(currentUser.getUid());
            addUserUseCase.updateExistingUser(user);
        }
        //after that add new order with this data
        Order order = new Order();
        order.setTotalPrice(getArguments().getInt("totalPrice"));
        order.setUserId(FirebaseAuth.getInstance().getCurrentUser().getUid());
        order.setDeliveryCost(getArguments().getInt("deliveryCost"));
        order.setDiscount(0);
        order.setOrderAtTime(mvcImp.getOrderAtTime());
        List<String> productsIds = getArguments().getStringArrayList("productsIds");
        List<Integer> productsCounts = getArguments().getIntegerArrayList("productsCounts");
        Map<String , Integer> map = buildMap(productsIds , productsCounts);
        order.setOrderedItemsWithCounts(map);
        order.setOrderState("In Progress");
        order.setTimeStamp(ServerValue.TIMESTAMP);
        addOrdersUseCase.addNewOrder(order);
        //send this data to order details page
        Bundle bundle = new Bundle();
        bundle.putString("orderId" , order.getId());
        bundle.putString("orderState" , order.getOrderState());
        bundle.putInt("totalItems" , order.getOrderedItemsWithCounts().size());
        bundle.putInt("deliveryCost" , order.getDeliveryCost());
        bundle.putInt("totalPrice" , order.getTotalPrice());
        bundle.putString("userId" , order.getUserId());
        ArrayList<String> orderedItems = new ArrayList<>();
        for (String productId : order.getOrderedItemsWithCounts().keySet()) {
            orderedItems.add(productId);
        }
        bundle.putStringArrayList("orderedProducts" , orderedItems);
        ArrayList<String> ordersCounts = new ArrayList<>();
        for (Integer keys : order.getOrderedItemsWithCounts().values()){
            ordersCounts.add(String.valueOf(keys));
        }
        //Delete all the data from the cart to clear it
        deleteAllCartData();

        bundle.putStringArrayList("ordersCounts" , ordersCounts);
        Navigator.instance(requireActivity()).navigate(R.id.orderDetailsFragment , bundle);
    }

    private void deleteAllCartData() {
        CartManager prodctsIDCartManager , productCountCartManagaer;
        prodctsIDCartManager = new CartManager(getContext() , "productsId");
        productCountCartManagaer = new CartManager(getContext() , "productCount");
        prodctsIDCartManager.deleteAllKeys();
        productCountCartManagaer.deleteAllKeys();
    }

    private Map<String , Integer> buildMap (List<String> list , List<Integer> integers) {
        Map<String , Integer> map = new HashMap<>();
        for(int i = 0; i < list.size() ; i++){
            map.put( list.get(i) , integers.get(i));
        }
        return map;
    }

    @Override
    public void onEditBtnClicked() {
        mvcImp.activateEditMode();
    }

    @Override
    public void onOrderAddedSuccessfully() {
        Toast.makeText(requireActivity(), "user added successfully ", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onOrderFailedToAdd() {
        Toast.makeText(requireActivity(), "Failed to add user ", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onOrderInputError(String message) {
        Toast.makeText(requireActivity(), "Error " + message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onOrderDataUpdated(Order order) {

    }

    @Override
    public void onUserDataGotSuccessfully(List<User> userList) {
        for(User _user : userList){
            user = _user;
        }

        mvcImp.hideProgressbar();
        mvcImp.bindData(user.getName(), user.getPhone() , user.getGpsAddress()
                    , user.getArea() , user.getStreet() , user.getFlat()
                    , user.getUniqueSign() , user.getxPos() , user.getyPos());

    }

    @Override
    public void onUserDataCanceled(DatabaseError error) {
        Toast.makeText(requireActivity(), "Failed to get User Data " + error.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStart() {
        super.onStart();
        mvcImp.registerListener(this);
        addUserUseCase.registerListener(this);
        userInfoFromFirebaseUseCase.registerListener(this);
        addOrdersUseCase.registerListener(this);
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser == null){
            //user never signed up before
            Intent intent = new Intent(requireActivity() , LoginActivity.class);
            intent.putExtra("FN" , "signUp");
            startActivity(intent);
        }else{
            //means that user is signed up before
            if(getArguments() != null) {
                //means that user is coming from cart
                FN = getArguments().getString("FN");
                if(FN.equals("comingFromCart")){
                    //get only the current user data
                    userInfoFromFirebaseUseCase.getCurrentUser();
                    mvcImp.activateCartConfirmationMode();
                }else if(FN.equals("comingFromAdminShow")){
                    //get this specific user to show his data to the admin
                    String specefiedUserID = getArguments().getString("userId");
                    userInfoFromFirebaseUseCase.getUserOfID(specefiedUserID);
                    Toast.makeText(requireActivity(), "Here", Toast.LENGTH_SHORT).show();
                    mvcImp.activateAdminShowMode();
                }

            }else{
                //means that user is not coming from any where
                userInfoFromFirebaseUseCase.getCurrentUser();
                mvcImp.activateNormalShowMode();
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        mvcImp.unregisterListener(this);
        addUserUseCase.unregisterListener(this);
        userInfoFromFirebaseUseCase.unregisterListener(this);
        addOrdersUseCase.unregisterListener(this);
    }

}
