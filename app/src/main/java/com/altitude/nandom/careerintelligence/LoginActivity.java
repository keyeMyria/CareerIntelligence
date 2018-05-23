package com.altitude.nandom.careerintelligence;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.altitude.nandom.careerintelligence.apolloclient.MyApolloClient;
import com.altitude.nandom.careerintelligence.classes.SessionManager;
import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;

import javax.annotation.Nonnull;

public class LoginActivity extends AppCompatActivity {

    CharSequence[] values = {" Job Seeker "," Employer ", " Career Adviser "};
    private Button bLogin;

    private String Aquestion1, AoptionA, AoptionB, AoptionC, AquestionId;

    private EditText etPhoneNumber, etPassword;

    AlertDialog alertDialog1;

    private EditText etLoginAccount;

    private String jwt, last_name;

    private String appoloError;

    boolean loginCorrect = true;

    private ProgressDialog progressDialog;

    // Session Manager Class
    SessionManager session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        bLogin = (Button) findViewById(R.id.bLogin);

        // Session Manager
        session = new SessionManager(LoginActivity.this);

        etLoginAccount = (EditText) findViewById(R.id.etLoginAccount);
        etPhoneNumber = (EditText) findViewById(R.id.etPhoneNumber);
        etPassword = (EditText) findViewById(R.id.etPassword);

        progressDialog = new ProgressDialog(this);

        etLoginAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateAlertDialogWithRadioButtonGroup();
            }
        });

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String phone = etPhoneNumber.getText().toString();
                String password = etPassword.getText().toString();

                if(phone.isEmpty() || password.isEmpty()){
                    Toast.makeText(LoginActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                }else {

                    progressDialog.setTitle("Please wait");
                    progressDialog.setMessage("Logging in...");
                    progressDialog.show();

                    MyApolloClient.getMyApolloClient().mutate(
                            LoginMutation.builder()
                            .phone(phone)
                            .password(password).build()).enqueue(new ApolloCall.Callback<LoginMutation.Data>() {
                        @Override
                        public void onResponse(@Nonnull Response<LoginMutation.Data> response) {
                            
                            if(response.data().loginCandidate == null){
                               loginCorrect = false; 
                            }else {
                                loginCorrect = true;
                                jwt = response.data().loginCandidate.jwt;
                                last_name = response.data().loginCandidate().name().last;

                                session.createLoginSession(last_name, jwt);
                            }
                                LoginActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        progressDialog.hide();
                                        
                                        if(loginCorrect) {

                                            // Staring MainActivity
                                            Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                            startActivity(i);
                                            finish();
                                        }else{
                                            Toast.makeText(LoginActivity.this, "Invalid Login Credentials", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }


                        @Override
                        public void onFailure(@Nonnull ApolloException e) {
                            appoloError = e.toString();
                            LoginActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    progressDialog.hide();

                                    Log.d("LoginActivityMain: ", appoloError);
                                }
                            });
                        }
                    });

//                Intent loginIntent = new Intent(LoginActivity.this, MainActivity.class);
//                startActivity(loginIntent);
                }
            }
        });

    }

    public void CreateAlertDialogWithRadioButtonGroup(){

        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);

        builder.setTitle("Login as");

        builder.setSingleChoiceItems(values, -1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                etLoginAccount.setText(values[item]);
                alertDialog1.dismiss();
            }
        });
        alertDialog1 = builder.create();
        alertDialog1.show();

    }


}
