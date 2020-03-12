package com.devahmed.demo.onlinepharmacy.Screens.Splash;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.devahmed.demo.onlinepharmacy.MainActivity;
import com.devahmed.demo.onlinepharmacy.R;
import com.devahmed.demo.onlinepharmacy.Screens.LocationAtFirstTime.LocationAtFirstTime;
import com.devahmed.demo.onlinepharmacy.Screens.LoginRegister.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.sql.SQLOutput;

public class SplashActivity extends AppCompatActivity {
    static int SECONDS = 0;
    ImageView splash_imageImageView;
    Handler handler;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onStart() {
        super.onStart();

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        getSupportActionBar().hide(); //hide the title bar
        setContentView(R.layout.activity_splash);
        firebaseAuth = firebaseAuth.getInstance();
        splash_imageImageView = findViewById(R.id.splashimage);

        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    //user is already connected we need to redirect him to HomePage
                    updateUI();
                }else{
                    startActivity(new Intent(getBaseContext() , LoginActivity.class));
                    finish();
                }

            }
        },SECONDS * 1000);

    }
    //update UI for user and go other Activity
    private void updateUI() {
        Intent intent = new Intent(SplashActivity.this , MainActivity.class);
        startActivity(intent);
        finish();
    }
}