package com.altitude.careerintelligence.pret;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.CompoundButton;

import com.altitude.careerintelligence.R;

public class PRETPayment extends AppCompatActivity {

    private AppCompatCheckBox cbPretAgreement;
    private Button bPretPayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pretpayment);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        cbPretAgreement = (AppCompatCheckBox) findViewById(R.id.cbPretAgreement);
        bPretPayment = (Button) findViewById(R.id.bPretPayment);

        cbPretAgreement.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b) {
                    bPretPayment.setEnabled(true);
                    bPretPayment.setBackground(getDrawable(R.drawable.button_outline_bg));
                    bPretPayment.setTextColor(Color.parseColor("#308F13"));
                    bPretPayment.setClickable(true);
                } else {
                    bPretPayment.setEnabled(false);
                    bPretPayment.setBackground(getDrawable(R.drawable.button_outline_disabled_bg));
                    bPretPayment.setTextColor(Color.parseColor("#bbbbbb"));
                    bPretPayment.setClickable(false);
                }


            }
        });



    }
}
