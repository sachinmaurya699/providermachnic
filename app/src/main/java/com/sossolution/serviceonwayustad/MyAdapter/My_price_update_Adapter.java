package com.sossolution.serviceonwayustad.MyAdapter;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sossolution.serviceonwayustad.Model_class.Price_Update;
import com.sossolution.serviceonwayustad.MyInterface.My_price_interface;
import com.sossolution.serviceonwayustad.R;

import java.util.ArrayList;
import java.util.List;

public class My_price_update_Adapter extends RecyclerView.Adapter<My_price_update_Adapter.My_viewholder>
{

    private Context context;
    public  List<Price_Update> price_updates_list;
    private My_price_interface my_price_interface;

    //add new code......
    private List<Integer> my_list = new ArrayList<>();

    private List<Double> my_list_price = new ArrayList<>();

    //crose click reove value
    private List<Integer> my_delete_list= new ArrayList<>();

    //multiple price update list
    private List<String> my_multiple_price_update=new ArrayList<>();
    private  int price1;
    String Id_object;
    boolean click = true;

    private String data1="";
    private String data2="";
    private String data3="";
    private String data4="";
    private String data5="";

    private String data6="";
    private String data7="";

    private String store_price;
    private String price;
    private String store_price_old;
    private int value;
    String price_id;
    /* ................//all service..........*/
    ///data store...
    public void adddata(String s1)
    {
        try {
            my_list.add(Integer.valueOf(s1));

        }catch (NumberFormatException exception)
        {

        }

    }
    //data remove.....
    public void  remove(String s2)
    {
        try {
            my_list.remove(Integer.valueOf(s2));
        }catch (NumberFormatException exception)
        {

        }

    }

    //final store data...
    public String finaldata()
    {
        String alldata="";

        for (int i:my_list)
        {
            alldata+=i+",";
        }
        return alldata;
    }

   /*...............all price.........*/
    ///...price_store
    public void priceadd(String s1)
    {
        if(my_list_price!=null)
        {
          //  Toast.makeText(context, "my list not null", Toast.LENGTH_SHORT).show();
            try
            {
                my_list_price.add(Double.valueOf(s1));
            }catch (NumberFormatException e)
            {

            }
        }else
        {
            //Toast.makeText(context, "my list null", Toast.LENGTH_SHORT).show();
        }


    }

   // price remove
    public void  priceremove(String s2)
    {
        try {
            my_list_price.remove(Double.valueOf(s2));
        }catch (NumberFormatException e)
        {

        }

    }


    public String updateprice()
    {
        String update="";

        for(double i :my_list_price)
        {
            update+=i+",";
        }
        return update;
    }

    //delete list
    public void add_delete_list(String s1)
    {

        my_delete_list.add(Integer.valueOf(s1));

    }
    //final list
    public String final_delete_list()
    {
        String add_delete_list="";


        for(int i:my_delete_list)
        {
            add_delete_list+=i;
        }

        return add_delete_list;
    }

    //multiple price update.......

    private void addpriceservice(String s1)
    {
        my_multiple_price_update.add(s1);
    }

    //multiple price update.........

    private void removepriceservice(String s1)
    {
        my_multiple_price_update.remove(s1);
    }

    //final price.....
    private String  finalpriceservice()
    {
        String finalpriceservice="";

        for(String i: my_multiple_price_update)
        {
            finalpriceservice+=i;
        }

        return finalpriceservice;
    }

    ///item remove method
   /* public void  remove_item(String position)
    {
        int newPosition =
        price_updates_list.remove();
        notifyItemRemoved(newPosition);
        notifyItemRangeChanged(newPosition, price_updates_list.size());
    }*/

    public My_price_update_Adapter(Context context,List<Price_Update> price_updates_list,My_price_interface my_price_interface)
    {

        this.context=context;
        this.price_updates_list=price_updates_list;
        this.my_price_interface=my_price_interface;

    }
    @NonNull
    @Override
    public My_viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(context).inflate(R.layout.my_desing_price2,parent,false);

