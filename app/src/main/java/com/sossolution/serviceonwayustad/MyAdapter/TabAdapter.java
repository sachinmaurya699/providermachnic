package com.sossolution.serviceonwayustad.MyAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.sossolution.serviceonwayustad.My_fragment.Kyc_Update_Fragment;
import com.sossolution.serviceonwayustad.My_fragment.Bank_Update;

public class TabAdapter  extends FragmentStatePagerAdapter
  {
      int numoftab;

    public TabAdapter(@NonNull FragmentManager fm, int numoftab)
    {
        super(fm);
        this.numoftab=numoftab;

    }

    @NonNull
    @Override
    public Fragment getItem(int position)
    {

        switch(position)
        {
            case 0:
                Kyc_Update_Fragment fm= new Kyc_Update_Fragment();
                return fm;
            case 1:
                Bank_Update fm1=new Bank_Update();
                return fm1;

            default:
                return null;


        }

    }

    @Override
    public int getCount()
    {
        return numoftab;
    }
}
