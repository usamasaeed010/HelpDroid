package com.tpxsofts.helpdroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        textView=findViewById(R.id.signin);
        textView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
       startActivity(new Intent(this,home.class));

    }
}
