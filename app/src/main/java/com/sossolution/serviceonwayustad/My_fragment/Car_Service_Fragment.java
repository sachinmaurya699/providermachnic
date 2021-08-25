package com.sossolution.serviceonwayustad.My_fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;
import com.sossolution.serviceonwayustad.Model_class.My_Bike_Service;
import com.sossolution.serviceonwayustad.Model_class.My_Car_Service;
import com.sossolution.serviceonwayustad.MyAdapter.Car_Service_Adapter;
import com.sossolution.serviceonwayustad.MyInterface.Car_Service_Interface;
import com.sossolution.serviceonwayustad.My_Activity.Car_price_custom_Activity;
import com.sossolution.serviceonwayustad.My_Activity.MainActivity;
import com.sossolution.serviceonwayustad.My_Activity.Signup_Activity;
import com.sossolution.serviceonwayustad.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;


public class Car_Service_Fragment extends Fragment
{

    RecyclerView recyclerView;
    List<My_Car_Service>  my_car_serviceList;
    LinearLayoutManager manager;
    Car_Service_Adapter my_adapter;
    ProgressDialog dialog;
    String item_check;
    Button btn_select;
    String provider_id_value;
    Button btn_price_update;
    private CheckBox checkBox_select_service;
    List<Integer> my_all_service;
    String data1="";
    String data2="";
    String select_value;
    String my_data_data1;
    Button next_btn;
    TextView textView_count;
    String increese_length;
    String Start_index;
    String End_index;
    int end_dext1;
    int start_dex1;
    String data_count_total;
    Button back_button;
    ImageView imageView_next;
    ImageView imageView_back;
    boolean my_b_value;
    String  data_count_total1;
    My_Car_Service my_car_service;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_car__service_, container, false);
        my_all_service= new ArrayList<>();
        imageView_back=view.findViewById(R.id.back_icon);
        my_car_service= new My_Car_Service();

        //start_dex1=6000;
        //end_dext1=6000;
        show_details();
        String Start_endex="0";
        String End_endex="500";
        showapi_hit(Start_endex,End_endex);

        start_dex1=6000;
        end_dext1=0;

        imageView_back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(start_dex1==0 && end_dext1==500)
                {
                    Toast.makeText(getActivity(), "not data", Toast.LENGTH_SHORT).show();
                }else
                {
                    Toast.makeText(getActivity(), "Previous_click ", Toast.LENGTH_SHORT).show();
                    dialog.setMessage("Loadingdata.....");
                    dialog.show();
                    //  start_dex1=end_dext1;
                    int final_index=start_dex1-500;
                    int final2=500;
                    start_dex1=final_index;
                    Log.d("start_index",String.valueOf(start_dex1));
                    end_dext1=final2;
                    Log.d("end_index",String.valueOf(end_dext1));
                    my_car_serviceList.clear();
                    showapi_hit(String.valueOf(start_dex1),String.valueOf(end_dext1));
                    show_details();
                }




            }
        });
        textView_count=view.findViewById(R.id.data_count);
        recyclerView=view.findViewById(R.id.recyclerview_car_service);
        imageView_next=view.findViewById(R.id.next_icon);

        String s1="500";
        SharedPreferences preferences=getActivity().getSharedPreferences("my_count",MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("my_amount_value",s1);
        editor.apply();

        start_dex1=0;
        end_dext1=500;

        textView_count.setText(String.valueOf(start_dex1)+"-"+String.valueOf(end_dext1)+"/"+data_count_total1);

        imageView_next.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                if(start_dex1>=6000)
                {
                    Toast.makeText(getActivity(), "data is not avilable", Toast.LENGTH_SHORT).show();

                }else
                {
                    dialog.setMessage("Loadingdata.....");
                    dialog.show();

                    //  start_dex1=end_dext1;
                    int final_index=start_dex1+500;
                    int final2=end_dext1;
                    start_dex1=final_index;
                    Log.d("start_index",String.valueOf(start_dex1));
                    end_dext1=final2;
                    Log.d("end_index",String.valueOf(end_dext1));
                    textView_count.setText(String.valueOf(start_dex1)+"-"+String.valueOf(end_dext1)+"/"+data_count_total1);
                    my_car_serviceList.clear();
                    showapi_hit(String.valueOf(start_dex1),String.valueOf(end_dext1));
                    show_details();
                    //textView_count.setText(String.valueOf(start_dex1)+"-"+String.valueOf(end_dext1)+"/"+data_count_total);

                }

            }
        });
        //run kr ok

        textView_count.setText(String.valueOf(start_dex1)+"-"+String.valueOf(end_dext1)+"/"+data_count_total1);

        recyclerView.setHasFixedSize(true);

