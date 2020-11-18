package com.devahmed.tech4fun.ecommerce.Screens.LoginRegister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;
import com.devahmed.tech4fun.ecommerce.MainActivity;
import com.devahmed.tech4fun.ecommerce.Models.Branch;
import com.devahmed.tech4fun.ecommerce.Models.User;
import com.devahmed.tech4fun.ecommerce.Screens.AdminDashboard.BranchesControl.UseCases.AddBranchUseCase;
import com.devahmed.tech4fun.ecommerce.Screens.LoginRegister.LoginRegisterUseCase.LoginRegisterUseCase;
import com.devahmed.tech4fun.ecommerce.Screens.UserAccount.UseCases.AddUserUseCase;
import com.devahmed.tech4fun.ecommerce.Screens.UserAccount.UseCases.FetchUserInfoFromFirebaseUseCase;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class LoginActivity extends AppCompatActivity implements LoginActivityMvc.Listener, LoginRegisterUseCase.Listener, AddUserUseCase.Listener, FetchUserInfoFromFirebaseUseCase.Listener,  AddBranchUseCase.Listener {
    LoginActivityMvcImp mvcImp;
    LoginRegisterUseCase loginRegisterUseCase;
    AddUserUseCase addUserUseCase;
    FetchUserInfoFromFirebaseUseCase userInfoFromFirebaseUseCase;
    String FN = "";
    String MODE = "CREATE_NEW_USER";//other mode is //UPDATE_EXISTING_USER
    User existingUser = null;
    AddBranchUseCase addBranchUseCase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        getSupportActionBar().hide(); //hide the title bar
        mvcImp = new LoginActivityMvcImp(getLayoutInflater() , null , getSupportFragmentManager());

        if(getIntent().getExtras() != null){
            FN = getIntent().getExtras().getString("FN");
        }
        userInfoFromFirebaseUseCase = new FetchUserInfoFromFirebaseUseCase(FirebaseDatabase.getInstance());
        loginRegisterUseCase = new LoginRegisterUseCase(this);
        addUserUseCase = new AddUserUseCase(this);
        addBranchUseCase = new AddBranchUseCase(this);
        setContentView(mvcImp.getRootView());
    }

    @Override
    public void onSubmitBtnCLicked() {
        mvcImp.showProgressbar();
        loginRegisterUseCase.registerNewUser(mvcImp.getRegisterPhoneNumber());
        Toast.makeText(this, "Logging", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onViewPagerPageChanged(int page) {

    }

    @Override
    public void onResendCodeBtnClicked() {
        Toast.makeText(this, "resend code", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSkipBtnClicked() {
        Intent intent = new Intent(LoginActivity.this , MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onEnterCodeBtnClicked(String code) {
        loginRegisterUseCase.signInWithCode(code);
    }


    @Override
    public void onVerificationCompleted() {
        mvcImp.hideProgressbar();
        Toast.makeText(this, "Log in", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onVerificationFailed(String message) {
        Toast.makeText(this, "" + message, Toast.LENGTH_SHORT).show();
        mvcImp.hideProgressbar();
    }

    @Override
    public void onCodeSent() {
        mvcImp.hideLoginPage();
        mvcImp.showCodeEnterencePage();
        mvcImp.startTimer(this);
    }

    @Override
    public void onCodeSentAndUserSuccessfullyRegister(FirebaseUser user) {
        if(MODE.equals("UPDATE_EXISTING_USER")){
           //just do nothing
        }else{
            //create new user with just userId & phone number
            addUserUseCase.addNewUser(user.getUid() , mvcImp.getRegisterPhoneNumber());
        }


        if(FN.equals("signUp")){
            //if we came from cart or account page to signup then just finish the activity to return to the same page
            finish();
        }else{
            Branch branch = new Branch();
            branch.setId("-M277YZ-fwm7UIQrJZQ6");
            branch.setName("Default");
            branch.setOpenNow(true);
            branch.setAcceptedOrdersRange(99999);
            branch.setGpsLocation("");
            branch.setmLat(0);
            branch.setmLong(0);
            branch.setOpenAt(" 9 : 00");
            addBranchUseCase.addNewBranch(branch);
            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("userBranch" , 0);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("userBranch" , "-M277YZ-fwm7UIQrJZQ6");
            editor.commit();
            //if wa came from splash activity just go to the mainActivity
            Intent intent = new Intent(LoginActivity.this , MainActivity.class);
            startActivity(intent);
            finish();
        }

    }

    @Override
    public void onCodeSentAndUserFailedToRegister(String message) {
        Toast.makeText(this, "onCodeSentAndUserFailedToRegister " + message, Toast.LENGTH_SHORT).show();
        mvcImp.hideProgressbar();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mvcImp.registerListener(this);
        loginRegisterUseCase.registerListener(this);
        addUserUseCase.registerListener(this);
        addBranchUseCase.registerListener(this);
        userInfoFromFirebaseUseCase.registerListener(this);
        userInfoFromFirebaseUseCase.getAllUsers();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mvcImp.unregisterListener(this);
        loginRegisterUseCase.unregisterListener(this);
        addUserUseCase.unregisterListener(this);
        userInfoFromFirebaseUseCase.unregisterListener(this);
        addBranchUseCase.unregisterListener(this);
    }

    @Override
    public void onUserAddedSuccessfully() {

    }

    @Override
    public void onUserFailedToAdd() {
    }



    @Override
    public void onInputError(String message) {
        Toast.makeText(this, "Login " + message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUserDataUpdated(User user) {

    }

    @Override
    public void onUserDataGotSuccessfully(List<User> userList) {
        for(User user : userList){
            if(user.getPhone().equals(mvcImp.getRegisterPhoneNumber())){
                MODE = "UPDATE_EXISTING_USER";
                existingUser = user;
            }
        }
    }

    @Override
    public void onUserDataCanceled(DatabaseError error) {

    }


    @Override
    public void onBranchAddedSuccessfully() {

    }

    @Override
    public void onBranchFailedToAdd() {

    }
}
