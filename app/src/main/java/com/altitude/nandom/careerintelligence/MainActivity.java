package com.altitude.nandom.careerintelligence;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.altitude.nandom.careerintelligence.classes.SessionManager;
import com.altitude.nandom.careerintelligence.mcc.MCCDashboard;
import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.CustomTypeAdapter;
import com.apollographql.apollo.exception.ApolloException;
import com.facebook.login.LoginManager;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.HashMap;

import javax.annotation.Nonnull;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import type.CustomType;

import static com.facebook.HttpMethod.GET;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    CardView mccCard, ciCard, jpCard, pretCard;

    private Fragment selectedFragment = null;

    private FragmentTransaction transaction;

    private String token;

    private String name;
    // Session Manager Class
    SessionManager session;

    private String image_uri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // Session class instance
        session = new SessionManager(MainActivity.this);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        navigationView.setItemIconTintList(null);

        TextView navUsername = (TextView) headerView.findViewById(R.id.tvUsername);
        CircleImageView userImage = (CircleImageView) headerView.findViewById(R.id.profile_image);

        /**
         * Call this function whenever you want to check user login
         * This will redirect user to LoginActivity is he is not
         * logged in
         * */
        session.checkLogin();

        selectedFragment = HomeFragment.newInstance();


        // get user data from session
        HashMap<String, String> user = session.getUserDetails();

        // name
        String name = user.get(SessionManager.KEY_NAME);

        // token
        token = user.get(SessionManager.KEY_JWT);

        // image uri
        image_uri = user.get(SessionManager.KEY_IMAGE_URI);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, selectedFragment);
        transaction.commit();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);

        toggle.syncState();

        navUsername.setText(name);

        if(image_uri != null) {
//            Picasso.with(this).load(image_uri).into(userImage);
        }else{
//            Picasso.with(this).load(R.drawable.default_account).into(userImage);
        }
        navigationView.setNavigationItemSelectedListener(this);



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();

        selectedFragment = null;

        if (id == R.id.nav_ci) {
            // Handle the camera action
        } else if (id == R.id.nav_mcc) {

        }else if (id == R.id.nav_home) {
            selectedFragment = HomeFragment.newInstance();
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout, selectedFragment);
            transaction.commit();
        } else if (id == R.id.nav_jp) {

        } else if (id == R.id.nav_pret) {

        } else if (id == R.id.nav_about) {
            selectedFragment = AboutFragment.newInstance();
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout, selectedFragment);
            transaction.commit();

        } else if (id == R.id.nav_faq) {
            selectedFragment = FAQFragment.newInstance();
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout, selectedFragment);
            transaction.commit();
        } else if (id == R.id.nav_send) {

        } else if (id == R.id.nav_logout) {
            // Clear the session data
            // This will clear all session data and
            // redirect user to LoginActivity

            if(image_uri != null){
                LoginManager.getInstance().logOut();
            }
                session.logoutUser();

        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
