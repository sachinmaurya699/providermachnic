package com.sossolution.serviceonwayustad.My_fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.sossolution.serviceonwayustad.My_Activity.All_Team_member_list_Activity;
import com.sossolution.serviceonwayustad.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class Home_Fragment extends Fragment
{
   // Button button;
   String  value;
   ProgressDialog dialog;

   TextView car_count,bike_count,total_service_count,team_member_count;

   CardView cardView_car_service,cardView_bike_service,cardView_team_member,cardView_total_booking;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_first_, container, false);
        cardView_car_service=view.findViewById(R.id.carview_1);
        cardView_bike_service=view.findViewById(R.id.carview_2);
        cardView_team_member=view.findViewById(R.id.carview_4);
        cardView_total_booking=view.findViewById(R.id.carview_3);
        cardView_total_booking.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Fragment fragment= new History_Fragment();
                FragmentTransaction transaction=getFragmentManager().beginTransaction();
                transaction.replace(R.id.second_container,fragment);
                transaction.commit();
            }
        });
        cardView_team_member.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getActivity(), All_Team_member_list_Activity.class);
                getActivity().startActivity(intent);

            }
        });
        cardView_bike_service.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                Fragment fragment=new Bike_Service_Fragment();
                FragmentTransaction transaction=getFragmentManager().beginTransaction();
                transaction.replace(R.id.second_container,fragment);
                transaction.commit();

            }
        });

        cardView_car_service.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Fragment thirdFragment = new Car_Service_Fragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.second_container, thirdFragment);
                transaction.commit();
            }
        });
        SharedPreferences preferences1 = getActivity().getSharedPreferences("Otp_activity",MODE_PRIVATE);
        value=preferences1.getString("user_id",null);
        dialog= new ProgressDialog(getActivity(),R.style.DialogTheme);
        dialog.setMessage("Loading Please wait...");
        dialog.show();
        car_count=view.findViewById(R.id.car_count);
        bike_count=view.findViewById(R.id.bike_count);
        total_service_count=view.findViewById(R.id.get_total_service);
        team_member_count=view.findViewById(R.id.team_member_count);

        showhomedata(value);

        return view;

    }

    private void showhomedata(String user_id)
    {

        String url = "http://serviceonway.com/getCount?id="+user_id;


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response)
            {
                dialog.dismiss();

                try {
                    String CarCount=response.getString("CarCount");
                    car_count.setText(CarCount);
                    String BikeCount=response.getString("BikeCount");
                    bike_count.setText(BikeCount);
                    String bookingcount=response.getString("BookingCount");
                    total_service_count.setText(bookingcount);
                    String memberCount=response.getString("MemberCount");
                    team_member_count.setText(memberCount);

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
             request.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS*48,
                     2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
             request.setShouldCache(false);
             Volley.newRequestQueue(getActivity()).add(request);
    }
}