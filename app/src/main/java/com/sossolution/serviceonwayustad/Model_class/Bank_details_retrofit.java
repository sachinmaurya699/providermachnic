package com.sossolution.serviceonwayustad.Model_class;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sossolution.serviceonwayustad.MyInterface.Bank_datails_Interface;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Bank_details_retrofit
{

    public static Bank_details_retrofit instance=null;
    private Bank_datails_Interface bank_datails_interface;


    public Bank_details_retrofit()
    {
        //showdata
        HttpLoggingInterceptor loggingInterceptor= new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        //okhhtop
        OkHttpClient okHttpClient= new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .connectTimeout(5, TimeUnit.MINUTES) // connect timeout
                .writeTimeout(5, TimeUnit.MINUTES) // write timeout
                .readTimeout(5, TimeUnit.MINUTES) // read timeout
                .build();

        //Gson
        Gson gson= new GsonBuilder()
                .setLenient()
                .create();

        //retrofit

        Retrofit retrofit= new Retrofit.Builder().baseUrl(Bank_datails_Interface.Base_url)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();


        bank_datails_interface=retrofit.create(Bank_datails_Interface.class);



    }
    public static synchronized Bank_details_retrofit getInstance()
    {
        if (instance == null) {
            instance = new Bank_details_retrofit();
        }
        return instance;
    }

    public Bank_datails_Interface getMyApi_bank()
    {
        return bank_datails_interface;
    }




}
