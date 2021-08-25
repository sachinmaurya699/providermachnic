package com.sossolution.serviceonwayustad.My_fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.Status;
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
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.sossolution.serviceonwayustad.My_Activity.MainActivity;
import com.sossolution.serviceonwayustad.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static android.content.Context.LOCATION_SERVICE;
import static android.content.Context.MODE_PRIVATE;
import static com.facebook.FacebookSdk.getApplicationContext;

public class Create_Shop_Fragment extends Fragment implements OnMapReadyCallback
{

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private static final int REQUEST_LOCATION = 1;

    private EditText edittext_name, editText_number, editText_email, editText_address, editText_time_from, editText_time_to;
    // private GoogleMap mMap;
    private String Name, Number, Email, Address, Id, Time_From, Time_To;
    private String user_id_value;
    private Button create_button;
    //current location.....
    private GoogleMap mGoogleMap;
    private SupportMapFragment mapFrag;
    private LocationRequest mLocationRequest;
    private Location mLastLocation;
    private Marker mCurrLocationMarker;
    private FusedLocationProviderClient mFusedLocationClient;
    //new add
    private List<Address> addresses = null;
    private LocationManager locationManager;
    ;
    private PlacesClient placesClient;
    private AutocompleteSupportFragment supportFragment;

    private ImageView image_first, image_second;
    private TextView text_time_from, text_time_to;
    private String time_from1;
    private String time_to;
    private String api_call;
    private ProgressDialog dialog;
    private CheckBox checkBox_EmerGency, checkBox_door_step, checkBox_Pickup_drop;
    private  Location location;
    private  String latitude, longitude;
    private String emergency1;
    private String  door_step_item;
    private String  pic_drop;
    private String my_emergency;
    private String my_door_step;
    private   String my_pick_drop;
    private  String pic_drop__create_time;
    private  String emeregency_create_time;
    private  String  door_steo_create_time;
    private  String time_from_current;
    private String time_to_current;
    private   String time_from_database;
    private  String time_to_database;
    private String   time_update_from;
    private  String time_update_to;
    //new code
    LocationManager my_locationManager;
    @SuppressLint("CutPasteId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create__shop_, container, false);
        dialog = new ProgressDialog(getActivity(),R.style.DialogTheme);
        SharedPreferences preferences2 = getActivity().getSharedPreferences("Otp_activity", MODE_PRIVATE);
        String value = preferences2.getString("user_id", null);
        permission();
        //  updatedata();

        checkBox_EmerGency = view.findViewById(R.id.checkbox_1);
        checkBox_EmerGency.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (checkBox_EmerGency.isChecked())
                {

                    checkBox_EmerGency.setBackgroundColor(Color.rgb(64, 131, 207));
                    String emergency1="1";
                    SharedPreferences preferences =getActivity().getSharedPreferences("emergency_check1",MODE_PRIVATE);
                    SharedPreferences.Editor editor=preferences.edit();
                    editor.putString("emergency",emergency1);
                    editor.apply();



                } else if (!checkBox_EmerGency.isChecked())
                {
                    checkBox_EmerGency.setBackgroundColor(Color.WHITE);
                    String emergency2="0";
                    SharedPreferences preferences =getActivity().getSharedPreferences("emergency_check1",MODE_PRIVATE);
                    SharedPreferences.Editor editor=preferences.edit();
                    editor.putString("emergency",emergency2);
                    editor.apply();


                }
            }
        });
        checkBox_door_step = view.findViewById(R.id.checkbox_2);
        checkBox_door_step.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (checkBox_door_step.isChecked())
                {
                    checkBox_door_step.setBackgroundColor(Color.rgb(64, 131, 207));
                    String dore_step1="1";
                    SharedPreferences preferences =getActivity().getSharedPreferences("dore_step_check2",MODE_PRIVATE);
                    SharedPreferences.Editor editor=preferences.edit();
                    editor.putString("door_step",dore_step1);
                    editor.apply();


                } else if (!checkBox_door_step.isChecked())
                {
                    checkBox_door_step.setBackgroundColor(Color.WHITE);
                    String dore_step2="0";
                    SharedPreferences preferences =getActivity().getSharedPreferences("dore_step_check2",MODE_PRIVATE);
                    SharedPreferences.Editor editor=preferences.edit();
                    editor.putString("door_step",dore_step2);
                    editor.apply();

                }
            }
        });
        checkBox_Pickup_drop = view.findViewById(R.id.checkbox_3);
        checkBox_Pickup_drop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox_Pickup_drop.isChecked())
                {
                    checkBox_Pickup_drop.setBackgroundColor(Color.rgb(64, 131, 207));
                    String pic_drop1="1";
                    SharedPreferences preferences =getActivity().getSharedPreferences("pic_drop_check3",MODE_PRIVATE);
                    SharedPreferences.Editor editor=preferences.edit();
                    editor.putString("pic_drop",pic_drop1);
                    editor.apply();



                } else if (!checkBox_Pickup_drop.isChecked())
                {
                    checkBox_Pickup_drop.setBackgroundColor(Color.WHITE);
                    String pic_drop2="0";
                    SharedPreferences preferences =getActivity().getSharedPreferences("pic_drop_check3",MODE_PRIVATE);
                    SharedPreferences.Editor editor=preferences.edit();
                    editor.putString("pic_drop",pic_drop2);
                    editor.apply();
                }
            }
        });

        edittext_name = view.findViewById(R.id.create_name);
        editText_number = view.findViewById(R.id.create_phone);
        editText_email = view.findViewById(R.id.create_email);
        editText_address = view.findViewById(R.id.create_address);
        create_button = view.findViewById(R.id.create_shop);
        text_time_from = view.findViewById(R.id.time_from_create_shop);
        text_time_to = view.findViewById(R.id.time_to_create_shop);
        dialog.setMessage("Loading....");
        dialog.show();

        Show_details(value);

        //location....
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        image_first = view.findViewById(R.id.image_first);
        image_second = view.findViewById(R.id.image_second_create);

        image_first.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity(), R.style.DialogTheme, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute)
                    {
                        String am_pm;
                        if(selectedHour>=12)
                        {
                            am_pm="PM";
                        }else {
                            am_pm="AM";
                        }

                        String time_from = selectedHour + ":" + selectedMinute;
                        SimpleDateFormat fmt = new SimpleDateFormat("HH:mm");
                        Date date = null;
                        try {
                            date = fmt.parse(time_from );
                        } catch (ParseException e) {

                            e.printStackTrace();
                        }
                        SimpleDateFormat fmtOut = new SimpleDateFormat("hh:mm");
                       // Log.d("fo-rmout", String.valueOf(Integer.valueOf(String.valueOf(fmtOut))));
                        time_from_current=fmtOut.format(date);
                        Log.d("formatted",   time_from_current);
                        // time_from_current=formattedTime;
                    /*    String my_time=time.split(",")*/
                        //Log.d("time",time_from1);
                        text_time_from.setText(   time_from_current+am_pm);
                         SharedPreferences preferences = getApplicationContext().getSharedPreferences("time", MODE_PRIVATE);
                         SharedPreferences.Editor editor = preferences.edit();
                         editor.putString("time_from", time_from_current);
                         editor.apply();
                    }
                }, hour, minute, false);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
        image_second.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity(), R.style.DialogTheme, new TimePickerDialog.OnTimeSetListener()
                {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour_of_day, int minute2)
                    {

                        String am_pm;
                        if(hour_of_day>=12)
                        {
                            am_pm="PM";
                        }else {
                            am_pm="AM";
                        }

                        String time_from = hour_of_day + ":" + minute2;
                        SimpleDateFormat fmt = new SimpleDateFormat("HH:mm");
                        Date date = null;
                        try {
                            date = fmt.parse(time_from );
                        } catch (ParseException e) {

                            e.printStackTrace();
                        }
                        SimpleDateFormat fmtOut = new SimpleDateFormat("hh:mm");
                        time_to_current=fmtOut.format(date);
                        text_time_to.setText(time_to_current+am_pm);

                        SharedPreferences preferences = getApplicationContext().getSharedPreferences("time", MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("time_to",   time_to_current);
                        editor.apply();

                    }
                }, hour, minute, false);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        mapFrag = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.my_map);
        mapFrag.getMapAsync(this);


        //add search item.....

        //place show data..........
        String placesKey = "AIzaSyAhJW0BL0uuVzXfhkhiQb3ZXF8f4pQ0vYQ";
        locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
        if (!Places.isInitialized()) {
            Places.initialize(getActivity(), placesKey);
        }
        placesClient = Places.createClient(getActivity());
        supportFragment = (AutocompleteSupportFragment) getChildFragmentManager().findFragmentById(R.id.autocomplete_fragment);
        supportFragment.getView().setBackgroundColor(Color.WHITE);
        supportFragment.setTypeFilter(TypeFilter.ADDRESS);

        assert supportFragment != null;
        supportFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.LAT_LNG, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.ADDRESS_COMPONENTS));
        supportFragment.setOnPlaceSelectedListener(new PlaceSelectionListener()
        {
            @Override
            public void onPlaceSelected(@NonNull Place place) {

                LatLng latLng2 = place.getLatLng();
                double s1 = latLng2.latitude;
                double s2 = latLng2.longitude;


                //  Toast.makeText(Map2_Activity.this, "place select ", Toast.LENGTH_SHORT).show();

                String Lat = String.valueOf(s1);
                String Lng = String.valueOf(s2);

                //////////data set sharedprfrence..................

                LatLng l2 = new LatLng(s1, s2);

                //data();
                mGoogleMap.addMarker(new MarkerOptions()
                        .position(l2)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                        .title("My Location"));
                //Toast.makeText(Map_Activity.this, latLng2.toString(), Toast.LENGTH_SHORT).show();
                // mGoogleMap.addMarker(new MarkerOptions().position(latLng2).title(place.getAddress()));
                // mGoogleMap.animateCamera(CameraUpdateFactory.zoomIn());
                mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(15), null);
                // mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(l2, 15.0f));

                if (latLng2 == null) {
                    Toast.makeText(getActivity(), "no value", Toast.LENGTH_SHORT).show();
                } else {
                    Geocoder gcd = new Geocoder(getActivity(),
                            Locale.getDefault());

                    try {
                        addresses = gcd.getFromLocation(latLng2.latitude,
                                latLng2.longitude, 1);
                        if (addresses.size() > 0) {
                            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                            String locality = addresses.get(0).getLocality();
                            String subLocality = addresses.get(0).getSubLocality();
                            String state = addresses.get(0).getAdminArea();
                            String country = addresses.get(0).getCountryName();
                            String postalCode = addresses.get(0).getPostalCode();
                            String all = address + "," + locality + "," + subLocality + "," + state + "," + country + "," + postalCode;
                            supportFragment.setText(all);
                            editText_address.setText(all);
                            // Toast.makeText(Map_Activity.this, all.toString() + "alladdress", Toast.LENGTH_SHORT).show();
                           /* SharedPreferences sharedPreferences = getSharedPreferences("address", MODE_PRIVATE);
                            SharedPreferences.Editor editor1 = sharedPreferences.edit();
                            editor1.putString("place", all);
                            editor1.apply();*/

                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e("error", e.toString());

                    }
                    //   Toast.makeText(Map2_Activity.this, "yes value", Toast.LENGTH_SHORT).show();


                }


                //Toast.makeText(Map_Activity.this, place.getAddress() + toString() + "placename", Toast.LENGTH_SHORT).show();

                if (mGoogleMap != null) {
                    mGoogleMap.clear();
                }
                //Toast.makeText(Map_Activity.this, latLng2.toString(), Toast.LENGTH_SHORT).show();
                // mGoogleMap.addMarker(new MarkerOptions().position(latLng2).title(place.getAddress()));
                mGoogleMap.animateCamera(CameraUpdateFactory.zoomIn());
                mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(15), null);
                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng2, 15.0f));

                //geocoder1();
            }

            @Override
            public void onError(@NonNull Status status) {
                Log.d("error", status.toString());
                // Toast.makeText(Map2_Activity.this, status.toString() + "not see", Toast.LENGTH_LONG).show();

            }

        });

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
      /*  mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map1);
        mapFrag.getMapAsync(this);
        mapFrag.getView().setVisibility(View.INVISIBLE);*/


        //editText_time_from = view.findViewById(R.id.time_from);

        /*.................add map....................*/

        //place show data..........


        // editText_time_to = view.findViewById(R.id.time_to);


        SharedPreferences preferences6 = getActivity().getSharedPreferences("Otp_activity", MODE_PRIVATE);
        user_id_value = preferences6.getString("user_id", null);

        create_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                SharedPreferences preferences = getActivity().getSharedPreferences("api_call", MODE_PRIVATE);
                api_call = preferences.getString("data", null);

              /*  SharedPreferences preferences1 = getActivity().getSharedPreferences("api_call",MODE_PRIVATE);
                api_call=preferences.getString("data_update",null);*/


                if (api_call.equals("1"))
                {
                    //Toast.makeText(getActivity(), "update shop", Toast.LENGTH_SHORT).show();

                    Name = edittext_name.getText().toString();
                    Number = editText_number.getText().toString();
                    Email = editText_email.getText().toString();
                    Address = editText_address.getText().toString();

                    // Time_From = editText_time_from.getText().toString();
                    // Time_To = editText_time_to.getText().toString();

                    SharedPreferences preferences3 = getApplicationContext().getSharedPreferences("time", MODE_PRIVATE);
                    time_from1 = preferences3.getString("time_from", null);
                     if(time_from1!=null)
                     {
                         time_update_from=time_from1;
                     }else
                     {
                        time_update_from=time_from_database;
                     }

                    time_to = preferences3.getString("time_to", null);
                     if(time_to!=null)
                     {
                         time_update_to=time_to;
                     }else
                     {
                         time_update_to=time_to_database;
                     }
                   // String my_time_to=time_to;

                    if (Name.isEmpty()) {
                        edittext_name.setError("Enter the name");
                        edittext_name.setFocusable(true);
                    } else if (Number.isEmpty()) {
                        editText_number.setError("Enter the Number");
                        editText_number.setFocusable(true);
                    } else if (Email.isEmpty()) {
                        editText_email.setError("Enter the Email");
                        editText_email.setFocusable(true);
                    } else if (Address.isEmpty())
                    {
                        editText_address.setError("Enter the Address");
                        editText_address.setFocusable(true);

                    } else
                        {

                   //emergency
                        SharedPreferences preferences1 =getActivity().getSharedPreferences("emergency_check1",MODE_PRIVATE);
                        String emergency=preferences1.getString("emergency",null);


                        if(emergency!=null)
                        {
                            if(emergency.equals("1"))
                            {
                                emergency1="1";
                            }else if(emergency.equals("0"))
                            {
                                emergency1="0";
                            }
                        }else
                        {
                            emergency1="0";
                        }




                     /* doore step....*/
                        SharedPreferences preferences2 =getActivity().getSharedPreferences("dore_step_check2",MODE_PRIVATE);
                        String doore_step=preferences2.getString("door_step",null);

                        if(doore_step!=null)
                        {
                            if(doore_step.equals("1"))
                            {
                                door_step_item="1";
                            }else
                            {
                                door_step_item="0";
                            }
                        }else
                        {
                            door_step_item="0";
                        }

                        //picdorp....
                        SharedPreferences preferences4 =getActivity().getSharedPreferences("pic_drop_check3",MODE_PRIVATE);
                        String pic_drop1=preferences4.getString("pic_drop",null);


                        if(pic_drop1!=null)
                        {
                            if(pic_drop1.equals("1"))
                            {
                                pic_drop="1";
                            }else
                            {
                                pic_drop="0";
                            }
                        }else
                        {
                            pic_drop="0";
                        }

                        //create_shop(user_id_value, Name, Number, Email, Address, time_from1, time_to);
                        updatedata(user_id_value, Name, Number, Email, Address,time_update_from,time_update_to,emergency1,door_step_item, pic_drop);
                       // Toast.makeText(getActivity(), "update shop_outside", Toast.LENGTH_SHORT).show();
                        //update();
                    }

                } else if (api_call.equals("2"))
                {

                    //Toast.makeText(getActivity(), "create_shop", Toast.LENGTH_SHORT).show();


                    Name = edittext_name.getText().toString();
                    Number = editText_number.getText().toString();
                    Email = editText_email.getText().toString();
                    Address = editText_address.getText().toString();
                    //Time_From = editText_time_from.getText().toString();
                    // Time_To = editText_time_to.getText().toString();

                    SharedPreferences preferences3 = getApplicationContext().getSharedPreferences("time", MODE_PRIVATE);
                    String time_from1 = preferences3.getString("time_from", null);
                    String time_to = preferences3.getString("time_to", null);


                    if (Name.isEmpty()) {
                        edittext_name.setError("Enter the name");
                        edittext_name.setFocusable(true);
                    } else if (Number.isEmpty()) {
                        editText_number.setError("Enter the Number");
                        editText_number.setFocusable(true);
                    } else if (Email.isEmpty()) {
                        editText_email.setError("Enter the Email");
                        editText_email.setFocusable(true);
                    } else if (Address.isEmpty()) {
                        editText_address.setError("Enter the Address");
                        editText_address.setFocusable(true);

                    } else {
                       // Toast.makeText(getActivity(), "create_shop", Toast.LENGTH_SHORT).show();

                        SharedPreferences preferences1 =getActivity().getSharedPreferences("emergency_check1",MODE_PRIVATE);
                        String emergency=preferences1.getString("emergency","");

                        if(emergency!=null)
                        {
                            if(emergency.equals("1"))
                            {
                                emeregency_create_time="1";
                            }else
                            {
                                emeregency_create_time="0";
                            }
                        }else
                        {
                            emeregency_create_time="0";
                        }



                        SharedPreferences preferences2 =getActivity().getSharedPreferences("dore_step_check2",MODE_PRIVATE);
                        String door_step=preferences2.getString("door_step","");

                        if(door_step!=null)
                        {
                            if(door_step.equals("1"))
                            {
                                door_steo_create_time="1";
                            }else
                            {
                                door_steo_create_time="0";
                            }
                        }else
                        {
                            door_steo_create_time="0";
                        }




                        SharedPreferences preferences4 =getActivity().getSharedPreferences("pic_drop_check3",MODE_PRIVATE);
                        String pic_drop=preferences4.getString("pic_drop","");

                        if(pic_drop!=null)
                        {
                            if(pic_drop.equals("1"))
                            {
                                pic_drop__create_time="1";

                            }else
                            {
                                pic_drop__create_time="0";
                            }
                        }else
                        {
                            pic_drop__create_time="0";
                        }





                        create_shop(user_id_value, Name, Number, Email, Address, time_from1, time_to,emeregency_create_time,door_steo_create_time,pic_drop__create_time);

                    }


                } else {
                    Toast.makeText(getActivity(), "somthing went wrong", Toast.LENGTH_SHORT).show();
                }

            }
        });


        return view;
    }

    private void permission()
    {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if (ContextCompat.checkSelfPermission(getActivity(),
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

    private void Show_details(String user_id)
    {

        String url = "http://www.serviceonway.com/fetchProviderShopDetails?id="+user_id;
        Log.d("get_show_details", url);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                dialog.dismiss();
               // Toast.makeText(getActivity(), "show_api_hit", Toast.LENGTH_SHORT).show();

                if (response.length() > 0)
                {
                    create_button.setText("Update_Shop");

                   // Toast.makeText(getActivity(), "data_update", Toast.LENGTH_SHORT).show();
                    String s1 = "1";
                    SharedPreferences preferences = getActivity().getSharedPreferences("api_call", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("data", s1);
                    editor.apply();

                    // dialog.dismiss();
                    try {
                        JSONObject object = response.getJSONObject(0);
                        String Shop_Id = object.getString("shop_id");
                         my_emergency=object.getString("emergency");
                         if(my_emergency.equals("1"))
                         {
                             checkBox_EmerGency.setChecked(true);
                             checkBox_EmerGency.setBackgroundColor(Color.parseColor("#ED930E"));
                         }else
                         {
                             checkBox_EmerGency.setChecked(false);
                         }
                         my_door_step=object.getString("door_step");
                        if( my_door_step.equals("1"))
                        {
                            checkBox_door_step.setChecked(true);
                            checkBox_door_step.setBackgroundColor(Color.parseColor("#ED930E"));;
                        }
                        else
                        {
                            checkBox_door_step.setChecked(false);
                        }
                         my_pick_drop=object.getString("pick_drop");
                        if(  my_pick_drop.equals("1"))
                        {
                            checkBox_Pickup_drop.setChecked(true);
                            checkBox_Pickup_drop.setBackgroundColor(Color.parseColor("#ED930E"));;
                        }
                        else
                        {
                            checkBox_Pickup_drop.setChecked(false);
                        }
                        String Shop_Name = object.getString("shop_name");
                        edittext_name.setText(Shop_Name);
                        String Contect = object.getString("contact");
                        editText_number.setText(Contect);
                        String Email = object.getString("email");
                        editText_email.setText(Email);
                        String Address = object.getString("address");
                        editText_address.setText(Address);
                         time_from_database = object.getString("timefrom");
                        text_time_from.setText(time_from_database+"AM");
                        time_to_database = object.getString("timeto");
                        text_time_to.setText(time_to_database+"PM");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else
                    {
                  //  Toast.makeText(getActivity(), "data_insert", Toast.LENGTH_SHORT).show();
                    create_button.setText("Create_Shop");
                    String s1 = "2";
                    SharedPreferences preferences = getActivity().getSharedPreferences("api_call", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("data", s1);
                    editor.apply();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", error.toString());
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                return super.getParams();
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 48,
                2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request.setShouldCache(false);
        Volley.newRequestQueue(getActivity()).add(request);


    }

    private void updatedata(String Id, String Shop_name, String contact, String emial, String address, String time_from, String time_to,String emergency,String doorstep,String pickdrop) {
        String url ="http://www.serviceonway.com/updateServiceProviderShopDetails?id="+Id+"&shop_name="+Shop_name+"&contact="+contact+"&email="+emial+"&address="+address+"&timefrom="+time_from +"&timeto="+ time_to+"&emergency="+emergency+"&doorstep="+doorstep+"&pickdrop="+pickdrop;

        Log.d("update_profile", url);

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("update_responce", response.toString());
                String Message = response;
                if (Message.equals("success"))
                {

                 /*   Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);*/
                   Toast.makeText(getActivity(), "Update_shop", Toast.LENGTH_SHORT).show();
                }else
                {
                    Toast.makeText(getActivity(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", error.toString());
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<>();
                return hashMap;

            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 48,
                2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request.setShouldCache(false);
        Volley.newRequestQueue(getActivity()).add(request);

    }


    @Override
    public void onPause()
    {
        super.onPause();

        //stop location updates when Activity is no longer active
        if (mFusedLocationClient != null) {
            mFusedLocationClient.removeLocationUpdates(mLocationCallback);
        }
    }


    private void create_shop(String id, String shop_name, String contact, String email, String address, String time_from, String time_to,String Emergency,String door_step,String pic_drop) {

        String url = "http://serviceonway.com/storeServiceProviderShopDetails?id="+id+"&shop_name="+shop_name+"&contact="+contact+"&email="+email+"&address=" + address + "&timefrom="+time_from+"&timeto="+time_to+"&emergency="+Emergency+"&doorstep="+door_step+"&pickdrop="+pic_drop;

        Log.d("get_shop_create", url);

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                String message = response;
                if (message.equals("success"))
                {
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    getActivity().startActivity(intent);
                    Toast.makeText(getActivity(), "Create_shop", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", error.toString());
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<>();
                return hashMap;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 48,
                2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request.setShouldCache(false);
        Volley.newRequestQueue(getActivity()).add(request);

    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mGoogleMap = googleMap;

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(120000); // two minute interval
        mLocationRequest.setFastestInterval(120000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mGoogleMap.setMyLocationEnabled(true);
      //add code

        mGoogleMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener()
        {
            @Override
            public boolean onMyLocationButtonClick()
            {
               // Toast.makeText(getActivity(), "zoom hai...", Toast.LENGTH_SHORT).show();

            my_locationManager = (LocationManager)getContext().getSystemService(Context.LOCATION_SERVICE);

                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
                {
                    OnGPS();
                } else {
                    getLocation1();
                }








/*
                if(location!=null)
                {
                    LatLng my_latLng = new LatLng(location.getLatitude(), location.getLongitude());

                    double s5 = location.getLatitude();
                    double s6 = location.getLongitude();

                    LatLng    l2 = new LatLng(s5, s6);

                    mGoogleMap.addMarker(new MarkerOptions()
                            .position(l2)
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                            .title("My Location"));

                    mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(15), null);

                    if (my_latLng == null)
                    {
                        Toast.makeText(getActivity(), "no value", Toast.LENGTH_SHORT).show();
                    } else
                    {
                        Geocoder gcd = new Geocoder(getActivity(),
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
                                //String all = address+","+locality+","+subLocality+","+state+","+country+","+postalCode;
                                String all=address;
                                supportFragment.setText(all);
                                editText_address.setText(all);
                                // Toast.makeText(Map_Activity.this, all.toString() + "alladdress", Toast.LENGTH_SHORT).show();
                           *//* SharedPreferences sharedPreferences = getSharedPreferences("address", MODE_PRIVATE);
                            SharedPreferences.Editor editor1 = sharedPreferences.edit();
                            editor1.putString("place", all);
                            editor1.apply();*//*

                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                            Log.e("error",e.toString());

                        }
                        //
                    }


                }*/
                //move map camera


               /* Toast.makeText(getActivity(),""+
                        mGoogleMap.getMyLocation().getLatitude(), mGoogleMap.getMyLocation().getLongitude()),
                        Toast.LENGTH_SHORT).show();*/

                return false;
            }
        });


    }

    @SuppressLint("SetTextI18n")
    private void getLocation1()
    {
        if (ActivityCompat.checkSelfPermission(
                getActivity(),Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        } else {
            Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (locationGPS != null)
            {
                double lat = locationGPS.getLatitude();
                double longi = locationGPS.getLongitude();
                latitude = String.valueOf(lat);
                longitude = String.valueOf(longi);

                LatLng my_latLng1 = new LatLng(locationGPS.getLatitude(), locationGPS.getLongitude());

                double s5 = locationGPS.getLatitude();
                double s6 = locationGPS.getLongitude();

                LatLng    l2 = new LatLng(s5, s6);

                mGoogleMap.addMarker(new MarkerOptions()
                        .position(l2)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                        .title("My Location"));

                mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(15), null);

                if (my_latLng1 == null)
                {
                   // Toast.makeText(getActivity(), "no value", Toast.LENGTH_SHORT).show();
                } else {
                    Geocoder gcd = new Geocoder(getActivity(),
                            Locale.getDefault());

                    try {
                        addresses = gcd.getFromLocation(locationGPS.getLatitude(),
                                locationGPS.getLongitude(), 1);
                        if (addresses.size() > 0) {
                            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                            String locality = addresses.get(0).getLocality();
                            String subLocality = addresses.get(0).getSubLocality();
                            String state = addresses.get(0).getAdminArea();
                            String country = addresses.get(0).getCountryName();
                            String postalCode = addresses.get(0).getPostalCode();
                            //String all = address+","+locality+","+subLocality+","+state+","+country+","+postalCode;
                            String all = address;
                            //supportFragment.setText(all);
                            editText_address.setText(all);
                            // Toast.makeText(Map_Activity.this, all.toString() + "alladdress", Toast.LENGTH_SHORT).show();
                            // SharedPreferences sharedPreferences = getSharedPreferences("address", MODE_PRIVATE);
                           /* SharedPreferences.Editor editor1 = sharedPreferences.edit();
                            editor1.putString("place", all);
                            editor1.apply();*/

                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e("error", e.toString());

                    }
                }

               // editText_address.setText("Your Location: " + "\n" + "Latitude: " + latitude + "\n" + "Longitude: " + longitude);
            } else {
                Toast.makeText(getActivity(), "Unable to find location.", Toast.LENGTH_SHORT).show();
            }
        }



    }

    private void OnGPS()
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("Yes", new  DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    /* ..................location..................*/
    LocationCallback mLocationCallback = new LocationCallback()
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
                mGoogleMap.addMarker(new MarkerOptions()
                        .position(l2)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                        .title("My Location"));
                //Toast.makeText(Map_Activity.this, latLng2.toString(), Toast.LENGTH_SHORT).show();
                // mGoogleMap.addMarker(new MarkerOptions().position(latLng2).title(place.getAddress()));
                // mGoogleMap.animateCamera(CameraUpdateFactory.zoomIn());
                mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(15), null);
                // mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(l2, 15.0f));

                if (my_latLng == null)
                {
                   // Toast.makeText(getActivity(), "no value", Toast.LENGTH_SHORT).show();
                } else
                {
                    Geocoder gcd = new Geocoder(getActivity(),
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
                            //String all = address+","+locality+","+subLocality+","+state+","+country+","+postalCode;
                            String all=address;
                            supportFragment.setText(all);
                            editText_address.setText(all);
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

                if (mGoogleMap != null)
                {
                    mGoogleMap.clear();
                }
                //Toast.makeText(Map_Activity.this, latLng2.toString(), Toast.LENGTH_SHORT).show();
                // mGoogleMap.addMarker(new MarkerOptions().position(latLng2).title(place.getAddress()));
                mGoogleMap.animateCamera(CameraUpdateFactory.zoomIn());
                mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(15), null);
                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(my_latLng, 15.0f));


                //add address.....
                CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(location.getLatitude(), location.getLongitude())).zoom(16).build();
                mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        }
    };

    private void checkLocationPermission()
    {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(getActivity())
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(getActivity(),
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults)
    {
        switch (requestCode)
        {
            case MY_PERMISSIONS_REQUEST_LOCATION:
                {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(getActivity(),
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED)
                    {

                        mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                                mLocationCallback, Looper.myLooper());
                        mGoogleMap.setMyLocationEnabled(true);
                    }

                } else
                    {
                    // if not allow a permission, the application will exit
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    getActivity().finish();
                    System.exit(0);
                }
            }
        }

    }
}




