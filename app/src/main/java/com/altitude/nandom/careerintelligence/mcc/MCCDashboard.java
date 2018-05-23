package com.altitude.nandom.careerintelligence.mcc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

import com.altitude.nandom.careerintelligence.R;

public class MCCDashboard extends AppCompatActivity {

    private CardView mccPaymentCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mccdashboard);


        mccPaymentCard = (CardView) findViewById(R.id.mccPaymentCard);

        mccPaymentCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent paymentIntent = new Intent(MCCDashboard.this, MCCPayment.class);
                startActivity(paymentIntent);
            }
        });
    }
}