        return new My_viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final My_viewholder holder, final int position)
    {

          final Price_Update price_update=price_updates_list.get(position);
         price=price_update.getPrice();
        String maker=price_update.getMaker();
        String model=price_update.getModel();
        Log.d("get_price",price);
        String service=price_update.getService();
        Log.d("get_service",service);
        String id=price_update.getId();
        final String price_update1=price_update.getPrice_update();

        String check= String.valueOf(price_update.getItem_selected());
        String service_provider="my_service";
        holder.textView_service.setText(maker+model+","+service);
        final String edit_box=holder.editText_price.getText().toString();
        Log.d("edit_text",edit_box.toString());
        holder.editText_price.setText(price);
        price_updates_list.get(position).setUpdated_price(price);

        /*.................condition create.............*/

        holder.image_check.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Price_Update price_update=price_updates_list.get(position);
               // Toast.makeText(context, "click image one_time", Toast.LENGTH_SHORT).show();
                holder.image_check.setImageResource(R.drawable.check_circle);

                if(!holder.editText_price.getText().toString().trim().isEmpty())
                    {
                       // Toast.makeText(context, "inside click", Toast.LENGTH_SHORT).show();

                        price_updates_list.get(position).setItem_selected(!price_update.getItem_selected());

                        // holder.image_check.setBackground(ContextCompat.getDrawable(context, R.drawable.icon_green_remove));
                        //price_update.setItem_selected(true);
                        // Toast.makeText(context, "not empty", Toast.LENGTH_SHORT).show();
                        priceadd( holder.editText_price.getText().toString().trim());
                        data3=updateprice();
                        my_price_interface.my_price_store(data3);


                        holder.image_check.setImageResource(R.drawable.check_circle);
                        //Toast.makeText(context, "edit_text_price", Toast.LENGTH_SHORT).show();

                        if(price_update.getItem_selected())
                        {
                            holder.image_check.setImageResource(R.drawable.check_circle);
                           // Toast.makeText(context, "click image two_time", Toast.LENGTH_SHORT).show();

                        }
                        else
                        {
                            holder.image_check.setImageResource(R.drawable.icon_remov);
                        }

                    }else
                    {
                       // Toast.makeText(context, "inside_not click", Toast.LENGTH_SHORT).show();
                        Toast.makeText(context, "field is empty", Toast.LENGTH_SHORT).show();
                    }

            }
        });

        holder.editText_price.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
                //store_price_old=holder.editText_price.getText().toString();
               // Log.d("value_store",store_price_old);
              //  Toast.makeText(context, "before text change....", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

              /*  store_price=holder.editText_price.getText().toString();

                   //{"id":tempId,"price":store_price}

                //JSONObject jsonObject= new JSONObject();




                Log.d("store_value",store_price);

                Toast.makeText(context, "value"+store_price, Toast.LENGTH_SHORT).show();*/
              /*  Double newData = new Double(store_price);
                 value = newData.intValue();
                Log.d("value", store_price);*/
            }

            @Override
            public void afterTextChanged(Editable s)
            {
                if(!holder.editText_price.getText().toString().trim().isEmpty())
                price_updates_list.get(position).setUpdated_price(holder.editText_price.getText().toString());
            }
        });
        holder.checkBox.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                Price_Update price_update=price_updates_list.get(position);
                String stroreprice2=store_price;
                CheckBox checkBox=(CheckBox) v;

                if(checkBox.isChecked())
                {
                    if(!holder.editText_price.getText().toString().trim().isEmpty())
                    {
                      //  Toast.makeText(context, "first click", Toast.LENGTH_SHORT).show();
                        checkBox.setChecked(true);
                        price_update.setItem_selected(true);
                        checkBox.setBackgroundColor(Color.rgb(64, 131, 207));

                        //new line add

                        adddata(price_updates_list.get(position).getId());
                        addpriceservice(price_updates_list.get(position).getId()+",");
                        data6=finalpriceservice();
                        my_price_interface.on_item_final_price(data6);
                        priceadd( holder.editText_price.getText().toString().trim());


                   /* if(checkBox.isChecked())
                    {
                        String stroreprice=store_price;
                        price_update.setItem_selected(true);
                        adddata(price_updates_list.get(position).getId());
                        addpriceservice(price_updates_list.get(position).getId()+",");
                        data6=finalpriceservice();
                        my_price_interface.on_item_final_price(data6);


                        if(!holder.editText_price.getText().toString().trim().isEmpty())
                        {
                           // Toast.makeText(context, "data hai", Toast.LENGTH_SHORT).show();
                            priceadd( holder.editText_price.getText().toString().trim());

                        }else
                        {
                           // Toast.makeText(context, "not edit data", Toast.LENGTH_SHORT).show();
                            // priceadd(store_price_old);
                            // priceadd(price);
                        }
                        //price value pass.....

                        checkBox.setBackgroundColor(Color.rgb(64, 131, 207));
                     *//*   data3=updateprice();*//*
                        data1=finaldata();
                        my_price_interface.on_item_price_check(data1);
                     *//*   my_price_interface.my_price_store(data3);*//*

                    }else if(!checkBox.isChecked())
                    {
                        price_update.setItem_selected(false);
                        remove(price_updates_list.get(position).getId());
                        removepriceservice(price_updates_list.get(position).getId()+"="+ value);
                        String stroreprice=store_price;
                        if(!holder.editText_price.getText().toString().trim().isEmpty())
                        {
                           // Toast.makeText(context, "data hai", Toast.LENGTH_SHORT).show();
                            priceremove(holder.editText_price.getText().toString().trim());
                        }else
                        {
                            //Toast.makeText(context, "not edit data", Toast.LENGTH_SHORT).show();
                            //  priceremove(store_price_old);
                            // priceremove(price);
                        }

                        checkBox.setBackgroundColor(Color.WHITE);
                        data2=finaldata();
                       *//* data4=updateprice();*//*
                        data6=finaldata();
                        my_price_interface.on_item_final_price(data6);
                        my_price_interface.on_item_price_check(data2);
                        *//*my_price_interface.my_price_store(data4);*//*
                    }
*/
                    }
                }else if(!checkBox.isChecked())
                {
                    checkBox.setBackgroundColor(Color.WHITE);

                   // Toast.makeText(context, "not checked", Toast.LENGTH_SHORT).show();
                }


                else
                {
                    checkBox.setChecked(false);
                    price_update.setItem_selected(true);
                   // Toast.makeText(context, "please fill price", Toast.LENGTH_SHORT).show();
                }

            }
        });

        holder.bind(price_updates_list.get(position), my_price_interface);
        holder.checkBox.setChecked(Boolean.parseBoolean(check));
        holder.image_crose.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                Price_Update price_update=price_updates_list.get(position);
                String listname=price_updates_list.get(position).getId();
               // Toast.makeText(context,listname, Toast.LENGTH_SHORT).show();
                add_delete_list(price_updates_list.get(position).getId());
                //remove_item(price_updates_list.get(position).getId());
                data5=final_delete_list();
                my_price_interface.my_delete_list(data5);

            }
        });

    }

    @Override
    public int getItemCount()
    {
        return price_updates_list.size();
    }

    public class My_viewholder  extends RecyclerView.ViewHolder
    {

        TextView textView_service;
        EditText editText_price;
        CheckBox checkBox;
        ImageView image_crose;
        ImageView image_check;

        public My_viewholder(@NonNull View itemView)
        {
            super(itemView);
            textView_service=itemView.findViewById(R.id.text_service2);
            editText_price=itemView.findViewById(R.id.my_price2);
            checkBox=itemView.findViewById(R.id.my_checkbox_bike);
            image_crose=itemView.findViewById(R.id.imageView2_bike);
            image_check=itemView.findViewById(R.id.check_bike_price_1);

        }

        public void bind(Price_Update price_update, My_price_interface my_price_interface)
        {
            my_price_interface.my_price_update(price_update);

        }
    }
}
