package com.devahmed.techx.foxmart.Screens.AdminDashboard.BranchesControl.UseCases;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;

import com.devahmed.techx.foxmart.Common.MVC.BaseObservableMvcView;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.util.List;
import java.util.Locale;

public class GetLocationUseCase extends BaseObservableMvcView<GetLocationUseCase.Listener> {

    private Activity activity;
    LocationRequest locationRequest;
    private double mLat , mLong;
    public interface Listener{
        void onLatLongLoaded(double mLat , double mLong);
        void onGpsLocationLoaded(String gpsLocation);
        void onError(String message);
    }
    public GetLocationUseCase(final Activity activity) {
        this.activity = activity;
    }


    private void getGpsLocation(double x , double y){
        String errorMessage = "";
        String city = "Unknown";
        Geocoder geocoder = new Geocoder(activity, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(x,y  , 1);
        }catch (Exception e){

            errorMessage = e.getMessage();
        }
        if(addresses == null || addresses.isEmpty()){
//            Toast.makeText(activity, "Address Not Found " + errorMessage, Toast.LENGTH_SHORT).show();
            notifyError("Address Not Found " + errorMessage);
        }else{
            city = addresses.get(0).getAddressLine(0);
        }
        notifyOnGpsLocationLoaded(city);
    }


    public void getLocation(){

            getCurrentLocation();

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
                            mLat = latitude;
                            mLong = longitude;
                            notifyOnLatLongLoaded(latitude , longitude);
                            getGpsLocation();
                        }else{
                            //we didn't get anything
                        }
                    }
                }, activity.getMainLooper());
    }

    public void getGpsLocation(){
        getGpsLocation(this.mLat , this.mLong);
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

    private void notifyError(String s) {
        for(Listener listener :getmListeners()){
            listener.onError(s);
        }
    }

}
