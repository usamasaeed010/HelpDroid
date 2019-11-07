package com.tpxsofts.helpdroid.Map_Activities;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import androidx.fragment.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.tpxsofts.helpdroid.R;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    LatLng currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    Geocoder geocoder=new Geocoder(getApplicationContext());
    List<Address> listofadress;
    Address addressobj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }




    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(this);
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location!=null){

                    currentLocation=new LatLng(location.getLatitude(),location.getLongitude());


                    // Add a marker in Sydney and move the camera
                    Toast.makeText(getApplicationContext(),String.valueOf(currentLocation),Toast.LENGTH_SHORT).show();
                    mMap.clear();
                    mMap.setMyLocationEnabled(true);

                    mMap.setMyLocationEnabled(true);

                    mMap.getUiSettings().setZoomControlsEnabled(true);
                    mMap.getUiSettings().setZoomGesturesEnabled(true);
                    mMap.getUiSettings().isIndoorLevelPickerEnabled();
                    mMap.getUiSettings().setAllGesturesEnabled(true);
                    mMap.getUiSettings().setMyLocationButtonEnabled(true);
                    mMap.setMaxZoomPreference(20);


                    mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
                    try {
                        listofadress=geocoder.getFromLocation(currentLocation.latitude,currentLocation.longitude,3);
                        addressobj=listofadress.get(0);


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    mMap.addMarker(new MarkerOptions()
                            .position(currentLocation)
                            .title("My Location")
                            .snippet(addressobj.getAdminArea()+"\n"+addressobj.getCountryName()+"\n"+addressobj.getLocality()));

                }
                else {

                    Toast.makeText(getApplicationContext(),"Turn onn Location ",Toast.LENGTH_SHORT).show();


                }
            }
        });

    }
}
