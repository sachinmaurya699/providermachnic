package com.sossolution.serviceonwayustad.MyAdapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.sossolution.serviceonwayustad.Model_class.My_Car_Service;
import com.sossolution.serviceonwayustad.MyInterface.Car_Service_Interface;
import com.sossolution.serviceonwayustad.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Car_Service_Adapter  extends RecyclerView.Adapter<Car_Service_Adapter.My_Viewholder>
{
    private Context context;
    private My_Car_Service my_car_service;
    private List<My_Car_Service> my_car_serviceList;
    private Car_Service_Interface car_service_interface;
    private List<Integer> data_id = new ArrayList<>();
    private String data_1 = "";
    private String data_2 = "";
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private   CheckBox checkBox;
    private ArrayList<Integer> my_service_id_store= new ArrayList<>();
    private String data_store_id1="";
    private String data_store_id2="";

    //data store...
    public void service_Add(String item)
    {
        data_id.add(Integer.valueOf(item));
    }

    //data remove...
    public void service_remove(String item)
    {
        data_id.remove(Integer.valueOf(item));
    }

    public String getfinallist()
    {
        String my_id = "";

        for (int i : data_id)
        {
            my_id += i + ",";

        }

        return my_id;
    }

    public void putHeart(boolean isChecked, int position)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPref",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        My_Car_Service service= my_car_serviceList.get(position);
        editor.putBoolean("my_store_key",isChecked);
        editor.apply();

    }

    public Car_Service_Adapter(Context context,List<My_Car_Service> my_car_serviceList,Car_Service_Interface car_service_interface)
    {

        this.context=context;
        this.my_car_serviceList=my_car_serviceList;
        this.car_service_interface=car_service_interface;

    }
    
    @NonNull
    @Override
    public My_Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {


        View view = LayoutInflater.from(context).inflate(R.layout.my_car_service_design,parent,false);

        return new My_Viewholder(view);

    }
    @Override
    public void onBindViewHolder(@NonNull Car_Service_Adapter.My_Viewholder holder, final int position)
    {
         My_Car_Service service = my_car_serviceList.get(position);
         String bike_service=service.getService();
         String charge=service.getCharge();
         String Model=service.getModel();
         String Maker=service.getMaker();
         String id=service.getId();
         String chek= String.valueOf(service.getSelected());
         holder.itemView.setOnClickListener
                 (
                 new View.OnClickListener()
            {
             @Override
             public void onClick(View v)
             {
                 //Object view;
                 Toast.makeText(context, "car_s1", Toast.LENGTH_SHORT).show();
                 My_Car_Service service = my_car_serviceList.get(position);

                 if(!service.getSelected())
                 {
                     service.setSelected(true);

                     service_Add(my_car_serviceList.get(position).getId());
                     data_1= getfinallist();
                     car_service_interface.onItemCheck(data_1);



                 }
                 else if(service.getSelected())
                 {
                     Toast.makeText(context, "d_select", Toast.LENGTH_SHORT).show();
                     service.setSelected(false);
                     service_remove(my_car_serviceList.get(position).getId());
                     data_2 =getfinallist();
                     car_service_interface.onItemCheck(data_2);


                 }
                 notifyItemChanged(position);

             }
         });
        holder.textView.setText(Maker+Model+","+bike_service+"\n"+charge);
        holder.checkBox.setChecked(service.getSelected());
      // holder.bind(my_bike_serviceList.get(position),myBikeServiceInterface);
        holder.bind(my_car_serviceList.get(position),car_service_interface);
      //  preferences1.edit().clear().apply();

    }

    public void selectAll()
    {
        ArrayList<My_Car_Service> templist=new ArrayList();
        templist.addAll(my_car_serviceList);
        for(int i=0;i<templist.size();i++)
        {
            templist.get(i).setSelected(true);
        }
        my_car_serviceList.clear();
        my_car_serviceList.addAll(templist);
        notifyDataSetChanged();

    }
   /* public void selected_store_value()
    {
        if( my_service_id_store!=null)
        {
            my_service_id_store.clear();
        }
        for(int i=0;i<my_car_serviceList.size();i++)
        {
            setitemadd(my_car_serviceList.get(i).getId());
        }
        data_store_id1=finallistvalue();
        Log.d("data1_value", data_store_id1);
    }*/

    //remove value
    /*public void remove_store_value()
    {
        if( my_service_id_store!=null)
        {
            my_service_id_store.clear();
        }
        for(int i=0;i<my_car_serviceList.size();i++)
        {
            setitem_remove(my_car_serviceList.get(i).getId());
        }
        data_store_id1=finallistvalue();
        Log.d("data1_value", data_store_id1);
    }*/

   /* private String finallistvalue()
    {
        String my_id="";

        for (int i : my_service_id_store)
        {
            my_id +=i+",";


        }

        return my_id;
    }*/

    /*public  void setitemadd(String s1)
    {
        my_service_id_store.add(Integer.valueOf(s1));
    }*/

    /*public  void setitem_remove(String s1)
    {
        my_service_id_store.remove(Integer.valueOf(s1));
    }*/

    public void deselectAll()
    {
        ArrayList<My_Car_Service> templist=new ArrayList();
        templist.addAll(my_car_serviceList);
        for(int i=0;i<templist.size();i++)
        {
            templist.get(i).setSelected(false);
        }
        my_car_serviceList.clear();
        my_car_serviceList.addAll(templist);
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount()
    {

        Log.d("my_size",String.valueOf(my_car_serviceList.size()));
        return my_car_serviceList.size();

    }

    public class My_Viewholder extends RecyclerView.ViewHolder
    {
        CheckBox checkBox;
        TextView textView;

        public My_Viewholder(@NonNull View itemView)
        {
            super(itemView);

            checkBox=itemView.findViewById(R.id.my_car_chekbox);
            textView=itemView.findViewById(R.id.my_car_details);

        }

        public void bind(My_Car_Service my_car_service, Car_Service_Interface car_service_interface)
        {

            car_service_interface.my_item(my_car_service);

        }
    }
}
