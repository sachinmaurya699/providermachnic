package com.sossolution.serviceonwayustad.My_Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.sossolution.serviceonwayustad.Model_class.My_Model_Class;
import com.sossolution.serviceonwayustad.MyAdapter.SecondAdapter;
import com.sossolution.serviceonwayustad.MyInterface.My_interface;
import com.sossolution.serviceonwayustad.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Maker_Activity extends AppCompatActivity
{
    TextView textView;
    //Fourth_Fragment fragment;
    My_interface click;
    Fragment frag1;
    RecyclerView recyclerView;
    GridLayoutManager gridLayoutManager;
    SecondAdapter towdapter;
    List<My_Model_Class> datalisttwo;
    GridLayout gridLayout;
    String Car;
    String Honda;
    String make;
    TabLayout tabLayout;
    TabItem tabItem;
    String qty;
    String Select;
    String string1;
    String ss;
    ProgressBar progressBar;
    String item;
    ImageView back;
    TextView header;
    String vechicle;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maker_);

        recyclerView=findViewById(R.id.recyclerviewservice);
        progressBar=findViewById(R.id.progressBar_service);
        progressBar.setVisibility(View.VISIBLE);
       // tabLayout=findViewById(R.id.tab_layout);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(Maker_Activity.this, 2,GridLayoutManager.VERTICAL,false));



        SharedPreferences preferences1=getSharedPreferences("vech_name", Context.MODE_PRIVATE);
         vechicle=preferences1.getString("vech","");
         Log.d("vechicle",vechicle);

        datalisttwo= new ArrayList<>();
         Makeritem(vechicle);

    }

    private void Makeritem(String vechicle)
    {
        String url="https://serviceonway.com/Get_All_Maker_Details?veh="+vechicle;
        Log.d("url",url.toString());
        JsonArrayRequest jsonArrayRequest= new JsonArrayRequest(Request.Method.POST, url,
                null, new Response.Listener<JSONArray>()
        {
            @Override
            public void onResponse(JSONArray response)
            {
              //  progressBar.setVisibility(View.GONE);

                for(int i=0;i<response.length();i++)
                {
                    try {
                        JSONObject jsonObject=response.getJSONObject(i);
                        My_Model_Class storedata= new My_Model_Class();
                        storedata.setText(jsonObject.getString("maker"));
                        storedata.setImage(jsonObject.getString("logo"));
                        datalisttwo.add(storedata);

                    } catch (JSONException e)
                    {
                        e.printStackTrace();
                        Log.e("error",e.toString());

                    }
                }
                towdapter=new SecondAdapter(getApplicationContext(),
                        (ArrayList<My_Model_Class>) datalisttwo, new My_interface()
                {
                    @Override
                    public void onItemClick1(My_Model_Class my_model_class)
                    {
                        //model value show doing

                        SharedPreferences mPrefs = getSharedPreferences("makeitem", MODE_PRIVATE);
                        SharedPreferences.Editor editor = mPrefs.edit();
                        editor.putString("bike_car", my_model_class.getText());
                        editor.apply();
                        // Toast.makeText(Maker_Activity.this, "one value", Toast.LENGTH_SHORT).show();

                        Intent intent= new Intent(Maker_Activity.this,Model_Activity.class);
                        startActivity(intent);


                    }
                });
                recyclerView.setAdapter(towdapter);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("error",error.toString());
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                HashMap<String,String> hashMap= new HashMap<String, String>();

                hashMap.put ("Content-Type", "application/json; charset=utf-8");
                // Toast.makeText(Maker_Activity.this,hashMap.toString(), Toast.LENGTH_SHORT).show();
                Log.d("maker",hashMap.toString());
                return hashMap;

            }
        };
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 48,
                2,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        jsonArrayRequest.setShouldCache(false);
        Volley.newRequestQueue(Maker_Activity.this).add(jsonArrayRequest);


    }
}