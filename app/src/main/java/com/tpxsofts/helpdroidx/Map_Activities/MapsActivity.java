package com.tpxsofts.helpdroidx.Map_Activities;

import android.Manifest;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.maps.android.PolyUtil;
import com.tpxsofts.helpdroidx.Activities.ActivityToShowSpecificClickedData;
import com.tpxsofts.helpdroidx.Model.GoogleApiUrl;
import com.tpxsofts.helpdroidx.Model.Place;
import com.tpxsofts.helpdroidx.NotificationX.notificatiouniversal;
import com.tpxsofts.helpdroidx.R;

import android.content.Intent;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Marker;
import com.tpxsofts.helpdroidx.Services.GeofenceManuelService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.system.Os.remove;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener, View.OnClickListener
{

    Toolbar toolbar;
    ImageView imageView,img;
    LinearLayout distimer;
    TextView textView,bar,timercal,distancecal;
    String mLocationName,mLocationTag,currentLocation,mLocationQueryStringUrl;
    private GoogleMap mGoogleMap;
    private boolean mMapReady = false;
    ArrayList<Place> arrayList;
    MapFragment mMapFragment;
    RelativeLayout Mapbotttom;
    LinearLayout Maplayout;
    GeofencingClient geofencingClient;
private String chk,destinationlatlon;
Bundle bundle=new Bundle();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(
                mMessageReceiver, new IntentFilter("GPSLocationUpdates"));


        geofencingClient = LocationServices.getGeofencingClient(this);
        imageView=(ImageView)findViewById(R.id.place_list_icon_view);
        textView=(TextView)findViewById(R.id.place_list_placeholder_text_view);
        timercal=(TextView)findViewById(R.id.timertxt);
        distancecal=(TextView)findViewById(R.id.distext);
        bar=findViewById(R.id.bar);
        img=findViewById(R.id.img); img.setOnClickListener(this);
        arrayList=new ArrayList<>();
        distimer=(LinearLayout)findViewById(R.id.distimer);
        Mapbotttom =(RelativeLayout) findViewById(R.id.btmnavigation);
        Maplayout =(LinearLayout) findViewById(R.id.mapx);

        mMapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mMapFragment.getMapAsync(this);


/////get last lattitude or longitude
        SharedPreferences sharedPreferences=getSharedPreferences("Data",MODE_PRIVATE);
        currentLocation=sharedPreferences.getString("current_location","");
try {
    chk=getIntent().getStringExtra("chk");
//    if (!TextUtils.isEmpty(chk)){
//        if (chk.equals("Fancing")){
//            GeofancingFragmentProcess();
//        }else {
//            destinationlatlon = getIntent().getStringExtra("location");
//                 directionworkingiNITIALIZTION();
//        }
//    }else{
//
//        placesworking();
//    }
}catch (Exception e){

}









    }


    public void placesworking(){
        Mapbotttom.setVisibility(View.VISIBLE);

    //////////map url data

    mLocationQueryStringUrl= GoogleApiUrl.BASE_URL+GoogleApiUrl.NEARBY_SEARCH_TAG+ "/"+
            GoogleApiUrl.JSON_FORMAT_TAG+"?"+GoogleApiUrl.LOCATION_TAG+"="+currentLocation +"&"+
            GoogleApiUrl.RADIUS_TAG+"="+GoogleApiUrl.RADIUS_VALUE+"&"+
            GoogleApiUrl.PLACE_TYPE_TAG+"="+mLocationTag+"&"+
            GoogleApiUrl.API_KEY_TAG+"="+GoogleApiUrl.API_KEY2;


/////////////get name of ActivityToShowSpecificClickedData capital or small from splash screen
    mLocationName=getIntent().getStringExtra("Name");
    mLocationTag=getIntent().getStringExtra("Tag");

    Toast.makeText(this, ""+mLocationName+mLocationTag, Toast.LENGTH_SHORT).show();

    //////////toolbar///////////////////

    SharedPreferences.Editor editor = getSharedPreferences("zaeem", MODE_PRIVATE).edit();
    editor.putString("Name", mLocationName);
    editor.putString("Tag", mLocationTag);
    editor.apply();
    String title="Near By"+" " +mLocationName+ " " +"List";

////////////////set text name

    textView.setText("Near BY"+ " "  +mLocationName+ " " + "Found");

    bar.setText(title);



    imageView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v)
        {


            Intent intent=new Intent(getApplicationContext(), ActivityToShowSpecificClickedData.class);
            intent.putExtra("exect_tag",mLocationTag);
            intent.putExtra("exect_Name",mLocationName);
            intent.putParcelableArrayListExtra("fetch",arrayList);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_up,R.anim.slide_out_in);

            Toast.makeText(getApplicationContext(), chk, Toast.LENGTH_LONG).show();

        }
    });






}




    ///////////go back Activity
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onMarkerClick(Marker marker)
    {

        Toast.makeText(this, "click", Toast.LENGTH_SHORT).show();
        return false;
    }


    @Override
    public void onMapReady(GoogleMap googleMap)

    {

        mGoogleMap=googleMap;
        mMapReady=true;
//        getLocation(mLocationQueryStringUrl);
        if (!TextUtils.isEmpty(chk)){
            if (chk.equals("Fencing")){
                GeofancingFragmentProcess();
            }else {
                destinationlatlon = getIntent().getStringExtra("location");
                directionworkingiNITIALIZTION();
            }
        }else{
            placesworking();
            getLocationOnGoogleMap(mLocationQueryStringUrl);
    }}





    private void getLocationOnGoogleMap(String locationQueryStringUrl) {
        //Tag to cancel request
        String jsonArrayTag = "jsonArrayTag";
        JsonObjectRequest placeJsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                locationQueryStringUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Toast.makeText(getApplicationContext(), "response objec:->  "+response.toString(), Toast.LENGTH_SHORT).show();

                        try {
                            JSONArray rootJsonArray = response.getJSONArray("results");
                            if (rootJsonArray.length() == 0)
                                ((TextView) findViewById(R.id.place_list_placeholder_text_view))
                                        .setText(getResources().getString(R.string.no_near_by_tag)
                                                + " " + mLocationName + " " + getString(R.string.found));
                            else
                                {
                                for (int i = 0; i < rootJsonArray.length(); i++) {
                                    JSONObject singlePlaceJsonObject = (JSONObject) rootJsonArray.get(i);

                                    String currentPlaceId = singlePlaceJsonObject.getString("place_id");
                                    Double currentPlaceLatitude = singlePlaceJsonObject
                                            .getJSONObject("geometry").getJSONObject("ltn")
                                            .getDouble("lat");
                                    Double currentPlaceLongitude = singlePlaceJsonObject
                                            .getJSONObject("geometry").getJSONObject("ltn")
                                            .getDouble("lng");
                                    String currentPlaceName = singlePlaceJsonObject.getString("name");

                                    Toast.makeText(getApplicationContext(),currentPlaceName,Toast.LENGTH_LONG).show();

                                    String currentPlaceOpeningHourStatus = singlePlaceJsonObject
                                            .has("opening_hours") ? singlePlaceJsonObject
                                            .getJSONObject("opening_hours")
                                            .getString("open_now") : "Status Not Available";


                                    Double currentPlaceRating = singlePlaceJsonObject.has("rating") ?
                                            singlePlaceJsonObject.getDouble("rating") : 0;
                                    String currentPlaceAddress = singlePlaceJsonObject.has("vicinity") ?
                                            singlePlaceJsonObject.getString("vicinity") :
                                            "Address Not Available";
                                    Place singlePlaceDetail = new Place(
                                            currentPlaceId,
                                            currentPlaceLatitude,
                                            currentPlaceLongitude,
                                            currentPlaceName,
                                            currentPlaceOpeningHourStatus,
                                            currentPlaceRating,
                                            currentPlaceAddress);
                                    arrayList.add(singlePlaceDetail);

                                }
                                if (mMapReady)
                                {
                                    //Set the camera position
                                    SharedPreferences sharedPreferences=getSharedPreferences("Data",MODE_PRIVATE);
                                    String loc=sharedPreferences.getString("current_location","");
//


                                    String currentPlace[] = loc.split(",");
                                    LatLng currentLocation = new LatLng(Double.valueOf(currentPlace[0])
                                            , Double.valueOf(currentPlace[1]));
                                    CameraPosition cameraPosition = CameraPosition.builder()
                                            .target(currentLocation)
                                            .zoom(15)
                                            .bearing(0)
                                            .tilt(0)
                                            .build();
                                    mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition),
                                            1500, null);
                                    /**
                                     *  Set the marker on Google Map
                                     */
                                    for (int i = 0; i < arrayList.size(); i++) {
                                        Double currentLatitude = arrayList.get(i).getmPlaceLatitude();
                                        Double currentLongitude = arrayList.get(i).getmPlaceLongitude();
                                        LatLng currentLatLng = new LatLng(currentLatitude, currentLongitude);
                                        mGoogleMap.addMarker(new MarkerOptions()
                                                .position(currentLatLng)
                                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location_on_map)));
                                    }

                                    mGoogleMap.addMarker(new MarkerOptions()
                                            .position(currentLocation)
                                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_current_location)));
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
        //Adding request to request queue
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(placeJsonObjectRequest);


    }

    ///////////////////////////////////////////////////////////////////////////2ndactivitylogic of comming from rc map click
    public  void directionworkingiNITIALIZTION(){

        bar.setText("Direction");
        distimer.setVisibility(View.VISIBLE);
directionworking();

    }

    public  void directionworking()   {

        //Set the camera position
        SharedPreferences sharedPreferences=getSharedPreferences("Data",MODE_PRIVATE);
        String loc=sharedPreferences.getString("current_location","");
        StringBuilder stringBuilder=new StringBuilder();
        String url = "https://maps.googleapis.com/maps/api/directions/json?";

        String sensor = "sensor=false";

        String parameters = "origin=" + loc + "&destination=" + destinationlatlon + "&" + sensor;


stringBuilder.append(url);
stringBuilder.append(parameters);
stringBuilder.append(GoogleApiUrl.API_KEY_TAG+"="+GoogleApiUrl.API_KEY2);
String appendedURL=stringBuilder.toString().trim();


        ////////

        JsonObjectRequest placeJsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                appendedURL , null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //////////////////////////////////////////////////////////////////////////////////////////////////////////////

                Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_LONG).show();
                try {
                    JSONArray jsonArray= response.getJSONArray("routes").getJSONObject(0)
                            .getJSONArray("legs").getJSONObject(0)
                            .getJSONArray("steps");


                    JSONObject jsonObjecttogetpolyline;

                    int count = jsonArray.length();
                    String[] polyline_srry=new String[count];
                    for (int i=0; i<count; i++){

                        jsonObjecttogetpolyline=jsonArray.getJSONObject(i);
                        polyline_srry[i]=jsonObjecttogetpolyline.getJSONObject("polyline").getString("points");


                    }

                    int count2=polyline_srry.length;
                    for (int i=0; i<count2; i++) {

                        PolylineOptions plyOptions=new PolylineOptions();
                        plyOptions.color(Color.CYAN);
                        plyOptions.width(10);
                        plyOptions.addAll(PolyUtil.decode(polyline_srry[i]));
                        mGoogleMap.addPolyline(plyOptions);


                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
        //Adding request to request queue
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(placeJsonObjectRequest);


//////////

        if (mMapReady)
        {
//

            Toast.makeText(getApplicationContext(),loc,Toast.LENGTH_LONG).show();

            String currentPlace[] = loc.split(",");
            LatLng currentLocation = new LatLng(Double.valueOf(currentPlace[0])
                    , Double.valueOf(currentPlace[1]));
            CameraPosition cameraPosition = CameraPosition.builder()
                    .target(currentLocation)
                    .zoom(15)
                    .bearing(0)
                    .tilt(0)
                    .build();
            mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition),
                    1500, null);
            mGoogleMap.addMarker(new MarkerOptions()
                    .position(currentLocation)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_current_location)));




