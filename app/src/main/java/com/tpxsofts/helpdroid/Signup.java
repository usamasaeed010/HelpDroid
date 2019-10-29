package com.tpxsofts.helpdroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Signup extends AppCompatActivity implements View.OnClickListener {
    TextView buttonreg,buttonsignin,show;
    EditText username,phone,email,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        buttonreg = findViewById(R.id.register);
        buttonreg.setOnClickListener(this);
        buttonsignin = findViewById(R.id.signin);
        buttonsignin.setOnClickListener(this);
        show=findViewById(R.id.showsignup);
        show.setOnClickListener(this);

        username=findViewById(R.id.usernamesignup);
        phone=findViewById(R.id.phnesignup);
        email=findViewById(R.id.emailsignup);
        password=findViewById(R.id.passsignup);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signin:
                startActivity(new Intent(this, Signup.class));
                break;

            case R.id.register:
                startActivity(new Intent(this, Signin.class));
                break;


            case R.id.showsignup:


                break;
        }
    }
}