package com.sossolution.serviceonwayustad.MyAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.sossolution.serviceonwayustad.My_fragment.First_tab_Fragment;

public class Session_pager_adapter extends FragmentPagerAdapter
{

    public Session_pager_adapter(@NonNull FragmentManager fm)
    {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position)
    {
        Fragment fragment=null;

        switch (position)
        {
            case 0:

                fragment= new First_tab_Fragment();

                break;
            case 1:

                fragment= new First_tab_Fragment();
                break;

        }


        return fragment;
    }

    @Override
    public int getCount()
    {
        return 2;
    }
}
