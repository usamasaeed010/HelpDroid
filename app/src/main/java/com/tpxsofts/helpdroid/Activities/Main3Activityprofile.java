package com.tpxsofts.helpdroid.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.tpxsofts.helpdroid.R;

public class Main3Activityprofile extends AppCompatActivity implements View.OnClickListener {
TextView saveBTN,editBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        saveBTN=findViewById(R.id.saved);
        saveBTN.setOnClickListener(this);
        editBtn=findViewById(R.id.editprf);
        editBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {

                case R.id.saved:
                startActivity(new Intent(this, home.class));
                break;

                case R.id.editprf:
                startActivity(new Intent(this, MainActivityeditprofile.class));
                break;



        }
    }
}
