package com.altitude.nandom.careerintelligence.apolloclient;

import com.apollographql.apollo.ApolloClient;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by Nandom on 5/21/2018.
 */

public class MyApolloClient {

    private static final String BASE_URL = "http://mcc-backend.herokuapp.com/graphql";
    private static ApolloClient myApolloClient;

    public static  ApolloClient getMyApolloClient(){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);


        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();



        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request original = chain.request();
                    Request.Builder builder = original.newBuilder().method(original.method(), original.body());
                    builder.header("Graphql-Access-Token", "kjkjhjkhkhkjkjhkhkjnkjjkjn");
                    return chain.proceed(builder.build());
                })
                .build();


        myApolloClient = ApolloClient.builder()
                .serverUrl(BASE_URL)
                .okHttpClient(httpClient)
                .build();

        return myApolloClient;

    }

}
