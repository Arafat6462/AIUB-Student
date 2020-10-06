package com.arafat6462.aiubstudent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    // create a  stack for storing history of user activity
    BottomNavigationView bottomNavigationView;
    int currentFragmentId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

        // default selected fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DashboardFragment()).commit();
        bottomNavigationView.setSelectedItemId(R.id.nav_dashboard); // default selected item


    }

    // change the fragment page from bottom navigation view according to user choice
    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = new
            BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                    Fragment selectedFragment = null;

                    switch (menuItem.getItemId()) {
                        case R.id.nav_home:
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.nav_dashboard:
                            selectedFragment = new DashboardFragment();
                            break;
                        case R.id.nav_profile:
                            selectedFragment = new ProfileFragment();
                            break;

                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                    currentFragmentId = menuItem.getItemId();
                    return true;
                }
            };

    // hide the bottom navigation & top status bar in calculate cgpa fragment from main activity. and also work for back button
    public void SetNavigationVisibilityAndBackButton(boolean b) {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        if (b) {
            bottomNavigationView.setVisibility(View.VISIBLE);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DashboardFragment()).commit();// for back button
            bottomNavigationView.setSelectedItemId(R.id.nav_dashboard);
            currentFragmentId=R.id.nav_dashboard; // dashboard fragment id will store when user back to calculate-result to dashboard
        } else {
            bottomNavigationView.setVisibility(View.GONE);
            // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
            currentFragmentId=R.id.ResultCalculationRecyclerView; // ResultCalculationRecyclerView id will store because user not on dashboard fragment.
        }
    }

    // change the view based on previous history
    @Override
    public void onBackPressed() {
        if (currentFragmentId == R.id.nav_dashboard) {
             Login.loginActivity.finish(); // also finish the login activity
             super.onBackPressed();
         } else {
            SetNavigationVisibilityAndBackButton(true); // for from result calculation to dashboard show bottom navigation instead of onPause method
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DashboardFragment()).commit();
            bottomNavigationView.setSelectedItemId(R.id.nav_dashboard);
        }
    }


}