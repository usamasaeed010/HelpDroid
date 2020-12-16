package com.tpxsofts.helpdroidx.Adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.tpxsofts.helpdroidx.Model.Nearbyuser_status_FORfragmen_chat_classs;
import com.tpxsofts.helpdroidx.R;

import java.util.ArrayList;

public class MyAdapterNearbyUserStatus extends  RecyclerView.Adapter<MyAdapterNearbyUserStatus.MyViewHolder> implements View.OnClickListener {
ArrayList<Nearbyuser_status_FORfragmen_chat_classs> dataArray=new ArrayList<>();

    int position=0;
Context context;
    Nearbyuser_status_FORfragmen_chat_classs data;
View v;
    public MyAdapterNearbyUserStatus(ArrayList<Nearbyuser_status_FORfragmen_chat_classs> dataArray, Context context) {
        this.dataArray = dataArray;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
     LayoutInflater layoutInflater=LayoutInflater.from(viewGroup.getContext());
     v=layoutInflater.inflate(R.layout.itemfor_grid_msg,viewGroup,false);
        MyViewHolder myViewHolder=new MyViewHolder(v);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
data=dataArray.get(i);
        myViewHolder.img.setImageBitmap(data.Pic);



    }

    @Override
    public int getItemCount() {
        return dataArray.size();
    }




    public class MyViewHolder extends RecyclerView.ViewHolder {
ImageView img;
        public MyViewHolder(View v) {
            super(v);
            img=v.findViewById(R.id.imggrid);
           // img.setOnClickListener((View.OnClickListener) context);


            //      position=getAdapterPosition();

        }
    }
    @Override
    public void onClick(View v) {

    }


}
