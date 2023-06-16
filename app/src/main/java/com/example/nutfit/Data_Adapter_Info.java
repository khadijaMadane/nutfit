package com.example.nutfit;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class Data_Adapter_Info extends RecyclerView.Adapter<Data_Adapter_Info.View_holder> {

    Data_Info [] data_list;

    public Data_Adapter_Info(Data_Info[] data_list) {
        this.data_list = data_list;
    }

    @NonNull
    @Override
    public View_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());

        View list_info_items=layoutInflater.inflate(R.layout.list_infos_items,null);

        View_holder view_info_holder=new View_holder(list_info_items);

        return view_info_holder;
    }

    @Override
    public void onBindViewHolder(@NonNull View_holder holder, int position) {
        Data_Info data_info=data_list[position];
        holder.my_img.setImageResource(data_info.getImage());
        holder.txt_title.setText(data_info.getTitle());
        holder.txt_description.setText(data_info.getDescription());

        holder.main_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myintent = new Intent(view.getContext(),Data_activity.class);
                myintent.putExtra("Title",data_info.getTitle());
                myintent.putExtra("Des",data_info.getDescription());
                myintent.putExtra("Image",data_info.getImage());
                view.getContext().startActivity(myintent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return data_list.length;
    }

    public class View_holder extends RecyclerView.ViewHolder {
        ImageView my_img;
        TextView txt_title,txt_description;
        CardView main_card;
        public View_holder(@NonNull View itemView){
            super(itemView);
            txt_title=itemView.findViewById(R.id.txt_title);
            txt_description=itemView.findViewById(R.id.id_desc);
            my_img=itemView.findViewById(R.id.id_img);
            main_card=itemView.findViewById(R.id.main_card);
        }
    }
}
