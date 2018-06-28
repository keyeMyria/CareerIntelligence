package com.altitude.nandom.careerintelligence;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.altitude.nandom.careerintelligence.mcc.MCCDashboard;
import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.CustomTypeAdapter;
import com.apollographql.apollo.exception.ApolloException;

import javax.annotation.Nonnull;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import type.CustomType;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    CardView mccCard, ciCard, jpCard, pretCard;

    // ApolloClient
    private static ApolloClient myApolloClient;

    private String name;

    private static final String BASE_URL = "http://mcc-backend.herokuapp.com/graphql";

    private String token;

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

        ciCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSecret2();
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

    private void getSecret2(){
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(chain -> {

            Request original = chain.request();

            // Request customization: add request headers
            Request.Builder requestBuilder = original.newBuilder().method(original.method(), original.body());
            requestBuilder.header("Authorization", "Bearer "+token); // <-- this is the important line

            return chain.proceed(requestBuilder.build());
        }).build();

        OkHttpClient client = httpClient.build();

        CustomTypeAdapter<String> customTypeAdapter = new CustomTypeAdapter<String>() {
            @Override
            public String decode(@Nonnull String value) {
                return value;
            }

            @Nonnull
            @Override
            public String encode(@Nonnull String value) {
                return value;
            }
        };

        myApolloClient = ApolloClient.builder()
                .okHttpClient(client)
                .serverUrl(HttpUrl.parse(BASE_URL))
                .addCustomTypeAdapter(CustomType.MONGOID, customTypeAdapter)
                .build();

        myApolloClient.query(InformationQuery.builder().build()).enqueue(new ApolloCall.Callback<InformationQuery.Data>() {
            @Override
            public void onResponse(@Nonnull com.apollographql.apollo.api.Response<InformationQuery.Data> response) {

                name = response.data().viewerCandidate.candidate.name.first;

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(), name, Toast.LENGTH_SHORT).show();
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
                        Log.d("MainResponse", ""+e);
                    }
                });

            }
        });


    }

}
