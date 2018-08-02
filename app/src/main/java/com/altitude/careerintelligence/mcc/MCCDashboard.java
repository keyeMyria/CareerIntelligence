package com.altitude.careerintelligence.mcc;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.altitude.careerintelligence.mcc.fragments.MCCAboutFragment;
import com.altitude.careerintelligence.mcc.fragments.MCCFAQFragment;
import com.altitude.careerintelligence.mcc.fragments.MCCHome;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import com.altitude.careerintelligence.R;

public class MCCDashboard extends AppCompatActivity {

    private ProgressDialog progressDialog;

    private String nairaValue = "", fragmentStatus = "home";

    Fragment selectedFragment;
    FragmentTransaction transaction;

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mccdashboard);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);

        progressDialog = new ProgressDialog(MCCDashboard.this);

        RequestQueue ExampleRequestQueue = Volley.newRequestQueue(this);

        //Manually displaying the first fragment - one time only
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, MCCHome.newInstance());
        transaction.commit();

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                selectedFragment = null;
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        selectedFragment = MCCHome.newInstance();
                        fragmentStatus = "home";
                        break;
                    case R.id.navigation_about:
                        selectedFragment = MCCAboutFragment.newInstance();
                        fragmentStatus = "about";
                        break;
                    case R.id.navigation_faq:
                        selectedFragment = MCCFAQFragment.newInstance();
                        fragmentStatus = "about";
                        break;
                }
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, selectedFragment);
                transaction.commit();
                return true;
            }
        });


    }


}

