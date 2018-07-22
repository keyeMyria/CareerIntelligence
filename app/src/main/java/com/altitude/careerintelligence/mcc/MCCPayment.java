package com.altitude.careerintelligence.mcc;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;


import com.altitude.careerintelligence.MainActivity;

import com.altitude.careerintelligence.R;
import com.altitude.careerintelligence.classes.SessionManager;

import com.android.volley.RequestQueue;

import com.android.volley.toolbox.Volley;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MCCPayment extends AppCompatActivity implements Serializable {


    private RequestQueue MyRequestQueue;

    private static ProgressDialog progressDialog;

    private static String nairaValue = "";

    String response = null;



    public static double nairaAmountdouble;



    RequestQueue ExampleRequestQueue;



    private  int myTestAmount;

    private String token, payStackRef;

    private static final String TAG = MainActivity.class.getSimpleName();

    // Session Manager Class
    SessionManager session;

    private double anyAmount;

    private PaymentRecyclerViewAdapter recyclerViewAdapter;

    private String email;

    private List<MCCPaymentModel> paymentList;

    private Double status;

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mccpayment);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        progressDialog = new ProgressDialog(this);

        ExampleRequestQueue = Volley.newRequestQueue(this);

        // Session class instance
        session = new SessionManager(MCCPayment.this);

        // get user data from session
        HashMap<String, String> user = session.getUserDetails();

        // email
        email = user.get(SessionManager.KEY_EMAIL);

        // token
        token = user.get(SessionManager.KEY_JWT);

//        getAmount(token);

        Intent intentExtras = getIntent();
        Bundle bundle = intentExtras.getExtras();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        if (!bundle.isEmpty()) {
            if (intentExtras.hasExtra("testValue")) {
                status = bundle.getDouble("testValue");
//                Toast.makeText(MCCPayment.this, status+"", Toast.LENGTH_SHORT).show();

            }
        }

//        Toast.makeText(MCCPayment.this, nairaValue, Toast.LENGTH_SHORT).show();

        // Initialize the volley request
        MyRequestQueue = Volley.newRequestQueue(this);


//        Toast.makeText(MCCPayment.this, ""+anyAmount, Toast.LENGTH_SHORT).show();



    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new MCCOrderDetailsFragment(), "Order Details");
        adapter.addFragment(new MCCPaymentHistoryFragment(), "Payment History");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment=null;
            switch (position){
                case 0:
                    fragment=new MCCOrderDetailsFragment();
                    Bundle bundle = new Bundle();
                    bundle.putDouble("testAmount", status);
                    fragment.setArguments(bundle);
                    break;
                case 1:
                    fragment=new MCCPaymentHistoryFragment();
                    break;
                default:
                    fragment=null;
                    break;
            }
            return  fragment;
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

//    private void getAmount(String token) {
//
//        progressDialog.setMessage("Please Wait");
//        progressDialog.setCancelable(false);
//        progressDialog.show();
//
//        MyApolloClient.getUsingTokenHeader(token).query(GetPriceQuery.builder().build()).enqueue(new ApolloCall.Callback<GetPriceQuery.Data>() {
//            @Override
//            public void onResponse(@Nonnull com.apollographql.apollo.api.Response<GetPriceQuery.Data> response) {
//
//                testAmount = response.data().price().mccPrice();
//
//                Log.d("MCC Price", String.valueOf(testAmount));
//
//                MCCPayment.this.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        progressDialog.dismiss();
//                        DecimalFormat df = new DecimalFormat("#,###.00");
//                        nairaAmount = (int) Math.ceil(testAmount);
//                        finalAmount = nairaAmount * 1;
//                        tvTotal.setText("N" + df.format(nairaAmount));
//                    }
//                });
//
//            }
//
//            @Override
//            public void onFailure(@Nonnull ApolloException e) {
//
//                Log.d("MCC Price Error", e.toString());
//
//                MCCPayment.this.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        progressDialog.dismiss();
//                        Toast.makeText(MCCPayment.this, " "+ e, Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//        });
//
//    }
//
//    // Method to manually check connection status
//    private boolean checkConnection() {
//        boolean isConnected = ConnectivityReceiver.isConnected();
//        return isConnected;
//
//    }


//    public void getNairaAmount() {
//        String url = "http://apilayer.net/api/live?access_key=abbc7ae80a8e367c0ed80743b0cd73d4&currencies=NGN&format=1";
//        StringRequest ExampleStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                //This code is executed if the server responds, whether or not the response contains data.
//                //The String 'response' contains the server's response.
//
//                try {
//                    JSONObject reader = null;
//                    reader = new JSONObject(response);
//                    JSONObject sys = reader.getJSONObject("quotes");
//                    nairaValue = sys.getString("USDNGN");
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//                MCCPayment.this.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        progressDialog.dismiss();
//                        nairaAmountdouble = Double.parseDouble(nairaValue);
//                        nairaAmount = (int) Math.ceil(nairaAmountdouble);
//                        finalAmount = nairaAmount * 10;
//                        tvTotal.setText("$10.00 (N" + finalAmount + ")");
//                    }
//                });
//
//            }
//        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                //This code is executed if there is an error.
//            }
//        }) {
//            protected Map<String, String> getParams() {
//                Map<String, String> MyData = new HashMap<String, String>();
//                MyData.put("Field", "Value"); //Add the data you'd like to send to the server.
//                return MyData;
//            }
//        };
//
//        ExampleRequestQueue.add(ExampleStringRequest);
//
//    }
//
//
}
