package com.sossolution.serviceonwayustad.My_fragment;

import android.app.ProgressDialog;
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
import com.sossolution.serviceonwayustad.MyAdapter.Bike_Service_Adapter;
import com.sossolution.serviceonwayustad.MyAdapter.Car_Service_Adapter;
import com.sossolution.serviceonwayustad.MyInterface.Car_Service_Interface;
import com.sossolution.serviceonwayustad.MyInterface.My_bike_service_interface;
import com.sossolution.serviceonwayustad.My_Activity.MainActivity;
import com.sossolution.serviceonwayustad.My_Activity.Bike_Price_Custom_Activity;
import com.sossolution.serviceonwayustad.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;


public class Bike_Service_Fragment extends Fragment
{
    RecyclerView recyclerView;
    List<My_Bike_Service> my_bike_serviceList;
    Bike_Service_Adapter my_adapter;
    LinearLayoutManager manager;
    ProgressDialog dialog;
    Button btn_selected;
    String  provider_id_value;
    String item_check;
    Button btn_price;
    ImageView imageView_next,imageView_back;
    List<Integer> my_list_item;
    String data1="";
    String data2="";
    int start_dex1;
    int end_dext1;
    TextView textView_count;
    String all_data_show;
    String   increese_length;
    CheckBox checkBox_bike_selectall;
    String  my_data_data1;
    String select_value;
    String    all_data_show1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_bike__service_, container, false);
        recyclerView=view.findViewById(R.id.bike_service);
        show_details();

        my_list_item=new ArrayList<>();
        imageView_back=view.findViewById(R.id.back_icon_bike);
        textView_count=view.findViewById(R.id.data_count_bike);
        checkBox_bike_selectall=view.findViewById(R.id.selecte_all_bike);
        checkBox_bike_selectall.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(isChecked)
                {
                    my_adapter.selectAll();

                    //add code
                    checkBox_bike_selectall.setBackgroundColor(Color.rgb(64, 131, 207));
                    String s2="0";
                    SharedPreferences preferences1=getActivity().getSharedPreferences("my_service",MODE_PRIVATE);
                    SharedPreferences.Editor editor=preferences1.edit();
                    editor.putString("all_selected",s2);
                    editor.apply();
                    //add code
                    if(my_list_item!=null)
                    {
                        my_list_item.clear();
                    }
                    for(int i=0;i<my_bike_serviceList.size();i++)
                    {
                        //setitemcheck(my_bike_serviceList.get(i).getId());
                        setitem_check(my_bike_serviceList.get(i).getId());
                    }
                    data1=finallistvalue();
                    Log.d("data1_value",data1);
                    my_data_data1=data1.replaceAll(",$","");
                    btn_selected.setVisibility(View.VISIBLE);
                }
                else{
                    my_adapter.deselectAll();
                    //add code
                    Toast.makeText(getContext(), "deselect", Toast.LENGTH_SHORT).show();
                    checkBox_bike_selectall.setBackgroundColor(Color.WHITE);
                    btn_selected.setVisibility(View.GONE);
                    //add code
                    for(int i=0;i<my_bike_serviceList.size();i++)
                    {
                        setitem_remove(my_bike_serviceList.get(i).getId());
                    }
                    data2=finallistvalue();
                    Log.d("data2_value",data2);
                }

            }
        });
        /*checkBox_bike_selectall.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                String s1="0";
                SharedPreferences preferences=getActivity().getSharedPreferences("my_service",MODE_PRIVATE);
                SharedPreferences.Editor editor=preferences.edit();
                editor.putString("all_selected",s1);
                editor.apply();


                My_Bike_Service my_all_service= new My_Bike_Service();
                my_all_service.setSelected(true);
                CheckBox checkBox= (CheckBox) v;
                int size = 0;

                if(checkBox.isChecked())
                {

                    checkBox.setBackgroundColor(Color.rgb(64, 131, 207));
                    //size= my_car_serviceList.size();
                    if(my_list_item!=null)
                    {
                        my_list_item.clear();
                    }

                    for(int i=0;i<my_bike_serviceList.size();i++)
                    {
                       setitem_check(my_bike_serviceList.get(i).getId());

                    }
                    data1=finallistvalue();
                    my_data_data1=data1.replaceAll(",$","");

                    Log.d("data1",data1);
                    //add_service_api(provider_id_value,item_check);

                    btn_selected.setVisibility(View.VISIBLE);

                }

            }
        });*/
        show_details();
        String satrt_count="0";
        String end_count="500";

        start_dex1=3500;
        end_dext1=500;

        first_count_data_api(satrt_count,end_count);
        imageView_back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                if(start_dex1==0 && end_dext1==500)
                {
                    Toast.makeText(getActivity(), "not data", Toast.LENGTH_SHORT).show();

                }
                else {
                    Toast.makeText(getActivity(), "Previous_click ", Toast.LENGTH_SHORT).show();
                    dialog.setMessage("Loadingdata.....");
                    dialog.show();
                    //  start_dex1=end_dext1;
                    int final_index=start_dex1-500;
                    int final2=end_dext1;
                    start_dex1=final_index;
                    Log.d("start_index",String.valueOf(start_dex1));
                    end_dext1=final2;
                    Log.d("end_index",String.valueOf(end_dext1));

                    my_bike_serviceList.clear();
                    //   showapi_hit(String.valueOf(start_dex1),String.valueOf(end_dext1));
                    showapi_hit(String.valueOf(start_dex1),String.valueOf(end_dext1));
                    show_details();
                }


            }
        });

         start_dex1=0;
         end_dext1=500;
        imageView_next=view.findViewById(R.id.next_icon_bike);
        imageView_next.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {


                if(start_dex1>=3500)
                {
                    Toast.makeText(getActivity(), "not data", Toast.LENGTH_SHORT).show();
                    textView_count.setText(String.valueOf(start_dex1)+"-"+3835+"/"+   all_data_show1);
                }else
                {
                    Toast.makeText(getActivity(), "next_click", Toast.LENGTH_SHORT).show();
                    dialog.setMessage("Loadingdata.....");
                    dialog.show();
                    //  start_dex1=end_dext1;
                    int final_index=start_dex1+500;
                    int final2=end_dext1;
                    start_dex1=final_index;
                    Log.d("start_index",String.valueOf(start_dex1));
                    end_dext1=final2;
                    Log.d("end_index",String.valueOf(end_dext1));
                    textView_count.setText(String.valueOf(start_dex1)+"-"+String.valueOf(end_dext1)+"/"+   all_data_show1);
                    textView_count.setText(String.valueOf(start_dex1)+"-"+String.valueOf(end_dext1)+"/"+   all_data_show1);
                    my_bike_serviceList.clear();
                    showapi_hit(String.valueOf(start_dex1),String.valueOf(end_dext1));
                    show_details();
                }


            }
        });
        textView_count.setText(String.valueOf(start_dex1)+"-"+String.valueOf(end_dext1)+"/"+all_data_show1);
        recyclerView.setHasFixedSize(true);
        btn_price=view.findViewById(R.id.edit_price);
        btn_price.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                Intent intent = new Intent(getActivity(), Bike_Price_Custom_Activity.class);
                startActivity(intent);

            }
        });
        manager= new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        my_bike_serviceList = new ArrayList<>();
        dialog= new ProgressDialog(getActivity(),R.style.DialogTheme);
        dialog.setMessage("Loading Please Wait.....");
        dialog.show();

        btn_selected=view.findViewById(R.id.bike_service_select);
        btn_selected.setVisibility(View.GONE);

        SharedPreferences preferences2 = getActivity().getSharedPreferences("Otp_activity",MODE_PRIVATE);
        provider_id_value=preferences2.getString("user_id",null);


        //my_price_update();

        btn_selected.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {



                dialog.setMessage("Please wait");
                dialog.show();


                SharedPreferences preferences=getActivity().getSharedPreferences("my_service",MODE_PRIVATE);
                select_value =preferences.getString("all_selected","");

                if(select_value.equals("0"))
                {
                    add_service_api_all(provider_id_value,my_data_data1);
                    Toast.makeText(getActivity(), "multiple selected item", Toast.LENGTH_SHORT).show();
                }
                else if(select_value.equals("1"))
                {
                    Toast.makeText(getActivity(), "single", Toast.LENGTH_SHORT).show();

                    String my_item_check =item_check;

                    String    my_final_value=my_item_check.replaceAll(",$","");

                    Bike_service_add(provider_id_value,my_final_value);
                }
                else
                {
                    Toast.makeText(getActivity(), "something went wrong", Toast.LENGTH_SHORT).show();
                }



            }

            private void Bike_service_add(String provider_id,String bike_service )
            {

                String url="http://www.serviceonway.com/storeProviderBikeService?provider_id="+provider_id+"&bike_service="+bike_service;
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
                            Toast.makeText(getActivity(), "Service select successfull", Toast.LENGTH_SHORT).show();
                            my_bike_serviceList.clear();
                            String start="0";
                            String end="500";
                            first_count_data_api(start,end);
                           // show_details();



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
        });

        show_details();

        return view;
    }

    private void setitem_remove(String s1)
    {
        my_list_item.remove(Integer.valueOf(s1));
    }

    private void showapi_hit(String Start_index,String EndIndex)
    {
        String url="http://www.serviceonway.com/view_bike_service?Sindex="+Start_index+"&Eindex="+EndIndex;
        Log.d("show_data",url);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, url, null, new Response.Listener<JSONArray>()
        {
            @Override
            public void onResponse(JSONArray response)
            {
                dialog.dismiss();
                Log.d("my_bike_responce",response.toString());
                try {
                    JSONArray array=response.getJSONArray(0);
                    for(int i=0;i<array.length();i++)
                    {
                        JSONObject jsonObject= array.getJSONObject(i);
                        My_Bike_Service service= new My_Bike_Service();
                        service.setId(jsonObject.getString("id"));
                        service.setMaker(jsonObject.getString("maker"));
                        service.setService(jsonObject.getString("service"));
                        service.setModel(jsonObject.getString("model"));
                        service.setCharge(jsonObject.getString("charges"));
                        service.setLogo(jsonObject.getString("logo"));
                        service.setDate(jsonObject.getString("date"));
                        my_bike_serviceList.add(service);

                        my_adapter= new   Bike_Service_Adapter(getActivity(), my_bike_serviceList, new My_bike_service_interface()
                        {
                            @Override
                            public void my_item_price(My_Bike_Service my_bike_service)
                            {

                            }

                            @Override
                            public void onItemCheck_bike(String string)
                            {

                                item_check = string;
                                Log.d("item_check_value",item_check);
                                String s1="1";
                                SharedPreferences preferences=getActivity().getSharedPreferences("my_service",MODE_PRIVATE);
                                SharedPreferences.Editor editor=preferences.edit();
                                editor.putString("all_selected",s1);
                                editor.apply();

                                if (item_check.length() > 0)
                                {
                                    btn_selected.setVisibility(View.VISIBLE);
                                }
                                else
                                {
                                    btn_selected.setVisibility(View.GONE);
                                }

                            }

                            @Override
                            public void onItemUncheck_bike(String string)
                            {

                            }
                        });

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
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

    private void add_service_api_all(String provider_id_value, String my_data_data1)
    {

        String url="http://www.serviceonway.com/storeProviderBikeService?provider_id="+provider_id_value+"&bike_service="+my_data_data1;
        Log.d("get_service_url",url);

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                dialog.dismiss();
                Log.d("selected_all_bike_item",response.toString());
                String message=response;
                if(message.equals("success"))
                {
                    Toast.makeText(getActivity(), "Service select successfull", Toast.LENGTH_SHORT).show();
                    my_bike_serviceList.clear();
                    checkBox_bike_selectall.setChecked(false);
                    String start="0";
                    String end="500";
                    first_count_data_api(start,end);
                    //show_details();



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

    private String finallistvalue()
    {
        String my_id = "";

        for (int i : my_list_item)
        {
            my_id +=i+",";


        }

        return my_id;
    }

    private void setitem_check(String s1)
    {

        my_list_item.add(Integer.valueOf(s1));


    }

    private void first_count_data_api(String Signdex,String Enddex)
    {
        String url="http://www.serviceonway.com/view_bike_service?Sindex="+Signdex+"&Eindex="+Enddex;
        Log.d("show_data",url);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, url, null, new Response.Listener<JSONArray>()
        {
            @Override
            public void onResponse(JSONArray response)
            {
                dialog.dismiss();
                btn_selected.setVisibility(View.GONE);
                Log.d("my_bike_responce",response.toString());
                try {
                    JSONArray array=response.getJSONArray(0);
                    for(int i=0;i<array.length();i++)
                    {
                        JSONObject jsonObject= array.getJSONObject(i);
                        My_Bike_Service service= new My_Bike_Service();
                        service.setId(jsonObject.getString("id"));
                        service.setMaker(jsonObject.getString("maker"));
                        service.setService(jsonObject.getString("service"));
                        service.setModel(jsonObject.getString("model"));
                        service.setCharge(jsonObject.getString("charges"));
                        service.setLogo(jsonObject.getString("logo"));
                        service.setDate(jsonObject.getString("date"));
                        my_bike_serviceList.add(service);

                        my_adapter= new   Bike_Service_Adapter(getActivity(), my_bike_serviceList, new My_bike_service_interface()
                        {
                            @Override
                            public void my_item_price(My_Bike_Service my_bike_service)
                            {

                            }

                            @Override
                            public void onItemCheck_bike(String string)
                            {

                                item_check = string;

                                String s1="1";
                                SharedPreferences preferences=getActivity().getSharedPreferences("my_service",MODE_PRIVATE);
                                SharedPreferences.Editor editor=preferences.edit();
                                editor.putString("all_selected",s1);
                                editor.apply();



                                if (item_check.length() > 0)
                                {
                                    btn_selected.setVisibility(View.VISIBLE);
                                }
                                else
                                {
                                    btn_selected.setVisibility(View.GONE);
                                }


                            }

                            @Override
                            public void onItemUncheck_bike(String string)
                            {

                            }
                        });



                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
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

    private void my_price_update(String price,String id)
    {
        String url="http://www.serviceonway.com/updateCarServicePrice?price="+price+"&car_service="+id;

        Log.d("get_url",url);

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                 Log.d("responce",response);

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
        request.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS*48,2
                ,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request.setShouldCache(false);
        Volley.newRequestQueue(getActivity()).add(request);

    }

    private void show_details()
    {
        String url="http://www.serviceonway.com/fetchDataOfBikeService";
        Log.d("url",url);

        final JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response)
            {
                dialog.dismiss();
                Log.d("bike_service",String.valueOf(response.length()));
                all_data_show1=String.valueOf(response.length());
                textView_count.setText(String.valueOf(start_dex1)+"-"+String.valueOf(end_dext1)+"/"+all_data_show1);
/*                 for(int i=0;i<response.length();i++)
                 {
                     try {
                         JSONObject jsonObject= response.getJSONObject(i);
                         My_Bike_Service service= new My_Bike_Service();
                         service.setId(jsonObject.getString("id"));
                         service.setMaker(jsonObject.getString("maker"));
                         service.setService(jsonObject.getString("service"));
                         service.setModel(jsonObject.getString("model"));
                         service.setCharge(jsonObject.getString("charges"));
                         service.setLogo(jsonObject.getString("logo"));
                         service.setDate(jsonObject.getString("date"));
                         my_bike_serviceList.add(service);

                         my_adapter= new Bike_Service_Adapter(getActivity(), my_bike_serviceList, new My_bike_service_interface()
                         {
                             @Override
                             public void my_item_price(My_Bike_Service my_bike_service)
                             {

                             }

                             @Override
                             public void onItemCheck_bike(String string)
                             {
                                 item_check = string;

                                 if (item_check.length() > 0)
                                 {
                                     btn_selected.setVisibility(View.VISIBLE);
                                 }
                                 else
                                 {
                                     btn_selected.setVisibility(View.GONE);
                                 }
                             }

                             @Override
                             public void onItemUncheck_bike(String string)
                             {


                             }
                         });
                         recyclerView.setAdapter(my_adapter);


                     } catch (JSONException e)
                     {
                         e.printStackTrace();
                         Log.d("exception",e.toString());
                     }
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