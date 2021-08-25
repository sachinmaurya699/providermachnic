package com.sossolution.serviceonwayustad.My_Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.sossolution.serviceonwayustad.R;

public class Upload_Activity extends AppCompatActivity
{

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_);

        button=findViewById(R.id.conform_form);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button=findViewById(R.id.conform_form);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent= new Intent(Upload_Activity.this,MainActivity.class);
                        startActivity(intent);
                    }
                });

            }
        });
    }
}