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

public class MyadapterHospital extends  RecyclerView.Adapter<MyadapterHospital.MyViewHolder>{
ArrayList<HospitalDataForPopullation> dataArray=new ArrayList<>();

    int position=0;
Context context;
    HospitalDataForPopullation data;

View v;
    public MyadapterHospital(ArrayList<HospitalDataForPopullation> dataArray, Context context) {
        this.dataArray = dataArray;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
     LayoutInflater layoutInflater=LayoutInflater.from(viewGroup.getContext());
     v=layoutInflater.inflate(R.layout.itemforhospital,viewGroup,false);
        MyViewHolder myViewHolder=new MyViewHolder(v);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
data=dataArray.get(i);
myViewHolder.Hnamex.setText(data.Name);
        myViewHolder.Hadressx.setText(data.Adress);



    }

    @Override
    public int getItemCount() {
        return dataArray.size();
    }




    public class MyViewHolder extends RecyclerView.ViewHolder {
TextView Hnamex, Hadressx;
ImageView call;
        public MyViewHolder(View v) {
            super(v);
            Hnamex =v.findViewById(R.id.HName);
                    Hadressx =v.findViewById(R.id.Hadress);
                    call=v.findViewById(R.id.Hcall);

      //      position=getAdapterPosition();
call.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Toast.makeText(context,"calling "+dataArray.get(getAdapterPosition()).number,Toast.LENGTH_SHORT).show();

    }
});
        }

    }



}
