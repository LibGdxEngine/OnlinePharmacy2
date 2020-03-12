package com.devahmed.demo.onlinepharmacy.Screens.Cart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.devahmed.demo.onlinepharmacy.Common.Navigator.Navigator;
import com.devahmed.demo.onlinepharmacy.Models.DeliverCost;
import com.devahmed.demo.onlinepharmacy.Models.Product;
import com.devahmed.demo.onlinepharmacy.Models.User;
import com.devahmed.demo.onlinepharmacy.R;
import com.devahmed.demo.onlinepharmacy.Screens.Cart.UseCase.FetchCartDataUseCase;
import com.devahmed.demo.onlinepharmacy.Screens.Cart.UseCase.FetchDeliveryCostesUseCase;
import com.devahmed.demo.onlinepharmacy.Screens.UserAccount.UseCases.FetchUserInfoFromFirebaseUseCase;
import com.devahmed.demo.onlinepharmacy.Utils.UtilsDialog;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class CartFragment extends Fragment implements CartMvc.Listener, FetchCartDataUseCase.Listener, FetchUserInfoFromFirebaseUseCase.Listener, FetchDeliveryCostesUseCase.Listener {

    CartMvcImp mvcImp;
    FetchCartDataUseCase cartDataUseCase;
    FetchUserInfoFromFirebaseUseCase userInfoFromFirebaseUseCase;
    FetchDeliveryCostesUseCase deliveryCostesUseCase;
    boolean isUserBlocked = false;
    List<DeliverCost> deliverCostList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        mvcImp = new CartMvcImp(getLayoutInflater() , null);
        cartDataUseCase = new FetchCartDataUseCase(requireActivity() , FirebaseDatabase.getInstance());
        userInfoFromFirebaseUseCase = new FetchUserInfoFromFirebaseUseCase(FirebaseDatabase.getInstance());
        deliveryCostesUseCase = new FetchDeliveryCostesUseCase(FirebaseDatabase.getInstance());
        userInfoFromFirebaseUseCase.getCurrentUser();
        return mvcImp.getRootView();
    }

    @Override
    public void onSubmitBtnClicked() {
        int deliverCost = 5;
        if(isUserBlocked){
            Toast.makeText(requireContext(), "Sorry You are blocked from this app", Toast.LENGTH_SHORT).show();
            return;
        }
        for (int i = 0; i <deliverCostList.size() ; i++) {
            //get the range that this order is in
            if(mvcImp.getTotalPrice() <= deliverCostList.get(0).getFrom() && mvcImp.getTotalPrice() <= deliverCostList.get(i).getTo()){
                deliverCost = deliverCostList.get(i).getPrice();
                break;
            }
        }
        Bundle bundle = new Bundle();
        bundle.putString("FN" , "comingFromCart");
        bundle.putInt("totalPrice" , mvcImp.getTotalPrice());
        bundle.putInt("deliveryCost" , deliverCost);
        bundle.putStringArrayList("productsIds" , mvcImp.getProductsIds());
        bundle.putIntegerArrayList("productsCounts" , mvcImp.getProductsCounts());
        Navigator.instance(requireActivity()).navigate(R.id.accountFragment , bundle);
    }

    @Override
    public void onGoToShoppingBtnClicked() {
        Navigator.instance(requireActivity()).navigate(R.id.nav_home);
    }

    @Override
    public void onCartItemImageClicked(Product product) {
        UtilsDialog dialog = new UtilsDialog(requireActivity());
        dialog.showFullImageDialog(product.getImage());
    }

    @Override
    public void onProductsChanged(List<Product> productList , List<String> productsCountList) {
        mvcImp.bindCartData(productList , productsCountList);
        mvcImp.hideProgressbar();
    }

    @Override
    public void onProductsCanceled(DatabaseError error) {
        Toast.makeText(requireContext(), "CartFragment " + error.getMessage(), Toast.LENGTH_SHORT).show();
        mvcImp.hideProgressbar();
    }

    @Override
    public void onUserDataGotSuccessfully(List<User> userList) {
        User user = null;
        for(User user1 : userList){
            user = user1;
        }
        if(user.getIsBlocked()){
            isUserBlocked = true;
        }
    }

    @Override
    public void onUserDataCanceled(DatabaseError error) {
        mvcImp.hideProgressbar();
    }

    @Override
    public void onDeliveryCostDataChange(List<DeliverCost> dataList) {

    }

    @Override
    public void onDeliveryCostDataCancel(DatabaseError error) {

    }

    @Override
    public void onStart() {
        super.onStart();
        mvcImp.registerListener(this);
        cartDataUseCase.registerListener(this);
        userInfoFromFirebaseUseCase.registerListener(this);
        deliveryCostesUseCase.registerListener(this);
        cartDataUseCase.getProductsAtCart();
        mvcImp.showProgressbar();
        deliveryCostesUseCase.getAllDeliveryCostes();
    }


    @Override
    public void onStop() {
        super.onStop();
        mvcImp.unregisterListener(this);
        cartDataUseCase.unregisterListener(this);
        userInfoFromFirebaseUseCase.unregisterListener(this);
        deliveryCostesUseCase.unregisterListener(this);
    }

}
