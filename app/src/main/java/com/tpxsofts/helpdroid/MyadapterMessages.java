package com.tpxsofts.helpdroid;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MyadapterMessages extends  RecyclerView.Adapter<MyadapterMessages.MyViewHolder>{
ArrayList<MessagesDataForPopullation> dataArray=new ArrayList<>();

    int position=0;
Context context;
MessagesDataForPopullation data;
View v;
    public MyadapterMessages(ArrayList<MessagesDataForPopullation> dataArray, Context context) {
        this.dataArray = dataArray;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
     LayoutInflater layoutInflater=LayoutInflater.from(viewGroup.getContext());
     v=layoutInflater.inflate(R.layout.itemfor_rc_msg,viewGroup,false);
        MyViewHolder myViewHolder=new MyViewHolder(v);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
data=dataArray.get(i);
myViewHolder.Mnamex.setText(data.Name);
        myViewHolder.Mnamex.setText(data.Message);



    }

    @Override
    public int getItemCount() {
        return dataArray.size();
    }




    public class MyViewHolder extends RecyclerView.ViewHolder {
TextView Mnamex, Madressx;
ImageView img;
        public MyViewHolder(View v) {
            super(v);
            Mnamex = v.findViewById(R.id.Mname);
            Madressx = v.findViewById(R.id.Nmessage);

            //      position=getAdapterPosition();

        }}



}
