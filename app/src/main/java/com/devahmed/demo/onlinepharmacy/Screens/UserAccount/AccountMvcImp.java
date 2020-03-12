package com.devahmed.demo.onlinepharmacy.Screens.UserAccount;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.devahmed.demo.onlinepharmacy.Common.MVC.BaseObservableMvcView;
import com.devahmed.demo.onlinepharmacy.Models.User;
import com.devahmed.demo.onlinepharmacy.R;

import java.util.ArrayList;

public class AccountMvcImp extends BaseObservableMvcView<AccountMvc.Listener> implements AccountMvc{

    private EditText phone , name , area , street , building, uniqueSign;
    private Button pointsBtn;
    private ConstraintLayout pointsContainer;
    private Button useGPSBtn , logOutBtn , applyDataBtn , cartConfirmationBtn;
    private ProgressBar progressBar;
    private ImageView editBtn;

    public AccountMvcImp(LayoutInflater inflater , ViewGroup parent) {
        setRootView(inflater.inflate(R.layout.account_fragment , parent , false));

        initViews();
        cartConfirmationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(Listener listener : getmListeners()){
                    listener.onCartConfirmBtnCLicked();
                }
            }
        });

        useGPSBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(Listener listener : getmListeners()){
                    listener.onUseGPSBtnClicked();
                }
            }
        });

        logOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(Listener listener : getmListeners()){
                    listener.onLogoutBtnClicked();
                }
            }
        });

        applyDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(Listener listener : getmListeners()){
                    listener.onApplyBtnClicked();
                }
            }
        });

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(Listener listener : getmListeners()){
                    listener.onEditBtnClicked();
                }
            }
        });

    }

    private void initViews() {
        phone = findViewById(R.id.accountPhoneNumber);
        progressBar = findViewById(R.id.progressBar);
        name = findViewById(R.id.accountName);
        cartConfirmationBtn = findViewById(R.id.cartConfirmationBtn);
        area = findViewById(R.id.accountArea);
        editBtn = findViewById(R.id.editBtn);
        street = findViewById(R.id.accountStreetNumber);
        building = findViewById(R.id.accountBuildingNumber);
        uniqueSign = findViewById(R.id.accountUniqueSign);
        pointsBtn = findViewById(R.id.pointsBtn);
        pointsContainer = findViewById(R.id.pointsContainer);
        useGPSBtn = findViewById(R.id.accoutnUseGPSBtn);
        logOutBtn = findViewById(R.id.accountLogOutBtn);
        applyDataBtn = findViewById(R.id.accountApplyDataBtn);
    }


    @Override
    public void bindData(String userName, String userPhone, String gpsAddress,
                         String area, String street, String flat,
                         String unieueSigns, double xPos, double yPos) {
        this.phone.setText(userPhone);
        this.name.setText(userName);
        this.area.setText(area);
        this.street.setText(street);
        this.building.setText(flat);
        this.uniqueSign.setText(unieueSigns);
    }

    @Override
    public void activateEditMode() {
        applyDataBtn.setVisibility(View.VISIBLE);
        logOutBtn.setVisibility(View.GONE);
        pointsContainer.setVisibility(View.GONE);
        Toast.makeText(getContext(), "activateEditMode", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void activateNormalShowMode() {
        applyDataBtn.setVisibility(View.GONE);
        logOutBtn.setVisibility(View.VISIBLE);
        pointsContainer.setVisibility(View.VISIBLE);
        cartConfirmationBtn.setVisibility(View.GONE);
        Toast.makeText(getContext(), "activateNormalShowMode", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void activateCartConfirmationMode() {
        pointsContainer.setVisibility(View.GONE);
        applyDataBtn.setVisibility(View.GONE);
        logOutBtn.setVisibility(View.GONE);
        cartConfirmationBtn.setVisibility(View.VISIBLE);
        Toast.makeText(getContext(), "activateCartConfirmationMode", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void activateAdminShowMode() {
        pointsContainer.setVisibility(View.GONE);
        applyDataBtn.setVisibility(View.GONE);
        logOutBtn.setVisibility(View.GONE);
        cartConfirmationBtn.setVisibility(View.GONE);
        editBtn.setVisibility(View.GONE);
    }

    private void enableEditText(EditText editText){
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);

    }
    public String getOrderAtTime(){
        return "now";
    }



    private void disableEditText(EditText editText){
        editText.setFocusable(false);
        editText.setFocusableInTouchMode(false);
        editText.setInputType(InputType.TYPE_NULL);
    }

    @Override
    public void showProgressbar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressbar() {
        progressBar.setVisibility(View.GONE);
    }


    public User getUserData() {
        User user = new User();
        user.setPhone(phone.getText().toString());
        user.setName(name.getText().toString());
        user.setStreet(street.getText().toString());
        user.setArea(area.getText().toString());
        user.setFlat(building.getText().toString());
        user.setUniqueSign(uniqueSign.getText().toString());
        user.setPoints(12);
        user.setGpsAddress("s");
        user.setxPos(1);
        user.setyPos(1);
        return user;
    }
}
