package com.tpxsofts.helpdroidx.SplashActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.tpxsofts.helpdroidx.Home;
import com.tpxsofts.helpdroidx.LoginSignUPForgepassANDVerificatiom.Signin;
import com.tpxsofts.helpdroidx.Model.NetworkConnection;
import com.tpxsofts.helpdroidx.R;

import static android.view.View.VISIBLE;

public class splash extends AppCompatActivity  implements GoogleApiClient.ConnectionCallbacks
        ,GoogleApiClient.OnConnectionFailedListener
        , LocationListener
        , ResultCallback<LocationSettingsResult>
{
    Dialog dialog;
    Context context=this;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mCurrentLocationRequest;
    private String mCurrentLocation = "";
    private SharedPreferences sharedPreferences;
    public static final int LOCATION_REQUEST_CODE = 100;
    public static final int LOCATION_PERMISSION_CODE = 101;
    public static final int CAMERq_PERMISSION_CODE = 004;
    public static final int DATA_WRIT_PERMISSION_CODE = 003;
    public static final int CALL_PERMISSION_CODE = 005;
    public static final int SMS_PERMISSION_CODE = 006;
    public static final int DATA_READ_PERMISSION_CODE =007;
    String chk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);






        dialog = new Dialog(context);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);



try {

     sharedPreferences =getSharedPreferences("signinchk", MODE_PRIVATE);
    chk=sharedPreferences.getString("chk",null);
}catch (Exception e){

}



        buildGoogleApiClient();

    }



    private void dialog(boolean flag)
    {
        if(!flag)
        {
            dialog.dismiss();
        }
        else
        {

            dialog.setContentView(R.layout.dialog);
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            ProgressBar prog = (ProgressBar) dialog.findViewById(R.id.myprogress);
            prog.setVisibility(VISIBLE);
            dialog.show();
        }

    }






    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

    }

    @Override
    protected void onStart()
    {
        super.onStart();
        if (!mGoogleApiClient.isConnecting() || !mGoogleApiClient.isConnected())
            mGoogleApiClient.connect();






    }

    @Override
    protected void onStop()
    {
        if (mGoogleApiClient.isConnecting() || mGoogleApiClient.isConnected())
            mGoogleApiClient.disconnect();
        super.onStop();
    }
    @Override
    public void onConnected(@Nullable Bundle bundle)
    {
        mCurrentLocationRequest = new LocationRequest();
        mCurrentLocationRequest.setInterval(1100);
        mCurrentLocationRequest.setFastestInterval(1100);
        mCurrentLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        Permission();


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED)
        {
            return;
        }

        if (LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient) != null)

        {
            mCurrentLocation = String.valueOf(
                    LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient).getLatitude())
                    + "," + String.valueOf(
                    LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient).getLongitude());



        }
        else
        {
            LocationServices.FusedLocationApi.requestLocationUpdates(
                    mGoogleApiClient,
                    mCurrentLocationRequest,
                    this);
        }


        SharedPreferences sharedPreferences=getSharedPreferences("Data", Context.MODE_PRIVATE);

        //SharedPreference to store current ltn
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("current_location", mCurrentLocation);
        editor.apply();




    }

    @Override
    public void onConnectionSuspended(int i)
    {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onResult(@NonNull LocationSettingsResult locationSettingsResult)
    {

        Status status = locationSettingsResult.getStatus();

        switch (status.getStatusCode()) {
            case LocationSettingsStatusCodes.SUCCESS:
                // NO need to show the dialog all permission are correct;


//
//                    new Handler().postDelayed(new Runnable() {
//
//                        /**
//                         * Showing Splash Screen With timer
//                         * and it is used for to display company logo
//                         */
//
//                        @Override
//                        public void run() {
//                            //Start HomeScreenActivity
//                            startActivity(new Intent(splash.this, home.class));
//
//                            //Stop SplashScreenActivity
//                            finish();
//                        }
//                    }, 2000);
//////////////////////////////////////////////////////////////


        if(!new NetworkConnection().checkConnection(context))
        {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run()
                {
                    dialog.dismiss();
                    new NetworkConnection().noInternetDialog(context,getLayoutInflater(),splash.this,"No Internet Connection");
                }
            },2000);

        }
        else
        {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run()
                {
                    dialog(false);
if (TextUtils.isEmpty(chk)){
                    startActivity(new Intent(getApplicationContext(), Signin.class));
                    finish();}else {
    startActivity(new Intent(getApplicationContext(), Home.class));
    finish();
                    }
                }
            },2000);
        }
                break;



            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                //  Location settings are not satisfied. Show the user a dialog
                try {
                    // Show the dialog by calling startResolutionForResult(), and check the result
                    // in onActivityResult().
                    status.startResolutionForResult(splash.this, LOCATION_REQUEST_CODE);
                }
                catch (IntentSender.SendIntentException e)
                {
                    //failed to show
                    e.printStackTrace();
                }
                break;

            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                // Location settings are unavailable so not possible to show any dialog now
                break;
        }







    }

    @Override
    public void onLocationChanged(Location location)
    {
        //get the current ltn of the user
        mCurrentLocation = String.valueOf(location.getLatitude()) + "," +
                String.valueOf(location.getLongitude());


        //SharedPreference to store current ltn

        SharedPreferences.Editor editor = getSharedPreferences("Data", Context.MODE_PRIVATE).edit();
        editor.putString("current_location", mCurrentLocation);
        editor.apply();



    }



    public void Permission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, DATA_READ_PERMISSION_CODE);
            }
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERq_PERMISSION_CODE);
            }
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, DATA_WRIT_PERMISSION_CODE);
            }
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, DATA_READ_PERMISSION_CODE);
            }
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, CAMERq_PERMISSION_CODE);
            }
            if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, CALL_PERMISSION_CODE);
            }

            if (checkSelfPermission(Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.SEND_SMS}, SMS_PERMISSION_CODE);
            }

                if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            {
                if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION))
                {
                    new AlertDialog.Builder(splash.this)
                            .setTitle("Permission")
                            .setMessage("We can\\'t get Near By Location without ltn permission, could you please grant it")
                            .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i)
                                {

                                    ActivityCompat.requestPermissions(splash.this,
                                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},LOCATION_PERMISSION_CODE);
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            }).show();

                }
                else
                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_CODE);
            }
            else
                getGPSPermission();
        }
        else
            getGPSPermission();





    }


    @Override
    protected void onActivityResult(final int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LOCATION_REQUEST_CODE) {
            if (resultCode == RESULT_OK)
            {


                ////////////////////permissin for update ltn

                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                                != PackageManager.PERMISSION_GRANTED)
                {
                    return;
                }
                LocationServices.FusedLocationApi.requestLocationUpdates(
                        mGoogleApiClient,
                        mCurrentLocationRequest,
                        this);

                //Start HomeScreenActivity
                startActivity(new Intent(splash.this, Signin.class));

                //Stop SplashScreenActivity
                finish();
            }
            else
            {
                new AlertDialog.Builder(splash.this)
                        .setTitle("Turn on Gps")
                        .setMessage("Please turn on GPS to detect current ltn")
                        .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Retry for GPS Permission
                                getGPSPermission();
                            }
                        })
                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Kill the application
                                finish();
                            }
                        }).show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        if (requestCode == LOCATION_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getGPSPermission();
            }
        }


        if (requestCode == CALL_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

            }
        }
        if (requestCode == DATA_READ_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

            }
        }
    }



    private void getGPSPermission()
    {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mCurrentLocationRequest);
        builder.setAlwaysShow(true);
        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(
                mGoogleApiClient,
                builder.build()
        );
        result.setResultCallback(splash.this);
    }








}