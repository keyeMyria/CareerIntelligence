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
import android.widget.QuickContactBadge;
import android.widget.TextView;
import android.widget.Toast;

import com.altitude.nandom.careerintelligence.apolloclient.MyApolloClient;
import com.altitude.nandom.careerintelligence.classes.SessionManager;
import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

import javax.annotation.Nonnull;

import static android.provider.ContactsContract.Intents.Insert.EMAIL;

public class LoginActivity extends AppCompatActivity {

    CharSequence[] values = {" Job Seeker ", " Employer ", " Career Adviser "};
    private Button bLogin;

    // Facebook login Button
    LoginButton fbLogin;

    CallbackManager callbackManager;

    private String Aquestion1, AoptionA, AoptionB, AoptionC, AquestionId;

    private TextView tvCreateAccount;

    private EditText etEmail, etPassword;

    private ProgressDialog mDialog;

    AlertDialog alertDialog1;

    private EditText etLoginAccount;

    private String jwt, last_name, email;

    private String appoloError;

    boolean loginCorrect = true;

    private ProgressDialog progressDialog;

    // Session Manager Class
    SessionManager session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Initial Facebook sdk
        FacebookSdk.sdkInitialize(getApplicationContext());

        setContentView(R.layout.activity_login);

        //Initial facebook button
        fbLogin = (LoginButton) findViewById(R.id.fbButton);
        fbLogin.setReadPermissions(Arrays.asList(EMAIL));
        fbLogin.setReadPermissions(Arrays.asList("public_profile", "email"));

        callbackManager = CallbackManager.Factory.create();

//        AccessToken accessToken = AccessToken.getCurrentAccessToken();
//        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
//


        fbLogin.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

//                Toast.makeText(LoginActivity.this, "Welcome "+ loginResult, Toast.LENGTH_SHORT).show();

                mDialog = new ProgressDialog(LoginActivity.this);
                mDialog.setMessage("Retrieving Data....");
                mDialog.show();

                String accesstoken = loginResult.getAccessToken().getToken();

                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        mDialog.dismiss();
                        Log.d("Our Response", response.toString());
                        getData(object);

                    }
                });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, email, name");
                request.setParameters(parameters);
                request.executeAsync();

                Log.d("LoginActivityMain: ", "Login Success" + loginResult.getAccessToken().getUserId() + " " + loginResult.getAccessToken().getToken());

            }

            @Override
            public void onCancel() {

                Toast.makeText(LoginActivity.this, "Facebook Login cancelled", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onError(FacebookException error) {

                Log.d("LoginActivityMain: ", " " + error);
            }

        });

        if (AccessToken.getCurrentAccessToken() != null) {

        }

        bLogin = (Button) findViewById(R.id.bLogin);
        tvCreateAccount = (TextView) findViewById(R.id.tvCreateAccount);

        // Session Manager
        session = new SessionManager(LoginActivity.this);

        etLoginAccount = (EditText) findViewById(R.id.etLoginAccount);
        etEmail = (EditText) findViewById(R.id.etEmail);
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

                email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                } else {
                    progressDialog.setTitle("Please wait");
                    progressDialog.setMessage("Logging in...");
                    progressDialog.show();

                    MyApolloClient.getMyApolloClient().mutate(
                            LoginMutation.builder()
                                    .email(email)
                                    .password(password).build()).enqueue(new ApolloCall.Callback<LoginMutation.Data>() {
                        @Override
                        public void onResponse(@Nonnull Response<LoginMutation.Data> response) {
                            Log.d("LoginActivityMain: ", "There was a good response");

                            if (response.data().loginCandidate == null) {
                                loginCorrect = false;
                            } else {
                                loginCorrect = true;
                                jwt = response.data().loginCandidate.jwt;
                                last_name = response.data().loginCandidate().name().last;
                                email = response.data().loginCandidate.email;

                                session.createLoginSession(last_name, jwt, email, null);
                            }

                            LoginActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    progressDialog.hide();

                                    if (loginCorrect) {

                                        // Staring MainActivity
                                        Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                        startActivity(i);
                                        finish();
                                    } else {
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

                                    Toast.makeText(LoginActivity.this, appoloError+" "+email+" "+password, Toast.LENGTH_LONG).show();

                                    Log.d("LoginActivityMall: ", appoloError+" "+email+" "+password);
                                }
                            });
                        }
                    });
                }
            }
        });

        tvCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signupIntent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(signupIntent);
            }
        });

    }

    private void getData(JSONObject object) {
        try {
            URL profile_picture = new URL("https://graph.facebook.com/" + object.getString("id") + "/picture?width=250&height=250");

            last_name = object.getString("name");

            jwt = object.getString("id");

            email = object.getString("email");

            Toast.makeText(LoginActivity.this, "Welcome " + last_name, Toast.LENGTH_SHORT).show();

            session.createLoginSession(last_name, jwt, email, profile_picture + "");

            Intent fbIntent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(fbIntent);
            finish();


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void CreateAlertDialogWithRadioButtonGroup() {

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

    }
}
