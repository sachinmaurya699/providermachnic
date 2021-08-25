package com.sossolution.serviceonwayustad.Model_class;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sossolution.serviceonwayustad.MyInterface.My_Api_interface;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient
{
    private static RetrofitClient instance = null;
    private My_Api_interface myApi;

    private RetrofitClient()
    {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(5, TimeUnit.MINUTES) // connect timeout
                .writeTimeout(5, TimeUnit.MINUTES) // write timeout
                .readTimeout(5, TimeUnit.MINUTES) // read timeout
                .build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();


        Retrofit retrofit = new Retrofit.Builder().baseUrl(My_Api_interface.Base_url)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        myApi = retrofit.create(My_Api_interface.class);
    }

    public static synchronized RetrofitClient getInstance()
    {
        if (instance == null) {
            instance = new RetrofitClient();
        }
        return instance;
    }

    public My_Api_interface getMyApi()
    {
        return myApi;
    }


}
