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
    private Stack<Integer> pageStack = new Stack<Integer>();
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

        // default selected fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DashboardFragment()).commit();
        bottomNavigationView.setSelectedItemId(R.id.nav_dashboard); // default selected item
        pageStack.push(R.id.nav_dashboard); // storing first page



    }

    // change the fragment page from bottom navigation view according to user choice
    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = new
            BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

            Fragment selectedFragment = null;

            switch (menuItem.getItemId()){
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

            pageStack.push(menuItem.getItemId()); // storing each page
            return true;
         }
    };

    // hide the bottom navigation & top status bar in calculate cgpa fragment from main activity. and also work for back button
    public void SetNavigationVisibility(boolean b){
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        if (b){
            bottomNavigationView.setVisibility(View.VISIBLE);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DashboardFragment()).commit();// for back button
            bottomNavigationView.setSelectedItemId(R.id.nav_dashboard);
         }
        else {
             bottomNavigationView.setVisibility(View.GONE);
           // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }

// change the view based on previous history
    @Override
    public void onBackPressed() {

         pageStack.pop();

        if (pageStack.isEmpty()){
            super.onBackPressed();
        }
        else {
            Fragment selectedFragment = null;
            int i = pageStack.pop();

            switch (i){
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
             bottomNavigationView.setSelectedItemId(i);
        }
    }




}