package com.tpxsofts.helpdroidx.Adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tpxsofts.helpdroidx.Model.HospitalDataForPopullation;
import com.tpxsofts.helpdroidx.R;

import java.util.ArrayList;

public class ActivityToShowSpecificClickedData_RC_ADAPTER extends  RecyclerView.Adapter<ActivityToShowSpecificClickedData_RC_ADAPTER.MyViewHolder>{
ArrayList<HospitalDataForPopullation> dataArray=new ArrayList<>();

    int position=0;
Context context;
    HospitalDataForPopullation data;

View v;
    public ActivityToShowSpecificClickedData_RC_ADAPTER(ArrayList<HospitalDataForPopullation> dataArray, Context context) {
        this.dataArray = dataArray;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
     LayoutInflater layoutInflater=LayoutInflater.from(viewGroup.getContext());
     v=layoutInflater.inflate(R.layout.itemfor_ctivity_to_show_specific_clicked_data,viewGroup,false);
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
