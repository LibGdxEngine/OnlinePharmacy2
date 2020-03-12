package com.devahmed.demo.onlinepharmacy;

import android.os.Bundle;

import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    DrawerLayout drawer;
    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initNavigationDrawerAndToolBar();
    }

    private void initNavigationDrawerAndToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        final NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_orders ,
                R.id.nav_home,R.id.nav_account,
                R.id.nav_add_item, R.id.accountFragment, R.id.contactUs)
                .setDrawerLayout(drawer)
                .build();
        
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);


        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);

//        NavigationUI.setupWithNavController(navigationView, navController);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        int currentDestination = navController.getCurrentDestination().getId();
        //if we are not already in cart => navigate to cart
        if(id == R.id.action_cart && currentDestination != R.id.action_cart){
            navController.navigate(R.id.cartFragment);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Bundle bundle = new Bundle();
//         Handle navigation view item clicks here.
        switch (menuItem.getItemId()) {
            case R.id.nav_home: {
                navController.navigate(R.id.nav_home);
                break;
            }
            case R.id.nav_search: {
                navController.navigate(R.id.searchFragment);
                break;
            }
            case R.id.adminDashboard:{
                navController.navigate(R.id.adminFragment);
                break;
            }
            case R.id.nav_cart:{
                navController.navigate(R.id.cartFragment);
                break;
            }
            case R.id.nav_account:{
                navController.navigate(R.id.accountFragment);
                break;
            }
            case R.id.nav_orders:{
                navController.navigate(R.id.ordersListFragment);
                break;
            }

        }
        //close navigation drawer
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }

    @Override
    public void onBackPressed() {
        int id = navController.getCurrentDestination().getId();
        switch (id){
            case R.id.nav_home:
                //show exite confirmation message
                break;
            case R.id.nav_products:
                navController.navigateUp();
                break;
            default:
                super.onBackPressed();
        }
    }
}
