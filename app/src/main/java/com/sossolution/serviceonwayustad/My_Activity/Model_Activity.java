package com.sossolution.serviceonwayustad.My_Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;
import com.sossolution.serviceonwayustad.Model_class.Model_item;
import com.sossolution.serviceonwayustad.MyAdapter.ModelAdapter;
import com.sossolution.serviceonwayustad.MyInterface.Mymodel_interface;
import com.sossolution.serviceonwayustad.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Model_Activity extends AppCompatActivity
{
    TextView textView;
    Mymodel_interface click;
    Fragment frag1;
    RecyclerView recyclerView;
    GridLayoutManager gridLayoutManager;
    ModelAdapter makeAdapter;
    List<Model_item> datalistmake;
    GridLayout gridLayout;
    String VEH;
    String Honda;
    String make;
    TabLayout tabLayout;
    String qty;
    ProgressBar progressBar;
    String p1;
    String s1;
    ImageView back;
    TextView header;
    String upload;

    String  vechicle;
    String maker_value;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model_);

        recyclerView=findViewById(R.id.recyclerviewmodemodel);
        progressBar=findViewById(R.id.progressBar_model);
        progressBar.setVisibility(View.VISIBLE);
        // tabLayout=findViewById(R.id.tab_layout);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(Model_Activity.this, 2,GridLayoutManager.VERTICAL,false));



         SharedPreferences preferences1=getSharedPreferences("vech_name", MODE_PRIVATE);
         vechicle=preferences1.getString("vech","");
         Log.d("vechicle",vechicle);

        SharedPreferences mPrefs = getSharedPreferences("makeitem", MODE_PRIVATE);
         maker_value=mPrefs.getString("bike_car","");

        datalistmake= new ArrayList<>();



        hitapimodel(vechicle,maker_value);
    }

    private void hitapimodel(String VEH, String Honda)
    {

        String url="https://serviceonway.com/Get_All_Model_Details?veh="+VEH+"&maker="+Honda;
        Log.d("url",url.toString());
        JsonArrayRequest jsonArrayRequest= new JsonArrayRequest(Request.Method.POST, url,
                null, new Response.Listener<JSONArray>()
        {
            @Override
            public void onResponse(JSONArray response)
            {
                progressBar.setVisibility(View.GONE);
                for(int i=0;i<response.length();i++)
                {
                    try {
                        JSONObject jsonObject=response.getJSONObject(i);
                        String model=jsonObject.getString("model");
                        datalistmake.add(new Model_item(model));

                    } catch (JSONException e)
                    {
                        e.printStackTrace();
                        Log.d("error",e.toString());

                    }
                }
                makeAdapter=new ModelAdapter(getApplicationContext(),
                        (ArrayList<Model_item>) datalistmake,  new Mymodel_interface()
                {
                    @Override
                    public void onItemClick1(Model_item model_items)
                    {
                        Toast.makeText(Model_Activity.this, "model", Toast.LENGTH_SHORT).show();
                        SharedPreferences preferences=getSharedPreferences("value1", MODE_PRIVATE);
                        upload=preferences.getString("value","");
                        Log.d("upload",upload);


                        Intent intent= new Intent(Model_Activity.this,Upload_Activity.class);
                        startActivity(intent);


                       /* if(upload.equals("2"))
                        {
                            Toast.makeText(Model_Activity.this, "upload hai", Toast.LENGTH_SHORT).show();
                            Intent intent= new Intent(Model_Activity.this,Upload_Activity.class);
                            startActivity(intent);


                        }else if(upload.equals("1"))
                        {
                            Toast.makeText(Model_Activity.this, "upload null", Toast.LENGTH_SHORT).show();

                            SharedPreferences mPrefs = getSharedPreferences("model item", MODE_PRIVATE);
                            SharedPreferences.Editor editor = mPrefs.edit();
                            editor.putString("model", newmodel.getModel());
                            editor.apply();

                            if (!VEH.equals("bike")) {
                                Intent intent = new Intent(Model_Activity.this, Fuel_Activity.class);
                                startActivity(intent);

                            } else {
                                Intent intent = new Intent(Model_Activity.this, Service_Activity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                        //add code
                        else if(upload.equals("5"))
                        {
                            Toast.makeText(Model_Activity.this, "formfill", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Model_Activity.this, FormFill_Activity2.class);
                            startActivity(intent);

                            SharedPreferences mPrefs = getSharedPreferences("model1_item", MODE_PRIVATE);
                            SharedPreferences.Editor editor = mPrefs.edit();
                            editor.putString("model_1", newmodel.getModel());
                            editor.apply();


                        }

                        else if(upload.equals("7"))
                        {
                            Toast.makeText(Model_Activity.this, "upload hai", Toast.LENGTH_SHORT).show();
                            Intent intent= new Intent(Model_Activity.this,Upload_Activity.class);
                            startActivity(intent);


                            SharedPreferences mPrefs = getSharedPreferences("model1_item", MODE_PRIVATE);
                            SharedPreferences.Editor editor = mPrefs.edit();
                            editor.putString("model_1", newmodel.getModel());
                            editor.apply();


                        }
*/






                    }
                });
                recyclerView.setAdapter(makeAdapter);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Log.e("error",error.toString());

            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap= new HashMap<String, String>();
                hashMap.put ("Content-Type", "application/json; charset=utf-8");
                return hashMap;

            }
        };
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 48,
                2,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        jsonArrayRequest.setShouldCache(false);
        Volley.newRequestQueue(Model_Activity.this).add(jsonArrayRequest);


    }
}