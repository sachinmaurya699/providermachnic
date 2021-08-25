package com.sossolution.serviceonwayustad.My_fragment;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sossolution.serviceonwayustad.Model_class.Bank_details_retrofit;
import com.sossolution.serviceonwayustad.Model_class.Get_Real_Path;
import com.sossolution.serviceonwayustad.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;

public class Bank_Update extends Fragment
{
    private static final int PICK_IMAGE1 =11 ;
    private static final int PICK_IMAGE2 =12 ;
    private EditText edit_pan_card,edit_gst_number,edit_account_holder_name,edit_ifsc_code,edit_account_number,edit_c_account_number;
    private String PAN_NUMBER,GST_NUMBER,ACCOUNT_HOLDER_NAME,IFSC_CODE,ACCOUNT_NUMBER,C_ACCOUNT_NUMBER;
    private Button button_upload;
    private String Provider_id;
    private ImageView imageView_pan_card,imageView_gst_number;
    private Get_Real_Path path;
    private TextView upload_pan_card,upload_gst;
    private MultipartBody.Part body1;
    private MultipartBody.Part body2;
    private ProgressDialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view= inflater.inflate(R.layout.fragment_kyc__blank, container, false);
        permission1();
        permission2();
        dialog= new ProgressDialog(getActivity(),R.style.DialogTheme);
        imageView_pan_card=view.findViewById(R.id.upload_card_image);
        imageView_gst_number=view.findViewById(R.id.upload_gst_image);
        path= new Get_Real_Path();
        edit_pan_card=view.findViewById(R.id.card_number);
        upload_pan_card=view.findViewById(R.id.upload_pancard);
        upload_pan_card.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {


                Intent pickIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                pickIntent.setType("image/*");
                startActivityForResult(Intent.createChooser(pickIntent, "Select Picture"), PICK_IMAGE1);
            }
        });
        upload_gst=view.findViewById(R.id.upload_gst);
        upload_gst.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent pickIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                pickIntent.setType("image/*");
                startActivityForResult(Intent.createChooser(pickIntent, "Select Picture"), PICK_IMAGE2);
            }
        });


        edit_gst_number=view.findViewById(R.id.gst_id);
        edit_account_holder_name=view.findViewById(R.id.holder_name);
        edit_gst_number=view.findViewById(R.id.gst_id);
        edit_ifsc_code=view.findViewById(R.id.ifsc_code);
        edit_account_number=view.findViewById(R.id.ACCOUNT_NUM);
        edit_c_account_number=view.findViewById(R.id.ACCOUNT_NUM_CONF);
        button_upload=view.findViewById(R.id.upload_bank);

        SharedPreferences preferences2 = getActivity().getSharedPreferences("Otp_activity",MODE_PRIVATE);
        Provider_id=preferences2.getString("user_id",null);
        Log.d("id_pro",Provider_id);

        button_upload.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                PAN_NUMBER=edit_pan_card.getText().toString();
                GST_NUMBER=edit_gst_number.getText().toString();
                ACCOUNT_HOLDER_NAME=edit_account_holder_name.getText().toString();
                IFSC_CODE=edit_ifsc_code.getText().toString();
                ACCOUNT_NUMBER=edit_account_number.getText().toString();
                C_ACCOUNT_NUMBER=edit_c_account_number.getText().toString();

                Log.d("all value",PAN_NUMBER+GST_NUMBER+ACCOUNT_HOLDER_NAME+IFSC_CODE+ACCOUNT_NUMBER);
                if(PAN_NUMBER.isEmpty())
                {
                    edit_pan_card.setError("Enter The PAN-NUMBER");
                    edit_pan_card.setFocusable(true);
                }else if(GST_NUMBER.isEmpty())
                {
                    edit_gst_number.setError("Enter The GST-NUMBER");
                    edit_gst_number.setFocusable(true);
                }else if(ACCOUNT_HOLDER_NAME.isEmpty())
                {
                    edit_account_holder_name.setError("Enter Holder Name");
                    edit_account_holder_name.setFocusable(true);
                }else if(IFSC_CODE.isEmpty())
                {
                    edit_ifsc_code.setError("Enter The Ifsc_Code");
                    edit_ifsc_code.setFocusable(true);
                }else if(ACCOUNT_NUMBER.isEmpty())
                {
                    edit_account_number.setError("Enter The Account Number");
                    edit_account_number.setFocusable(true);
                }else if(!ACCOUNT_NUMBER.equals( C_ACCOUNT_NUMBER))
                {
                    edit_c_account_number.setError("Account number and Confirm Account number should be same");
                }
                else
                {
                    dialog.setMessage("Upload Image....");
                    dialog.setCancelable(false);
                    dialog.show();

                    get_upadte_bank();
                }

            }
        });

        return view;
    }

    private void get_upadte_bank()
    {

        RequestBody provider_id = RequestBody.create(
                MediaType.parse("text/plain"),Provider_id);
        RequestBody pan_number = RequestBody.create(
                MediaType.parse("text/plain"), PAN_NUMBER);
        RequestBody gst_number = RequestBody.create(
                MediaType.parse("text/plain"), GST_NUMBER);
        RequestBody account_holder_name=RequestBody.create(
                MediaType.parse("text/plain"), ACCOUNT_HOLDER_NAME);
        RequestBody ifsc_code=RequestBody.create(
                MediaType.parse("text/plain"),IFSC_CODE);
        RequestBody account_number=RequestBody.create(
                MediaType.parse("text/plain"),ACCOUNT_NUMBER);

        final Call<String> listcall= Bank_details_retrofit.getInstance().getMyApi_bank().get_bank_details_update(provider_id,pan_number,gst_number,account_holder_name,ifsc_code,account_number,body1,body2);
        listcall.enqueue(new Callback<String>()
        {
            @Override
            public void onResponse(Call<String> call, Response<String> response)
            {
                dialog.dismiss();
                Log.d("my_result", String.valueOf(response.body()));
                Log.d("call_data", listcall.request().toString());
                Log.e("message", " : " + response.message());
                Log.e("body", " : " + response.body());
               // Toast.makeText(getActivity(), "Response " + response.raw().message(), Toast.LENGTH_LONG).show();
                Toast.makeText(getActivity(), "Bank_Details_update", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<String> call, Throwable t)
            {
                Log.d("error",t.toString());
                Toast.makeText(getActivity(), "fail", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void permission2()
    {
        if (Build.VERSION.SDK_INT >=Build.VERSION_CODES.M)
        {
            if (getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED)
            {
                Log.v(TAG, "Permission is granted1");

             //   permission2();
            } else {

                Log.v(TAG, "Permission is revoked1");
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 3);

            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG, "Permission is granted1");

        }


    }

    private void permission1()
    {
        if (Build.VERSION.SDK_INT >=Build.VERSION_CODES.M)
        {
            if (getActivity().checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED)
            {
                Log.v(TAG, "Permission is granted2");
               // permission2();
            } else {

                Log.v(TAG, "Permission is revoked2");
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);

            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG, "Permission is granted2");

        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE1)
        {
            Toast.makeText(getActivity(), "kyc_update", Toast.LENGTH_SHORT).show();

            if (requestCode == PICK_IMAGE1 && resultCode == getActivity().RESULT_OK && null != data)
            {

                imageView_pan_card.setVisibility(View.VISIBLE);

                Uri selectedImage = data.getData();
                //add code
                String   realPath = path.getUriRealPath(getActivity(),selectedImage);
                 Log.d("onActivityResult",realPath);

                Bitmap bitmap = null;
                try {
                    bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(selectedImage));
                } catch (FileNotFoundException e)
                {
                    e.printStackTrace();
                }
                imageView_pan_card.setImageBitmap(bitmap);

                try {
                    String path = savebitmap1(bitmap,"2345.jpg");
                    File f2=new File(path);
                    RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), f2);
                    body1 = MultipartBody.Part.createFormData("file", f2.getName(), reqFile);
                    Log.d("body", String.valueOf(body1));
                } catch (IOException e)
                {
                    e.printStackTrace();
                }


            }else
            {
                Toast.makeText(getActivity(), "data hai", Toast.LENGTH_SHORT).show();
            }

        }else if (requestCode == PICK_IMAGE2)
        {
            if (requestCode == PICK_IMAGE2 && resultCode == getActivity().RESULT_OK && null != data)
            {
                imageView_gst_number.setVisibility(View.VISIBLE);
                Uri selectedImage1 = data.getData();

                String     realPath = path.getUriRealPath(getActivity(),selectedImage1);
                //Log.i(TAG, "onActivityResult: file path : " + realPath);
                Log.d("onActivityResult",realPath);

                Bitmap bitmap1 = null;
                try {
                    bitmap1 = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(selectedImage1));
                } catch (FileNotFoundException e)
                {
                    e.printStackTrace();
                }
                imageView_gst_number.setImageBitmap(bitmap1);
                String path = savebitmap(bitmap1,"12345.jpg");
                File f2=new File(path);
                RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), f2);
                body2 = MultipartBody.Part.createFormData("file", f2.getName(), reqFile);
                Log.d("body", String.valueOf(body2));
                //postman pr api hit krke dikkha
            }else
            {
                Toast.makeText(getActivity(), "data hai", Toast.LENGTH_SHORT).show();
            }
        }else
        {
            //
        }

    }

    private String savebitmap(Bitmap bitmap1, String filename)
    {

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap1.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        File f = new File(
                (getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                        .toString() + File.separator + filename)
        );
        try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileOutputStream fo = null;
        try {
            fo = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            fo.write(bytes.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fo.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return f.getAbsolutePath();
    }

    private String savebitmap1(Bitmap bitmap1, String filename2) throws IOException
    {

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap1.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

        File f1 = new File(
                (getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                        .toString() + File.separator + filename2)
        );
        try {
            f1.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileOutputStream fo = null;
        try {
            fo = new FileOutputStream(f1);
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        try {
            fo.write(bytes.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
        fo.close();
        return f1.getAbsolutePath();

    }
}