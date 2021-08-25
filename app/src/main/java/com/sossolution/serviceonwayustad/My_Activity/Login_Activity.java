package com.sossolution.serviceonwayustad.My_Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.sossolution.serviceonwayustad.R;
import com.sossolution.serviceonwayustad.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login_Activity extends AppCompatActivity
{
    private  Button btn;
    private EditText my_phone_number;
    private String Phone_Number;
    private ProgressDialog dialog;
    private SessionManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_);
        btn=findViewById(R.id.login_btn_id);
        manager = new SessionManager(this);
        my_phone_number=findViewById(R.id.my_number_id);
        dialog= new ProgressDialog(this);
       // dialog.setTitle("Please wait......");


        btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Phone_Number=my_phone_number.getText().toString();
                dialog = new ProgressDialog(Login_Activity.this, R.style.MyAlertDialogStyle);
                dialog.setMessage("Loading data....");
                dialog.setCancelable(true);
                dialog.show();

                SharedPreferences preferences = getSharedPreferences("Login_Activity",MODE_PRIVATE);
                SharedPreferences.Editor editor =preferences.edit();
                editor.putString("get_number",Phone_Number);
                editor.apply();


                if(Phone_Number.isEmpty())
                {
                    my_phone_number.setError("Enter the phone number");
                    my_phone_number.setFocusable(true);
                }else if(Phone_Number.length()<10)
                {
                    my_phone_number.setError("Enter the 10 digit number");

                }else
                {
                    otp_generate_api(Phone_Number);
                }

            }
        });
    }

    private void otp_generate_api(String Phonenumber)
    {

        String url = "http://www.serviceonway.com/GenerateOtp?contact="+Phonenumber;
        Log.d("get_otp", url);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response)
            {
                dialog.dismiss();
                try {
                    String message=response.getString("message");
                    String user_type=response.getString("userType");

                    if(user_type.equals("old"))
                    {

                        String value="2";
                        SharedPreferences preferences = getSharedPreferences("Signup_Activity_new",MODE_PRIVATE);
                        SharedPreferences.Editor editor =preferences.edit();
                        editor.putString("new_user",value);
                        editor.apply();

                        Intent intent = new Intent(Login_Activity.this,Otp_Activity.class);
                        startActivity(intent);
                        finish();

                    }else if(user_type.equals("new"))
                    {
                        Intent intent = new Intent(Login_Activity.this,Signup_Activity.class);
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        Toast.makeText(Login_Activity.this, "Somthing Went wrong ", Toast.LENGTH_SHORT).show();
                    }

                   } catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Log.d("error",error.toString());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                HashMap<String,String> hashMap= new HashMap<>();
                return hashMap;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS*48,
                2,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request.setShouldCache(false);
        Volley.newRequestQueue(this).add(request);


    }
}