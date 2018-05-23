package com.altitude.nandom.careerintelligence.apolloclient;

import com.apollographql.apollo.ApolloClient;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by Nandom on 5/21/2018.
 */

public class MyApolloClient {

    private static final String BASE_URL = "http://ktt-yez-backend.herokuapp.com/graphql";
    private static ApolloClient myApolloClient;

    public static  ApolloClient getMyApolloClient(){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();

        myApolloClient = ApolloClient.builder()
                .serverUrl(BASE_URL)
                .okHttpClient(okHttpClient)
                .build();

        return myApolloClient;

    }

}
