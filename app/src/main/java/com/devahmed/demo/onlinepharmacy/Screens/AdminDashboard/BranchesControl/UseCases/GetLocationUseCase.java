package com.devahmed.demo.onlinepharmacy.Screens.AdminDashboard.BranchesControl.UseCases;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.devahmed.demo.onlinepharmacy.Common.MVC.BaseObservableMvcView;
import com.devahmed.demo.onlinepharmacy.MainActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;
import java.util.Locale;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.INTERNET;

public class GetLocationUseCase extends BaseObservableMvcView<GetLocationUseCase.Listener> {

    private Activity activity;
    LocationRequest locationRequest;
    public interface Listener{
        void onLatLongLoaded(double mLat , double mLong);
        void onGpsLocationLoaded(String gpsLocation);
        void onError(String message);
    }
    public GetLocationUseCase(final Activity activity) {
        this.activity = activity;
    }


    private void getGpsLocation(){
        String errorMessage = "";
        String city = "Unknown";
        Geocoder geocoder = new Geocoder(activity, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(30.0113027,31.3131621  , 1);
        }catch (Exception e){

            errorMessage = e.getMessage();
        }
        if(addresses == null || addresses.isEmpty()){
            Toast.makeText(activity, "Address Not Found " + errorMessage, Toast.LENGTH_SHORT).show();
        }else{

            city = addresses.get(0).getAddressLine(0);
        }
        notifyOnGpsLocationLoaded(city);
    }

    public void getLocation(){
        if(ContextCompat.checkSelfPermission(activity , Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(activity , new String[] {Manifest.permission.ACCESS_FINE_LOCATION} , 1);

        }else{
            getCurrentLocation();
        }
    }

    private void getCurrentLocation() {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationServices.getFusedLocationProviderClient(activity)
                .requestLocationUpdates(locationRequest , new LocationCallback() {
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        super.onLocationResult(locationResult);
                        LocationServices.getFusedLocationProviderClient(activity)
                                .removeLocationUpdates(this);
                        if(locationResult != null && locationResult.getLocations().size() > 0){
                            int latestLocationIndex = locationResult.getLocations().size() - 1;
                            double latitude = locationResult.getLocations().get(latestLocationIndex).getLatitude();
                            double longitude = locationResult.getLocations().get(latestLocationIndex).getLongitude();
                            notifyOnLatLongLoaded(latitude , longitude);
                            getGpsLocation();
                        }else{
                            //we didn't get anything
                        }
                    }
                }, activity.getMainLooper());
    }



    void notifyOnLatLongLoaded(double mLat , double mLong){
        for(Listener listener :getmListeners()){
            listener.onLatLongLoaded(mLat , mLong);
        }
    }

    void notifyOnGpsLocationLoaded(String gpsLocation){
        for(Listener listener :getmListeners()){
            listener.onGpsLocationLoaded(gpsLocation);
        }
    }
}
