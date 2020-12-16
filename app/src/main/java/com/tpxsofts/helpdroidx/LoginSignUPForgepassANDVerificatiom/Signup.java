package com.tpxsofts.helpdroidx.LoginSignUPForgepassANDVerificatiom;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tpxsofts.helpdroidx.R;

public class Signup extends AppCompatActivity implements View.OnClickListener {
    TextView buttonSignup,buttonsignin,show;
    EditText username,phone,email,password;
    String   Suser,Sphone,Semail,Spass;
    int chk;
    ProgressDialog progressDialog;
    Bundle mydatabundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        chk=0;
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Validating...");


        buttonsignin = findViewById(R.id.TextSignin);
        buttonsignin.setOnClickListener(this);
        buttonSignup = findViewById(R.id.signup);
        buttonSignup.setOnClickListener(this);
        show=findViewById(R.id.showsignuppass);
        show.setOnClickListener(this);

        username=findViewById(R.id.usernamesignup);
        phone=findViewById(R.id.phnesignup);
        email=findViewById(R.id.emailsignup);
        password=findViewById(R.id.passsignup);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.TextSignin:
                startActivity(new Intent(this, Signin.class));
                break;



            case R.id.signup:

                validationchk();

                break;

            case R.id.showsignuppass:
                if (chk==0){
                password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                //by defaul cursor move to star so what we doing is moving it to the last of its length oh yeah
                password.setSelection(password.getText().length());
                show.setText("Hide");
                chk=1;}else {

                    password.setInputType(InputType.TYPE_CLASS_TEXT |InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    //by defaul cursor move to star so what we doing is moving it to the last of its length oh yeah
                    password.setSelection(password.getText().length());
                    show.setText("show");
                    chk=0;
                }
                break;
        }
    }




    public void validationchk(){
         mydatabundle=new Bundle();
        Suser=username.getText().toString().trim();
        Sphone=phone.getText().toString().trim();
        Semail=email.getText().toString().trim();
        Spass=password.getText().toString().trim();


//Name check
        if (TextUtils.isEmpty(Suser)){

            username.setError("Name is required");
            username.requestFocus();
        }
        else{
            if (Suser.length()<6){
                username.setError("Name must be more then 6 digits");

                Suser="";
            }else {
                if (Suser.length()>16){
                    username.setError("Name must not be more then 16 digits");
                    Suser="";
                }else {
                    mydatabundle.putString("Name",Suser);
                }
            }

        }

 //Pass check
        if (TextUtils.isEmpty(Spass)){

            password.setError("Password is required");
            password.requestFocus();
        }
        else{
            if (Spass.length()<8){
                password.setError("Password must be more then 8 digits");
                Spass="";
            }else {
                if (Spass.length()>16){
                    password.setError("Password must not be more then 16 digits");
                    Spass="";
                }else {
                    mydatabundle.putString("Pass",Spass);
                }
            }
        }

 //Phone check
        if (TextUtils.isEmpty(Sphone)){

            phone.setError("Phone number is required");
            phone.requestFocus();
        }
        else{
            if (Sphone.length()<10 || Sphone.length()>11){
                phone.setError("Invalid phone number");
                phone.requestFocus();
                Sphone="";
            }
            else {
                if (Sphone.startsWith("0")){

                   Sphone="+92"+Sphone.substring(1).trim();
                   mydatabundle.putString("Phone",Sphone);

                }else {
                    Sphone="+92"+Sphone.trim();

                    mydatabundle.putString("Phone",Sphone);

                }
            }
        }


//Email check
        if (TextUtils.isEmpty(Semail)){

            email.setError("Email adress is required");
            email.requestFocus();
        }
        else{
            if (!Semail.contains("@") || !Semail.contains(".com")){


                if (!Semail.contains("@") || !Semail.contains(".COM")){

                    email.setError("Invalid Email address");
                    email.requestFocus();
                    Semail = "";
                }else {
                    Semail=Semail.replace(".",",");
                    mydatabundle.putString("Email",Semail);
                }
                }else {
                Semail=Semail.replace(".",",");
                mydatabundle.putString("Email",Semail);
            }
        }

 //final check to pass data to verification

        if (!TextUtils.isEmpty(Semail)&&!TextUtils.isEmpty(Spass)&&!TextUtils.isEmpty(Sphone)&&!TextUtils.isEmpty(Suser)){
            progressDialog.show();
            FirebaseValidation(Semail,Sphone);

//optional if u dont want the user to go back to previous activity in verification
//finish();
        }

    }



public void FirebaseValidation(final String m, final String p){

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("UsersDB");
    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
           if (dataSnapshot.hasChild(m)){
               email.setError("Email already associated with another account");
               email.requestFocus();
               progressDialog.dismiss();



           }else{ if (dataSnapshot.child(m).hasChild(p)){
               phone.setError("Phone already associated with another account");
               phone.requestFocus();

               progressDialog.dismiss();


           }else {
               progressDialog.dismiss();
               mydatabundle.putString("chk","s");
               Intent intent=new Intent(getApplicationContext(),Verifiction.class);
               intent.putExtras(mydatabundle);

               startActivity(intent);
               mydatabundle.clear();

               Semail="";
               Suser="";
               Sphone="";
               Spass="";

           }


           }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
progressDialog.dismiss();
        }
    });






}


}