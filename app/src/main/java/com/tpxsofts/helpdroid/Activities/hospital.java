package com.tpxsofts.helpdroid.Activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tpxsofts.helpdroid.Adapter.MyadapterHospital;
import com.tpxsofts.helpdroid.Model.HospitalDataForPopullation;
import com.tpxsofts.helpdroid.R;

import java.util.ArrayList;

public class hospital extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<HospitalDataForPopullation> dataarray= new ArrayList<>();
    MyadapterHospital myadapterHospital;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital);



        recyclerView= findViewById(R.id.rchospital);
        dataarray.add(new HospitalDataForPopullation("CMH","Cant, Lahore","11223"));
        dataarray.add(new HospitalDataForPopullation("Foji foundation","Cant, Lahore","11223"));
        dataarray.add(new HospitalDataForPopullation("services","Cant, Lahore","11223"));
        myadapterHospital=new MyadapterHospital(dataarray,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        recyclerView.setAdapter(myadapterHospital);

    }
}
