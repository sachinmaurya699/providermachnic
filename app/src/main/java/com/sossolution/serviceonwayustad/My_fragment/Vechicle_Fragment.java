package com.sossolution.serviceonwayustad.My_fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.sossolution.serviceonwayustad.My_Activity.Maker_Activity;
import com.sossolution.serviceonwayustad.R;


public class Vechicle_Fragment extends Fragment
{

    ImageButton car_item,bike_item;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment

        View  view=inflater.inflate(R.layout.fragment_listing_, container, false);
        car_item=view.findViewById(R.id.checkBox_car);
        bike_item=view.findViewById(R.id.checkBox_bike);
        car_item.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                String car="car";
                SharedPreferences preferences1=getActivity().getSharedPreferences("vech_name", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=preferences1.edit();
                editor.putString("vech",car);
                editor.apply();
                Intent intent= new Intent(getActivity(), Maker_Activity.class);
                startActivity(intent);





            }
        });

        bike_item.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                String bike="bike";
                SharedPreferences preferences2=getActivity().getSharedPreferences("vech_name", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=preferences2.edit();
                editor.putString("vech",bike);
                editor.apply();

                Intent intent= new Intent(getActivity(), Maker_Activity.class);
                startActivity(intent);

            }
        });

        return view;


    }
}