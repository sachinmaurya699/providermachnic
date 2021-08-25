package com.sossolution.serviceonwayustad.My_Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import com.sossolution.serviceonwayustad.Model_class.Carprice;
import com.sossolution.serviceonwayustad.MyAdapter.My_Car_price_Adapter;
import com.sossolution.serviceonwayustad.MyInterface.My_car_price_update_interfacr;
import com.sossolution.serviceonwayustad.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Car_price_custom_Activity extends AppCompatActivity
{
    ProgressDialog dialog2;
    RecyclerView recyclerView;
    List<Carprice> car_price_modelList;
    LinearLayoutManager manager;
    My_Car_price_Adapter adapter;
    Button button_selected;
    private  String item_check;
    private String price_check;
    private String price;
    private ImageView imageView_back;
    private TextView text_header;
    private ProgressDialog dialog;
    private   String   value;
    private String multiple_price;
    private Button button_bulk_price;
    private    String  proveder_id;
    private  String edittext_value;
    private String multiple;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_price_custom_);
        button_bulk_price=findViewById(R.id.bulk_update_car_price);
        dialog2=new ProgressDialog(this);
        button_bulk_price.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                String price=multiple_price;

                if(price!=null)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Car_price_custom_Activity.this,R.style.DialogTheme);
                    builder.setTitle("Please Enter The Amount");

                    final View customLayout
                            = getLayoutInflater()
                            .inflate(
                                    R.layout.custom_desing,
                                    null);
                    builder.setView(customLayout);


                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i)
                        {
                            EditText editText  = customLayout.findViewById( R.id.editText);

                            String edittext_value=editText.getText().toString();

                            SharedPreferences preferences = getSharedPreferences("Bike_price",MODE_PRIVATE);
                            String value=preferences.getString("single_update",null);


                            SharedPreferences preferences2 = getSharedPreferences("Otp_activity",MODE_PRIVATE);
                            String  proveder_id=preferences2.getString("user_id",null);


                            String Type="car";

                            String price=multiple_price;

                            String str = price.replaceAll(",$", "");
                            String Service_update=str;

                            String price_update=edittext_value;


                            //  bulk_price_update(Type,Service_update,price_update,proveder_id);

                            bulk_price_update(Type,Service_update,price_update,proveder_id);

                        }
                    });
                    builder.show();

                }else
                {
                    Toast.makeText(Car_price_custom_Activity.this, "Please Select the service", Toast.LENGTH_SHORT).show();
                }



            }
        });


        imageView_back=findViewById(R.id.back);
        imageView_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                onBackPressed();
            }
        });
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading....");
        dialog.show();
        text_header=findViewById(R.id.header);
        text_header.setText("Car Service Price Update");
        imageView_back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onBackPressed();
            }
        });
        recyclerView=findViewById(R.id.car_update_price_recyclerview);
        manager= new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        car_price_modelList= new ArrayList<>();
        button_selected=findViewById(R.id.car_price_update_btn);
        button_selected.setVisibility(View.GONE);
        button_selected.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                SharedPreferences preferences = getSharedPreferences("Car_price",MODE_PRIVATE);
                 multiple=preferences.getString("single_update",null);

             /*   if(value.equals("2"))
                {
                   // Toast.makeText(Car_price_custom_Activity.this, "single", Toast.LENGTH_SHORT).show();

                   String price=price_check;
                    String price_value=price.replace(",","");
                    String service=item_check;
                    String service_value=service.replace(",","");
                    single_price_update(price_value,service_value);


                    Toast.makeText(Car_price_custom_Activity.this, "single", Toast.LENGTH_SHORT).show();

                    String price1=price_check;
                  //  String price_value=price.replace(",","");
                   *//* String service=item_check;
                    String service_value=service.replace(",","");*//*


                    String selected_data="";
                    String selected_data2="";

                    for(int i = 0; i<adapter.car_price_modelList.size();i++ )
                    {
                        if(adapter.car_price_modelList.get(i).getItem_selected_car())
                        {
                            if(selected_data.isEmpty())
                            {
                                selected_data=adapter.car_price_modelList.get(i).getId();
                                selected_data2=adapter.car_price_modelList.get(i).getUpdated_car_price();
                            }
                            else
                            {
                                selected_data=selected_data+","+adapter.car_price_modelList.get(i).getId();
                                selected_data2=selected_data2+","+adapter.car_price_modelList.get(i).getUpdated_car_price();

                            }


                        }

                    }
                    //price_update(price_value,selected_data);
                    price_update(selected_data,price);

                }*/

                   // Toast.makeText(Car_price_custom_Activity.this, "multiple", Toast.LENGTH_SHORT).show();

                    SharedPreferences preferences2 = getSharedPreferences("Otp_activity",MODE_PRIVATE);
                    String  proveder_id=preferences2.getString("user_id",null);

                /*    String Type="car";
                    String Service=item_check;
                    String service_update= Service.replaceAll(",$", "");
                    String Price =price_check;
                    String[] s1=Price.split(",");
                    String price_update=s1[0];
                    String list= multiple_price;*/



                    //String Service=item_check;
                    //String service_update= Service.replaceAll(",$", "");
                    //String Price =price_check;
                    // String[] s1=Price.split(",");
                    //String price_update=s1[0];

                    String selected_data="";
                    for(int i = 0; i<adapter.car_price_modelList.size();i++ )
                    {
                        if (adapter.car_price_modelList.get(i).getItem_selected_car())
                        {
                            if (selected_data.isEmpty())
                                selected_data = adapter.car_price_modelList.get(i).getId() + "=" + adapter.car_price_modelList.get(i).getUpdated_car_price();
                            else
                                selected_data = selected_data + "," + adapter.car_price_modelList.get(i).getId() + "=" + adapter.car_price_modelList.get(i).getUpdated_car_price();
                        }
                    }

                    String Type="car";
                    String list_item=selected_data;


                   // multiple_price_update(Type,service_update,price_update,proveder_id);
                     my_multiple_price_update(proveder_id,Type,list_item);
                  //  Toast.makeText(Car_price_custom_Activity.this, "multi", Toast.LENGTH_SHORT).show();



            }
        });

       // button=findViewById(R.id.car_update_price);

       // my_price_update();

        SharedPreferences preferences2 = getSharedPreferences("Otp_activity",MODE_PRIVATE);
        value=preferences2.getString("user_id",null);

        String User_id=value;
        String Type="car";
        String Sindex="0";
        String Eindex="5000";

        show_data_details(User_id,Type,Sindex,Eindex);

    }

    private void price_update(String Price,String Service)
    {

        String url="http://www.serviceonway.com/updateBikeServicePrice?price="+Price+"&bike_service="+Service;
        Log.d("get_price_update",url);
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                Log.d("responce",response);

                String message=response;
                if(message.equals("updated"))
                {

                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.custmize_toast, (ViewGroup) findViewById(R.id.custom_toast_layout));
                    TextView tv = (TextView) layout.findViewById(R.id.txtvw);
                    tv.setTextColor(Color.WHITE);
                    tv.setTextSize(18);
                    tv.setText("Single Price Update Successfull");
                    Toast toast = new Toast(getApplicationContext());
                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 100);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(layout);
                    toast.show();

                    Intent intent = new Intent(Car_price_custom_Activity.this,MainActivity.class);
                    startActivity(intent);
                    finish();

                    //   Toast.makeText(Bike_Price_Custom_Activity.this, "update price", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(Car_price_custom_Activity.this, "Something went worng", Toast.LENGTH_SHORT).show();
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
        Volley.newRequestQueue(this).add(request);
    }

    private void

    bulk_price_update(String type, String service, String price, final String pid)
    {

        String url="http://www.serviceonway.com/UpdateBulkServicePrice?type="+type+"&service="+service+"&price="+price+"&pid="+pid;

        Log.d("get_url",url);

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                Log.d("responce",response);

                String message=response;
                if(message.equals("success"))
                {

                    Toast.makeText(Car_price_custom_Activity.this, "Bulk Price Update Successfull", Toast.LENGTH_SHORT).show();

                    /*LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.custmize_toast, (ViewGroup) findViewById(R.id.custom_toast_layout));
                    TextView tv = (TextView) layout.findViewById(R.id.txtvw);
                    tv.setTextColor(Color.WHITE);
                    tv.setTextSize(18);
                    tv.setText("Bulk Price Update Successfull");
                    Toast toast = new Toast(getApplicationContext());
                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 100);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(layout);
                    toast.show();*/

                    dialog2.setTitle("Loading......");
                    dialog2.show();



                    SharedPreferences preferences2 = getSharedPreferences("Otp_activity",MODE_PRIVATE);
                    value=preferences2.getString("user_id",null);

                    String User_id1=value;
                    String Type1="car";
                    String Sindex1="0";
                    String Eindex1="5000";



                    car_price_modelList.clear();
                    show_data_details(User_id1,Type1,Sindex1,Eindex1);

                  /*  Intent intent = new Intent(Car_price_custom_Activity.this,MainActivity.class);
                    startActivity(intent);
                    finish();*/

                }else
                {
                    Toast.makeText(Car_price_custom_Activity.this, "something went wrong", Toast.LENGTH_SHORT).show();
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
        Volley.newRequestQueue(this).add(request);

    }

    private void my_multiple_price_update(String pid,String type,String list)
    {

        String url="http://www.serviceonway.com/Update_Provider_All_Service_Price?pid="+pid+"&type="+type+"&list="+list;
        Log.d("get_url",url);

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                Log.d("responce",response);

                String message=response;
                if(message.equals("success"))
                {

                    Toast.makeText(Car_price_custom_Activity.this, "update price", Toast.LENGTH_SHORT).show();


                    dialog2.setTitle("Loading......");
                    dialog2.show();



                    SharedPreferences preferences2 = getSharedPreferences("Otp_activity",MODE_PRIVATE);
                    value=preferences2.getString("user_id",null);

                    String User_id1=value;
                    String Type1="car";
                    String Sindex1="0";
                    String Eindex1="5000";



                    car_price_modelList.clear();
                    show_data_details(User_id1,Type1,Sindex1,Eindex1);



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
        Volley.newRequestQueue(this).add(request);

    }

    private void multiple_price_update(String type,String service,String price,String pid)
    {

        String url="http://www.serviceonway.com/UpdateBulkServicePrice?type="+type+"&service="+service+"&price="+price+"&pid="+pid;

        Log.d("get_url",url);

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                Log.d("responce",response);

                String message=response;
                if(message.equals("success"))
                {
                    Intent intent = new Intent(Car_price_custom_Activity.this,MainActivity.class);
                    startActivity(intent);
                    finish();

                    Toast.makeText(Car_price_custom_Activity.this, "update price", Toast.LENGTH_SHORT).show();
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
        Volley.newRequestQueue(this).add(request);







    }

    private void single_price_update(String price,String service)
    {
        String url="http://www.serviceonway.com/updateCarServicePrice?price="+price+"&car_service="+service;

        Log.d("get_price_update",url);
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                Log.d("responce",response);

                String message=response;
                if(message.equals("updated"))
                {
                    Intent intent = new Intent(Car_price_custom_Activity.this,MainActivity.class);
                    startActivity(intent);

                    Toast.makeText(Car_price_custom_Activity.this, "update price", Toast.LENGTH_SHORT).show();
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
        Volley.newRequestQueue(this).add(request);


    }


    private void show_data_details(String user_id, String type, final String Sindex, String Eindex)
    {
        String url="http://www.serviceonway.com/GetServiceProviderCompleteServiceDetails?id="+user_id+"&type="+type+"&Sindex="+Sindex+"&Eindex="+Eindex;
        Log.d("get_url",url);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, url, null, new Response.Listener<JSONArray>()
        {
            @Override
            public void onResponse(JSONArray response)
            {
                dialog2.dismiss();
                dialog.dismiss();
                //add code
                button_selected.setVisibility(View.GONE);
                Log.d("car_responce",response.toString());
                Log.d("my_price_item_show",String.valueOf(response.length()));
               // Toast.makeText(Car_price_custom_Activity.this,"data"+response, Toast.LENGTH_SHORT).show();
                Log.d("lenght", String.valueOf(response.length()));

                if(response.length()>0)
                {

                    for(int i=0;i<response.length();i++)
                    {
                        try {
                            JSONObject jsonObject =response.getJSONObject(i);
                            Log.d("jsonobject",jsonObject.toString());
                            Carprice carprice= new Carprice();
                            carprice.setMaker(jsonObject.getString("maker"));
                            carprice.setMOdel(jsonObject.getString("model"));
                            carprice.setService(jsonObject.getString("service"));
                            carprice.setPrice(jsonObject.getString("price"));
                            carprice.setId(jsonObject.getString("service_id"));
                            car_price_modelList.add(carprice);


                        } catch (JSONException e)
                        {
                            e.printStackTrace();
                            Log.d("get_exception",e.toString());
                          //  Toast.makeText(Car_price_custom_Activity.this, "get_exection"+e.toString(), Toast.LENGTH_SHORT).show();
                        }

                    }
                }else
                {
                    Toast.makeText(Car_price_custom_Activity.this, "service not avilable", Toast.LENGTH_SHORT).show();
                }


                adapter= new My_Car_price_Adapter(getApplicationContext(), car_price_modelList, new My_car_price_update_interfacr()
                {
                    @Override
                    public void my_car_price_update(Carprice carprice)
                    {

                    }

                    @Override
                    public void on_item_car_price_check(String s1)
                    {
                        item_check=s1;
                        Log.d("my_price_value", String.valueOf(item_check.length()));

                        if(item_check.length()>0)
                        {


                            String s2="3";
                            SharedPreferences preferences = getSharedPreferences("Car_price",MODE_PRIVATE);
                            SharedPreferences.Editor editor=preferences.edit();
                            editor.putString("single_update",s2);
                            editor.apply();
                          //  Toast.makeText(Car_price_custom_Activity.this, "more one id", Toast.LENGTH_SHORT).show();








                            //Toast.makeText(Car_price_custom_Activity.this, ""+item_check.length(), Toast.LENGTH_SHORT).show();

                           /* if(item_check.length()<=8)
                            {
                              //  Toast.makeText(Car_price_custom_Activity.this, "one id", Toast.LENGTH_SHORT).show();
                                String s3="2";
                                SharedPreferences preferences = getSharedPreferences("Car_price",MODE_PRIVATE);
                                SharedPreferences.Editor editor=preferences.edit();
                                editor.putString("single_update",s3);
                                editor.apply();

                            }else
                            {

                                String s2="3";
                                SharedPreferences preferences = getSharedPreferences("Car_price",MODE_PRIVATE);
                                SharedPreferences.Editor editor=preferences.edit();
                                editor.putString("single_update",s2);
                                editor.apply();
                                Toast.makeText(Car_price_custom_Activity.this, "more one id", Toast.LENGTH_SHORT).show();

                            }*/

                            Toast.makeText(Car_price_custom_Activity.this, "value"+s1, Toast.LENGTH_SHORT).show();
                           // button_selected.setVisibility(View.VISIBLE);
                        }
                        else
                        {
                           // button_selected.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void my_car_price_store(String s2)
                    {
                        price_check=s2;
                        Log.d("my_price_value",price_check);
                        if(price_check.length()>0)
                        {
                           // Toast.makeText(Car_price_custom_Activity.this, "value"+s2, Toast.LENGTH_SHORT).show();
                            //button_selected.setVisibility(View.VISIBLE);
                        }else
                        {
                            //button_selected.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void my_delete_list(String s3)
                    {
                        String delete_list=s3;
                       /* dialog2.setMessage("Loading....");
                        dialog2.show();*/
                       // Log.d("delete_value",delete_list);
                        String type="car";

                        delete_lsit(value,type,delete_list);



                    }

                    @Override
                    public void on_item_final_price_car(String s4)
                    {
                        multiple_price=s4;
                        Log.d("get_price_car",multiple_price);
                      //  Toast.makeText(Car_price_custom_Activity.this, ""+multiple_price, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void my_price_store_car(String s5)
                    {


                        String my_price=s5;
                        if(my_price.length()>0)
                        {

                            button_selected.setVisibility(View.VISIBLE);

                           /* if(price_check.length()<=7)
                            {
                                //Toast.makeText(Bike_Price_Custom_Activity.this, "one id", Toast.LENGTH_SHORT).show();
                                String s1="0";
                                SharedPreferences preferences = getSharedPreferences("Bike_price",MODE_PRIVATE);
                                SharedPreferences.Editor editor=preferences.edit();
                                editor.putString("single_update",s1);
                                editor.apply();

                            }else
                            {

                                String s1="1";
                                SharedPreferences preferences = getSharedPreferences("Bike_price",MODE_PRIVATE);
                                SharedPreferences.Editor editor=preferences.edit();
                                editor.putString("single_update",s1);
                                editor.apply();
                                // Toast.makeText(Bike_Price_Custom_Activity.this, "more one id", Toast.LENGTH_SHORT).show();

                            }*/


                        }else {
                            button_selected.setVisibility(View.GONE);
                        }

                    }

                });


                recyclerView.setAdapter(adapter);

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

    private void delete_lsit(String pid,String type,String sid)
    {
        String url="http://www.serviceonway.com/Delete_Provider_Service?pid="+pid+"&type="+type+"&sid="+sid;

        Log.d("get_url",url);

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                Log.d("responce",response);

                String message=response;
                if(message.equals("success"))
                {
                    Toast.makeText(Car_price_custom_Activity.this, "delete price", Toast.LENGTH_SHORT).show();
                    dialog2.setTitle("Loading......");
                    dialog2.show();



                    SharedPreferences preferences2 = getSharedPreferences("Otp_activity",MODE_PRIVATE);
                    value=preferences2.getString("user_id",null);

                    String User_id1=value;
                    String Type1="car";
                    String Sindex1="0";
                    String Eindex1="5000";



                    car_price_modelList.clear();
                    show_data_details(User_id1,Type1,Sindex1,Eindex1);

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
        Volley.newRequestQueue(this).add(request);



    }


}