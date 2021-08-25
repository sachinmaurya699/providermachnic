package com.sossolution.serviceonwayustad.MyInterface;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface Bank_datails_Interface
{

    String Base_url="http://www.serviceonway.com/";
    @Multipart
    @POST("uploadBankDetails")
    Call<String> get_bank_details_update(
            @Part("pid")RequestBody provider_id,
            @Part("pan_no") RequestBody pan_number,
            @Part("gst_no") RequestBody gst_number,
            @Part("name") RequestBody name,
            @Part("ifsc") RequestBody ifsc,
            @Part("ac_no") RequestBody account_number,
            @Part MultipartBody.Part body1,
            @Part MultipartBody.Part body2
    );




}
