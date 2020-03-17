package com.devahmed.techx.foxmart.Screens.LocationAtFirstTime;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.devahmed.techx.foxmart.Models.Branch;
import com.devahmed.techx.foxmart.Models.User;
import com.devahmed.techx.foxmart.R;
import com.devahmed.techx.foxmart.Screens.AdminDashboard.BranchesControl.UseCases.FetchBranchesUseCase;
import com.devahmed.techx.foxmart.Screens.AdminDashboard.BranchesControl.UseCases.GetLocationUseCase;
import com.devahmed.techx.foxmart.Screens.HowDidYouHearAboutUs.HowDidYouHearAboutUsActivity;
import com.devahmed.techx.foxmart.Screens.UserAccount.UseCases.AddUserUseCase;
import com.devahmed.techx.foxmart.Screens.UserAccount.UseCases.FetchUserInfoFromFirebaseUseCase;
import com.devahmed.techx.foxmart.Utils.UtilsDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class LocationAtFirstTime extends AppCompatActivity implements GetLocationUseCase.Listener, FetchBranchesUseCase.Listener, AddUserUseCase.Listener, FetchUserInfoFromFirebaseUseCase.Listener {
    Button getLocationButton;
    ProgressBar progressBar;
    TextView weNeedYourLocationTextView;
    GetLocationUseCase locationUseCase;
    FetchBranchesUseCase branchesUseCase;
    FetchUserInfoFromFirebaseUseCase userInfoFromFirebaseUseCase;
    AddUserUseCase userUseCase;
    List<Branch> dataList;
    String userGpsLocation = "";
    Location userLocation;
    boolean stepOneApprove = false, stepTwoApprove = false;
    User currentUser = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_at_first_time);
        locationUseCase = new GetLocationUseCase(this);
        branchesUseCase = new FetchBranchesUseCase(FirebaseDatabase.getInstance());
        userInfoFromFirebaseUseCase = new FetchUserInfoFromFirebaseUseCase(FirebaseDatabase.getInstance());
        userUseCase = new AddUserUseCase(this);
        weNeedYourLocationTextView = findViewById(R.id.weNeedYourLocationText);
        weNeedYourLocationTextView.setText(getResources().getString(R.string.we_nee_your_location));
        progressBar = findViewById(R.id.progressBar);
        getLocationButton = findViewById(R.id.getLocationBtn);
        getLocationButton.setText(getResources().getString(R.string.get_location));
        getLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(getApplicationContext() , Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(LocationAtFirstTime.this , new String[] {Manifest.permission.ACCESS_FINE_LOCATION} , 1);
                }else{
                     locationUseCase.getLocation();
                     progressBar.setVisibility(View.VISIBLE);
                     getLocationButton.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    @Override
    public void onLatLongLoaded(double mLat, double mLong) {
        userLocation = new Location("userProvider");
        userLocation.setLatitude(mLat);
        userLocation.setLongitude(mLong);
    }

    @Override
    public void onGpsLocationLoaded(String gpsLocation) {
        userGpsLocation = gpsLocation;
        checkIfUserCanGoHome();
    }

    private void checkIfUserCanGoHome() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (dataList == null || currentUser == null) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    checkIfUserCanGoHome();
                }else{
                    int acceptedBranchIndex = -1;
                    for (int i = 0; i < dataList.size(); i++) {
                        String[] branch = dataList.get(i).getGpsLocation().split(",");
                        String[] userGpsLocations = userGpsLocation.split(",");
                        //check if the user is in the same country as the branch
                        if (branch[1].equals(userGpsLocations[1])) {
                            //user is in the same city => we then check the distance
                            stepOneApprove = true;
                            Location branchLocation = new Location("providerNA");
                            branchLocation.setLatitude(dataList.get(i).getmLat());
                            branchLocation.setLongitude(dataList.get(i).getmLong());
                            double acceptedRangeInMeters = dataList.get(i).getAcceptedOrdersRange() * 1000;
                            if (userLocation.distanceTo(branchLocation) <= acceptedRangeInMeters) {
                                //user can go he is approved
                                //link the user with this branch
                                stepTwoApprove = true;
                                acceptedBranchIndex = i;
                                break;
                            }
                        }
                    }
                    //if user is in the same city with an accepted distance
                    //we link him to some branch
                    if (stepOneApprove && stepTwoApprove) {
                        if (acceptedBranchIndex != -1) {
                            currentUser.setNearestBranch(dataList.get(acceptedBranchIndex).getId());
                            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("userBranch" , 0);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("userBranch" , dataList.get(acceptedBranchIndex).getId());
                            editor.commit();
                            currentUser.setyPos(userLocation.getLatitude());
                            currentUser.setyPos(userLocation.getLongitude());
                            currentUser.setGpsAddress(userGpsLocation);
                            userUseCase.updateExistingUserAndDeleteExtraUser(currentUser);
                        }
                    } else {
                        UtilsDialog dialog = new UtilsDialog(getApplicationContext());
                        dialog.showTextMessage("sorry you are not allowed to use this app");
                        System.out.println("sorry you are not allowed to use this app");
                    }
                }
            }
        }).start();
    }

    @Override
    public void onError(String message) {
        Toast.makeText(this, "" + message, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onUserAddedSuccessfully() {

    }

    @Override
    public void onUserFailedToAdd() {

    }

    @Override
    public void onInputError(String message) {

    }

    @Override
    public void onUserDataUpdated(User user) {
//        getLocationButton.setVisibility(View.VISIBLE);
//        progressBar.setVisibility(View.INVISIBLE);
        Intent intent = new Intent(LocationAtFirstTime.this , HowDidYouHearAboutUsActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBranchDataChange(List<Branch> dataList) {
        this.dataList = dataList;
    }


    @Override
    public void onBranchDataCancel(DatabaseError error) {
        Toast.makeText(this, "" + error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUserDataGotSuccessfully(List<User> userList) {
        for(User user : userList){
            this.currentUser = user;
        }
    }

    @Override
    public void onUserDataCanceled(DatabaseError error) {

    }

    @Override
    protected void onStart() {
        super.onStart();
        locationUseCase.registerListener(this);
        branchesUseCase.registerListener(this);
        userUseCase.registerListener(this);
        userInfoFromFirebaseUseCase.registerListener(this);
        branchesUseCase.getAllBranches();
        userInfoFromFirebaseUseCase.getUserOfID(FirebaseAuth.getInstance().getCurrentUser().getUid());
    }

    @Override
    protected void onStop() {
        super.onStop();
        locationUseCase.unregisterListener(this);
        branchesUseCase.unregisterListener(this);
        userUseCase.unregisterListener(this);
        userInfoFromFirebaseUseCase.unregisterListener(this);
    }


}
