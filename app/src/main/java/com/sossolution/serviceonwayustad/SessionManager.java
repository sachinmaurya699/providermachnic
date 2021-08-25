package com.sossolution.serviceonwayustad;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import java.util.HashMap;

public class SessionManager
{

    private  SharedPreferences preferences;
    private  SharedPreferences.Editor editor;
    private Context context;
    private int Mode_private=0;
    private static final String pref_name="My_data";
    private static final String login="loged_in";
    private static final String get_number="get_number";
    private static final String Number="";

    private  String get_mynumber;



    @SuppressLint("CommitPrefEdits")
    public SessionManager(Context context)
    {
        this.context=context;
        preferences=context.getSharedPreferences(pref_name,0);
        editor=preferences.edit();
    }
    public void setLogin(String number)
    {

        //store value
        editor.putString(get_number,number);
        editor.putBoolean(login,true);
        editor.commit();

    }
    public String  getLogin()
    {
         get_mynumber=preferences.getString(get_number,null);
         return get_mynumber;
    }

    public void Logout()
    {
         editor.clear();
         editor.commit();
    }

    public HashMap<String,String> getuser()
    {
         HashMap<String,String> user = new HashMap<>();
         user.put(get_number,preferences.getString("get_number",null));
        return user;
    }
    // Get Login State
    public boolean isLoggedIn()
    {
        return preferences.getBoolean("Is_login", false);
    }





}
