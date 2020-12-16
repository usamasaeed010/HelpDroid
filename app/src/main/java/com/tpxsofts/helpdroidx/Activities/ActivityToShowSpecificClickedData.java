package com.tpxsofts.helpdroidx.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tpxsofts.helpdroidx.Adapter.PlaceListAdapter;
import com.tpxsofts.helpdroidx.Map_Activities.MapsActivity;
import com.tpxsofts.helpdroidx.Model.Place;
import com.tpxsofts.helpdroidx.R;

import java.util.ArrayList;

public class ActivityToShowSpecificClickedData extends AppCompatActivity {
    RecyclerView recyclerView;
    TextView textView,bar;
    ImageView call,direction;
    String mLocationName,mLocationTag,currentLocation,mLocationQueryStringUrl;
    ArrayList<Place> array=new ArrayList<>();
    PlaceListAdapter placeListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_show_specific_clicked_data);
        textView=(TextView)findViewById(R.id.empty);
        recyclerView= findViewById(R.id.rchospital);
        bar=findViewById(R.id.bar);
        call =findViewById(R.id.img);
        ///////////get name or tag
        SharedPreferences prefs = getSharedPreferences("zaeem", MODE_PRIVATE);
        String Name = prefs.getString("Name", "");
        String Tag = prefs.getString("Tag", "");

        bar.setText(Name);



call.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

       startActivity(new Intent(ActivityToShowSpecificClickedData.this, MapsActivity.class));
        finish();
    }
});


        // dummy daata
        array.add(new Place("+923024436359",31.4845,74.2975));
        array.add(new Place("+923024436359",31.4845,74.2975));
        array.add(new Place("+923024436359",31.4845,74.2975));
         array.add(new Place("+923024436359",31.4845,74.2975));


        //////////////get parcelable data

     //   array=getIntent().getParcelableArrayListExtra("fetch");

        Toast.makeText(getApplicationContext(),array.get(0).toString()+String.valueOf(array.size()),Toast.LENGTH_LONG).show();
        if (array.size()==0)
        {


            textView.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);

        }
        else
        {

            textView.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);

            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setHasFixedSize(true);
            placeListAdapter=new PlaceListAdapter(array,getApplicationContext());
            recyclerView.setAdapter(placeListAdapter);

        }









    }






}
