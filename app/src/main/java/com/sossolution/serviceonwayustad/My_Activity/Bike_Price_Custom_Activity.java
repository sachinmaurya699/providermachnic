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
import com.sossolution.serviceonwayustad.Model_class.Price_Update;
import com.sossolution.serviceonwayustad.MyAdapter.My_price_update_Adapter;
import com.sossolution.serviceonwayustad.MyInterface.My_price_interface;
import com.sossolution.serviceonwayustad.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Bike_Price_Custom_Activity extends AppCompatActivity
{

    private RecyclerView recyclerView;
    private LinearLayoutManager manager;
    private List<Price_Update> price_updates_list;
    private  String  value;
    private My_price_update_Adapter adapter;
    private ProgressDialog dialog;
    private Button btn_selected;
    private  String item_check;
    private String price_check;
    private String price;
    private String[] bulkupdate;
    private ImageView imageView_back;
    private TextView textView_header;
    private  String delete_list;
    private String multiple_price;
    private Button button_bulk;
    private   String edittext_valuel;
    private String edittext_value;
    private   String  proveder_id;
    private  String message;
    private  String bulk_update_service;
    private ProgressDialog dialog2;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price__custom_);
        imageView_back=findViewById(R.id.back);
        dialog2= new ProgressDialog(this);

        button_bulk=findViewById(R.id.bulk_update);

        SharedPreferences preferences2 = getSharedPreferences("Otp_activity",MODE_PRIVATE);
        value=preferences2.getString("user_id",null);

        button_bulk.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                String service=multiple_price;

                if(service!=null)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Bike_Price_Custom_Activity.this,R.style.DialogTheme);
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

                            edittext_value=editText.getText().toString();

                            SharedPreferences preferences = getSharedPreferences("Bike_price",MODE_PRIVATE);
                            String value=preferences.getString("single_update",null);


                            SharedPreferences preferences2 = getSharedPreferences("Otp_activity",MODE_PRIVATE);
                            proveder_id=preferences2.getString("user_id",null);
                                                    /*
                                                       String selected_data="";

                                                       for(int j = 0; j<adapter.price_updates_list.size();j++ )
                                                       {
                                                           if (adapter.price_updates_list.get(i).getItem_selected())
                                                           {
                                                               if (selected_data.isEmpty())
                                                                   selected_data = adapter.price_updates_list.get(j).getId();
                                                               else
                                                                   selected_data = selected_data + "," + adapter.price_updates_list.get(j).getId();
                                                           }
                                                       }*/

                            String Type="bike";

                            String service=multiple_price;

                            String str = service.replaceAll(",$", "");

                            String Service_update=str;

                            String price_update=edittext_value;

                            //bulk price update......
                            bulk_price_update(Type,Service_update,price_update,proveder_id);

                        }
                    });
                    builder.show();
                }else {


                  /*  LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.custmize_toast, (ViewGroup) findViewById(R.id.custom_toast_layout));
                    TextView tv = (TextView) layout.findViewById(R.id.txtvw);
                    tv.setTextColor(Color.WHITE);
                    tv.setTextSize(15);
                    tv.setText("Please Select the Service");
                    Toast toast = new Toast(getApplicationContext());
                 *//*   toast.setGravity(Gravity.CENTER_VERTICAL, 0, 100);*//*
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(layout);
                    toast.show();*/

                    Toast.makeText(Bike_Price_Custom_Activity.this, "Please Select The Service", Toast.LENGTH_SHORT).show();
                }

            }
        });


        imageView_back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onBackPressed();

            }
        });
        textView_header=findViewById(R.id.header);
        textView_header.setText("Bike Service Price Update");
        imageView_back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onBackPressed();
            }
        });
        recyclerView=findViewById(R.id.my_recyclerview_price);
        recyclerView.setHasFixedSize(true);
        btn_selected=findViewById(R.id.my_price_update);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading.....");
        dialog.show();
        btn_selected.setVisibility(View.GONE);
        btn_selected.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {


                SharedPreferences preferences = getSharedPreferences("Bike_price",MODE_PRIVATE);
                String value=preferences.getString("single_update",null);
                
/*                if(value.equals("0"))
                {
                    Toast.makeText(Bike_Price_Custom_Activity.this, "single", Toast.LENGTH_SHORT).show();

                    String price=price_check;
                    String price_value=price.replace(",","");
                   *//* String service=item_check;
                    String service_value=service.replace(",","");*//*


                    String selected_data="";
                    String selected_data2="";

                    for(int i = 0; i<adapter.price_updates_list.size();i++ )
                    {
                        if(adapter.price_updates_list.get(i).getItem_selected())
                        {
                            if(selected_data.isEmpty())
                            {
                                selected_data=adapter.price_updates_list.get(i).getId();
                                selected_data2=adapter.price_updates_list.get(i).getPrice_update();
                            }
                            else
                            {
                                selected_data=selected_data+","+adapter.price_updates_list.get(i).getId();
                                selected_data2=selected_data2+","+adapter.price_updates_list.get(i).getPrice_update();

                            }


                        }

                    }
                    price_update(price_value,selected_data);



                }*/
               /* else if(value.equals("1"))
                {*/

                   // Toast.makeText(Bike_Price_Custom_Activity.this, "multiple", Toast.LENGTH_SHORT).show();

                    SharedPreferences preferences2 = getSharedPreferences("Otp_activity",MODE_PRIVATE);
                    String  proveder_id=preferences2.getString("user_id",null);

                    String Type="bike";
                    //String Service=item_check;
                    //String service_update= Service.replaceAll(",$", "");
                    //String Price =price_check;
                   // String[] s1=Price.split(",");
                    //String price_update=s1[0];
                    String selected_data="";
                 for(int i = 0; i<adapter.price_updates_list.size();i++ )
                 {
                if(adapter.price_updates_list.get(i).getItem_selected())
                {
                if(selected_data.isEmpty())
                  selected_data=adapter.price_updates_list.get(i).getId()+"="+adapter.price_updates_list.get(i).getUpdated_price();
              else
                  selected_data=selected_data+","+adapter.price_updates_list.get(i).getId()+"="+adapter.price_updates_list.get(i).getUpdated_price();
                 }

                }

                    Log.d("####selected_data", "onClick: "+selected_data);


                    String list= selected_data;
                  //  String multiple_price_comma_remove= Service.replaceAll(",$", "");

                    all_price_update(proveder_id,Type,list);


                    //Toast.makeText(Bike_Price_Custom_Activity.this, "multi", Toast.LENGTH_SHORT).show();

              //  }
               /* else if(value.equals("2"))
                {
                    SharedPreferences preferences2 = getSharedPreferences("Otp_activity",MODE_PRIVATE);
                    String  proveder_id1=preferences2.getString("user_id",null);



                   // all_price_update(proveder_id1,Type,list);

                }
                */
                //Toast.makeText(Bike_Price_Custom_Activity.this, "Service_Selected", Toast.LENGTH_SHORT).show();


             /*   String type="bike";
                String pid="115";
              //  String price="200";
                String price=price_check;
                String price_value=price.replace(",","");
                String service=item_check;
                String service_value=service.replace(",","");


               price_update(price_value,service_value);*/
                 // price_update(type,service,price_check,pid);

               // add_service_api(provider_id_value,item_check);

            }
        });
        manager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        price_updates_list= new ArrayList<>();
       /* dialog= new ProgressDialog(this);
*/



        String User_id=value;
        String Type="bike";
        String Sindex="0";
        String Eindex="5000";


        show_data_details(User_id,Type,Sindex,Eindex);

    }

    private void all_price_update(String pid,String type,String list)
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

                    Toast.makeText(Bike_Price_Custom_Activity.this, "Price Update Successfull", Toast.LENGTH_SHORT).show();

