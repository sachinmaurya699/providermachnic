package com.sossolution.serviceonwayustad.My_fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.sossolution.serviceonwayustad.Model_class.History_item;
import com.sossolution.serviceonwayustad.MyAdapter.My_history_adapter;
import com.sossolution.serviceonwayustad.MyInterface.History_interface;
import com.sossolution.serviceonwayustad.My_Activity.History_service_details_Activity;
import com.sossolution.serviceonwayustad.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class History_Fragment extends Fragment
{
     private List<History_item>history_itemList;
     private LinearLayoutManager manager;
     private RecyclerView recyclerView;
     private My_history_adapter adapter;
     private ProgressDialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_history_, container, false);
        recyclerView=view.findViewById(R.id.my_history_recyclerview);
        manager= new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        history_itemList= new ArrayList<>();


        SharedPreferences preferences2 = getActivity().getSharedPreferences("Otp_activity",MODE_PRIVATE);
        String value=preferences2.getString("user_id",null);
        my_details_api(value);

        return view;

    }

    private void my_details_api(String id)
    {
        String url="http://www.serviceonway.com/GetProviderBookingDetails?id="+id;
        Log.d("get_url",url);
        JsonArrayRequest  request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response)
            {
                Log.d("history_responce",response.toString());

                for(int i=0;i<response.length();i++)
                {
                    try {
                        JSONObject object =response.getJSONObject(i);
                        History_item item = new History_item();
                        item.setDate(object.getString("date"));
                        item.setAddress(object.getString("user_address"));
                        item.setId(object.getString("booking_id"));
                        item.setPrice(object.getString("price"));
                        item.setService(object.getString("service_include"));
                        history_itemList.add(item);

                        adapter= new My_history_adapter(getActivity(), history_itemList, new History_interface()
                        {
                            @Override
                            public void my_history_item(History_item history_item)
                            {
                                Toast.makeText(getActivity(), "api hit", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(getActivity(), History_service_details_Activity.class);
                                startActivity(intent);

                                SharedPreferences preferences =getActivity().getSharedPreferences("booking",MODE_PRIVATE);
                                SharedPreferences.Editor editor=preferences.edit();
                                editor.putString("booking_id",history_item.getId());
                                editor.apply();


                            }
                        });

                        recyclerView.setAdapter(adapter);

                    } catch (JSONException e)
                    {
                        e.printStackTrace();
                        Log.d("exception",e.toString());
                    }
                }



            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
               Log.d("hist_errror",error.toString());
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