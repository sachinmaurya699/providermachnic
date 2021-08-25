package com.sossolution.serviceonwayustad.My_Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.sossolution.serviceonwayustad.Model_class.Interduction_firsttime;
import com.sossolution.serviceonwayustad.Model_class.PrefManager;
import com.sossolution.serviceonwayustad.R;
import com.sossolution.serviceonwayustad.SessionManager;

public class Splash_Activity extends AppCompatActivity
{

    private TextView textView_app_name,textView_copy_rights,textView_all;
    private ImageView imageView_logo,imageView_car,imageView_bike,second_logo_img;
    private Animation animation,animRotate;
    private PrefManager manager1;
    private SessionManager manager;
    private Interduction_firsttime firsttime;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_);

         manager= new SessionManager(this);
        manager1= new PrefManager(this);

        textView_app_name = findViewById(R.id.text_app_name);

        /*String s1="I agree to terms and conditions";

        SpannableString spanable= new SpannableString(s1);
        spanable.setSpan(new ForegroundColorSpan(Color.RED), 1, 8, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView_app_name.setText(spanable);*/

      /*  SpannableString spannableString = new SpannableString("Hello World!");
        ForegroundColorSpan foregroundSpan = new ForegroundColorSpan(Color.RED);
        spannableString.setSpan(foregroundSpan, 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView_app_name.setText(spannableString);*/



        initilization();

        animRotate = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.rotet);

        if(savedInstanceState == null)
        {

            flyin();
        }

        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                endsplesh();



            }
        },5000);

    }

    private void endsplesh()
    {


        animation = AnimationUtils.loadAnimation(this,
                R.anim.logo_back_animation);
        //image_car...

        animation = AnimationUtils.loadAnimation(this,
                R.anim.app_name_back_animation);
        imageView_car.startAnimation(animation);

        animation = AnimationUtils.loadAnimation(this,
                R.anim.app_name_back_animation);


        //app name...

        animation = AnimationUtils.loadAnimation(this,
                R.anim.app_name_back_animation);
        textView_app_name.startAnimation(animation);

        animation = AnimationUtils.loadAnimation(this,
                R.anim.app_name_back_animation);

        //image_second logo...

        animation = AnimationUtils.loadAnimation(this,
                R.anim.logo_back_animation);
        second_logo_img.startAnimation(animation);

        animation = AnimationUtils.loadAnimation(this,
                R.anim.logo_back_animation);

        //image logo

        animation = AnimationUtils.loadAnimation(this,
                R.anim.logo_back_animation);
        imageView_logo.startAnimation(animation);

        animation = AnimationUtils.loadAnimation(this,
                R.anim.logo_back_animation);

        //text_copy_rights

        animation = AnimationUtils.loadAnimation(this,
                R.anim.app_name_back_animation);
        textView_copy_rights.startAnimation(animation);

        animation = AnimationUtils.loadAnimation(this,
                R.anim.app_name_back_animation);

        //text_copy_all right

        animation = AnimationUtils.loadAnimation(this,
                R.anim.app_name_back_animation);
        textView_all.startAnimation(animation);

        animation = AnimationUtils.loadAnimation(this,
                R.anim.app_name_back_animation);




        //image_bike...

        imageView_bike.startAnimation(animation);
        animation = AnimationUtils.loadAnimation(this,
                R.anim.app_name_back_animation);
        textView_app_name.startAnimation(animation);



        animation.setAnimationListener(new Animation.AnimationListener()
        {
            @Override
            public void onAnimationStart(Animation animation)
            {

            }

            @Override
            public void onAnimationEnd(Animation animation)
            {
                final SessionManager manager= new SessionManager(Splash_Activity.this);



                 SharedPreferences preferences= getSharedPreferences("Introduction_activity",MODE_PRIVATE);
                 String url=preferences.getString("first_time","");

                 if(url.equals("1"))
                 {


                     if(manager.getLogin()!=null)
                     {

                         Intent intent1 = new Intent(Splash_Activity.this, MainActivity.class);
                         startActivity(intent1);
                         finish();

                     }else
                     {
                         Intent intent2 = new Intent(Splash_Activity.this, Login_Activity.class);
                         startActivity(intent2);
                         finish();

                     }

                 }else
                 {
                     Intent intent= new Intent(Splash_Activity.this,Introduction_Activity.class);
                     startActivity(intent);
                     finish();
                 }

            }

            @Override
            public void onAnimationRepeat(Animation animation)
            {

            }
        });


    }

    private void flyin()
    {

        animation = AnimationUtils.loadAnimation(this, R.anim.rotet);
        animation.setDuration(12000);
        imageView_logo.startAnimation(animation);

        animation = AnimationUtils.loadAnimation(this,
                R.anim.bounce_anim);
        animation.setDuration(12000);
        textView_app_name.startAnimation(animation);

        animation = AnimationUtils.loadAnimation(this,
                R.anim.rotet);
        animation.setDuration(12000);
        second_logo_img.startAnimation(animation);


        animation = AnimationUtils.loadAnimation(this,
                R.anim.car_image);
        imageView_car.startAnimation(animation);

        animation = AnimationUtils.loadAnimation(this,
                R.anim.bike);
        imageView_bike.startAnimation(animation);

    }

    private void initilization()
    {
        imageView_logo=findViewById(R.id.imageview_logo);
        imageView_car=findViewById(R.id.image_car);
        imageView_bike=findViewById(R.id.image_bike);
        textView_app_name=findViewById(R.id.text_app_name);
        textView_all=findViewById(R.id.text_all);
        textView_copy_rights=findViewById(R.id.text_copy_rights);
        second_logo_img=findViewById(R.id.second_logo);

    }

}