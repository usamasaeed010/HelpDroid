package com.tpxsofts.helpdroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Signin extends AppCompatActivity implements View.OnClickListener {
TextView signingo,signin,signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        signingo=findViewById(R.id.signingo);
        signingo.setOnClickListener(this);

        signin=findViewById(R.id.signin);
        signin.setOnClickListener(this);

        signup=findViewById(R.id.signup);
        signup.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signingo: startActivity(new Intent(this, home.class));          break;
            case R.id.signup:   startActivity(new Intent(this,  Signup.class));          break;


        }
    }
}
