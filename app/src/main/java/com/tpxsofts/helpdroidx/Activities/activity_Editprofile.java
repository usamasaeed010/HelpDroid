package com.tpxsofts.helpdroidx.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.tpxsofts.helpdroidx.R;

import java.io.File;

public class activity_Editprofile extends AppCompatActivity implements View.OnClickListener{
    TextView saveBTN,uploadbtn;
ImageView m,f,dp;
TextView tvf,tvm;
EditText email,phone,name;
    Uri uri;
    File file;
  String  chk;
  ProgressDialog progressDialog;
String Gender,SecendaryPhone,ScendaryEmail,Name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);

        saveBTN=findViewById(R.id.saved);   saveBTN.setOnClickListener(this);
        uploadbtn=findViewById(R.id.uplodpic);   uploadbtn.setOnClickListener(this);
        m=findViewById(R.id.male); m.setOnClickListener(this);
        f=findViewById(R.id.female); f.setOnClickListener(this);
        dp=findViewById(R.id.dp);
        tvf=findViewById(R.id.femaletxt); tvf.setOnClickListener(this);
        tvm=findViewById(R.id.maletxt);     tvm.setOnClickListener(this);

        name=findViewById(R.id.namefrag);
        phone=findViewById(R.id.secondarPhone);
        email=findViewById(R.id.secondaryEmail);



        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Processing.....");




    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {

                case R.id.saved:
            validationchk();
                break;


            case R.id.male:
                case R.id.maletxt:
                Gender="Male";
                tvm.setTextColor(Color.parseColor("#D81B60"));
                tvf.setTextColor(Color.parseColor("#000000"));
                break;

            case R.id.female:
            case R.id.femaletxt:
                Gender="Female";
                tvf.setTextColor(Color.parseColor("#D81B60"));
                tvm.setTextColor(Color.parseColor("#000000"));
                break;

            case R.id.uplodpic:
                uploadbtn.setTextColor(Color.parseColor("#000000"));


                uploadprocess();
                break;

        }
    }
    public void validationchk(){
       Name =name.getText().toString().trim();
       SecendaryPhone=phone.getText().toString().trim();
        ScendaryEmail=email.getText().toString().trim();

        if (TextUtils.isEmpty(chk)){

           Toast.makeText(getApplicationContext(),"Image is not selected",Toast.LENGTH_SHORT).show();
            uploadbtn.setTextColor(Color.parseColor("#D81B60"));
        }
        else{
        }
//Name check
        if (TextUtils.isEmpty(Name)){

            name.setError("Name is required");
            name.requestFocus();
        }
        else{
            if (Name.length()<6){
                name.setError("Name must be more then 6 digits");

                Name="";
            }else {
                if (Name.length()>16){
                    name.setError("Name must not be more then 16 digits");
                    Name ="";
                }}

        }


        //Phone check
        if (TextUtils.isEmpty(SecendaryPhone)){

            phone.setError("Phone number is required");
            phone.requestFocus();
        }
        else{
            if (SecendaryPhone.length()<10 || SecendaryPhone.length()>11){
                phone.setError("Invalid phone number");
                phone.requestFocus();
                SecendaryPhone="";
            }
            else {
                if (SecendaryPhone.startsWith("0")){

                    SecendaryPhone="+92"+SecendaryPhone.substring(1).trim();

                }else {
                    SecendaryPhone="+92"+SecendaryPhone.trim();

                }
            }
        }


//Email check
        if (TextUtils.isEmpty(ScendaryEmail)){

            email.setError("Email adress is required");
            email.requestFocus();
        }
        else{
            if (!ScendaryEmail.contains("@") || !ScendaryEmail.contains(".com")){


                if (!ScendaryEmail.contains("@") || !ScendaryEmail.contains(".COM")){

                    email.setError("Invalid Email address");
                    email.requestFocus();
                    ScendaryEmail = "";
                }else {
                    ScendaryEmail=ScendaryEmail.replace(".",",");
                }
            }else {
                ScendaryEmail=ScendaryEmail.replace(".",",");
            }
        }

        //final check to pass data to verification

        if (!TextUtils.isEmpty(ScendaryEmail)&&!TextUtils.isEmpty(SecendaryPhone)&&
                !TextUtils.isEmpty(Name)&&!TextUtils.isEmpty(Gender)&&!TextUtils.isEmpty(chk)){
progressDialog.show();
            FirebaseExecution();

//optional if u dont want the user to go back to previous activity in verification
//finish();
        }

    }




    public void uploadprocess(){

        Intent intent=new Intent(   Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/jpg");
            startActivityForResult(Intent.createChooser(intent,"Select profile picture"),0);
    }
    /*
     * When the Activity of the app that hosts files sets a result and calls
     * finish(), this method is invoked. The returned Intent contains the
     * content URI of a selected file. The result code indicates if the
     * selection worked or not.
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // If the selection didn't work
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode==0) {

            // Get the file's content URI from the incoming Intent
             uri = data.getData();
             file=new File(getRealPathFromURI(uri));
            dp.setImageURI(uri);
            dp.setVisibility(View.VISIBLE);
            chk="selected";
        /*
             * Try to open the file for "read" access using the
             * returned URI. If the file isn't found, write to the
             * error log and return.
             */


        }
    }
    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    DatabaseReference myRef;
    String usermail = "";
    public void FirebaseExecution(){
         StorageReference StorageRef;
        StorageRef = FirebaseStorage.getInstance().getReference();
         myRef = FirebaseDatabase.getInstance().getReference("UsersPROFILE");

        try {

            SharedPreferences sharedPreferences =getSharedPreferences("signinchk", MODE_PRIVATE);
             usermail = sharedPreferences.getString("email", null);

        } catch (Exception E) { }
if (!TextUtils.isEmpty(usermail)){
    Toast.makeText(this, usermail, Toast.LENGTH_SHORT).show();
    StorageReference riversRef = StorageRef.child(usermail.trim()+".jpg");
if(!(file.length() <0)){
    riversRef.putFile(Uri.fromFile(file))
            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    myRef.child(usermail).child("SPhone").setValue(SecendaryPhone.trim());
                    myRef.child(usermail).child("SEmail").setValue(ScendaryEmail.trim());
                    myRef.child(usermail).child("Name").setValue(Name.trim());
                    myRef.child(usermail).child("Gender").setValue(Gender.trim());
                    progressDialog.dismiss();
                        finish();
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),"Failed to upload data \n Please Retry",Toast.LENGTH_LONG).show();
                }
            });

}




}else{


}

    }


}
