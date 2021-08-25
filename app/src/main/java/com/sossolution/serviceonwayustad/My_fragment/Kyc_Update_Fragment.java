package com.sossolution.serviceonwayustad.My_fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.loader.content.CursorLoader;

import android.os.Environment;
import android.os.FileUtils;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.text.GetChars;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.sossolution.serviceonwayustad.Model_class.Get_Real_Path;
import com.sossolution.serviceonwayustad.Model_class.RetrofitClient;
import com.sossolution.serviceonwayustad.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;

public class Kyc_Update_Fragment extends Fragment
{

    private static final int INTENT_REQUEST_CODE = 1;
    private static final int RESULT_OK = 121;
    private static final int RECORD_REQUEST_CODE = 55;
    private static final int RESULT_CANCELED = 20;
    private static final int PICK_IMAGE = 211;
    private static final int PICK_IMAGE2 = 200;
    EditText edit_adahar_number;
    String ADDhar_NUmber;
    Button kyc_btn;
    String provider_id;
    TextView text_fount, text_addhar_back;
    private Bitmap bitmap;
    private MultipartBody.Part body1,body2;
    private ImageView imageView_upload,imageView_addhar_back;
    private  File file1;
    private File file2;
    private String front_side_path = "";
    private File my_file;
    private ProgressDialog dialog;
    private TextView  text_aadhar_card_fount,text_aadhar_card_back;
    private Get_Real_Path path;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bank_2, container, false);
       dialog= new ProgressDialog(getActivity());
       path= new Get_Real_Path();
       text_aadhar_card_fount=view.findViewById(R.id.aadhar_card_fount_text);
       text_aadhar_card_back=view.findViewById(R.id.aadhar_card_back_text);
       text_aadhar_card_back.setVisibility(View.GONE);
       text_aadhar_card_fount.setVisibility(View.GONE);
        //permission();

        permission2();

        final SharedPreferences preferences1 = getActivity().getSharedPreferences("Otp_activity", MODE_PRIVATE);
        provider_id = preferences1.getString("user_id", null);

        String type="service_provider";
        String user_id=provider_id;
        showing_data(user_id,type);



        text_addhar_back=view.findViewById(R.id.addhar_back);
        text_addhar_back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                permission1();
                permission2();
               // imageView_addhar_back.setVisibility(View.VISIBLE);
                Toast.makeText(getActivity(), "addhar back", Toast.LENGTH_SHORT).show();
               /* Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(i, "Select Picture"), PICK_IMAGE2);*/

                Intent pickIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                pickIntent.setType("image/*");
                startActivityForResult(Intent.createChooser(pickIntent, "Select Picture"), PICK_IMAGE2);

            }
        });
        imageView_upload = view.findViewById(R.id.my_image_view);
        imageView_upload.setVisibility(View.GONE);
        imageView_addhar_back=view.findViewById(R.id.addhar_image_back);
        imageView_addhar_back.setVisibility(View.GONE);
        edit_adahar_number = view.findViewById(R.id.aadhnum);
        text_fount = view.findViewById(R.id.fount);

        ///first_click_select
        text_fount.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                permission1();
                permission2();
                Toast.makeText(getActivity(), "addhar_fount", Toast.LENGTH_SHORT).show();

                Intent pickIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                pickIntent.setType("image/*");
                startActivityForResult(Intent.createChooser(pickIntent, "Select Picture"), PICK_IMAGE);

            }
        });
        kyc_btn = view.findViewById(R.id.btn_update_kyc);
        kyc_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                dialog.setMessage("Loading image......");
                dialog.show();
                ADDhar_NUmber = edit_adahar_number.getText().toString();

                if (ADDhar_NUmber.isEmpty()) {
                    edit_adahar_number.setError("Enter The Aadhar_number");
                    edit_adahar_number.setFocusable(true);
                } else {
                    // kyc_update(provider_id,ADDhar_NUmber);
                    get_upadte_kyc();

                }
            }
        });
        return view;
    }

    private void showing_data(String user_id,String Type)
    {

        String url="http://www.serviceonway.com/UserKYCDetails?uid="+user_id+"&type="+Type;
        Log.d("show_url",url);
        final JsonArrayRequest request= new JsonArrayRequest(Request.Method.POST, url, null, new com.android.volley.Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response)
            {
                 Log.d("showing_responce",response.toString());
                 imageView_upload.setVisibility(View.VISIBLE);
                 imageView_addhar_back.setVisibility(View.VISIBLE);
                text_aadhar_card_back.setVisibility(View.VISIBLE);
                text_aadhar_card_fount.setVisibility(View.VISIBLE);
               //  text_fount.setVisibility(View.GONE);
                //text_addhar_back.setVisibility(View.GONE);

                try {
                    JSONObject object=response.getJSONObject(0);
                    String adhar_number=object.getString("aadhar_number");
                    edit_adahar_number.setText(adhar_number);
                    String font_image=object.getString("aadhar_front_image");
                    Log.d("font",font_image);
                    String my_fount="https://serviceonway.com/UploadedFiles/ServiceProviderDocsImages/";
                    Glide.with(getActivity())
                            .load(my_fount+font_image)
                            .into(imageView_upload);
                    String back_image=object.getString("aadhar_back_image");
                    Log.d("font", back_image);
                    Glide.with(getActivity())
                            .load(my_fount+back_image)
                            .into(imageView_addhar_back);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new com.android.volley.Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Log.d("error",error.toString());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                HashMap<String,String> hashMap= new HashMap<>();
                return hashMap;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS*48,
                2,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request.setShouldCache(false);
        Volley.newRequestQueue(getActivity()).add(request);




    }

    private void permission2()
    {
        if (Build.VERSION.SDK_INT >=Build.VERSION_CODES.M)
        {
            if (getActivity().checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED)
            {
                Log.v(TAG, "Permission is granted2");
              permission1();
            } else {

                Log.v(TAG, "Permission is revoked2");
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);

            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG, "Permission is granted2");

        }
    }

    private void permission1()
    {

        if (Build.VERSION.SDK_INT >=Build.VERSION_CODES.M)
        {
            if (getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED)
            {
                Log.v(TAG, "Permission is granted1");

                permission2();
            } else {

                Log.v(TAG, "Permission is revoked1");
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 3);

            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG, "Permission is granted1");

        }

    }

    private void permission()
    {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                Log.i(TAG, "Permission to record denied");
                makeRequest();
            }
        } else {

            Toast.makeText(getActivity(), "permission deniy", Toast.LENGTH_SHORT).show();

        }

    }

    private void makeRequest() {
        ActivityCompat.requestPermissions(getActivity(),
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                RECORD_REQUEST_CODE);

    }

    private void get_upadte_kyc()
    {
        RequestBody aadhaar = RequestBody.create(
                MediaType.parse("text/plain"), ADDhar_NUmber);
        RequestBody provider = RequestBody.create(
                MediaType.parse("text/plain"), provider_id);
         //database me check kr
        final Call<String> listCall = RetrofitClient.getInstance().getMyApi().get_upadte_kyc(provider,aadhaar,body1,body2);
        listCall.enqueue(new Callback<String>()
        {
            @Override
            public void onResponse(Call<String> call, Response<String> response)
            {
                dialog.dismiss();
                Log.d("my_result", String.valueOf(response.body()));
                Log.d("call_data", listCall.request().toString());
                Log.e("message", " : " + response.message());
                Log.e("body", " : " + response.body());
                Toast.makeText(getActivity(), "Response " + response.raw().message(), Toast.LENGTH_LONG).show();
                Toast.makeText(getActivity(), "Kyc_update", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t)
            {
                Log.d("_error", t.toString());
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE)
        {
            Toast.makeText(getActivity(), "kyc_update", Toast.LENGTH_SHORT).show();

            if (requestCode == PICK_IMAGE && resultCode == getActivity().RESULT_OK && null != data)
            {

                imageView_upload.setVisibility(View.VISIBLE);

                Uri selectedImage = data.getData();
                //add code
              String     realPath = path.getUriRealPath(getActivity(),selectedImage);
                //Log.i(TAG, "onActivityResult: file path : " + realPath);
                Log.d("onActivityResult",realPath);




               /* String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();
                //  int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                if (cursor == null)
                    return;
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String filePath = cursor.getString(columnIndex);
                cursor.close();*/

                Bitmap bitmap = null;
                try {
                    bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(selectedImage));
                } catch (FileNotFoundException e)
                {
                    e.printStackTrace();
                }
                imageView_upload.setImageBitmap(bitmap);

                try {
                    String path = savebitmap1(bitmap,"2345.jpg");
                    File f2=new File(path);
                    RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), f2);
                    body1 = MultipartBody.Part.createFormData("file", f2.getName(), reqFile);
                    Log.d("body", String.valueOf(body1));
                } catch (IOException e) {
                    e.printStackTrace();
                }

               /* RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"),file2);
                body1 = MultipartBody.Part.createFormData("file",file2.getName(), reqFile);*/

            }else
            {
                Toast.makeText(getActivity(), "data hai", Toast.LENGTH_SHORT).show();
            }
        }
        else if (requestCode == PICK_IMAGE2)
        {
            if (requestCode == PICK_IMAGE2 && resultCode == getActivity().RESULT_OK && null != data) {
                imageView_addhar_back.setVisibility(View.VISIBLE);
                Uri selectedImage = data.getData();

                String     realPath = path.getUriRealPath(getActivity(),selectedImage);
                //Log.i(TAG, "onActivityResult: file path : " + realPath);
                Log.d("onActivityResult",realPath);


               /* //add code
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();
                //  int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                if (cursor == null)
                    return;
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String filePath1 = cursor.getString(columnIndex);
                //Log.d("file_path",filePath);
                cursor.close();*/
                Bitmap bitmap = null;
                try {
                    bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(selectedImage));
                } catch (FileNotFoundException e)
                {
                    e.printStackTrace();
                }
                imageView_addhar_back.setImageBitmap(bitmap);
                try {
                    String path = savebitmap(bitmap,"12345.jpg");
                    File f2=new File(path);
                    RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), f2);
                    body2 = MultipartBody.Part.createFormData("file", f2.getName(), reqFile);
                    Log.d("body", String.valueOf(body2));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //postman pr api hit krke dikkha


            }
        }
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
        fo.write(bytes.toByteArray());
        fo.close();
        return f1.getAbsolutePath();
    }

    public String savebitmap(Bitmap bmp, String filename ) throws IOException
    {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    bmp.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
    File f = new File(
            (getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                    .toString() + File.separator + filename)
    );
    f.createNewFile();
        FileOutputStream fo = new FileOutputStream(f);
    fo.write(bytes.toByteArray());
    fo.close();
    return f.getAbsolutePath();
    }

}
