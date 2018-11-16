package com.mapbox.demo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.annotations.MarkerViewOptions;
import com.mapbox.mapboxsdk.annotations.PolygonOptions;
import com.mapbox.mapboxsdk.annotations.PolylineOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;

import java.util.ArrayList;
import java.util.List;

public class AnnotationActivity extends AppCompatActivity implements View.OnClickListener {

    private MapView mapView;
    private MapboxMap mMap;
    private Marker marker;
    private Marker customMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, getString(R.string.mapbox_access_token));
        setContentView(R.layout.activity_annotation);

        mapView = findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);

        mapView.getMapAsync(mapboxMap ->
                this.mMap = mapboxMap
        );

        findViewById(R.id.btn_add_marker).setOnClickListener(this);
        findViewById(R.id.btn_remove_marker).setOnClickListener(this);
        findViewById(R.id.btn_custom_marker).setOnClickListener(this);
        findViewById(R.id.btn_line).setOnClickListener(this);
        findViewById(R.id.btn_surface).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (mMap == null) {
            return;
        }
        switch (view.getId()) {
            case R.id.btn_add_marker:
                if (marker != null) {
                    mMap.removeMarker(marker);
                }
                marker = mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(-37.821648, 144.978594))
                        .title("marker"));
                break;

            case R.id.btn_remove_marker:
//                if (marker != null) {
//                    mMap.removeMarker(marker);
//                }
                mMap.clear(); // 删除所有marker
                break;
            case R.id.btn_custom_marker:
                if (customMarker != null) {
                    return;
                }
                IconFactory iconFactory = IconFactory.getInstance(this);
                Icon icon = iconFactory.fromResource(R.mipmap.ic_launcher);
                customMarker = mMap.addMarker(new MarkerViewOptions()
                        .position(new LatLng(-37.82164, 144.97859))
                        .icon(icon));
                break;
            case R.id.btn_line:
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

                mMap.addPolyline(new PolylineOptions()
                        .addAll(polygonLatLngList).width(2f).color(Color.RED));
                break;
            case R.id.btn_surface:
                List<LatLng> polygonLatLngList1 = new ArrayList<>();

                polygonLatLngList1.add(new LatLng(-37.522585, 144.685699));
                polygonLatLngList1.add(new LatLng(-37.534611, 144.708873));
                polygonLatLngList1.add(new LatLng(-37.530883, 144.678833));
                polygonLatLngList1.add(new LatLng(-37.547115, 144.667503));
                polygonLatLngList1.add(new LatLng(-37.530643, 144.660121));
                polygonLatLngList1.add(new LatLng(-37.533529, 144.636260));
                polygonLatLngList1.add(new LatLng(-37.521743, 144.659091));
                polygonLatLngList1.add(new LatLng(-37.510677, 144.648792));
                polygonLatLngList1.add(new LatLng(-37.515008, 144.664070));
                polygonLatLngList1.add(new LatLng(-37.502496, 144.669048));
                polygonLatLngList1.add(new LatLng(-37.515369, 144.678489));
                polygonLatLngList1.add(new LatLng(-37.506346, 144.702007));
                polygonLatLngList1.add(new LatLng(-37.522585, 144.685699));
                mMap.addPolygon(new PolygonOptions()
                        .addAll(polygonLatLngList1)
                        .fillColor(Color.YELLOW));
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