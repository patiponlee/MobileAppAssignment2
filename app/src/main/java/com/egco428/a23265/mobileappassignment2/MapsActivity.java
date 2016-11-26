package com.egco428.a23265.mobileappassignment2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback  {

    private GoogleMap mMap;
    protected List<Comment> data = new ArrayList<>();
    private Double latitude;
    private Double longtitude;
    private String locationuser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        locationuser = getIntent().getExtras().getString("username");
        String latitudemap = getIntent().getExtras().getString("latitude");
        String longtitudemap = getIntent().getExtras().getString("longtitude");
        latitude =  Double.parseDouble(latitudemap);
        longtitude = Double.parseDouble(longtitudemap);
        getSupportActionBar().setTitle(locationuser + "'s Location");

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng userlocation = new LatLng(latitude,longtitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(userlocation));
        mMap.addMarker(new MarkerOptions().position(userlocation).title(locationuser+"'s location"));
    }

}