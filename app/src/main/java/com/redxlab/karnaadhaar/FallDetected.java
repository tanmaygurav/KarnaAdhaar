package com.redxlab.karnaadhaar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationRequest;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.tasks.OnSuccessListener;

public class FallDetected extends FragmentActivity implements OnMapReadyCallback{
    private static final String TAG="Fall Detected";
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    LocationRequest mLocationRequest;
    private Double longitude, latitude;
    private LatLng location_lat_log;
    private Location location_var;
    private GoogleMap mMap;
    private TextView timer;
    private ImageView alertImage;
    private boolean alertMark=true;
    private String number,msg;
    private Button safeBTN;
    public String sendMSG="true",fallDetected="true";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fall_detected);

        timer=findViewById(R.id.idTimer);
        alertImage=findViewById(R.id.idFallAlertImage);
        safeBTN=findViewById(R.id.idSafeBTN);


        loadData();
        safeBTN.setOnClickListener(v->{
            sendMSG="false";
            fallDetected="true";
            saveData();
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        });

//        Map - Location Code
        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager()
                        .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        fusedLocationClient.getLastLocation().addOnSuccessListener(FallDetected.this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                    location_var = location;
                    location_lat_log = new LatLng(latitude, longitude);
                    //                  next line to get the blue circle
                    if (ActivityCompat.checkSelfPermission(FallDetected.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(FallDetected.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    mMap.setMyLocationEnabled(true);
                    moveCameratoLocation(location_lat_log);
                }
            }
        });

        //        End Map - Location Code

//Countdown Code
        new CountDownTimer(5000, 1000) {

            public void onTick(long millisUntilFinished) {
                timer.setText(String.valueOf( millisUntilFinished / 1000));
                if (alertMark){
                    alertImage.setVisibility(View.VISIBLE);
                    alertMark=false;
                }else {
                    alertImage.setVisibility(View.INVISIBLE);
                    alertMark=true;
                }
            }

            public void onFinish() {
                sendMessageToContacts();
            }

        }.start();
    }


    private void moveCameratoLocation(LatLng location_lat_log) {
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(location_lat_log)
                .zoom(18).build();
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    private void sendMessageToContacts() {
//        TODO:send message to contacts with location
        // For testing Jay vanjare's number
//        number="8850008536";
        // For testing Akshay Patil's number
//        number="9869318062";
        msg="Fall detected! : Person X's location : Click here to open on google Maps: https://www.google.com/maps/@"+String.valueOf(location_lat_log.latitude)+","+String.valueOf(location_lat_log.longitude)+"z";
        Log.d(TAG, "sendMessageToContacts: msg: "+msg);
        if (sendMSG=="true"){
            try {
                SmsManager smsManager=SmsManager.getDefault();
                smsManager.sendTextMessage(number,null,msg,null,null);
                Toast.makeText(getApplicationContext(),"Message Sent to Emergency Contacts",Toast.LENGTH_LONG).show();
            }catch (Exception e)
            {
                Toast.makeText(getApplicationContext(),"Failed to send message",Toast.LENGTH_LONG).show();
            }
        }


    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
    }
    private void loadData() {
        try {
            SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
            sendMSG=sharedPreferences.getString("sendMSG", "true");
            number=sharedPreferences.getString("Number", null);
            // after loading data we are displaying a toast message.
            Toast.makeText(this, "Loaded sendMSG from Shared preferences. ", Toast.LENGTH_LONG).show();
        }catch (Exception e){
            Toast.makeText(this, "Error while loading : "+e, Toast.LENGTH_LONG).show();
        }
    }

    private void saveData() {
        try {
            SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("fallDetected", fallDetected);
            editor.apply();
            // after saving data we are displaying a toast message.
            Toast.makeText(this, "Saved fallDetected to Shared preferences. ", Toast.LENGTH_LONG).show();
        }catch (Exception e){
            Toast.makeText(this, "Error while saving : "+e, Toast.LENGTH_LONG).show();
        }
    }

}