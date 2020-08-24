package com.example.tongxiang;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MapView map = findViewById(R.id.map);
        map.setOnProvinceClickLisener(new MapView.onProvinceClickLisener() {
            @Override
            public void onSelectProvince(String provinceName) {
                Log.d("Leon", provinceName);
            }
        });
    }
}