Toast.makeText(getApplicationContext(),destinationlatlon,Toast.LENGTH_LONG).show();
            String destination[] = destinationlatlon.split(",");
            LatLng destinationlocation = new LatLng(Double.valueOf(destination[0])
                    , Double.valueOf(destination[1]));
            CameraPosition cameraPositiondesti = CameraPosition.builder()
                    .target(destinationlocation)
                    .zoom(15)
                    .bearing(0)
                    .tilt(0)
                    .build();
            mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPositiondesti),
                    1500, null);
            /**
             *  Set the marker on Google Map
             */

            mGoogleMap.addMarker(new MarkerOptions()
                    .position(destinationlocation)
                    .title("Destination"));
        }
    }

    private void GeofancingFragmentProcess() {


        bar.setText("Fancing");

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) Maplayout.getLayoutParams();
        params.weight =4.6f;
        Maplayout.setLayoutParams(params);

        SharedPreferences sharedPreferences=getSharedPreferences("Data",MODE_PRIVATE);
        String loc=sharedPreferences.getString("current_location","");

        String currentPlace[] = loc.split(",");
        LatLng currentLocation = new LatLng(Double.valueOf(currentPlace[0])
                , Double.valueOf(currentPlace[1]));

        addcircle(currentLocation);
         bundle.putString("L",loc);
        View v1 = getWindow().getDecorView().getRootView();




        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {


            if (chkbglocationpermission()) {
//next
                startService();
            } else {
                ProgressDialog progressDialog = new ProgressDialog(getApplicationContext());
                progressDialog.setMessage("Access to background location Required in order to work at full potential");
                progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Ok!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        chkbglocationpermission();
                    }
                });
                progressDialog.show();

            }

        }else {
            //next
startService();

        }



    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String locup = intent.getStringExtra("LOC");
            String m= intent.getStringExtra("m");
            String km= intent.getStringExtra("km");
                    Mycurrentlocation(locup,m,km);

            Toast.makeText(getApplicationContext(), locup+" "+m+" "+km, Toast.LENGTH_SHORT).show();
            // Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
    };

