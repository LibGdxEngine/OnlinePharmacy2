package com.devahmed.demo.onlinepharmacy.Screens.LoginRegister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

import com.devahmed.demo.onlinepharmacy.Common.MVC.MvcView;
import com.devahmed.demo.onlinepharmacy.Common.Navigator.Navigator;
import com.devahmed.demo.onlinepharmacy.MainActivity;
import com.devahmed.demo.onlinepharmacy.R;
import com.devahmed.demo.onlinepharmacy.Screens.LoginRegister.LoginRegisterUseCase.LoginRegisterUseCase;

public class LoginActivity extends AppCompatActivity implements LoginActivityMvc.Listener, LoginRegisterUseCase.Listener {
    LoginActivityMvcImp mvcImp;
    LoginRegisterUseCase loginRegisterUseCase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        getSupportActionBar().hide(); //hide the title bar
        mvcImp = new LoginActivityMvcImp(getLayoutInflater() , null , getSupportFragmentManager());
        loginRegisterUseCase = new LoginRegisterUseCase(this);

        setContentView(mvcImp.getRootView());
    }

    @Override
    public void onSubmitBtnCLicked() {
        mvcImp.showProgressbar();
        loginRegisterUseCase.registerNewUser(mvcImp.getRegisterPhoneNumber());
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
    public void onVerificationCompleted() {
        mvcImp.hideProgressbar();
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
    public void onCodeSentAndUserSuccessfullyRegister() {
        Intent intent = new Intent(LoginActivity.this , MainActivity.class);
        startActivity(intent);
        finish();
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
    }

    @Override
    protected void onStop() {
        super.onStop();
        mvcImp.unregisterListener(this);
        loginRegisterUseCase.unregisterListener(this);
    }
}
