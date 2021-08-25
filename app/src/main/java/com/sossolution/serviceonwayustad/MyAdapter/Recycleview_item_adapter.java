package com.sossolution.serviceonwayustad.MyAdapter;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.sossolution.serviceonwayustad.Model_class.Recyclerview_include_data;
import com.sossolution.serviceonwayustad.R;

import java.util.List;

public class Recycleview_item_adapter extends RecyclerView.Adapter<Recycleview_item_adapter.My_viewholder>
{
    private Context context;
    private List<Recyclerview_include_data> recyclerview_include_data_item_list;



    public Recycleview_item_adapter(Context context,List<Recyclerview_include_data> recyclerview_include_data_item)
    {
        this.context=context;
        this.recyclerview_include_data_item_list=recyclerview_include_data_item;

    }

    @NonNull
    @Override
    public My_viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(context).inflate(R.layout.recyclerview_service_price,parent,false);

        return new My_viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Recycleview_item_adapter.My_viewholder holder, int position)
    {
       Recyclerview_include_data recyclerview_include_data=recyclerview_include_data_item_list.get(position);
       String service=recyclerview_include_data.getService();
        Log.d("get_service",service);
       String[] service_item=service.split("=");
       String service1=service_item[0];
       String price=recyclerview_include_data.getPrice();
       holder.textView_service.setText(service1);
       holder.textView_price.setText(price);
    }

    @Override
    public int getItemCount()
    {

        return recyclerview_include_data_item_list.size();
    }

    public class My_viewholder  extends RecyclerView.ViewHolder
    {
        TextView textView_service;
        TextView textView_price;

        public My_viewholder(@NonNull View itemView)
        {
            super(itemView);
            textView_price=itemView.findViewById(R.id.text_price);
            textView_service=itemView.findViewById(R.id.service_name);

        }
    }
}
