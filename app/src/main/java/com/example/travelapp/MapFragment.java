package com.example.travelapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        SupportMapFragment supportMapFragment = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.google_map);

        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                float zoomLevel = 9.0f;
                LatLng Berlin = new LatLng(52.520008, 13.404954);
                googleMap.addMarker(new MarkerOptions().position(Berlin).title("Berlin"));
                googleMap.addMarker(new MarkerOptions().position(new LatLng(52.5185418295755, 13.376337400702134)).title("Reichstag"));
                googleMap.addMarker(new MarkerOptions().position(new LatLng(52.51617663870269, 13.377704097006644)).title("Branderburg Gate"));
                googleMap.addMarker(new MarkerOptions().position(new LatLng(52.52072357704697, 13.40933326632387)).title("Berlin Television Tower"));
                googleMap.addMarker(new MarkerOptions().position(new LatLng(52.51723375597242, 13.401776879537424)).title("Humboldt Forum"));
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Berlin, zoomLevel));
            }
        });
        return view;
    }

}