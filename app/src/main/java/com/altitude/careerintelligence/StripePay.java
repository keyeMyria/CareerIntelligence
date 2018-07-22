package com.altitude.careerintelligence;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.altitude.careerintelligence.R;
import com.stripe.android.model.Card;
import com.stripe.android.view.CardMultilineWidget;

public class StripePay extends AppCompatActivity {

//    ErrorDialogHandler mErrorDialogHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stripe_pay);



        CardMultilineWidget mCardInputWidget = (CardMultilineWidget) findViewById(R.id.card_input_widget);

        Card cardToSave = mCardInputWidget.getCard();
        if (cardToSave == null) {
//            mErrorDialogHandler.showError("Invalid Card Data");

            Toast.makeText(StripePay.this, "Invalid Card Data", Toast.LENGTH_SHORT).show();
        }

//        cardToSave.setName("Customer Name");
//        cardToSave.setAddressZip("12345");
    }
}
