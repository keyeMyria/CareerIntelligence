package com.altitude.careerintelligence.pret;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.altitude.careerintelligence.R;
import com.altitude.careerintelligence.mcc.fragments.MCCHome;
import com.altitude.careerintelligence.pret.fragments.PRETHomeFragment;

public class PRETDashboard extends AppCompatActivity {

    private TextView mTextMessage;
    private Fragment selectedFragment;
    private String fragmentStatus;

    FragmentTransaction transaction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pretdashboard);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.pret_frame_layout, PRETHomeFragment.newInstance());
        transaction.commit();



        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                selectedFragment = null;
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        selectedFragment = PRETHomeFragment.newInstance();
                        fragmentStatus = "home";
                        break;
                    case R.id.navigation_dashboard:
                        selectedFragment = PRETHomeFragment.newInstance();
                        fragmentStatus = "home";
                        break;
                    case R.id.navigation_notifications:
                        selectedFragment = PRETHomeFragment.newInstance();
                        fragmentStatus = "home";
                        break;
                }
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.pret_frame_layout, selectedFragment);
                transaction.commit();
                return true;

            }
        });
    }

}
