package com.mapbox.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_annotation).setOnClickListener(v ->
                startActivity(new Intent(this, AnnotationActivity.class))
        );
        findViewById(R.id.btn_camera).setOnClickListener(view ->
                startActivity(new Intent(this, CameraActivity.class)));
        findViewById(R.id.btn_layer).setOnClickListener(view ->
                startActivity(new Intent(this, LayerActivity.class)));
    }
}
