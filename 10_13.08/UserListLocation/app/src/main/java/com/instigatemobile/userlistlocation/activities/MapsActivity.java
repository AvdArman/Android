package com.instigatemobile.userlistlocation.activities;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.instigatemobile.userlistlocation.R;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Double lat;
    private Double lng;
    private String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        lat = Double.valueOf(getIntent().getStringExtra("lat"));
        lng = Double.valueOf(getIntent().getStringExtra("lng"));
        address = getIntent().getStringExtra("addr");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng location = new LatLng(lat, lng);
        mMap.addMarker(new MarkerOptions().position(location).title(address));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
    }
}