/*

                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.custmize_toast, (ViewGroup) findViewById(R.id.custom_toast_layout));
                    TextView tv = (TextView) layout.findViewById(R.id.txtvw);
                    tv.setTextColor(Color.WHITE);
                    tv.setTextSize(18);
                    tv.setText("Price Update Successfull");
                    Toast toast = new Toast(getApplicationContext());
                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 100);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(layout);
                    toast.show();
*/


                    dialog2.setTitle("Loading......");
                    dialog2.show();



                    SharedPreferences preferences2 = getSharedPreferences("Otp_activity",MODE_PRIVATE);
                    value=preferences2.getString("user_id",null);




                    String User_id=value;
                    String Type="bike";
                    String Sindex="0";
                    String Eindex="5000";

                    price_updates_list.clear();
                    show_data_details(User_id,Type,Sindex,Eindex);



                    //Toast.makeText(Bike_Price_Custom_Activity.this, "update price", Toast.LENGTH_SHORT).show();
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

    private void bulk_price_update(String type,String service,String price,String pid)
    {
        String url="http://www.serviceonway.com/UpdateBulkServicePrice?type="+type+"&service="+service+"&price="+price+"&pid="+pid;

        Log.d("get_url_bulk",url);

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {

                Log.d("responce",response);

                 message=response;
                if(message.equals("success"))
                {

                    Toast.makeText(Bike_Price_Custom_Activity.this, "Bulk Price Update Successfull", Toast.LENGTH_SHORT).show();
                   /* LayoutInflater inflater = getLayoutInflater();
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
                    String Type1="bike";
                    String Sindex1="0";
                    String Eindex1="5000";



                    price_updates_list.clear();
                    show_data_details(User_id1,Type1,Sindex1,Eindex1);





                }else
                {
                    Toast.makeText(Bike_Price_Custom_Activity.this, "something went wrong", Toast.LENGTH_SHORT).show();
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

    private void price_update(String Price,String Service)
    {

        //String url="http://www.serviceonway.com/UpdateBulkServicePrice?type="+type+"&service="+service+"&price="+price+"&pid="+pid;

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

                    Intent intent = new Intent(Bike_Price_Custom_Activity.this,MainActivity.class);
                    startActivity(intent);
                    finish();

                 //   Toast.makeText(Bike_Price_Custom_Activity.this, "update price", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(Bike_Price_Custom_Activity.this, "Something went worng", Toast.LENGTH_SHORT).show();
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

    private void show_data_details(final String user_id, String type, String Sindex, String Eindex)
    {

        String url="http://www.serviceonway.com/GetServiceProviderCompleteServiceDetails?id="+user_id+"&type="+type+"&Sindex="+Sindex+"&Eindex="+Eindex;
        Log.d("get_url",url);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, url, null, new Response.Listener<JSONArray>()
        {
            @Override
            public void onResponse(JSONArray response)
            {
                btn_selected.setVisibility(View.GONE);
                dialog.dismiss();
                dialog2.dismiss();
                Log.d("responce",response.toString());
             //   Toast.makeText(Bike_Price_Custom_Activity.this,"data"+response, Toast.LENGTH_SHORT).show();
               Log.d("lenght", String.valueOf(response.length()));
               for(int i=0;i<response.length();i++)
                {
                    try {
                        JSONObject jsonObject =response.getJSONObject(i);
                        Log.d("jsonobject",jsonObject.toString());
                        Price_Update price_update= new Price_Update();
                        price=jsonObject.getString("price");
                          price_update.setPrice(jsonObject.getString("price"));
                          price_update.setMaker(jsonObject.getString("maker"));
                          price_update.setModel(jsonObject.getString("model"));
                        price_update.setService(jsonObject.getString("service"));
                        price_update.setId(jsonObject.getString("service_id"));
                        price_update.setPrice_update("update_price");
                        price_updates_list.add(price_update);

                    } catch (JSONException e)
                    {
                        e.printStackTrace();
                        Log.d("get_exception",e.toString());
                     //   Toast.makeText(Bike_Price_Custom_Activity.this, "get_exection"+e.toString(), Toast.LENGTH_SHORT).show();
                    }

                }
                adapter= new My_price_update_Adapter(Bike_Price_Custom_Activity.this, price_updates_list, new My_price_interface()
                {
                    @Override
                    public void my_price_update(Price_Update price_update)
                    {

                    }

                    @Override
                    public void on_item_price_check(String s1)
                    {
                           item_check=s1;

                           Log.d("item_check", String.valueOf(item_check.length()));
                           Log.d("my_price_value",item_check);
                           if(item_check.length()>0)
                           {
                               // btn_selected.setVisibility(View.VISIBLE);
                               // Toast.makeText(Bike_Price_Custom_Activity.this, ""+item_check.length(), Toast.LENGTH_SHORT).show();

                           }
                           else
                           {
                             //  btn_selected.setVisibility(View.GONE);
                           }



                    }

                    @Override
                    public void on_item_price_uncheck(String s2)
                    {

                    }

                    @Override
                    public void my_price_store(String s3)
                    {

                        price_check=s3;

                        Log.d("my_price_value",price_check);
                        if(price_check.length()>0)
                        {
                            btn_selected.setVisibility(View.VISIBLE);


                            if(price_check.length()<=7)
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

                            }

                        }else
                        {
                            btn_selected.setVisibility(View.GONE);
                        }

                    }

                    @Override
                    public void my_delete_list(String s5)
                    {
                        String delete_list=s5;

                        if(delete_list.length()>0)
                        {
                           /* dialog2.setMessage("Loading....");
                            dialog2.show();*/

                            Log.d("detetlist",delete_list);
                            String type="bike";

                            delete_lsit(value,type,delete_list);
                        }





                    }

                    @Override
                    public void on_item_final_price(String s6)
                    {

                         multiple_price=s6;

                         if(multiple_price.length()>0)
                         {
                             String s1="1";
                             SharedPreferences preferences = getSharedPreferences("B_update",MODE_PRIVATE);
                             SharedPreferences.Editor editor =preferences.edit();
                             editor.putString("Bulk_update",s1);
                             editor.apply();

                         }else
                         {
                             String s1="0";
                             SharedPreferences preferences = getSharedPreferences("B_update",MODE_PRIVATE);
                             SharedPreferences.Editor editor =preferences.edit();
                             editor.putString("Bulk_update",s1);
                             editor.apply();
                         }

                        //Log.d("price",price);
                      //  Toast.makeText(Bike_Price_Custom_Activity.this, ""+price, Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void my_price_remove(String s4)
                    {

                    }
                });
                adapter.notifyDataSetChanged();
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

                dialog2.dismiss();
                Log.d("responce",response);

                String message=response;
                if(message.equals("success"))
                {

                    Toast.makeText(Bike_Price_Custom_Activity.this, "delete price successfull", Toast.LENGTH_SHORT).show();


                    dialog2.setTitle("Loading......");
                    dialog2.show();



                    SharedPreferences preferences2 = getSharedPreferences("Otp_activity",MODE_PRIVATE);
                    value=preferences2.getString("user_id",null);

                    String User_id1=value;
                    String Type1="bike";
                    String Sindex1="0";
                    String Eindex1="5000";



                    price_updates_list.clear();
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