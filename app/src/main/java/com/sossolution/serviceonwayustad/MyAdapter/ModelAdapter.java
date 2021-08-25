package com.sossolution.serviceonwayustad.MyAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sossolution.serviceonwayustad.Model_class.Model_item;
import com.sossolution.serviceonwayustad.MyInterface.Mymodel_interface;
import com.sossolution.serviceonwayustad.R;

import java.util.ArrayList;

public class ModelAdapter extends RecyclerView.Adapter<ModelAdapter.My_viewholder>
{

    private Context context2;
    private ArrayList<Model_item> datalistmake;
    private Mymodel_interface mymodel_interface;


    public ModelAdapter(Context context2, ArrayList<Model_item> datalistmake, Mymodel_interface mymodel_interface)
    {
        this.context2 = context2;
        this.datalistmake = datalistmake;
        this.mymodel_interface = mymodel_interface;
    }



    @NonNull
    @Override
    public My_viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.model,parent,false);
        return new My_viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull My_viewholder holder, int position)
    {
        Model_item newmodel= datalistmake.get(position);
        String text=newmodel.getModel();
        holder.textView.setText(text);
        holder.bind(datalistmake.get(position),mymodel_interface);
    }

    @Override
    public int getItemCount()
    {
        return datalistmake.size();
    }

    public class My_viewholder extends RecyclerView.ViewHolder{

        TextView textView;

        public My_viewholder(@NonNull View itemView)
        {
            super(itemView);

            textView=itemView.findViewById(R.id.textviewmodel);
        }
        public void bind(final Model_item my_model_class, final Mymodel_interface customItem2)
        {

            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                     customItem2.onItemClick1(my_model_class);

                }
            });
        }
    }
}
