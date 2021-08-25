package com.sossolution.serviceonwayustad.MyAdapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sossolution.serviceonwayustad.Model_class.My_Model_Class;
import com.sossolution.serviceonwayustad.MyInterface.My_interface;
import com.sossolution.serviceonwayustad.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SecondAdapter extends RecyclerView.Adapter<SecondAdapter.My_viewholder>
{
    private Context context1;
    private ArrayList<My_Model_Class> datalisttwo;
    private My_interface click1;


    public SecondAdapter(Context context1, ArrayList<My_Model_Class> datalisttwo,My_interface click)
    {
        this.context1=context1;
        this.datalisttwo=datalisttwo;
        this.click1=click;

    }

    @NonNull
    @Override
    public My_viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.my_card,parent,false);
        return new My_viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull My_viewholder holder, int position)
    {
        My_Model_Class storedata=datalisttwo.get(position);
        final Context context = holder.imageView.getContext();
        //<----- Add this line
        //show image
        String image = storedata.getImage();
        Log.d("image_item",image);
        Picasso.get()
                .load("https://serviceonway.com/UploadedFiles/Logos/"+image)
                .fit()
                .into(holder.imageView);
        final String text = storedata.getText();
        holder.textView.setText(text);

        holder.bind(datalisttwo.get(position),click1);

    }

    @Override
    public int getItemCount()
    {
        return datalisttwo.size();
    }

    public class My_viewholder  extends RecyclerView.ViewHolder
    {

        TextView textView;
        ImageView imageView;
        LinearLayout linearLayout;

        public My_viewholder(@NonNull View itemView)
        {
            super(itemView);
            textView=itemView.findViewById(R.id.textviewc);
            imageView=itemView.findViewById(R.id.imagec);
        }

        public void bind(final My_Model_Class my_model_class, final My_interface click1)
        {
            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    //click1.onItemClick1(storedata1);
                    click1.onItemClick1(my_model_class);
                }
            });

        }
    }
}
