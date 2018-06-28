package com.altitude.nandom.careerintelligence.mcc;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import com.altitude.nandom.careerintelligence.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MCCDashboard extends AppCompatActivity {

    private CardView mccPaymentCard;

    private ProgressDialog progressDialog;

    private String nairaValue = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mccdashboard);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        mccPaymentCard = (CardView) findViewById(R.id.mccPaymentCard);

        progressDialog = new ProgressDialog(MCCDashboard.this);

        RequestQueue ExampleRequestQueue = Volley.newRequestQueue(this);

        mccPaymentCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent paymentIntent = new Intent(MCCDashboard.this, MCCPayment.class);
                startActivity(paymentIntent);

            }
        });

    }
}

