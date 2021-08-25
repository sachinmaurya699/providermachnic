package com.sossolution.serviceonwayustad.My_Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sossolution.serviceonwayustad.Model_class.Recyclerview_include_data;
import com.sossolution.serviceonwayustad.MyAdapter.Recycleview_item_adapter;
import com.sossolution.serviceonwayustad.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class History_service_details_Activity extends AppCompatActivity implements OnMapReadyCallback
{

    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 1;
    private ImageView imageView;
    private TextView textView;
    private TextView text_booking_id,text_model,text_vechicle,text_maker,text_name,text_contact,new_price,text_date;
    //setup recyclerview
    private RecyclerView recyclerView;
    private LinearLayoutManager manager;
    private List<Recyclerview_include_data> recyclerview_include_data_item_list;
    private Recycleview_item_adapter adapter;
    //location get...
    private List<Address> addresses = null;
    private GoogleMap map;
    private SupportMapFragment mapFrag;
    private LocationRequest mLocationRequest;
    private Location mLastLocation;
    private Marker mCurrLocationMarker;
    private FusedLocationProviderClient mFusedLocationClient;
   // private int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    //create location
   private  Location location;
   private Spinner spinner_item;
   String s2[]={"a","b","c"};


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_service_details_);
        spinner_item=findViewById(R.id.team_member_list);
        permission();
        mFusedLocationClient= LocationServices.getFusedLocationProviderClient(this);
        SupportMapFragment mapFragment= (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.my_map_history);
        mapFragment.getMapAsync(this);

        SharedPreferences preferences2 = getSharedPreferences("Otp_activity",MODE_PRIVATE);
        String value=preferences2.getString("user_id",null);

        Team_member_list(value);
        imageView=findViewById(R.id.back);
        new_price=findViewById(R.id.newprice3);
        text_date=findViewById(R.id.my_current_date);
        recyclerView=findViewById(R.id.my_include_recyclerview);
        recyclerView.setHasFixedSize(true);
        manager= new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        text_booking_id=findViewById(R.id.my_current_id);
        text_model=findViewById(R.id.my_current_Model);
        text_vechicle=findViewById(R.id.my_Current_vechicle);
        text_maker=findViewById(R.id.my_current_maker);
        text_model=findViewById(R.id.my_current_Model);
        text_name=findViewById(R.id.current_name_item);
        text_contact=findViewById(R.id.my_current_connect_number);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                onBackPressed();
            }
        });
        textView=findViewById(R.id.header);
        textView.setText("Booking Details");
        SharedPreferences preferences =getSharedPreferences("booking",MODE_PRIVATE);
        String booking_id=preferences.getString("booking_id","");
        recyclerview_include_data_item_list= new ArrayList<>();

        booking_details_api(booking_id);
    }

    private void Team_member_list(String provider_id)
    {

        String url="https://www.serviceonway.com/view_provider_team_member?id="+provider_id;


        JsonArrayRequest request= new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response)
            {
              Log.d("get_list_responce",response.toString());
              for(int i=0;i<response.length();i++)
              {
                  try {
                      JSONObject jsonObject= response.getJSONObject(i);
                      String name=jsonObject.getString("name");
                      Log.d("name_value",name);

                  } catch (JSONException e) {
                      e.printStackTrace();
                  }

              }


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
        Volley.newRequestQueue(this).add(request);



    }

    private void permission()
    {

        if(android.os.Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
        {
            checklocation_permission();


        }

    }

    private void checklocation_permission()
    {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED)
        {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION))
            {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }

        } else {

        }
    }

    private void booking_details_api(String user_id)
    {
        String url="http://www.serviceonway.com/GetProviderBookingDetailsBySpecBId?bid="+user_id;
        Log.d("url_service_details",url);
        JsonArrayRequest jsonArrayRequest= new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response)
            {

                Log.d("get_responce_details",response.toString());

                 for(int i=0;i<response.length();i++)
                 {
                     try {
                         JSONArray jsonArray=response.getJSONArray(i);
                         JSONObject jsonObject=jsonArray.getJSONObject(0);
                         String date=jsonObject.getString("date");
                         text_date.setText(date);
                         String bookingid=jsonObject.getString("booking_id");
                         text_booking_id.setText(bookingid);
                         String Model=jsonObject.getString("model");
                         text_model.setText(Model);
                         String Maker=jsonObject.getString("maker");
                         text_maker.setText(Maker);
                         String vechicle=jsonObject.getString("vehicle");
                         text_vechicle.setText(vechicle);
                         String Name=jsonObject.getString("user_name");
                         text_name.setText(Name);
                         String Contact=jsonObject.getString("user_contact");
                         text_contact.setText(Contact);
                         String total_price=jsonObject.getString("price");
                         new_price.setText(total_price);
                         Recyclerview_include_data recyclerview_include_data= new Recyclerview_include_data();
                         recyclerview_include_data.setService(jsonObject.getString("service_include"));
                         recyclerview_include_data.setPrice(jsonObject.getString("price"));
                         recyclerview_include_data_item_list.add(recyclerview_include_data);


      adapter= new Recycleview_item_adapter(History_service_details_Activity.this,recyclerview_include_data_item_list);

      recyclerView.setAdapter(adapter);




                     } catch (JSONException e)
                     {
                         e.printStackTrace();
                     }
                 }


            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Log.d("details_error",error.toString());
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                HashMap<String,String> hashMap= new HashMap<>();
                return hashMap;
            }
        };
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS*48,
                2,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        jsonArrayRequest.setShouldCache(false);
        Volley.newRequestQueue(this).add(jsonArrayRequest);
    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        map=googleMap;
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        map.getUiSettings().setZoomControlsEnabled(true);
        map.getUiSettings().setZoomGesturesEnabled(true);
        map.getUiSettings().setCompassEnabled(true);

       // LatLng lng = new LatLng()

    }

    //create location current......
    LocationCallback callback = new LocationCallback()
    {
        @Override
        public void onLocationResult(LocationResult locationResult)
        {
            List<Location> locationList = locationResult.getLocations();
            if (locationList.size() > 0)
            {
                //The last location in the list is the newest
                location = locationList.get(locationList.size() - 1);
                mLastLocation = location;
                if (mCurrLocationMarker != null)
                {
                    mCurrLocationMarker.remove();
                }

                //move map camera
                LatLng my_latLng = new LatLng(location.getLatitude(), location.getLongitude());

                //add code
                //LatLng latLng2 = place.getLatLng();
                double s5 = location.getLatitude();
                double s6 = location.getLongitude();


                //  Toast.makeText(Map2_Activity.this, "place select ", Toast.LENGTH_SHORT).show();

                String  Lat = String.valueOf(s5);
                String  Lng = String.valueOf(s6);

                //////////data set sharedprfrence..................

                LatLng    l2 = new LatLng(s5, s6);

                //data();
                map.addMarker(new MarkerOptions()
                        .position(l2)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                        .title("My Location"));
                //Toast.makeText(Map_Activity.this, latLng2.toString(), Toast.LENGTH_SHORT).show();
                // mGoogleMap.addMarker(new MarkerOptions().position(latLng2).title(place.getAddress()));
                // mGoogleMap.animateCamera(CameraUpdateFactory.zoomIn());
                map.animateCamera(CameraUpdateFactory.zoomTo(15), null);
                // mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(l2, 15.0f));

                if (my_latLng == null)
                {
                    // Toast.makeText(getActivity(), "no value", Toast.LENGTH_SHORT).show();
                } else
                {
                    Geocoder gcd = new Geocoder(History_service_details_Activity.this,
                            Locale.getDefault());

                    try {
                        addresses = gcd.getFromLocation(location.getLatitude(),
                                location.getLongitude(), 1);
                        if (addresses.size() > 0)
                        {
                            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                            String locality = addresses.get(0).getLocality();
                            String subLocality = addresses.get(0).getSubLocality();
                            String state = addresses.get(0).getAdminArea();
                            String country = addresses.get(0).getCountryName();
                            String postalCode = addresses.get(0).getPostalCode();

                            String all = address+","+locality+","+subLocality+","+state+","+country+","+postalCode;
                            Log.d("get_all",all);
                           // String all=address;
                            //supportFragment.setText(all);
                           // editText_address.setText(all);
                            // Toast.makeText(Map_Activity.this, all.toString() + "alladdress", Toast.LENGTH_SHORT).show();
                           /* SharedPreferences sharedPreferences = getSharedPreferences("address", MODE_PRIVATE);
                            SharedPreferences.Editor editor1 = sharedPreferences.edit();
                            editor1.putString("place", all);
                            editor1.apply();*/

                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e("error",e.toString());

                    }
                    //   Toast.makeText(Map2_Activity.this, "yes value", Toast.LENGTH_SHORT).show();


                }


                //Toast.makeText(Map_Activity.this, place.getAddress() + toString() + "placename", Toast.LENGTH_SHORT).show();

               /* if (map != null)
                {
                    map.clear();
                }*/
                //Toast.makeText(Map_Activity.this, latLng2.toString(), Toast.LENGTH_SHORT).show();
                 map.addMarker(new MarkerOptions().position(my_latLng));
                map.animateCamera(CameraUpdateFactory.zoomIn());
                map.animateCamera(CameraUpdateFactory.zoomTo(15), null);
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(my_latLng, 15.0f));
                //add address.....
                CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(location.getLatitude(), location.getLongitude())).zoom(16).build();
                map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        }
    };


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode)
        {
            case MY_PERMISSIONS_REQUEST_LOCATION :

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED)
                    {

                        mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                                callback, Looper.myLooper());
                        map.setMyLocationEnabled(true);
                    }

                } else
                {
                    // if not allow a permission, the application will exit
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                    System.exit(0);
                }
        }


    }




    }
