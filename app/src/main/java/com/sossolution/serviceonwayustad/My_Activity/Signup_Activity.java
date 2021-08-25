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
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

public class Signup_Activity extends AppCompatActivity
{

    Button button;
    EditText edit_grrage_name_id,edit_wonner_id,edit_email_id,edit_phone_number,edit_city_id,edit_local;
    RadioButton radio_1,radio_2;
    RadioGroup group_id;
    String get_number;
    String Grage_Name_id,Wonner_id,Email_id,Phone_number_id,City_id,Local_id,Radio_button1,Radio_button2;
    String  Radio_btn;
    RadioButton radioButton;
    String text;
    String vehicle;
    SessionManager manager;
    ProgressDialog dialog;
    String check;
    String phone_number;
    TextView textView_phone_number;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_);

        SharedPreferences preferences = getSharedPreferences("Login_Activity",MODE_PRIVATE);
         phone_number=preferences.getString("get_number","");
        textView_phone_number=findViewById(R.id.phone_number);
         textView_phone_number.setText(phone_number);

         Log.d("phone_no",phone_number);

        SharedPreferences preferences1= getSharedPreferences("Login_activity",MODE_PRIVATE);
        get_number=preferences1.getString("phone_number","");


        manager = new SessionManager(this);
        dialog= new ProgressDialog(this);
        edit_grrage_name_id=findViewById(R.id.name_id);
        edit_wonner_id=findViewById(R.id.wonner_id);
        edit_email_id=findViewById(R.id.email_id);

        edit_city_id=findViewById(R.id.city_id);
        edit_local=findViewById(R.id.local_id);
        button=findViewById(R.id.signup_id);
        radio_2=findViewById(R.id.radio_2);
        radio_2.isChecked();
        radio_1=findViewById(R.id.radio_1);
        radio_2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                String radio_1="Twov-Wheeler";

                SharedPreferences preferences1 = getSharedPreferences("Wheeler",MODE_PRIVATE);
                SharedPreferences.Editor editor=preferences1.edit();
                editor.putString("radio_btn",radio_1);
                editor.apply();

            }
        });
        radio_1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String radio_2="Four-Wheeler";
                //String radio_1="Four-Wheeler";

                SharedPreferences preferences1 = getSharedPreferences("Wheeler",MODE_PRIVATE);
                SharedPreferences.Editor editor=preferences1.edit();
                editor.putString("radio_btn",radio_2);
                editor.apply();

            }
        });

       // radio_1=findViewById(R.id.radio_1);
        group_id=findViewById(R.id.r_group);



        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dialog.setMessage("Please wait....");
                dialog.show();

                Phone_number_id=phone_number;
                Grage_Name_id=edit_grrage_name_id.getText().toString();
              //  Phone_number_id=edit_phone_number.getText().toString();
                Wonner_id=edit_wonner_id.getText().toString();
                Email_id=edit_email_id.getText().toString();
                City_id=edit_city_id.getText().toString();
                Local_id=edit_local.getText().toString();

                SharedPreferences preferences1 = getSharedPreferences("Wheeler",MODE_PRIVATE);
                 vehicle=preferences1.getString("radio_btn",null);

                 if(vehicle!=null)
                 {
                     check=vehicle;
                 }
                 else {
                     check="four-wheeler";
                 }


                /*............................Signup store value..................*/

                SharedPreferences preferences2= getSharedPreferences("Signup_store_value",MODE_PRIVATE);
                SharedPreferences.Editor editor=preferences2.edit();
                editor.putString("grage_name",Grage_Name_id);
                editor.putString("wonner_name",Wonner_id);
                editor.putString("emial_id",Email_id);
                editor.putString("phone_number",Phone_number_id);
                editor.putString("city_id",City_id);
                editor.putString("Local_id",Local_id);
                editor.putString("wheeler",vehicle);
                editor.apply();


                if(Grage_Name_id.isEmpty())
                {
                    edit_grrage_name_id.setError("Enter the Shop name");
                    edit_grrage_name_id.setFocusable(true);

                }
                else if(Wonner_id.isEmpty())
                {
                    edit_wonner_id.setError("Enter the Shop name");
                    edit_wonner_id.setFocusable(true);

                } else if(Email_id.isEmpty())
                {
                    edit_email_id.setError("Enter the email id");
                    edit_email_id.setFocusable(true);

                }
                else if(City_id.isEmpty())
                {
                    edit_city_id.setError("Enter the city name");
                    edit_city_id.setFocusable(true);
                }
                else if(Local_id.isEmpty())
                {
                    edit_local.setError("Enter the Address");
                    edit_local.setFocusable(true);
                }

                else
                {
                    signupapi(Grage_Name_id,Phone_number_id,Email_id,Wonner_id,check,City_id,Local_id);

                }

            }
        });
    }

    private void signupapi(String name,String contact,String email,String graeg_name,String wheeler,String city,String locality)
    {
        String url ="http://www.serviceonway.com/ProviderSignin?name="+name+"&contact="+contact+"&email="+email+"&graeg_name="+graeg_name+"&wheeler="+wheeler+"&city="+city+"&locality="+locality;
        Log.d("get_user_details",url);

        StringRequest request= new StringRequest(Request.Method.POST, url, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
              String message=response;

              if(message.equals("success"))
              {
                  dialog.dismiss();
                  Log.d("responce","value"+message);

                  Toast.makeText(Signup_Activity.this, "Your successfully", Toast.LENGTH_SHORT).show();

                  generate_otp(phone_number);

                  SharedPreferences preferences2 = getSharedPreferences("Wheeler",MODE_PRIVATE);
                  SharedPreferences.Editor editor=preferences2.edit();
                  editor.clear();
                  editor.apply();

              }


            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
               Log.d("signup_error",error.toString());

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                HashMap<String,String> hashMap = new HashMap<>();
                return hashMap;
            }
        };

       // request.setRetryPolicy(new DefaultRetryPolicy(),DefaultRetryPolicy*48)

        request.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS*48,
                2,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request.setShouldCache(false);
        Volley.newRequestQueue(Signup_Activity.this).add(request);


    }

    public  void  generate_otp(String Phonenumber)
    {
        String url ="http://www.serviceonway.com/GenerateOtp?contact="+Phonenumber;
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
                        Intent intent = new Intent(Signup_Activity.this,Otp_Activity.class);
                        startActivity(intent);
                        finish();


                         String value="1";
                        SharedPreferences preferences = getSharedPreferences("Signup_Activity_new",MODE_PRIVATE);
                        SharedPreferences.Editor editor =preferences.edit();
                        editor.putString("new_user",value);
                        editor.apply();

                    }


                } catch (JSONException e)
                {
                    e.printStackTrace();
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
        request.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS*48,
                2,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request.setShouldCache(false);
        Volley.newRequestQueue(this).add(request);



    }
}