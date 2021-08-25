package com.sossolution.serviceonwayustad.My_Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sossolution.serviceonwayustad.Model_class.PrefManager;
import com.sossolution.serviceonwayustad.R;
import com.sossolution.serviceonwayustad.SessionManager;

public class Introduction_Activity extends AppCompatActivity
  {

    private ViewPager viewPager;
    private MyViewPagerAdapter my_viewpager_adapter;
    private LinearLayout dotsLayout;
    private TextView[] dots;
    private int[] layouts;
    private Button btnSkip, btnNext;
    private PrefManager prefManager;
    private  ViewPager.OnPageChangeListener changeListener1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction_);

        String s1="1";
        SharedPreferences preferences= getSharedPreferences("Introduction_activity",MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("first_time",s1);
        editor.apply();



        viewPager = findViewById(R.id.view_pager_id);
        final SessionManager manager= new SessionManager(this);
        dotsLayout = findViewById(R.id.layoutDots);
        btnSkip = findViewById(R.id.button_skip);
        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                launchHomeScreen();
            }
        });
        btnNext = findViewById(R.id.button_next);
        btnNext.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                if(manager.getLogin()!=null)
                {
                    Intent intent = new Intent(Introduction_Activity.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                }else
                {
                   // Toast.makeText(Introduction_Activity.this, "data  nhi hai", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Introduction_Activity.this, Login_Activity.class);
                    startActivity(intent);
                    finish();

                }

               /* Intent intent = new Intent(Introduction_Activity.this,Login_Activity.class);
                startActivity(intent);*/

            }

        });

        if (Build.VERSION.SDK_INT >= 21)
        {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        // add few more layouts if you want
        layouts = new int[]
                {

                R.layout.first_slider,
                R.layout.second_slide,
                R.layout.three_slider,
                R.layout.four_slider
                };

        // adding bottom dots
        addBottomDots(0);

        changeStatusBarColor();


        my_viewpager_adapter = new MyViewPagerAdapter();
        viewPager.setAdapter(my_viewpager_adapter);
        viewPager.addOnPageChangeListener(changeListener1);

         changeListener1 = new ViewPager.OnPageChangeListener()
         {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            {
              //  Toast.makeText(Introduction_Activity.this, "first", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPageSelected(int position)
            {
               // Toast.makeText(Introduction_Activity.this, "second", Toast.LENGTH_SHORT).show();

                addBottomDots(position);

                // changing the next button text 'NEXT' / 'GOT IT'
                if (position == layouts.length - 1)
                {
                   // Toast.makeText(Introduction_Activity.this, "decrise1", Toast.LENGTH_SHORT).show();
                    // last page. make button text to GOT IT
                    btnNext.setText(getString(R.string.start));
                    btnSkip.setVisibility(View.GONE);
                } else {
                    // still pages are left
                    //Toast.makeText(Introduction_Activity.this, "decrise2", Toast.LENGTH_SHORT).show();

                    btnNext.setText(getString(R.string.next));
                    btnSkip.setVisibility(View.VISIBLE);
                }


            }

            @Override
            public void onPageScrollStateChanged(int state)
            {
               // Toast.makeText(Introduction_Activity.this,"thired"+state, Toast.LENGTH_SHORT).show();
            }
        };

    }

    private void changeStatusBarColor()
    {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }


    }

    private void addBottomDots(int currentPage) {
        dots = new TextView[layouts.length];

        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorsInactive[currentPage]);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(colorsActive[currentPage]);


    }

    private int getItem(int i)
    {
        return viewPager.getCurrentItem() + i;
    }

    private void launchHomeScreen() {

        prefManager.setFirstTimeLaunch(false);

        startActivity(new Intent(Introduction_Activity.this, MainActivity.class));
        finish();


    }

    public class MyViewPagerAdapter extends PagerAdapter
    {
        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter()
        {

        }

        @Override
        public Object instantiateItem(ViewGroup container, int position)
        {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(layouts[position], container, false);
            container.addView(view);

            return view;
        }

        @Override
        public int getCount()
        {
           // Toast.makeText(Introduction_Activity.this,layouts.length+"value1", Toast.LENGTH_SHORT).show();
            return layouts.length;

        }

        @Override
        public boolean isViewFromObject(View view, Object obj)
        {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object)
        {
            View view = (View) object;
            container.removeView(view);
           // Toast.makeText(Introduction_Activity.this, "last"+position, Toast.LENGTH_SHORT).show();
        }
    }
}
