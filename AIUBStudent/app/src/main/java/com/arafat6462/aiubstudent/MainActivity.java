package com.arafat6462.aiubstudent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigation);

        bottomNavigationView.setOnNavigationItemSelectedListener((BottomNavigationView.OnNavigationItemSelectedListener) bottomNavMethod);
        getSupportFragmentManager().beginTransaction().replace(R.id.container,new HomeFragment()).commit();
    }

    private BottomNavigationView.OnNavigationItemReselectedListener bottomNavMethod = new
            BottomNavigationView.OnNavigationItemReselectedListener() {
        @Override
        public boolean onNavigationItemReselected(@NonNull MenuItem menuItem) {

            Fragment fragment = null;
            switch (menuItem.getItemId())
            {
                case R.id.home:
                    fragment = new HomeFragment();
                    break;

                 case R.id.dashboard:
                    fragment = new DashboardFragment();
                    break;

                 case R.id.profile:
                    fragment = new ProfileFragment();
                    break;
              }

            getSupportFragmentManager().beginTransaction().replace(R.id.container , fragment).commit();

            return true;
        }
    };
}