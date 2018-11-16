package com.mapbox.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.geometry.LatLngBounds;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;

import java.util.ArrayList;
import java.util.List;

public class CameraActivity extends AppCompatActivity implements View.OnClickListener {

    private MapView mapView;
    private MapboxMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, getString(R.string.mapbox_access_token));
        setContentView(R.layout.activity_camera);

        mapView = findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);

        mapView.getMapAsync(mapboxMap -> this.mMap = mapboxMap);
        findViewById(R.id.btn_move_center).setOnClickListener(this);
        findViewById(R.id.btn_move_centers).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (mMap == null) {
            return;
        }
        switch (view.getId()) {
            case R.id.btn_move_center:
                // moveCamera()   easeCamera()	 animateCamera()
                // mMap.getCameraPosition()
                CameraPosition position = new CameraPosition.Builder()
                        .target(new LatLng(51.50550, -0.07520)) // Sets the new camera position
                        .zoom(10) // Sets the zoom to level 10
                        .build(); // Builds the CameraPosition object from the builder
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(position), 2000);
                break;
            case R.id.btn_move_centers:
                List<LatLng> polygonLatLngList = new ArrayList<>();

                polygonLatLngList.add(new LatLng(-37.522585, 144.685699));
                polygonLatLngList.add(new LatLng(-37.534611, 144.708873));
                polygonLatLngList.add(new LatLng(-37.530883, 144.678833));
                polygonLatLngList.add(new LatLng(-37.547115, 144.667503));
                polygonLatLngList.add(new LatLng(-37.530643, 144.660121));
                polygonLatLngList.add(new LatLng(-37.533529, 144.636260));
                polygonLatLngList.add(new LatLng(-37.521743, 144.659091));
                polygonLatLngList.add(new LatLng(-37.510677, 144.648792));
                polygonLatLngList.add(new LatLng(-37.515008, 144.664070));
                polygonLatLngList.add(new LatLng(-37.502496, 144.669048));
                polygonLatLngList.add(new LatLng(-37.515369, 144.678489));
                polygonLatLngList.add(new LatLng(-37.506346, 144.702007));
                polygonLatLngList.add(new LatLng(-37.522585, 144.685699));
                for (int i = 0, j = polygonLatLngList.size(); i < j; i++) {
                    mMap.addMarker(new MarkerOptions().position(polygonLatLngList.get(i)));
                }
                LatLngBounds latLngBounds = new LatLngBounds.Builder().includes(polygonLatLngList).build();
                mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, 20));
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
}
