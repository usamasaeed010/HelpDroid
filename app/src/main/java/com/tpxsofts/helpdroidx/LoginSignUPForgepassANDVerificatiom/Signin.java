package com.tpxsofts.helpdroidx.LoginSignUPForgepassANDVerificatiom;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tpxsofts.helpdroidx.Home;
import com.tpxsofts.helpdroidx.R;
import com.tpxsofts.helpdroidx.Services.GeofenceManuelService;
import com.tpxsofts.helpdroidx.Services.OnlineStatuschkAndMessagesReciveServices;

public class Signin extends AppCompatActivity implements View.OnClickListener {
    Button signin;
    TextView signup, show, forgotpass;
    String user, pass;
    EditText userMail, userPass;
    int chk;
    ProgressDialog progressDialog;
    Context context;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        chk = 0;
        editor = getSharedPreferences("signinchk", MODE_PRIVATE).edit();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Authenticating...");

        signin = findViewById(R.id.signin);
        signin.setOnClickListener(this);
        show = findViewById(R.id.show);
        show.setOnClickListener(this);
        signup = findViewById(R.id.TextSignup);
        signup.setOnClickListener(this);
        forgotpass = findViewById(R.id.forgotpass);
        forgotpass.setOnClickListener(this);

        userMail = findViewById(R.id.usermail);
        userPass = findViewById(R.id.userpass);

    }




    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signin:
                validationchk();
                break;


            case R.id.TextSignup:
                startActivity(new Intent(this, Signup.class));
                break;

            case R.id.show:
                if (chk == 0) {
                    userPass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    //by defaul cursor move to star so what we doing is moving it to the last of its length oh yeah
                    userPass.setSelection(userPass.getText().length());
                    show.setText("Hide");
                    chk = 1;
                } else {

                    userPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    //by defaul cursor move to star so what we doing is moving it to the last of its length oh yeah
                    userPass.setSelection(userPass.getText().length());
                    show.setText("show");
                    chk = 0;
                }
                break;


            case R.id.forgotpass:
                forget(userMail.getText().toString().trim());
                break;

        }
    }


    public void validationchk() {
        user = userMail.getText().toString().trim();
        pass = userPass.getText().toString().trim();


        //Pass check
        if (TextUtils.isEmpty(pass)) {

            userPass.setError("Password is required");
            userPass.requestFocus();
        } else {
            if (pass.length() < 8) {
                userPass.setError("Password must be more then 8 digits");
                pass = "";
            } else {
                if (pass.length() > 16) {
                    userPass.setError("Password must not be more then 16 digits");
                    pass = "";
                }
            }
        }


//Email check
        if (TextUtils.isEmpty(user)) {

            userMail.setError("Email adress is required");
            userMail.requestFocus();
        } else {
            if (!user.contains("@") || !user.contains(".com")) {


                if (!user.contains("@") || !user.contains(".COM")) {

                    userMail.setError("Invalid Email address");
                    userMail.requestFocus();
                    user = "";
                } else {
                    user = user.replace(".", ",");
                }
            } else {
                user = user.replace(".", ",");
            }
        }

        //final check to pass data to verification

        if (!TextUtils.isEmpty(user) && !TextUtils.isEmpty(pass)) {
            progressDialog.show();
            firebaseValidation(user, pass);

//optional if u dont want the user to go back to previous activity in verification
//finish();
        }

    }

    public void firebaseValidation(final String u, final String p) {

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("UsersDB");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(u)) {


                    if (dataSnapshot.child(u).child("Pass").getValue().toString().trim().equals(p)) {


                        editor.putString("chk", "login");
                        editor.putString("email",u);
                        editor.putString("name",dataSnapshot.child(u).child("Name").getValue().toString());
                        editor.putString("phone",dataSnapshot.child(u).child("PhoneNumber").getValue().toString());
                        editor.commit();


                        Toast.makeText(getApplicationContext(), "sccess", Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(getApplicationContext(), Home.class);
                        Intent intentservice=new Intent(getApplicationContext(), OnlineStatuschkAndMessagesReciveServices.class);

                        Bundle bundle= new Bundle();
                        bundle.putString("m",u);
                        intentservice.putExtras(bundle);


                        startService(intentservice);
                        startActivity(intent);

                    } else {

                        userPass.setError("incorrect password");
                        userPass.requestFocus();


                        progressDialog.dismiss();
                        user = "";
                        pass = "";

                    }
                } else {

                    userMail.setError("Email isn't Associated with any account ");
                    userMail.requestFocus();

                    progressDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        });


    }

     String phone;
    public void forget( String M) {

        if (TextUtils.isEmpty(M)) {

            userMail.setError("Email adress is required");
            userMail.requestFocus();
        } else {
            if (!M.contains("@") || !M.contains(".com")) {


                if (!M.contains("@") || !M.contains(".COM")) {

                    userMail.setError("Invalid Email address");
                    userMail.requestFocus();
                    M = "";
                } else {
                    M = M.replace(".", ",");
                }
            } else {
                M = M.replace(".", ",");
            }
        }

        //final check to pass data to verification

        if (!TextUtils.isEmpty(M)) {

            progressDialog.show();
final String mail=M;

            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("UsersDB");

            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.hasChild(mail)) {
                        phone=  dataSnapshot.child(mail).child("PhoneNumber").getValue().toString().trim();
                        Intent intent = new Intent(getApplicationContext(), Verifiction.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("Email", mail);
                        bundle.putString("Phn",phone);
                        bundle.putString("chk", "f");
                                intent.putExtras(bundle);
                        progressDialog.dismiss();
                          startActivity(intent);


                    } else {

                        userMail.setError("Email isn't Associated with any account ");
                        userMail.requestFocus();

                        progressDialog.dismiss();
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }


            });
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
       AlertDialog.Builder alertDialogBuilder= new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("HelpDroid will be terminated!\n Are you sure you want to exit?");
        alertDialogBuilder .setCancelable(true);
        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                });
        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        AlertDialog alertt = alertDialogBuilder.create();
        alertt.show();

    }
}