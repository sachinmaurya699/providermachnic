package com.sossolution.serviceonwayustad.MyAdapter;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.sossolution.serviceonwayustad.Model_class.My_Bike_Service;
import com.sossolution.serviceonwayustad.Model_class.My_Car_Service;
import com.sossolution.serviceonwayustad.MyInterface.My_bike_service_interface;
import com.sossolution.serviceonwayustad.R;

import java.util.ArrayList;
import java.util.List;

public class Bike_Service_Adapter  extends RecyclerView.Adapter<Bike_Service_Adapter.Myview_holder>
{

    private Context context;
    private List<My_Bike_Service> my_bike_serviceList;
    private My_bike_service_interface myBikeServiceInterface;
    private List<Integer> my_data= new ArrayList<>();
    private String data_1="";
    private String data_2="";

    //store data.....
    public void add_service(String value1)
    {
        my_data.add(Integer.valueOf(value1));

    }
    //remove data.....
    public void remove_service(String value2)
    {
        my_data.add(Integer.valueOf(value2));
    }
    public String finallist()
    {
        String my_id="";

        for(int i:my_data)
        {
            my_id+=i+",";
        }
     return my_id;
    }
    public Bike_Service_Adapter(Context context,List<My_Bike_Service> my_bike_serviceList,My_bike_service_interface myBikeServiceInterface)
    {
        this.context=context;
        this.my_bike_serviceList=my_bike_serviceList;
        this.myBikeServiceInterface=myBikeServiceInterface;
    }
    @NonNull
    @Override
    public Myview_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {

        View view = LayoutInflater.from(context).inflate(R.layout.my_desing_bike_service,parent,false);
        return new Myview_holder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull Bike_Service_Adapter.Myview_holder holder, final int position)
    {
                final My_Bike_Service service1= my_bike_serviceList.get(position);
                String bike_service=service1.getService();
                String charge=service1.getCharge();
                String Model=service1.getModel();
                String Maker=service1.getMaker();
                String id=service1.getId();
                String chek= String.valueOf(service1.getselected());

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        My_Bike_Service service1= my_bike_serviceList.get(position);

                        if(!service1.getSelected1())
                        {
                            service1.setSelected1(true);
                            add_service(my_bike_serviceList.get(position).getId());
                            data_1=finallist();
                            myBikeServiceInterface.onItemCheck_bike(data_1);

                        }else if(service1.getSelected1())
                        {
                            service1.setSelected1(false);
                            remove_service(my_bike_serviceList.get(position).getId());
                            data_2=finallist();
                            myBikeServiceInterface.onItemCheck_bike(data_2);
                        }
                        notifyItemChanged(position);
                    }
                });
                     holder.checkBox.setChecked(service1.getSelected1());
                     holder.my_textview.setText(Maker+Model+","+bike_service+"\n"+charge);
        holder.bind(my_bike_serviceList.get(position),myBikeServiceInterface);
    }
    //add method
    public void selectAll()
    {

        ArrayList<My_Bike_Service> templist=new ArrayList();
        templist.addAll(my_bike_serviceList);

        for(int i=0;i<templist.size();i++)
        {
            templist.get(i).setSelected1(true);
        }

        my_bike_serviceList.clear();
        my_bike_serviceList.addAll(templist);
        notifyDataSetChanged();

    }
    //add deselected
    public void deselectAll()
    {
        ArrayList<My_Bike_Service> templist=new ArrayList();
        templist.addAll(my_bike_serviceList);
        for(int i=0;i<templist.size();i++)
        {
            templist.get(i).setSelected1(false);
        }
        my_bike_serviceList.clear();
        my_bike_serviceList.addAll(templist);
        notifyDataSetChanged();

    }

    @Override
    public int getItemCount()
    {

        return my_bike_serviceList.size();
    }

    public class Myview_holder extends RecyclerView.ViewHolder
    {

        CheckBox checkBox;
        TextView my_textview;

        public Myview_holder(@NonNull View itemView)
        {
            super(itemView);
            checkBox=itemView.findViewById(R.id.my_checkbox);
            my_textview=itemView.findViewById(R.id.my_bike_details);
        }

        public void bind(final My_Bike_Service my_bike_service, final My_bike_service_interface myBikeServiceInterface)
        {

            myBikeServiceInterface.my_item_price(my_bike_service);


        }
    }
}
