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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sossolution.serviceonwayustad.R;
import com.sossolution.serviceonwayustad.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Otp_Activity extends AppCompatActivity
{

    private Button verfy_button;
    private TextView resend_text;
    private EditText editText_otp;
    private String Phonenumber;
    private   String user_phone_number;
    private String Otp;
    private ProgressDialog dialog;
    private SessionManager manager;
    private String message;
    private String value_new_user;
    private  String grage_name;
    private String Wonner_name;
    private String Emial_id;
    private String phone_number;
    private String  city_id;
    private String Local_id;
    private String Wheeler;
    private String Type;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_2);

        verfy_button=findViewById(R.id.otp_getid);
        resend_text=findViewById(R.id.resend_id);
        editText_otp=findViewById(R.id.otp_id);

        manager = new SessionManager(this);
        dialog= new ProgressDialog(this);


        SharedPreferences preferences = getSharedPreferences("Login_Activity",MODE_PRIVATE);
        user_phone_number=preferences.getString("get_number","");



        SharedPreferences preferences2= getSharedPreferences("Signup_store_value",MODE_PRIVATE);
         grage_name=preferences2.getString("grage_name",null);
         Wonner_name=preferences2.getString("wonner_name",null);
         Emial_id=preferences2.getString("emial_id",null);
         phone_number=preferences2.getString("phone_number",null);
         city_id=preferences2.getString("city_id",null);
         Local_id=preferences2.getString("Local_id",null);
         Wheeler=preferences2.getString("wheeler",null);

        //initialazation();



        verfy_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                /*.................................new user..............................*/

                SharedPreferences preferences1 = getSharedPreferences("Signup_Activity_new",MODE_PRIVATE);
                value_new_user=preferences1.getString("new_user",null);

                if(value_new_user.equals("1"))
                {
                   // Toast.makeText(Otp_Activity.this, "new user", Toast.LENGTH_SHORT).show();

                    dialog = new ProgressDialog(Otp_Activity.this, R.style.MyAlertDialogStyle);
                    dialog.setMessage("Loading data....");
                    dialog.setCancelable(true);
                    dialog.show();

                    Otp=editText_otp.getText().toString();
                    Type="new";

                    verfy_otp_new_user( user_phone_number,Otp,grage_name,Wheeler,grage_name,Emial_id,city_id,Local_id,Type);

                }else if(value_new_user.equals("2"))
                    {
                        //Toast.makeText(Otp_Activity.this, "old_user", Toast.LENGTH_SHORT).show();
                      /*  dialog = new ProgressDialog(Otp_Activity.this, R.style.MyAlertDialogStyle);
                        dialog.setMessage("Loading data....");
                        dialog.setCancelable(true);
                        dialog.show();*/

                        Otp=editText_otp.getText().toString();
                        Type="old";

                        otp_verfy(user_phone_number,Otp,Type);


                    }
                else
                {
                    Toast.makeText(Otp_Activity.this, "somthing went wrong", Toast.LENGTH_SHORT).show();
                }



            }
        });
        resend_text.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //dialog = new ProgressDialog(this, R.style.MyAlertDialogStyle);
                dialog.setMessage("Loading data....");
                dialog.setCancelable(true);
                dialog.show();

                String url ="http://www.serviceonway.com/GenerateOtp?contact="+user_phone_number;
                Log.d("get_otp", url);

                JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        dialog.dismiss();
                        try {
                            String message=response.getString("message");
                            String user_type=response.getString("userType");
                            if(message.equals("success"))
                            {
                                Toast.makeText(Otp_Activity.this, "generate otp", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(Otp_Activity.this, "Somthing Went wrong ", Toast.LENGTH_SHORT).show();
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
                Volley.newRequestQueue(Otp_Activity.this).add(request);
            }
        });
    }

    private void verfy_otp_new_user(String Phonenumber,String otp,String grage_name,String wheeler,String name,String email,String city,String locality,String type)
    {

        String url="http://www.serviceonway.com/CheckSignUpOtp?contact="+Phonenumber+"&otp="+otp+"&graeg_name="+grage_name+"&wheeler="+wheeler+"&name="+name+"&email="+email+"&city="+city+"&locality="+locality+"&type="+type;
        Log.d("otp_verfy",url);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url,null, new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response)
            {

                Log.d("otp_responce",response.toString());

                try {
                    String id =response.getString("id");
                    SharedPreferences preferences1 = getSharedPreferences("Otp_activity",MODE_PRIVATE);
                    SharedPreferences.Editor editor =preferences1.edit();
                    editor.putString("user_id",id);

                    editor.apply();

                  /*  SessionManager user = new SessionManager(Otp_Activity.this);
                    user.setLogin(user_phone_number);*/

                    message=response.getString("message");

                    if(message.equals("success"))
                    {

                        manager.setLogin(user_phone_number);


                        Intent intent = new Intent(Otp_Activity.this,MainActivity.class);
                        startActivity(intent);
                        finish();

                      /*  //old user.......................
                        if(manager.getuser()!=null)
                        {
                            Intent intent = new Intent(Otp_Activity.this,MainActivity.class);
                            startActivity(intent);
                            finish();


                        }else
                        {
                            //new user.......................

                            Intent intent1 = new Intent(Otp_Activity.this,Signup_Activity.class);
                            startActivity(intent1);
                            finish();
                            // Toast.makeText(Otp_Activity.this, "user_null", Toast.LENGTH_SHORT).show();
                        }*/

                    } else if(message.equals("error"))
                    {

                        Toast.makeText(Otp_Activity.this, "Something went wrong", Toast.LENGTH_SHORT).show();

                    }else
                    {
                        Toast.makeText(Otp_Activity.this, "Something went wrong", Toast.LENGTH_SHORT).show();

                    }

                } catch (JSONException e)
                {
                    e.printStackTrace();
                    Log.d("error",e.toString());
                }


            }
        }, new Response.ErrorListener()
        {
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

        request.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS*48,2,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request.setShouldCache(false);
        Volley.newRequestQueue(this).add(request);
    }


    private void otp_verfy(String Phone_num,String Otp,String type)
    {

        String url="http://www.serviceonway.com/CheckSignUpOtp?contact="+Phone_num+"&otp="+Otp+"&type="+type;
         Log.d("otp_verfy",url);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url,null, new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response)
            {

                Log.d("otp_responce",response.toString());

                try {
                    String id =response.getString("id");
                    SharedPreferences preferences1 = getSharedPreferences("Otp_activity",MODE_PRIVATE);
                    SharedPreferences.Editor editor =preferences1.edit();
                    editor.putString("user_id",id);
                    editor.apply();

                  /*  SessionManager user = new SessionManager(Otp_Activity.this);
                    user.setLogin(user_phone_number);*/
                    manager.setLogin(user_phone_number);

                     message=response.getString("message");

                    if(message.equals("success"))
                    {

                        Intent intent = new Intent(Otp_Activity.this,MainActivity.class);
                        startActivity(intent);
                        finish();

                      /*  //old user.......................
                        if(manager.getuser()!=null)
                        {
                            Intent intent = new Intent(Otp_Activity.this,MainActivity.class);
                            startActivity(intent);
                            finish();


                        }else
                        {
                            //new user.......................

                            Intent intent1 = new Intent(Otp_Activity.this,Signup_Activity.class);
                            startActivity(intent1);
                            finish();
                            // Toast.makeText(Otp_Activity.this, "user_null", Toast.LENGTH_SHORT).show();
                        }*/

                    } else if(message.equals("error"))
                    {

                        Toast.makeText(Otp_Activity.this, "Something went wrong", Toast.LENGTH_SHORT).show();

                    }else
                    {
                        Toast.makeText(Otp_Activity.this, "Something went wrong", Toast.LENGTH_SHORT).show();

                    }

                } catch (JSONException e)
                {
                    e.printStackTrace();
                    Log.d("error",e.toString());
                }


                }
        }, new Response.ErrorListener()
         {
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

        request.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS*48,2,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request.setShouldCache(false);
        Volley.newRequestQueue(this).add(request);
    }

}