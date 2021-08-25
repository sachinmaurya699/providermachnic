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
import com.sossolution.serviceonwayustad.Model_class.Carprice;
import com.sossolution.serviceonwayustad.MyInterface.My_car_price_update_interfacr;
import com.sossolution.serviceonwayustad.R;

import java.util.ArrayList;
import java.util.List;

public class My_Car_price_Adapter extends RecyclerView.Adapter<My_Car_price_Adapter.My_viewholder>
   {
       private Context context;

       public List<Carprice> car_price_modelList;

       private My_car_price_update_interfacr my_car_price_update_interfacr;
       String check;
       //add new code
       //add new code......
       private List<Integer> my_list_car_service = new ArrayList<>();

       private List<Double> my_list_car_price = new ArrayList<>();

       private List<Integer> my_list_car_delete= new ArrayList<>();

       private List<String> my_multiple_price_car=new ArrayList<>();

       private String data1="";
       private String data2="";
       private String data3="";
       private String data4="";
       private String data5="";
       //..
       private String data6="";

       private String store_price;
       private String store_price_old;
       private  String price;
       //all service store
       /* ................//all service..........*/
       ///data store...
       public void adddata_car(String s1)
       {
           my_list_car_service.add(Integer.valueOf(s1));
       }
       //data remove.....
       public void  remove_data_car(String s2)
       {
           my_list_car_service.remove(Integer.valueOf(s2));
       }

       //final store data...
       public String finaldata_car()
       {
           String alldata="";

           for (int i:my_list_car_service)
           {
               alldata+=i+",";
           }
           return alldata;
       }

       //price data store

       /*...............all price.........*/
       ///...price_store
       public void priceadd_car(String s4)
       {
           my_list_car_price.add(Double.valueOf(s4));

       }
       // price remove
       public void  priceremove_car(String s2)
       {
           my_list_car_price.remove(Double.valueOf(s2));
       }

       public String updateprice_car()
       {
           String update="";

           for(Double i :my_list_car_price)
           {
               update+=i+",";
           }
           return update;
       }
       public void delete_list(String s1)
       {
           my_list_car_delete.add(Integer.valueOf(s1));
       }
       public String final_delete_list()
       {
           String my_delete_list="";
           for(int i:my_list_car_delete)
           {
               my_delete_list+=i;
           }

         return my_delete_list;

       }

       //multiple price update.......
       //multiple price update.......

       private void addpriceservice_car(String s1)
       {
           my_multiple_price_car.add(s1);
       }

       //multiple price update.........

       private void removepriceservice_car(String s1)
       {
           my_multiple_price_car.remove(s1);
       }

       //final price.....
       private String  finalpriceservice_car()
       {
           String finalpriceservice="";

           for(String i: my_multiple_price_car)
           {
               finalpriceservice+=i;
           }

           return finalpriceservice;
       }



       public My_Car_price_Adapter(Context context,List<Carprice> car_price_modelList,My_car_price_update_interfacr my_car_price_update_interfacr)
       {
           this.context=context;
           this.car_price_modelList = car_price_modelList;
           this.my_car_price_update_interfacr=my_car_price_update_interfacr;
       }
       @NonNull
    @Override
    public My_viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.my_upadate_car_price,parent,false);

        return new My_viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final My_Car_price_Adapter.My_viewholder holder, final int position)
    {

        Carprice carprice=car_price_modelList.get(position);
         price=carprice.getPrice();
         Log.d("get_pirice",price);
        String Maker=carprice.getMaker();
        String Model=carprice.getMOdel();
        String service=carprice.getService();
        String service_id=carprice.getId();
        holder.editText_price.setText(price);
        holder.car_textview.setText(Maker+Model+","+service);

         check= String.valueOf(carprice.getItem_selected_car());
        final String edit_box=holder.editText_price.getText().toString();
        Log.d("edit_text",edit_box.toString());
        holder.editText_price.setText(price);
        holder.editText_price.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
               // store_price_old=holder.editText.getText().toString();
/*
                if(!holder.editText_price.getText().toString().trim().isEmpty())
                    car_price_modelList.get(position).setUpdated_car_price(holder.editText_price.getText().toString());*/

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

                store_price=holder.editText_price.getText().toString();

               // Toast.makeText(context, ""+store_price, Toast.LENGTH_SHORT).show();

                Log.d("value", store_price);
            }

            @Override
            public void afterTextChanged(Editable s)
            {
                if(!holder.editText_price.getText().toString().trim().isEmpty())
                    car_price_modelList.get(position).setUpdated_car_price(holder.editText_price.getText().toString());

            }
        });

        holder.checkBox.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Carprice price_update=car_price_modelList.get(position);

                String stroreprice2=store_price;
                CheckBox checkBox=(CheckBox) v;

                if(checkBox.isChecked())
                {
                    checkBox.setBackgroundColor(Color.parseColor("#cbff75"));
                    if(!holder.editText_price.getText().toString().trim().isEmpty())
                    {
                        checkBox.setChecked(true);

                        //  Toast.makeText(context, "value hai...", Toast.LENGTH_SHORT).show();

                        if(checkBox.isChecked())
                        {
                            // Toast.makeText(context, "check", Toast.LENGTH_SHORT).show();
                            checkBox.setBackgroundColor(Color.parseColor("#cbff75"));
                            price_update.setItem_selected_car(true);
                            adddata_car(car_price_modelList.get(position).getId());
                            //price value pass.....
                            String stroreprice=store_price;
                            if(!holder.editText_price.getText().toString().trim().isEmpty())
                            {
                                checkBox.setBackgroundColor(Color.parseColor("#cbff75"));
                                //  Toast.makeText(context, "data hai", Toast.LENGTH_SHORT).show();
                                priceadd_car(holder.editText_price.getText().toString().trim());

                            }else
                            {
                                // Toast.makeText(context, "not edit data", Toast.LENGTH_SHORT).show();
                                // priceadd_car(store_price_old);
                                // priceadd_car(price);
                            }

                            addpriceservice_car( car_price_modelList.get(position).getId()+",");
                            data6=finalpriceservice_car();
                            my_car_price_update_interfacr.on_item_final_price_car(data6);

                            // priceadd_car( store_price);
                            data3=updateprice_car();
                            data1=finaldata_car();
                            my_car_price_update_interfacr.on_item_car_price_check(data1);
                            my_car_price_update_interfacr.my_car_price_store(data3);

                        }else if(!checkBox.isChecked())
                        {
                            //  Toast.makeText(context, "uncheck", Toast.LENGTH_SHORT).show();
                            price_update.setItem_selected_car(false);
                            remove_data_car(car_price_modelList.get(position).getId());

                            String stroreprice=store_price;
                            if(!holder.editText_price.getText().toString().trim().isEmpty())
                            {
                                //  Toast.makeText(context, "data hai", Toast.LENGTH_SHORT).show();
                                priceremove_car(holder.editText_price.getText().toString().trim());
                            }else
                            {
                                // Toast.makeText(context, "not edit data", Toast.LENGTH_SHORT).show();
                                // priceremove_car(store_price_old);
                                //priceremove_car(price);
                            }

                            //  priceremove_car(store_price);
                            data6=finaldata_car();
                            my_car_price_update_interfacr.on_item_final_price_car(data6);
                            data2=finaldata_car();
                            data4=updateprice_car();
                            my_car_price_update_interfacr.on_item_car_price_check(data2);
                            my_car_price_update_interfacr.my_car_price_store(data4);
                        }

                    }else
                    {
                        checkBox.setChecked(false);
                        //  Toast.makeText(context, "fill value", Toast.LENGTH_SHORT).show();
                        // Toast.makeText(context, ""+price, Toast.LENGTH_SHORT).show();
                    }
                }else if(!checkBox.isChecked())
                {
                    checkBox.setBackgroundColor(Color.WHITE);

                   // Toast.makeText(context, "not checked", Toast.LENGTH_SHORT).show();
                }
                else
                    {
                    checkBox.setChecked(false);
                    price_update.setItem_selected_car(true);
                   // Toast.makeText(context, "please fill price", Toast.LENGTH_SHORT).show();
                   }

                holder.bind(car_price_modelList.get(position),my_car_price_update_interfacr);

            }
        });
        holder.imageView_crose.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String price_update=car_price_modelList.get(position).getId();
               // Toast.makeText(context,price_update, Toast.LENGTH_SHORT).show();
                delete_list(car_price_modelList.get(position).getId());
                //add_delete_list(price_updates_list.get(position).getId());
                data5=final_delete_list();
                my_car_price_update_interfacr.my_delete_list(data5);

            }
        });
        holder.checkBox.setChecked(Boolean.parseBoolean(check));
        car_price_modelList.get(position).setUpdated_car_price(price);
        holder.image_check_price.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Carprice price_update1=car_price_modelList.get(position);
                holder.image_check_price.setImageResource(R.drawable.check_circle);

                if(!holder.editText_price.getText().toString().trim().isEmpty())
                {
                    holder.image_check_price.setImageResource(R.drawable.check_circle);
                    //price_update1.setItem_selected_car(true);
                    // Toast.makeText(context, "not empty", Toast.LENGTH_SHORT).show();
                    priceadd_car( holder.editText_price.getText().toString().trim());
                    //new add line....
                    car_price_modelList.get(position).setItem_selected_car(!price_update1.getItem_selected_car());
                    data3=updateprice_car();
                    my_car_price_update_interfacr.my_price_store_car(data3);
                    holder.image_check_price.setImageResource(R.drawable.check_circle);

                    if(price_update1.getItem_selected_car())
                    {
                       // Toast.makeText(context, "car_one", Toast.LENGTH_SHORT).show();
                        holder.image_check_price.setImageResource(R.drawable.check_circle);

                    }else
                    {
                       // Toast.makeText(context, "car_one", Toast.LENGTH_SHORT).show();
                        holder.image_check_price.setImageResource(R.drawable.icon_remov);
                    }


                }else
                {
                    Toast.makeText(context, "field is empty", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    public int getItemCount()
    {
        return car_price_modelList.size();
    }

    public class My_viewholder extends RecyclerView.ViewHolder
    {
         CheckBox checkBox;
         TextView car_textview;
         EditText editText_price;
         ImageView imageView_crose;
         ImageView image_check_price;

        public My_viewholder(@NonNull View itemView)
        {
            super(itemView);
            checkBox=itemView.findViewById(R.id.my__car_price_checkbox);
            car_textview=itemView.findViewById(R.id.service_my_car);
            editText_price=itemView.findViewById(R.id.my_price_car);
            imageView_crose=itemView.findViewById(R.id.imageView2);
            image_check_price=itemView.findViewById(R.id.check_car_price);

        }

        public void bind(Carprice carprice, My_car_price_update_interfacr my_car_price_update_interfacr)
        {
            my_car_price_update_interfacr.my_car_price_update(carprice);
        }
    }
}
