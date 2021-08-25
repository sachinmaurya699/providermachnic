package com.sossolution.serviceonwayustad.MyInterface;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface My_Api_interface
{
    String Base_url="http://www.serviceonway.com/";
   // String Base_url="http://bvn.org.in/";
    @Multipart
           // @POST("UploadData")
    @POST("uploadKycDetails")
   Call<String> get_upadte_kyc
    (
            @Part("userid") RequestBody user_id,
            @Part("aadhar_no") RequestBody aadhar_no,
            @Part MultipartBody.Part body1,
            @Part MultipartBody.Part body2
    );


}
