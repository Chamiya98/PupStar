package com.example.pupstar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.pupstar.Fragments.AppointmentsFragment;
import com.example.pupstar.Fragments.DashboardFragment;
import com.example.pupstar.Fragments.MyDogsFragment;
import com.example.pupstar.Fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DashboardActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        bottomNavigationView = (BottomNavigationView) this.findViewById(R.id.navigatorView);

        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        bottomNavigationView.setSelectedItemId(R.id.nav_dashboard);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {

                        case R.id.nav_dashboard:
                            selectedFragment = new DashboardFragment();
                            break;

                        case R.id.nav_my_dogs:
                            selectedFragment = new MyDogsFragment();
                            break;

//                        case R.id.nav_search:
//                            selectedFragment = new SearchFragment();
//                            break;

                        case R.id.nav_appointment:
                            selectedFragment = new AppointmentsFragment();
                            break;

//                        case R.id.nav_explore:
//                            selectedFragment = new ExploreFragment();
//                            break;

                        case R.id.nav_profile:
                            selectedFragment = new ProfileFragment();
                            break;

                        case R.id.nav_sign_out:
                            startActivity(new Intent(getApplicationContext()
                                , SignInActivity.class));
                            overridePendingTransition(0, 0);
                            return true;

                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,
                            selectedFragment).commit();

                    return true;
                }
            };

}