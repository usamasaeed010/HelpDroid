package com.tpxsofts.helpdroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Signup extends AppCompatActivity implements View.OnClickListener {
TextView buttonreg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        buttonreg=findViewById(R.id.register);
        buttonreg.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        startActivity(new Intent(this, Signin.class));
    }
}
