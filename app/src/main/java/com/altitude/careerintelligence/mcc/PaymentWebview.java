package com.altitude.careerintelligence.mcc;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

import com.altitude.careerintelligence.classes.SessionManager;
import com.altitude.careerintelligence.R;

import co.paystack.android.Paystack;
import co.paystack.android.PaystackSdk;
import co.paystack.android.Transaction;
import co.paystack.android.model.Card;
import co.paystack.android.model.Charge;

public class PaymentWebview extends AppCompatActivity {

    private WebView paymentWebview;

    private ProgressDialog progressDialog;

    private SessionManager sessionManager;

    private String amount, email;

    private Card card;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_webview);
        PaystackSdk.initialize(PaymentWebview.this);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        amount = intent.getStringExtra("amount");

        paymentWebview = (WebView) findViewById(R.id.paymentWebview);

        progressDialog = new ProgressDialog(this);

        sessionManager = new SessionManager(PaymentWebview.this);


        // This sets up the card and check for validity
        // This is a test card from paystack
        String cardNumber = "4084084084084081";
        int expiryMonth = 11; //any month in the future
        int expiryYear = 18; // any year in the future. '2018' would work also!
        String cvv = "408";  // cvv of the test card

        card = new Card(cardNumber, expiryMonth, expiryYear, cvv);
        if (card.isValid()) {
            // charge card
            performCharge();
        } else {
            //do something
            Log.d("Payment Response: ", "The thing no work at all ooo");
        }



    }

    // This is the subroutine you will call after creating the charge
    // adding a card and setting the access_code
    public void performCharge(){
        //create a Charge object
        Charge charge = new Charge();
        charge.setCard(card); //sets the card to charge

        charge.setAmount(10);
        charge.setCurrency("USD");
        charge.setEmail(email);

        PaystackSdk.chargeCard(PaymentWebview.this, charge, new Paystack.TransactionCallback() {
            @Override
            public void onSuccess(Transaction transaction) {
                // This is called only after transaction is deemed successful.
                // Retrieve the transaction, and send its reference to your server
                // for verification.

                Log.d("Payment Response: ", "Wow this card actually worked");

            }

            @Override
            public void beforeValidate(Transaction transaction) {
                // This is called only before requesting OTP.
                // Save reference so you may send to server. If
                // error occurs with OTP, you should still verify on server.

                Log.d("Payment Response: ", "There was an OTP error");

            }

            @Override
            public void onError(Throwable error, Transaction transaction) {
                //handle error here
                Log.d("Payment Response: ", "There was an OTP error");
            }

        });
    }
}

//    public void processPayment(String amount){
//        // get user data from session
//        HashMap<String, String> user = sessionManager.getUserDetails();
//
//        // email
//        String email = user.get(SessionManager.KEY_EMAIL);
//        String url = "https://cipg.accessbankplc.com/MerchantServices/MakePayment.aspx";
//
//        StringRequest myStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//
//                progressDialog.dismiss();
//                Log.d("Payment Response", response.toString());
//
//                // Get the response content
//                String line = "";
//
//                StringBuilder contentBuilder = new StringBuilder();
////                BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
//                try {
//                    while ((line = rd.readLine()) != null) {
//                        contentBuilder.append(line);
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                String content = contentBuilder.toString();
//
//// Do whatever you want with the content
//
//// Show the web page
//                paymentWebview.loadDataWithBaseURL(url, content, "text/html", "UTF-8", null);
//
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                progressDialog.dismiss();
//                Log.d("Payment Response", error.toString());
//            }
//        }) {
//            protected Map<String, String> getParams(){
//                Map<String, String> MyData = new HashMap<String, String>();
//                MyData.put("mercId", "00037"); //
//                MyData.put("currCode", "566"); //
//                MyData.put("amt", amount); //
//                MyData.put("orderId", "erf43"); //
//                MyData.put("prod", "MCC Test Payment"); //
//                MyData.put("email", email); //
//                MyData.put("gatekey", "VISIN"); //
//
//                return MyData;
//            }
//        };
//
//    }


