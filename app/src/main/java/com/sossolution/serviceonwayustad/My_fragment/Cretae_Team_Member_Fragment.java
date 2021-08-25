package com.sossolution.serviceonwayustad.My_fragment;


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


public class Cretae_Team_Member_Fragment extends Fragment
{
    private EditText editText_name,editText_contact,editText_address,editText_email,editText_password,editText_adhar_number;

   // private String
    private String Name,Contact,Address,Email,Password,Adhar_number;
    private  String provider_id;
    private String value;
    private Button button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        final View view= inflater.inflate(R.layout.fragment_bank_, container, false);

        SharedPreferences preferences1 = getActivity().getSharedPreferences("Otp_activity",MODE_PRIVATE);
         value=preferences1.getString("user_id",null);
         button=view.findViewById(R.id.create_team_btn);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                editText_name=view.findViewById(R.id.name_team);
                editText_contact=view.findViewById(R.id.contact_id);
                editText_address=view.findViewById(R.id.address);
                editText_email=view.findViewById(R.id.email_1);
                editText_password=view.findViewById(R.id.password);
                editText_adhar_number=view.findViewById(R.id.Aadhar_number);

                Name=editText_name.getText().toString();
                Contact=editText_contact.getText().toString();
                Address= editText_address.getText().toString();
                Email= editText_email.getText().toString();
                Password=editText_password.getText().toString();
                Adhar_number=editText_password.getText().toString();

                user_id_details(value);

                if(Name.isEmpty())
                {
                    editText_name.setError("Enter The Name");
                    editText_name.setFocusable(true);
                }else if(Contact.isEmpty())
                {
                    editText_contact.setError("Enter The Phone NUmber");
                    editText_contact.setFocusable(true);
                }else if(Address.isEmpty())
                {
                    editText_address.setError("Enter the Address");
                    editText_address.setFocusable(true);
                }else if(Email.isEmpty())
                {
                    editText_email.setError("Enter The Email");
                    editText_email.setFocusable(true);
                }else if(Password.isEmpty())
                {
                    editText_password.setError("Enter The Password");
                    editText_password.setFocusable(true);
                }else if(Adhar_number.isEmpty())
                {
                    editText_adhar_number.setError("Enter the Aadhar_Number");
                    editText_adhar_number.setFocusable(true);
                }else
                {

                    My_team_creater_api(provider_id,Name,Contact,Email,Address,Adhar_number);


                }

            }
        });

        return view;
    }

    private void user_id_details(String user_id)
    {

        String url="http://www.serviceonway.com/fetchProviderProfileData?id="+user_id;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response)
            {

                Log.d("get_data_request",response.toString());
                for(int i=0;i<=response.length();i++)
                {
                    try {
                        JSONObject object=response.getJSONObject(0);
                        provider_id=object.getString("provider_id");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Log.d("get_error",error.toString());
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

    private void My_team_creater_api(String Provider_id,String name,String contact,String email,String address,String aadhar_number)
    {
        String url="http://www.serviceonway.com/createTeamMember?provider_id="+Provider_id+"&name="+name+"&contact="+contact+"&email="+email+"&address="+address+"&aadhar_number="+aadhar_number;

        Log.d("get_team_url",url);

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                Log.d("responce",response);
                String message =response;
                if(message.equals("success"))
                {
                    editText_name.setText("");
                    editText_contact.setText("");
                    editText_address.setText("");
                    editText_email.setText("");
                    editText_password.setText("");
                    editText_adhar_number.setText("");
                    Toast.makeText(getActivity(), "create team", Toast.LENGTH_SHORT).show();

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
                return super.getParams();
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS*48,
                2,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request.setShouldCache(false);
        Volley.newRequestQueue(getActivity()).add(request);

    }
}