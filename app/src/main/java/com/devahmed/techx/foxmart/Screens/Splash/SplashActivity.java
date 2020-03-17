package com.devahmed.techx.foxmart.Screens.Splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.Window;
import android.widget.ImageView;

import com.devahmed.techx.foxmart.MainActivity;
import com.devahmed.techx.foxmart.R;
import com.devahmed.techx.foxmart.Screens.LocationAtFirstTime.LocationAtFirstTime;
import com.devahmed.techx.foxmart.Screens.LoginRegister.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Locale;

public class SplashActivity extends AppCompatActivity {
    static int SECONDS = 2;
    ImageView splash_imageImageView;
    Handler handler;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        getSupportActionBar().hide(); //hide the title bar
        setContentView(R.layout.activity_splash);
        firebaseAuth = firebaseAuth.getInstance();
        splash_imageImageView = findViewById(R.id.splashimage);

        loadAppLanguage();


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

    private void loadAppLanguage() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String language = sharedPreferences.getString("language", "null");//en for english , ar for arabic
        Locale locale;
        if(language.equals("null")){
            locale = new Locale(Locale.getDefault().getDisplayLanguage());
        }else{
            locale = new Locale(language);
        }
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
    }

    //update UI for user and go other Activity
    private void updateUI() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("userBranch" , 0);
        if(sharedPreferences.getBoolean("gotUserLocation?" , false)){
            Intent intent = new Intent(SplashActivity.this , MainActivity.class);
            startActivity(intent);
            finish();
        }else{
            startActivity(new Intent(getBaseContext() , LocationAtFirstTime.class));
            finish();
        }

    }
}