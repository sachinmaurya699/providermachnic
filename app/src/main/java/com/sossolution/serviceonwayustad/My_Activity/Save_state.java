package com.sossolution.serviceonwayustad.My_Activity;

import android.content.Context;
import android.content.SharedPreferences;

public class Save_state
{
    Context context;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;


    public Save_state(Context context)
    {
        this.context = context;
    }

    public void setstate(boolean b)
    {
         editor= (SharedPreferences.Editor) context.getSharedPreferences("value",Context.MODE_PRIVATE);
         editor.putBoolean("bkey",b);
         editor.apply();


    }
    public boolean getstate()
    {
        return preferences.getBoolean("bkey",false);
    }


}
