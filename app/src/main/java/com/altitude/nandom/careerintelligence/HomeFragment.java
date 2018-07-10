package com.altitude.nandom.careerintelligence;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.altitude.nandom.careerintelligence.apolloclient.MyApolloClient;
import com.altitude.nandom.careerintelligence.classes.SessionManager;
import com.altitude.nandom.careerintelligence.mcc.MCCDashboard;
import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;


import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.apollographql.apollo.response.CustomTypeAdapter;
import com.apollographql.apollo.response.CustomTypeValue;

import java.util.HashMap;

import javax.annotation.Nonnull;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import type.CustomType;
import type.CustomType;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    CardView mccCard, ciCard, jpCard, pretCard;

    private Boolean isActivated;

    private LinearLayout rlWarning;

    private TextView tvActivationLink;

    // ApolloClient
    private static ApolloClient myApolloClient;

    private String name, activationStatus, email;

    private static final String BASE_URL = "http://mcc-backend.herokuapp.com/graphql";

    private String token;

    private SessionManager sessionManager;

    public static HomeFragment newInstance() {
        // Required empty public constructor
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        mccCard = (CardView) view.findViewById(R.id.mccCard);
        ciCard = (CardView) view.findViewById(R.id.ciCard);
        jpCard = (CardView) view.findViewById(R.id.jpCard);
        pretCard = (CardView) view.findViewById(R.id.pretCard);
        rlWarning = (LinearLayout) view.findViewById(R.id.rlWarning);

        tvActivationLink = (TextView) view.findViewById(R.id.tvActivationLink);

        sessionManager = new SessionManager(getContext());

        // get user data from session
        HashMap<String, String> user = sessionManager.getUserDetails();

        // token
        token = user.get(SessionManager.KEY_JWT);

        getSecret2(token);

        tvActivationLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSecret3(token);
            }
        });


        ciCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSecret2(token);
            }
        });

        mccCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent mccIntent = new Intent(getContext(), MCCDashboard.class);
                startActivity(mccIntent);

            }
        });


        // Inflate the layout for this fragment
        return view;
    }

    private void getSecret2(String token) {

        MyApolloClient.getUsingTokenHeader(token).query(InformationQuery.builder().build()).enqueue(new ApolloCall.Callback<InformationQuery.Data>() {
            @Override
            public void onResponse(@Nonnull com.apollographql.apollo.api.Response<InformationQuery.Data> response) {

                name = response.data().viewerCandidate.candidate.firstName;
                isActivated = response.data().viewerCandidate().candidate.isActivated;

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (!isActivated) {
                            rlWarning.setVisibility(View.VISIBLE);
                        }
//                        Toast.makeText(getContext(), name, Toast.LENGTH_SHORT).show();
                        Log.d("MainResponse", name);
                    }
                });

            }

            @Override
            public void onFailure(@Nonnull ApolloException e) {

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(), "This thing has failed ooo", Toast.LENGTH_SHORT).show();
                        Log.d("MainResponse", "" + e);
                    }
                });

            }
        });


    }

    private void getSecret3(String token) {

        MyApolloClient.getUsingTokenHeader(token).mutate(LinkMutation.builder().build()).enqueue(new ApolloCall.Callback<LinkMutation.Data>() {
            @Override
            public void onResponse(@Nonnull Response<LinkMutation.Data> response) {
                activationStatus = response.data().candidateResendActivationLink().status;


                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (activationStatus.contentEquals("success")) {
                            Toast.makeText(getContext(), "Activation Link sent to email...", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }

            @Override
            public void onFailure(@Nonnull ApolloException e) {

            }
        });
    }
}