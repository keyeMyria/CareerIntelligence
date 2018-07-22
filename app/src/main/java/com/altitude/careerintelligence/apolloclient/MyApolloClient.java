package com.altitude.careerintelligence.apolloclient;

import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.response.CustomTypeAdapter;
import com.apollographql.apollo.response.CustomTypeValue;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Nonnull;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import type.CustomType;

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

    public static  ApolloClient getUsingTokenHeader(String token){
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
            public String decode(@Nonnull CustomTypeValue value) {
                return CustomType.ID.toString();
            }

            @Nonnull
            @Override
            public CustomTypeValue encode(@Nonnull String value) {
                return CustomTypeValue.fromRawValue(value);
            }
        };

        CustomTypeAdapter<Double> doubleCustomTypeAdapter = new CustomTypeAdapter<Double>() {
            @Override
            public Double decode(@Nonnull CustomTypeValue value) {
                return Double.parseDouble(value.toString());
            }

            @Nonnull
            @Override
            public CustomTypeValue encode(@Nonnull Double value) {
                return new CustomTypeValue.GraphQLString(String.valueOf(value));
            }
        };


//        CustomTypeAdapter<Date> dateCustomTypeAdapter = new CustomTypeAdapter<Date>() {
//            @Override public Date decode(CustomTypeValue value) {
//                try {
//                    return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").parse(value.value.toString());
//                } catch (ParseException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//
//            @Override public CustomTypeValue encode(Date value) {
//                return new CustomTypeValue.GraphQLString(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").format(value));
//            }
//        };

        myApolloClient = ApolloClient.builder()
                .okHttpClient(client)
                .serverUrl(HttpUrl.parse(BASE_URL))
                .addCustomTypeAdapter(CustomType.MONGOID, customTypeAdapter)
                .build();

        return myApolloClient;

    }


}
