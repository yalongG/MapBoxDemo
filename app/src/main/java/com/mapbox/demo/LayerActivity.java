package com.mapbox.demo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.gson.JsonObject;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.style.layers.FillLayer;
import com.mapbox.mapboxsdk.style.layers.LineLayer;
import com.mapbox.mapboxsdk.style.layers.PropertyFactory;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.mapbox.mapboxsdk.style.sources.Source;
import com.mapbox.services.commons.geojson.Feature;
import com.mapbox.services.commons.geojson.FeatureCollection;
import com.mapbox.services.commons.geojson.LineString;
import com.mapbox.services.commons.geojson.Point;
import com.mapbox.services.commons.geojson.Polygon;
import com.mapbox.services.commons.models.Position;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static com.mapbox.mapboxsdk.style.layers.Property.LINE_CAP_ROUND;
import static com.mapbox.mapboxsdk.style.layers.Property.LINE_JOIN_ROUND;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.fillColor;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineColor;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineWidth;

public class LayerActivity extends AppCompatActivity implements View.OnClickListener {

    private MapView mapView;
    private MapboxMap mMap;

    private final static String GEOJSON_SOURCE_ID = "geojson_source_id";
    private final static String GEOJSON_LAYER_ID = "geojson_layer_id";

    private final static String FILL_SOURCE_ID = "fill_source";
    private final static String FILL_LAYER_ID = "fill_layer";

    private final static String TEXT_LAYER_ID = "text_layer";
    private final static String TEXT_SOURCE_ID = "text_source";

    private final static String LINE_SOURCE_ID = "line_source";
    private final static String LINE_LAYER_ID = "line_layer";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, getString(R.string.mapbox_access_token));
        setContentView(R.layout.activity_layer);

        mapView = findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);
        findViewById(R.id.btn_geojson).setOnClickListener(this);
        findViewById(R.id.btn_line_layer).setOnClickListener(this);
        findViewById(R.id.btn_fill_layer).setOnClickListener(this);
        findViewById(R.id.btn_text_layer).setOnClickListener(this);

        mapView.getMapAsync(mapboxMap ->
                this.mMap = mapboxMap
        );
    }

    @Override
    public void onClick(View view) {
        if (mMap == null) {
            return;
        }
        switch (view.getId()) {
            case R.id.btn_geojson:
                mMap.removeLayer(GEOJSON_LAYER_ID);
                mMap.removeSource(GEOJSON_SOURCE_ID);
                CameraPosition position = new CameraPosition.Builder()
                        .target(new LatLng(38.89770383940516, -77.03666187812637)) // Sets the new camera position
                        .zoom(16) // Sets the zoom to level 10
                        .build(); // Builds the CameraPosition object from the builder
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(position), 2000);
                GeoJsonSource geoJsonSource = new GeoJsonSource(GEOJSON_SOURCE_ID,
                        loadGeoJsonFromAsset("white_house.geojson"));
                mMap.addSource(geoJsonSource);
                LineLayer layer = new LineLayer(GEOJSON_LAYER_ID, GEOJSON_SOURCE_ID);
                layer.setProperties(lineColor(Color.YELLOW), lineWidth(5f));
                mMap.addLayer(layer);
                break;
            case R.id.btn_line_layer:
                mMap.removeLayer(LINE_LAYER_ID);
                mMap.removeSource(LINE_SOURCE_ID);
                List<Position> lineCoordinates = new ArrayList<>();
                for (int i = 0, j = getPositionList().size(); i < j; i++) {
                    lineCoordinates.add(Position.fromCoordinates(getPositionList().get(i).getLongitude(), getPositionList().get(i).getLatitude()));
                }
                LineString lineString = LineString.fromCoordinates(lineCoordinates);
                FeatureCollection featureCollection = FeatureCollection.fromFeatures(new Feature[]{Feature.fromGeometry(lineString)});
                Source source = new GeoJsonSource(LINE_SOURCE_ID, featureCollection);
                mMap.addSource(source);
                LineLayer lineLayer1 = new LineLayer(LINE_LAYER_ID, LINE_SOURCE_ID);
                lineLayer1.setProperties(
                        PropertyFactory.lineCap(LINE_CAP_ROUND),
                        PropertyFactory.lineJoin(LINE_JOIN_ROUND),
                        PropertyFactory.lineWidth(2f),
                        PropertyFactory.lineColor(Color.GREEN));
                mMap.addLayer(lineLayer1);
                break;
            case R.id.btn_fill_layer:
                mMap.removeLayer(FILL_LAYER_ID);
                mMap.removeSource(FILL_SOURCE_ID);
                List<Position> positionList = new ArrayList<>();
                for (int i = 0, j = getPositionList().size(); i < j; i++) {
                    positionList.add(Position.fromCoordinates(getPositionList().get(i).getLongitude(), getPositionList().get(i).getLatitude()));
                }

                List<List<Position>> listList = new ArrayList<>();
                listList.add(positionList);
                Polygon polygon = Polygon.fromCoordinates(listList);
                FeatureCollection featureCollection1 = FeatureCollection.fromFeatures(new Feature[]{Feature.fromGeometry(polygon)});
                Source fillSource = new GeoJsonSource(FILL_SOURCE_ID, featureCollection1);
                mMap.addSource(fillSource);
                FillLayer fillLayer = new FillLayer(FILL_LAYER_ID, FILL_SOURCE_ID);
                fillLayer.setProperties(
                        fillColor(Color.RED));
                mMap.addLayer(fillLayer);
                break;
            case R.id.btn_text_layer:
                mMap.removeLayer(TEXT_LAYER_ID);
                mMap.removeSource(TEXT_SOURCE_ID);

                List<Feature> textFeature = new ArrayList<>();
                for (int i = 0, j = getPositionList().size(); i < j; i++) {
                    JsonObject object = new JsonObject();
                    object.addProperty("position", "第" + i + "个");
                    textFeature.add(Feature.fromGeometry(
                            Point.fromCoordinates(
                                    Position.fromCoordinates(getPositionList().get(i).getLongitude(),
                                            getPositionList().get(i).getLatitude()))
                            , object
                            )
                    );
                }
                FeatureCollection mFeature = FeatureCollection.fromFeatures(textFeature);
                mMap.addSource(new GeoJsonSource(TEXT_SOURCE_ID, mFeature));

                SymbolLayer textLayer = new SymbolLayer(TEXT_LAYER_ID, TEXT_SOURCE_ID);
                textLayer.setProperties(
                        PropertyFactory.textField("{position}"),
                        PropertyFactory.textSize(15f),
                        PropertyFactory.textColor(Color.DKGRAY)
                );
                mMap.addLayer(textLayer);
                break;
        }
    }

    private List<LatLng> getPositionList() {
        CameraPosition position2 = new CameraPosition.Builder()
                .target(new LatLng(-37.522585, 144.685699))
                .zoom(12)
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(position2), 2000);
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
        return polygonLatLngList;
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

    private String loadGeoJsonFromAsset(String filename) {
        try {
            // Load GeoJSON file
            InputStream is = getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            return new String(buffer, "UTF-8");

        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }

    }
}