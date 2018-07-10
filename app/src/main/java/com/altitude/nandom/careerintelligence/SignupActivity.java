package com.altitude.nandom.careerintelligence;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.altitude.nandom.careerintelligence.apolloclient.MyApolloClient;
import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;

import javax.annotation.Nonnull;

public class SignupActivity extends AppCompatActivity {

    private EditText etFirstName, etLastName, etPhone, etEmail, etPassword, etConfirmPassword;

    private Button bSignup;
    private TextView tvLogin;
    private ProgressDialog progressDialog;

    String errorMessage;

    private Boolean loginCorrect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        etFirstName = (EditText) findViewById(R.id.etRegFName);
        etLastName = (EditText) findViewById(R.id.etRegLName);
        etEmail = (EditText) findViewById(R.id.etRegEmail);
        etPhone = (EditText) findViewById(R.id.etPhoneNumber);
        etPassword = (EditText) findViewById(R.id.etRegPassword);
        etConfirmPassword = (EditText) findViewById(R.id.etConfirmPassword);

        progressDialog = new ProgressDialog(SignupActivity.this);

        bSignup = (Button) findViewById(R.id.bSignup);
        tvLogin = (TextView) findViewById(R.id.tvLogin);

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent(SignupActivity.this, LoginActivity.class);
                loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(loginIntent);
            }
        });

        bSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String firstname = etFirstName.getText().toString();
                String lastname = etLastName.getText().toString();
                String phone = etPhone.getText().toString();
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                String confirmPassword = etConfirmPassword.getText().toString();

                if (firstname.isEmpty() || lastname.isEmpty() || phone.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    Toast.makeText(SignupActivity.this, "All fields are required", Toast.LENGTH_LONG).show();
                } else {
                    if (!password.contentEquals(confirmPassword)) {

                        Toast.makeText(SignupActivity.this, "Passwords do not match. Please check again ", Toast.LENGTH_LONG).show();

                    }else{

                        progressDialog.setTitle("Please wait");
                        progressDialog.setMessage("Logging in...");
                        progressDialog.show();

                        MyApolloClient.getMyApolloClient().mutate(
                                SignUpMutation.builder()
                                        .firstName(firstname)
                                        .lastName(lastname)
                                        .email(email)
                                        .password(password).build()).enqueue(new ApolloCall.Callback<SignUpMutation.Data>() {
                            @Override
                            public void onResponse(@Nonnull Response<SignUpMutation.Data> response) {


                                if(response.data().candidateCreateAccount() == null){
                                    loginCorrect = false;
                                    errorMessage = response.errors().get(0).message();

                                    if (errorMessage == "Email already Exists"){

                                        Log.d("SignupMain: ", errorMessage);

                                    }else if(errorMessage == "Phone already Exists"){

                                        Log.d("SignupMain: ", errorMessage);
                                    }
                                }else{
                                    loginCorrect = true;
                                    Log.d("SignupMain: ", "You have Successfully Registered");
                                }

                                SignupActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        progressDialog.dismiss();


                                        if(loginCorrect) {
                                            Toast.makeText(SignupActivity.this, "Registration Successfull...", Toast.LENGTH_SHORT).show();
                                            Intent loginIntent = new Intent(SignupActivity.this, LoginActivity.class);
                                            loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(loginIntent);
                                        }
                                        else
                                            Toast.makeText(SignupActivity.this, errorMessage, Toast.LENGTH_SHORT).show();


                                    }
                                });
                                
                            }

                            @Override
                            public void onFailure(@Nonnull ApolloException e) {

                                SignupActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        progressDialog.dismiss();
                                        Toast.makeText(SignupActivity.this, "This thing no work ooo" + e.toString(), Toast.LENGTH_LONG).show();
                                    }
                                });

                            }
                        });

                    }
                }
            }
        });
    }
}
