package com.devahmed.demo.onlinepharmacy.Common.Navigator;

import android.app.Activity;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.devahmed.demo.onlinepharmacy.R;

public class Navigator {

    private static NavController navController;

    public static NavController instance(Activity activity){
        if(navController == null){
            navController = Navigation.findNavController(activity , R.id.nav_host_fragment);
        }
        return navController;
    }

}
