package com.sossolution.serviceonwayustad.My_Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.EditText;

public class MyBroadcast extends BroadcastReceiver
{

    static EditText editText;
    /////1.....
    // Get the object of SmsManager
   // final SmsManager sms = SmsManager.getDefault();

    public void setedittextotp(EditText editText)
    {
       // MyBroadcast.editText=editText;
        MyBroadcast.editText=editText;
    }


    @Override
    public void onReceive(Context context, Intent intent)
    {

        SmsMessage[] smsMessage= Telephony.Sms.Intents.getMessagesFromIntent(intent);



        for(SmsMessage smsMessage1:smsMessage)
        {
            String sms=smsMessage1.getMessageBody();
            //String sms1=smsMessage1.
            Log.d("sme",sms);
            String get_otp=sms.split("OTP")[1];
            Log.d("getotp",get_otp+"v1");
            editText.setText(get_otp);

        }

    }
}

