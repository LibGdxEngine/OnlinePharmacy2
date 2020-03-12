package com.devahmed.demo.onlinepharmacy.Screens.AdminDashboard.BillsControl;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.devahmed.demo.onlinepharmacy.Common.Navigator.Navigator;
import com.devahmed.demo.onlinepharmacy.Models.Order;
import com.devahmed.demo.onlinepharmacy.Models.Product;
import com.devahmed.demo.onlinepharmacy.Models.User;
import com.devahmed.demo.onlinepharmacy.R;
import com.devahmed.demo.onlinepharmacy.Screens.AdminDashboard.BillsControl.UseCase.TakeScreenShotUseCase;
import com.devahmed.demo.onlinepharmacy.Screens.Orders.MyOrdersList.UseCases.FetchOrdersUseCase;
import com.devahmed.demo.onlinepharmacy.Screens.ProductsShow.UseCases.FetchProductsUseCase;
import com.devahmed.demo.onlinepharmacy.Screens.UserAccount.UseCases.FetchUserInfoFromFirebaseUseCase;
import com.devahmed.demo.onlinepharmacy.Utils.CanvasView;
import com.devahmed.demo.onlinepharmacy.Utils.UtilsDialog;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
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
        ordersUseCase.getAllFinishedOrders();
        screenShotUseCase = new TakeScreenShotUseCase(requireActivity());

        return mvcImp.getRootView();
    }

    @Override
    public void onBillItemClicked(Order order , CanvasView canvasView) {
        UtilsDialog dialog = new UtilsDialog(requireActivity());
        dialog.showFullImageDialog(canvasView);
    }

    @Override
    public void onDownloadItemClicked(Order order,CanvasView canvasView) {
        mvcImp.activateDownloadMode(canvasView);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                screenShotUseCase.takeScreenshot();
                Toast.makeText(requireContext(), "Downloaded", Toast.LENGTH_SHORT).show();
                mvcImp.activateNormalMode();
            }
        } , 500);

    }

    @Override
    public void onShareBtnClicked(Order order,CanvasView canvasView) {

    }

    @Override
    public void onOrdersDataChange(List<Order> orderList, User user) {

    }

    @Override
    public void onOrdersDataCancel(DatabaseError error) {

    }

    @Override
    public void onFinishedOrdersGot(List<Order> finishedOrders) {
        mvcImp.bindData(finishedOrders);
        mvcImp.hideProgressbar();
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
