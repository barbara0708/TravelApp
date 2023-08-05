package com.example.travelapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Map extends FragmentActivity implements OnMapReadyCallback {
    GoogleMap map;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        SupportMapFragment supportMapFragment=(SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map=googleMap;
        float zoomLevel=9.0f;
        LatLng Berlin =new LatLng(52.520008,13.404954);
        map.addMarker(new MarkerOptions().position(Berlin).title("Berlin"));
        map.addMarker(new MarkerOptions().position(new LatLng(52.5185418295755, 13.376337400702134)).title("Reichstag"));
        map.addMarker(new MarkerOptions().position(new LatLng(52.51617663870269, 13.377704097006644)).title("Branderburg Gate"));
        map.addMarker(new MarkerOptions().position(new LatLng(52.52072357704697, 13.40933326632387)).title("Berlin Television Tower"));
        map.addMarker(new MarkerOptions().position(new LatLng(52.51723375597242, 13.401776879537424)).title("Humboldt Forum"));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(Berlin,zoomLevel));
    }
}