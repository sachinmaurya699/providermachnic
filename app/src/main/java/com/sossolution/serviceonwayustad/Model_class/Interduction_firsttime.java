package com.sossolution.serviceonwayustad.Model_class;

import android.content.Context;
import android.content.SharedPreferences;

public class Interduction_firsttime
{
    Context context;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

     String value="my_introduction";
     int  mode_pricate=0;

    public Interduction_firsttime(Context context)
    {
        this.context = context;
        preferences=context.getSharedPreferences(value,mode_pricate);
        editor=preferences.edit();
    }

    public void my_introduction_first_time(boolean my_item)
     {
         editor.putBoolean("my_introduction",my_item);
         editor.apply();

     }
     public boolean my_introduction()
     {

         return preferences.getBoolean("my_introduction",true);


     }



}
