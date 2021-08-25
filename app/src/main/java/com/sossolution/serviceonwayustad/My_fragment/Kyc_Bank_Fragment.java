package com.sossolution.serviceonwayustad.My_fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.sossolution.serviceonwayustad.MyAdapter.TabAdapter;
import com.sossolution.serviceonwayustad.R;

public class Kyc_Bank_Fragment extends Fragment
{

    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
         //Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_kyc__bank_, container, false);
        tabLayout=v.findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("FirstTab"));
       tabLayout.addTab(tabLayout.newTab().setText("SecondTab"));
        viewPager=v.findViewById(R.id.viewpage_2);
        TabAdapter adapter= new TabAdapter(getFragmentManager(),tabLayout.getTabCount());
       viewPager.setAdapter(adapter);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener()
        {
            @Override
            public void onTabSelected(TabLayout.Tab tab)
            {
              viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab)
            {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab)
            {

            }
        });

        return v;
    }

}