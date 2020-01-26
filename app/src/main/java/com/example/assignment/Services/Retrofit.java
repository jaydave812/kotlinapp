package com.example.assignment.Services;

import com.example.assignment.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.converter.gson.GsonConverterFactory;

public class Retrofit {
    private static Api_Services api_services = null;

    private static OkHttpClient provideOkHttpClient(HttpLoggingInterceptor interceptor) {
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        okHttpClient.addInterceptor(interceptor);
        okHttpClient.connectTimeout(180, TimeUnit.SECONDS)
                .writeTimeout(180, TimeUnit.SECONDS)
                .readTimeout(180, TimeUnit.SECONDS);
        return okHttpClient.build();
    }

    private static HttpLoggingInterceptor provideLoggingInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.level(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }

    public static Api_Services getApi_services() {
        if (api_services == null) {
            retrofit2.Retrofit retrofit =
                    new retrofit2.Retrofit.Builder()
                            .baseUrl(BuildConfig.BASE_URL)
                            .client(Retrofit.provideOkHttpClient(provideLoggingInterceptor()))
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

            api_services = retrofit.create(Api_Services.class);
        }
        return api_services;
    }

}
