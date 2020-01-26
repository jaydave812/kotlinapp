package com.example.assignment.Services;

import com.example.assignment.Utils.AppConstant;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.converter.gson.GsonConverterFactory;

public class Retrofit {
    public static Api_Services api_services = null;

    public static Api_Services getApi_services() {
        if (api_services == null) {
            retrofit2.Retrofit retrofit = new retrofit2.Retrofit.Builder().client(okHttpClient)
                    .baseUrl(AppConstant.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            api_services = retrofit.create(Api_Services.class);
        }
        return api_services;
    }




    final static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(180, TimeUnit.SECONDS)
            .writeTimeout(180, TimeUnit.SECONDS)
            .readTimeout(180, TimeUnit.SECONDS)
            .build();

}