public void addcircle(LatLng pos){

    CameraPosition cameraPosition = CameraPosition.builder()
            .target(pos)
            .zoom(15)
            .bearing(0)
            .tilt(0)
            .build();
    mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition),
            2000, null);
    mGoogleMap.addMarker(new MarkerOptions()
            .position(pos)
            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_current_location)));
//manuel circle
          Circle circle = mGoogleMap.addCircle(new CircleOptions()
                .center(pos)
                .radius(1000)
                  .strokeWidth(1.0f)
                  .strokeColor(Color.parseColor("#D81B60"))
                    .fillColor(Color.parseColor("#4BF18EB2")));

        return;
}

    Marker  mar;

    public void Mycurrentlocation(String locupdate ,String m,String km) {
try {
    mar.remove();

}catch (Exception e){


}

        String x = locupdate.replace("(", "");
        x = x.replace(")", "");
        x = x.replace("lat/lng:", "");
//
//        Toast.makeText(context, "meter"+m+"  km "+m+" \n"+newloc+"\n"+x.trim(), Toast.LENGTH_LONG).show();

        String currentPlace[] = x.trim().split(",");
        LatLng currentLocationupdate = new LatLng(Double.valueOf(currentPlace[0]), Double.valueOf(currentPlace[1]));

        CameraPosition cameraPosition = CameraPosition.builder()
                .target(currentLocationupdate)
                .zoom(15)
                .bearing(0)
                .tilt(0)
                .build();
        mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition),
                2000, null);

          mar= mGoogleMap.addMarker(new MarkerOptions()
                .position(currentLocationupdate)
                .title("Remaining Distance : " + (1000 - Double.valueOf(m)))
        );
        mar.showInfoWindow();
    }





        public void startService() {
            notificatiouniversal notificatioun=new notificatiouniversal(getApplicationContext(),"HelpDroid is fencing");
            notificatioun.sendNotification();
        Intent intent=new Intent(this,GeofenceManuelService.class);
        intent.putExtras(bundle);
            startService(intent);
        }

        // Method to stop the service
        public void stopService() {
    //        notificatiouniversal notificatioun=new notificatiouniversal(this,"a");
    //        notificatioun.cancelnotification();
    GeofenceManuelService go=new GeofenceManuelService();
    go.dest();
    //            Intent intent = new Intent(MapsActivity.this, GeofenceManuelService.class);
    //            stopService(intent);

        }




public boolean chkbglocationpermission(){
        boolean chkbl=false;
    boolean permissionAccessCoarseLocationApproved = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED;

    if (permissionAccessCoarseLocationApproved) {
        boolean backgroundLocationPermissionApproved =
                ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_BACKGROUND_LOCATION)
                        == PackageManager.PERMISSION_GRANTED;

        if (backgroundLocationPermissionApproved) {
            chkbl=backgroundLocationPermissionApproved;
            // App can access location both in the foreground and in the background.
            // Start your service that doesn't have a foreground service type
            // defined.
        } else {
            // App can only access location in the foreground. Display a dialog
            // warning the user that your app must have all-the-time access to
            // location in order to function properly. Then, request background
            // location.
            chkbl=backgroundLocationPermissionApproved;
            ActivityCompat.requestPermissions(this, new String[] {
                            Manifest.permission.ACCESS_BACKGROUND_LOCATION},
                    0);
        }
    } else {
        ActivityCompat.requestPermissions(this, new String[] {
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_BACKGROUND_LOCATION
                },
                0);
    }
    return chkbl;
}








    public ArrayList<String> processisover() {
        final ArrayList<String> chk=new ArrayList<>();

return chk;
    }



    @Override
    public void onClick(View v) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}


