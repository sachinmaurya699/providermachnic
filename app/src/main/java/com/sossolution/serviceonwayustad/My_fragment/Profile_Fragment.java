package com.sossolution.serviceonwayustad.My_fragment;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sossolution.serviceonwayustad.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class Profile_Fragment extends Fragment
{
     private EditText edit_name,edit_number,edit_email,edit_address,edit_city,edit_wheeler;
     private Button button;
     private Button button1;
     private String Id,Name,Number,Contact,Email,Shop_name,Address,Wheeler,City;
     private String value;
     private String Message;
     private ProgressDialog dialog;
    private String user;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_second_, container,false);


      /*  SharedPreferences preferences1 = getActivity().getSharedPreferences("Otp_activity",MODE_PRIVATE);
        user=preferences1.getString("user",null);*/


            SharedPreferences preferences2 = getActivity().getSharedPreferences("Otp_activity",MODE_PRIVATE);
            value=preferences2.getString("user_id",null);


        //Toast.makeText(getActivity(),value+"data", Toast.LENGTH_SHORT).show();
        dialog = new ProgressDialog(getActivity(),R.style.DialogTheme);
        dialog.setMessage("Loading Please wait...");
        dialog.show();
        show_user_details(value);

       // initilizae1();
        edit_name=view.findViewById(R.id.profile__name);
        edit_name.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                edit_name.setText("");
            }
        });
        edit_number=view.findViewById(R.id.profile_number);
        edit_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit_number.setText("");
            }
        });
        edit_email=view.findViewById(R.id.profile_email);
        edit_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit_email.setText("");
            }
        });
        edit_address=view.findViewById(R.id.profile_address);
        edit_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit_address.setText("");
            }
        });
       // edit_city=view.findViewById(R.id.profile_city);
       // edit_wheeler=view.findViewById(R.id.profile_vechicle);


        button=view.findViewById(R.id.profile_button);
        //show_user_details();
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Name=edit_name.getText().toString();
                Number=edit_number.getText().toString();
                Email=edit_email.getText().toString();
                Address=edit_address.getText().toString();
               // City=edit_city.getText().toString();
               // Wheeler=edit_wheeler.getText().toString();

                update_api(value,Name,Number,Email,Address);
            }
        });
        return view;
    }

    private void show_user_details( String Id)
    {

        String url="http://www.serviceonway.com/fetchProviderProfileData?id="+Id;
        Log.d("get_url",url);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, url, null, new Response.Listener<JSONArray>()
        {

            @Override
            public void onResponse(JSONArray response)
            {
                dialog.dismiss();
                  Log.d("responce",response.toString());

                try {
                    JSONObject jsonObject = response.getJSONObject(0);
                    String Name =jsonObject.getString("name");
                    edit_name.setText(Name);
                    String Contact =jsonObject.getString("contact");
                    edit_number.setText(Contact);
                    String Email =jsonObject.getString("email");
                    edit_email.setText(Email);
                    String Address=jsonObject.getString("address");
                    edit_address.setText(Address);
                    String Wheeler=jsonObject.getString("wheeler");
                  //  edit_wheeler.setText(Wheeler);

                   // String City =jsonObject.getString("");


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
                HashMap<String,String> hashMap = new HashMap<>();
                return hashMap;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS*48,
                2,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request.setShouldCache(false);
        Volley.newRequestQueue(getActivity()).add(request);

    }

    private void update_api(String id,String name,String contact,String email,String address)
    {
        String url="http://www.serviceonway.com/updateProviderProfileData?id="+id+"&name="+name+"&contact="+contact+"&email="+email+"&address=d"+address;

        Log.d("update_profile",url);

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                Log.d("update_responce",response.toString());
                Message=response;
               if(Message.equals("success"))
               {
                   /*Intent intent = new Intent(getActivity(), MainActivity.class);
                   startActivity(intent);*/
                   Toast.makeText(getActivity(), "Update_Profile", Toast.LENGTH_SHORT).show();
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
        Volley.newRequestQueue(getActivity()).add(request);

    }


}