package com.sossolution.serviceonwayustad.My_Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;


import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;
import com.sossolution.serviceonwayustad.My_fragment.Create_Shop_Fragment;
import com.sossolution.serviceonwayustad.My_fragment.Cretae_Team_Member_Fragment;
import com.sossolution.serviceonwayustad.My_fragment.Bike_Service_Fragment;
import com.sossolution.serviceonwayustad.My_fragment.Car_Service_Fragment;
import com.sossolution.serviceonwayustad.My_fragment.History_Fragment;
import com.sossolution.serviceonwayustad.My_fragment.Home_Fragment;
import com.sossolution.serviceonwayustad.My_fragment.Fourth_Fragment;
import com.sossolution.serviceonwayustad.My_fragment.Kyc_Bank_Fragment;
import com.sossolution.serviceonwayustad.My_fragment.Profile_Fragment;
import com.sossolution.serviceonwayustad.My_fragment.Vechicle_Fragment;
import com.sossolution.serviceonwayustad.My_fragment.View_vehicle_Fragment;
import com.sossolution.serviceonwayustad.My_fragment.Wallet_Fragment;
import com.sossolution.serviceonwayustad.R;
import com.sossolution.serviceonwayustad.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.sossolution.serviceonwayustad.My_fragment.Create_Shop_Fragment.MY_PERMISSIONS_REQUEST_LOCATION;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    DrawerLayout layout;
    Toolbar toolbar;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;
    Home_Fragment f1;
    Profile_Fragment f2;
     //Shop_Fragment f3;
    Fourth_Fragment f4;

    private SwitchCompat darkModeSwitch;
    MenuItem menuItem1;
    Save_state save_state;
    SharedPreferences preferences;
    String value;
    TextView text;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        permission();
        //manager= new SessionManager(this);
     //  darkModeSwitch = findViewById(R.id.switcher);

        SharedPreferences preferences2 = getSharedPreferences("Otp_activity",MODE_PRIVATE);
        value=preferences2.getString("user_id",null);



        //home page set
        f1= new Home_Fragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.second_container,f1).commit();

        //setDarkModeSwitchListener();
        toolbar =  (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        layout=findViewById(R.id.drawer_layout);
        toggle=new ActionBarDrawerToggle(this,layout,toolbar,R.string.open,R.string.close);
        toggle.syncState();


        layout.addDrawerListener(toggle);
        navigationView=findViewById(R.id.nevegation_view);
        View header = navigationView.getHeaderView(0);
         text =  header.findViewById(R.id.text_header);

        profile_api(value);


        setupDrawerContent(navigationView);

      //  menuItem1=navigationView.getMenu().getItem(1).setActionView(R.layout.menu_switch);




    }

    private void permission()
    {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED)
            {
                //Location Permission already granted
               /* mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback,
                        Looper.myLooper());
                mGoogleMap.setMyLocationEnabled(true);*/
            } else {
                //Request Location Permission
                checkLocationPermission();
            }
        } else {
            /*mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback,
                    Looper.myLooper());
            mGoogleMap.setMyLocationEnabled(true);*/
        }
    }

    private void checkLocationPermission()
    {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(MainActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
        }
    }

    private void profile_api(String Id)
    {

        String url="http://www.serviceonway.com/fetchProviderProfileData?id="+Id;
        Log.d("get_url",url);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, url, null, new Response.Listener<JSONArray>()
        {

            @Override
            public void onResponse(JSONArray response)
            {
                //dialog.dismiss();
                Log.d("responce",response.toString());

                try {
                    JSONObject jsonObject = response.getJSONObject(0);
                    String Name =jsonObject.getString("name");
                    text.setText(Name);
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
        Volley.newRequestQueue(this).add(request);

    }

    private void setTheme(String value)
    {

        SharedPreferences preferences = getSharedPreferences("Theme", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("ThemeName", value);
        editor.apply();
        recreate();


    }

    private void reset() {


        Intent intent= new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        finish();


    }

    private void setDarkModeSwitchListener()
    {
        darkModeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean ischecked)
            {
                if(!ischecked)
                {
                   // Toast.makeText(MainActivity.this, "dark mode off", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //Toast.makeText(MainActivity.this, "dark mode on", Toast.LENGTH_SHORT).show();

                }

            }
        });





    }

    private void setupDrawerContent(NavigationView navigationView)
    {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem)
                    {

                        selectDrawerItem(menuItem);
                        return true;
                    }
                });



    }

    private void selectDrawerItem(MenuItem menuItem)
    {
        Fragment fragment=null;

       // Toast.makeText(this, "hello", Toast.LENGTH_SHORT).show();
        switch (menuItem.getItemId())
        {
            case R.id.setting:
                fragment= new Home_Fragment();
             //   Toast.makeText(this, "first", Toast.LENGTH_SHORT).show();
                break;

            case R.id.message:
                fragment= new Profile_Fragment();

                break;
            case R.id.bike_id:
                fragment= new Bike_Service_Fragment();

                break;

            case R.id.car_id:
                fragment= new Car_Service_Fragment();
                //Toast.makeText(this, "second", Toast.LENGTH_SHORT).show();
                break;

            case R.id.shop:
                fragment= new Create_Shop_Fragment();
                //Toast.makeText(this, "Third", Toast.LENGTH_SHORT).show();

                break;

           /* case R.id.shop:
                fragment= new Shop_Fragment();
                //Toast.makeText(this, "Third", Toast.LENGTH_SHORT).show();

                break;*/

            case R.id.history_id:
                fragment= new History_Fragment();
               // Toast.makeText(this, "Third", Toast.LENGTH_SHORT).show();
                break;

            case R.id.wallet_history_id:
                fragment= new Wallet_Fragment();
               // Toast.makeText(this, "Third", Toast.LENGTH_SHORT).show();
                break;


            case R.id.wallet_id:
                fragment= new Wallet_Fragment();
               // Toast.makeText(this, "Third", Toast.LENGTH_SHORT).show();
                break;

            case R.id.kyc_bank:
                fragment= new Kyc_Bank_Fragment();
               // Toast.makeText(this, "Third", Toast.LENGTH_SHORT).show();
                break;

            case R.id.listing:
                fragment= new Vechicle_Fragment();
               // Toast.makeText(this, "Third", Toast.LENGTH_SHORT).show();
                break;


            case R.id.View_id:
                fragment= new View_vehicle_Fragment();
                //Toast.makeText(this, "Third", Toast.LENGTH_SHORT).show();
                break;

            case R.id.logout:
                SessionManager manager= new SessionManager(this);
                 manager.Logout();
                 Intent intent= new Intent(MainActivity.this,Login_Activity.class);
                 startActivity(intent);
                 finish();


                break;


           /* case R.id.call:
                fragment= new Fourth_Fragment();
                Toast.makeText(this, "fourth", Toast.LENGTH_SHORT).show();

                break;*/

            case R.id.bank:
                fragment= new Cretae_Team_Member_Fragment();
                //Toast.makeText(this, "Bank", Toast.LENGTH_SHORT).show();

        }
        getSupportFragmentManager().beginTransaction().replace(R.id.second_container,fragment).commit();
        layout.closeDrawer(GravityCompat.START);
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        //Toast.makeText(this, "yes", Toast.LENGTH_SHORT).show();

        switch (item.getItemId())
        {
            case R.id.setting:
                f1= new Home_Fragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.second_container,f1).commit();
                break;
        }

        return true;
    }
}