//dobara run kr

        checkBox_select_service=view.findViewById(R.id.selecte_all);
        checkBox_select_service.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
    {
        if(isChecked)
        {
            my_adapter.selectAll();
           // my_adapter.selected_store_value();
            //add code
            checkBox_select_service.setBackgroundColor(Color.rgb(64, 131, 207));
            String s2="0";
            SharedPreferences preferences1=getActivity().getSharedPreferences("my_service",MODE_PRIVATE);
            SharedPreferences.Editor editor=preferences1.edit();
            editor.putString("all_selected",s2);
            editor.apply();

            //my_adapter.selectAll_service_id();
            //add code
            if(my_all_service!=null)
            {
                my_all_service.clear();
            }
            for(int i=0;i<my_car_serviceList.size();i++)
            {
                setitemcheck(my_car_serviceList.get(i).getId());
            }
            data1=finallistvalue();
            Log.d("data1_value",data1);
            my_data_data1=data1.replaceAll(",$","");
            btn_select.setVisibility(View.VISIBLE);
            }

        else{
            my_adapter.deselectAll();
           // my_adapter.de_selectAll();
            //add code
            Toast.makeText(getContext(), "deselect", Toast.LENGTH_SHORT).show();
            checkBox_select_service.setBackgroundColor(Color.WHITE);
            btn_select.setVisibility(View.GONE);
            //add code
            for(int i=0;i<my_car_serviceList.size();i++)
            {
                setitemremove(my_car_serviceList.get(i).getId());
            }
            data2=finallistvalue();
            Log.d("data2_value",data2);
               }
               }
               });

        manager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        my_car_serviceList = new ArrayList<>();

        dialog = new ProgressDialog(getActivity(),R.style.DialogTheme);
        dialog.setMessage("Loading Please wait...");
        dialog.show();

        btn_price_update=view.findViewById(R.id.price_update_car);
        btn_price_update.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
               // Toast.makeText(getActivity(), "hello", Toast.LENGTH_SHORT).show();
                 Intent intent = new Intent(getActivity(), Car_price_custom_Activity.class);
                 startActivity(intent);
            }
        });

        SharedPreferences preferences2 = getActivity().getSharedPreferences("Otp_activity",MODE_PRIVATE);
        provider_id_value=preferences2.getString("user_id",null);

        btn_select=view.findViewById(R.id.select_service);
        btn_select.setVisibility(View.GONE);

        show_details();

        btn_select.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
               // Toast.makeText(getActivity(), "Service_Selected", Toast.LENGTH_SHORT).show();

                dialog.setMessage("Please wait");
                dialog.show();

                SharedPreferences preferences=getActivity().getSharedPreferences("my_service",MODE_PRIVATE);
                select_value =preferences.getString("all_selected","");

                if(select_value.equals("0"))
                {
                    //Toast.makeText(getActivity(), "all select", Toast.LENGTH_SHORT).show();
                    //add_service_api_all(provider_id_value,my_data_data1);
                    add_service_api_all(provider_id_value,my_data_data1);

                    Toast.makeText(getActivity(), " multiple select item", Toast.LENGTH_SHORT).show();
                }
                else if(select_value.equals("1"))
                {
                    Toast.makeText(getActivity(), "single", Toast.LENGTH_SHORT).show();
                    add_service_api(provider_id_value,item_check);

                }else
                    {

                    Toast.makeText(getActivity(), "something went wrong", Toast.LENGTH_SHORT).show();
                }


            }

            private void add_service_api(String provider_id,String Car_service)
            {

                String url="http://www.serviceonway.com/storeProviderCarService?provider_id="+provider_id+"&car_service="+Car_service;

                Log.d("get_service_url",url);

                StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        dialog.dismiss();
                       String message=response;
                       Log.d("responce",response.toString());
                       if(message.equals("success"))
                       {
                           Toast.makeText(getActivity(), "Service Select Successfull", Toast.LENGTH_SHORT).show();

                           my_car_serviceList.clear();

                           String Start="0";
                           String end="500";

                           showapi_hit(Start,end);
                           //show_details();
                           //show_details();

                       }
                    }
                }, new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Log.d("All_selecte_error",error.toString());
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
        });

        return view;
    }

    private void setitemremove(String s1)
    {
        my_all_service.remove(Integer.valueOf(s1));
    }

    private void showapi_hit(String Signdex,String Enddex)
    {
        String url="http://www.serviceonway.com/view_car_service?Sindex="+Signdex+"&Eindex="+Enddex;
        Log.d("show_data",url);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, url, null, new Response.Listener<JSONArray>()
        {
            @Override
            public void onResponse(JSONArray response)
            {
                dialog.dismiss();
                btn_select.setVisibility(View.GONE);
              //  Toast.makeText(getActivity(), ""+response.length(), Toast.LENGTH_SHORT).show();
                // textView_count.setText("0"+"-"+increese_length);
                try {
                    JSONArray jsonArray=response.getJSONArray(0);
                   // Log.d("show_data",String.valueOf(jsonArray.length()+"length"));
                    increese_length=String.valueOf(jsonArray.length());
                  //  Toast.makeText(getActivity(),"get_tenght"+jsonArray.length(), Toast.LENGTH_SHORT).show();
                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject object=jsonArray.getJSONObject(i);
                        My_Car_Service service = new My_Car_Service();
                        service.setId(object.getString("id"));
                        service.setMaker(object.getString("maker"));
                        service.setModel(object.getString("model"));
                        service.setService(object.getString("service"));
                        service.setCharge(object.getString("charges"));
                        service.setLogo(object.getString("logo"));
                        service.setDate(object.getString("date"));
                        my_car_serviceList.add(service);

                    }
                   // Toast.makeText(getActivity(),"my_data"+jsonArray.length(), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                my_adapter = new Car_Service_Adapter(getActivity(), my_car_serviceList, new Car_Service_Interface()
                {
                    @Override
                    public void my_item(My_Car_Service my_car_service)
                    {
                        //Toast.makeText(getActivity(), "hello", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onItemCheck(String string)
                    {

                        item_check = string;
                        Log.d("item_check_value",item_check);
                        if (item_check.length() > 0)
                        {
                            String s1="1";
                            SharedPreferences preferences=getActivity().getSharedPreferences("my_service",MODE_PRIVATE);
                            SharedPreferences.Editor editor=preferences.edit();
                            editor.putString("all_selected",s1);
                            editor.apply();

                            btn_select.setVisibility(View.VISIBLE);
                        }
                        else
                        {
                            btn_select.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onItemUncheck(String s3)
                    {


                    }

                    @Override
                    public void onItemallservice(String s1)
                    {
                        Log.d("my_store_id",s1);
                    }

                    @Override
                    public void on_item_all_service(String s2)
                    {
                        Log.d("all_service",s2);
                        Toast.makeText(getActivity(), "remove_id"+2, Toast.LENGTH_SHORT).show();
                    }
                });

                recyclerView.setAdapter(my_adapter);

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
        Volley.newRequestQueue(getActivity()).add(request);


    }

    private void add_service_api_all(String provider_id_value,String  data1)
    {

        String url="http://www.serviceonway.com/storeProviderCarService?provider_id="+provider_id_value+"&car_service="+data1;

        Log.d("get_service_url",url);

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {

                dialog.dismiss();
                String message=response;
                if(message.equals("success"))
                {
                    Toast.makeText(getActivity(), "Service All_Select Successfull", Toast.LENGTH_SHORT).show();
                    checkBox_select_service.setChecked(false);
                    my_car_serviceList.clear();
                    String start="0";
                    String end="500";
                    showapi_hit(start,end);

                }
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Log.d("get_all_service_error",error.toString());
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

    private String finallistvalue()
    {

        String my_id = "";

        for (int i : my_all_service)
        {
            my_id +=i+",";


        }

        return my_id;

    }

    private void setitemcheck(String s1)
    {

        my_all_service.add(Integer.valueOf(s1));

    }


    private void show_details()
    {
        String url ="http://www.serviceonway.com/fetchDataOfCarService";
        Log.d("url",url);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, url, null, new Response.Listener<JSONArray>()
        {
            @Override
            public void onResponse(JSONArray response)
            {
                dialog.dismiss();
                Log.d("car_responce",response.toString());
                data_count_total1=String.valueOf(response.length());
                textView_count.setText(String.valueOf(start_dex1)+"-"+String.valueOf(end_dext1)+"/"+data_count_total1);
                //Log.d("car_responce_data",String.valueOf(response.length()));
                //Toast.makeText(getActivity(), "data"+response, Toast.LENGTH_SHORT).show();
               /* for(int i=0;i<=response.length();i++)
                {
                    try {

                        JSONObject object =response.getJSONObject(i);
                        My_Car_Service service = new My_Car_Service();
                        service.setId(object.getString("id"));
                        service.setMaker(object.getString("maker"));
                        service.setModel(object.getString("model"));
                        service.setService(object.getString("service"));
                        service.setCharge(object.getString("charges"));
                        service.setLogo(object.getString("logo"));
                        service.setDate(object.getString("date"));
                        my_car_serviceList.add(service);


                    } catch (JSONException e)
                    {
                        e.printStackTrace();
                    }

                    my_adapter = new Car_Service_Adapter(getActivity(), my_car_serviceList, new Car_Service_Interface()
                    {
                        @Override
                        public void my_item(My_Car_Service my_car_service)
                        {
                            //Toast.makeText(getActivity(), "hello", Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onItemCheck(String string)
                        {

                            item_check = string;
                            if (item_check.length() > 0)
                            {
                                btn_select.setVisibility(View.VISIBLE);
                            }
                            else
                            {
                                btn_select.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onItemUncheck(String string)
                        {


                        }
                    });

                    recyclerView.setAdapter(my_adapter);
                }*/
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
}