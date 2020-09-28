package com.devahmed.techx.aswaqmisr.Screens.AdminDashboard.BillsControl;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.devahmed.techx.aswaqmisr.Models.Order;
import com.devahmed.techx.aswaqmisr.Models.Product;
import com.devahmed.techx.aswaqmisr.Models.User;
import com.devahmed.techx.aswaqmisr.Screens.AdminDashboard.BillsControl.UseCase.TakeScreenShotUseCase;
import com.devahmed.techx.aswaqmisr.Screens.Orders.MyOrdersList.UseCases.FetchOrdersUseCase;
import com.devahmed.techx.aswaqmisr.Screens.ProductsShow.UseCases.FetchProductsUseCase;
import com.devahmed.techx.aswaqmisr.Screens.UserAccount.UseCases.FetchUserInfoFromFirebaseUseCase;
import com.devahmed.techx.aswaqmisr.Utils.BillCanvasView;
import com.devahmed.techx.aswaqmisr.Utils.UtilsDialog;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class BillsFragment extends Fragment implements BillsMvc.Listener, FetchOrdersUseCase.Listener, FetchProductsUseCase.Listener, FetchUserInfoFromFirebaseUseCase.Listener {

    BillsMvcImp mvcImp;
    FetchOrdersUseCase ordersUseCase;
    FetchProductsUseCase productsUseCase;
    FetchUserInfoFromFirebaseUseCase userInfoFromFirebaseUseCase;
    TakeScreenShotUseCase screenShotUseCase;
    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        mvcImp = new BillsMvcImp(getLayoutInflater() , null);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        ordersUseCase = new FetchOrdersUseCase(database);
        productsUseCase = new FetchProductsUseCase(database);
        userInfoFromFirebaseUseCase = new FetchUserInfoFromFirebaseUseCase(database);
        ordersUseCase.getAllOrders();
        screenShotUseCase = new TakeScreenShotUseCase(requireActivity());

        return mvcImp.getRootView();
    }

    @Override
    public void onBillItemClicked(Order order , BillCanvasView billCanvasView) {
        UtilsDialog dialog = new UtilsDialog(requireActivity());
        dialog.showFullImageDialog(billCanvasView);
    }

    @Override
    public void onDownloadItemClicked(Order order, final BillCanvasView billCanvasView) {
        mvcImp.activateDownloadMode(billCanvasView);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                screenShotUseCase.takeScreenshot(billCanvasView);
                Toast.makeText(requireContext(), "Downloaded", Toast.LENGTH_SHORT).show();
                mvcImp.activateNormalMode();
            }
        } , 500);

    }

    @Override
    public void onShareBtnClicked(Order order, BillCanvasView billCanvasView) {

    }

    @Override
    public void onOrdersDataChange(List<Order> orderList, User user) {
        mvcImp.bindData(orderList);
        mvcImp.hideProgressbar();
    }

    @Override
    public void onOrdersDataCancel(DatabaseError error) {

    }

    @Override
    public void onFinishedOrdersGot(List<Order> finishedOrders) {

    }

    @Override
    public void onProductsDataChange(List<Product> products) {

    }

    @Override
    public void onProductsDataCancel(DatabaseError error) {
        mvcImp.hideProgressbar();
    }

    @Override
    public void onUserDataGotSuccessfully(List<User> userList) {

    }

    @Override
    public void onUserDataCanceled(DatabaseError error) {

    }

    @Override
    public void onStart() {
        super.onStart();
        mvcImp.registerListener(this);
        ordersUseCase.registerListener(this);
        productsUseCase.registerListener(this);
        userInfoFromFirebaseUseCase.registerListener(this);

    }

    @Override
    public void onStop() {
        super.onStop();
        mvcImp.unregisterListener(this);
        ordersUseCase.unregisterListener(this);
        productsUseCase.unregisterListener(this);
        userInfoFromFirebaseUseCase.unregisterListener(this);
    }
}
