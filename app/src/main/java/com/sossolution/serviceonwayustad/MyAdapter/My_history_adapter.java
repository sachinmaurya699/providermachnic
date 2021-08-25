package com.sossolution.serviceonwayustad.MyAdapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sossolution.serviceonwayustad.Model_class.History_item;
import com.sossolution.serviceonwayustad.MyInterface.History_interface;
import com.sossolution.serviceonwayustad.My_Activity.History_add_service_Activity;
import com.sossolution.serviceonwayustad.R;

import java.util.List;

public class My_history_adapter extends RecyclerView.Adapter< My_history_adapter.My_view_holder>
{

    private Context context;
    private List<History_item> history_itemList;
    private History_interface history_interface;


    public My_history_adapter(Context context,List<History_item> history_itemList,History_interface history_interface)
    {
         this.context=context;
         this.history_itemList=history_itemList;
         this.history_interface=history_interface;

    }

    @NonNull
    @Override
    public My_view_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(context).inflate(R.layout.history_desing,parent,false);
        return new My_view_holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull My_history_adapter.My_view_holder holder, int position)
    {
          History_item history_item = history_itemList.get(position);
          String date=history_item.getDate();
          String id=history_item.getId();
          String price=history_item.getPrice();
          String Service=history_item.getService();
          String my_service[]=Service.split("=");
          String my_fix_service=my_service[0];


          holder.bind(history_itemList.get(position),history_interface);

          holder.price.setText("Amount"+"-"+"\u20B9"+" "+price);
          holder.service.setText("Service"+"-"+my_fix_service);
          holder.date.setText("Date"+"-"+date);
          holder.Id.setText("ID"+"-"+id);
          holder.button_historybutton.setText("Waiting");
    }

    @Override
    public int getItemCount()
    {
        return history_itemList.size();
    }

    public class My_view_holder extends RecyclerView.ViewHolder
    {
        TextView date;
        TextView Id;
        TextView service;
        TextView address;
        TextView price;
        Button button_historybutton;
        Button btn_add;


        public My_view_holder(@NonNull View itemView)
        {
            super(itemView);

            date=itemView.findViewById(R.id.text_historydate);
            Id=itemView.findViewById(R.id.text_historyid);
            service=itemView.findViewById(R.id.text_historyInclude);
            price=itemView.findViewById(R.id.text_historyammount);
            button_historybutton=itemView.findViewById(R.id.button_historybutton);
            btn_add= itemView.findViewById(R.id.button_add);
            btn_add.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent intent = new Intent(context, History_add_service_Activity.class);
                    context.startActivity(intent);
                }
            });


        }

        public void bind(final History_item history_item, final History_interface history_interface)
        {

            button_historybutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    history_interface.my_history_item(history_item);
                }
            });


        }
    }
}
