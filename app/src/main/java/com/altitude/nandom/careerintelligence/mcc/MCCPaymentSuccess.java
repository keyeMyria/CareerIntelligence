package com.altitude.nandom.careerintelligence.mcc;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.altitude.nandom.careerintelligence.MainActivity;
import com.altitude.nandom.careerintelligence.R;
import com.altitude.nandom.careerintelligence.SendPaymentMutation;
import com.altitude.nandom.careerintelligence.apolloclient.MyApolloClient;
import com.altitude.nandom.careerintelligence.classes.SessionManager;
import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.wang.avi.AVLoadingIndicatorView;
import com.wang.avi.Indicator;

import org.w3c.dom.Text;

import java.util.HashMap;

import javax.annotation.Nonnull;

public class MCCPaymentSuccess extends AppCompatActivity {

    private Button bCopy;
    private EditText etTestCode;

    private String token, payStackRef, email;

    private Button bDashboard, bTest;

    private String testCode;
    public AVLoadingIndicatorView indicatorView;

    private LinearLayout llCodeLayout;

    private TextView tvCodeStatus;

    // Session Manager Class
    SessionManager session;

    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mccpayment_success);

        Intent intentExtras = getIntent();
        Bundle bundle =  intentExtras.getExtras();

        if(!bundle.isEmpty()){
            if(intentExtras.hasExtra("response")){
                payStackRef = intentExtras.getStringExtra("response");

                Log.d("PaystackResponse", payStackRef);
                Toast.makeText(this, payStackRef, Toast.LENGTH_LONG).show();
            }
        }

        indicatorView = (AVLoadingIndicatorView) findViewById(R.id.indicator);

        tvCodeStatus = (TextView) findViewById(R.id.tvCodeStatus);

        llCodeLayout = (LinearLayout) findViewById(R.id.llCodeLayout);

        bDashboard = (Button) findViewById(R.id.bDashboard);
        bTest = (Button) findViewById(R.id.bTest);

        // Session class instance
        session = new SessionManager(MCCPaymentSuccess.this);

        bCopy = (Button) findViewById(R.id.bCopy);

        etTestCode = (EditText) findViewById(R.id.etTestCode);

        // get user data from session
        HashMap<String, String> user = session.getUserDetails();

        // email
        email = user.get(SessionManager.KEY_EMAIL);

        // token
        token = user.get(SessionManager.KEY_JWT);

        getSecret3(token, payStackRef);


        bDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mccHomeIntent = new Intent(MCCPaymentSuccess.this, MCCDashboard.class);
                startActivity(mccHomeIntent);
                finish();
            }
        });

        bCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String testCode = etTestCode.getText().toString();

                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("testCode", testCode);
                clipboard.setPrimaryClip(clip);

                Toast.makeText(MCCPaymentSuccess.this, "Test Code copied", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Back to Dashboard?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user pressed "yes", then he is allowed to exit from application
                Intent dashbaordIntent = new Intent(MCCPaymentSuccess.this, MCCDashboard.class);
                startActivity(dashbaordIntent);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user select "No", just cancel this dialog and continue with app
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void getSecret3(String token, String paystackReference) {

        MyApolloClient.getUsingTokenHeader(token).mutate(SendPaymentMutation.builder()
        .paystackReference(paystackReference).build()).enqueue(new ApolloCall.Callback<SendPaymentMutation.Data>() {
            @Override
            public void onResponse(@Nonnull com.apollographql.apollo.api.Response<SendPaymentMutation.Data> response) {

                testCode = response.data().candidateFindOrCreatePaymentRecord().testCode().code().toString();
                Log.d("MCCPaymentResponse",  testCode);

                MCCPaymentSuccess.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        progressDialog.dismiss();
                        tvCodeStatus.setText("Your Test code is");
                        indicatorView.hide();
                        etTestCode.setText(testCode);
                        llCodeLayout.setVisibility(View.VISIBLE);
                        bDashboard.setVisibility(View.VISIBLE);
                        bTest.setVisibility(View.VISIBLE);
                    }
                });

            }

            @Override
            public void onFailure(@Nonnull ApolloException e) {

                MCCPaymentSuccess.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        progressDialog.dismiss();
                        e.printStackTrace();
                        indicatorView.hide();
                        llCodeLayout.setVisibility(View.VISIBLE);

                        Toast.makeText(MCCPaymentSuccess.this, e.toString(), Toast.LENGTH_LONG).show();
                        Log.d("MCCPaymentResponse: ", paystackReference);
//                        Toast.makeText(MCCPaymentSuccess.this, token + " "+ paystackReference , Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

    }
}
