package com.example.sensorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Button accelerometerBtn = findViewById(R.id.accelerometer);
        Button magnetometerBtn = findViewById(R.id.magnetometer);
        Switch broadcastSwitch = findViewById(R.id.broadcastSwitch);
        Switch deviceSwitch = findViewById(R.id.deviceSwitch);

        broadcastSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.v("Switch Broadcast State=", ""+isChecked);
            }
        });

        deviceSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.v("Switch Device State=", ""+isChecked);
            }
        });


        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {

            //set listener for button
            accelerometerBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //add intent that takes use to the sensor's activity if the button pressed
                    Intent accelerometerIntent = new Intent(MainActivity.this, Accelerometer.class);
                    startActivity(accelerometerIntent);
                }
            });
        } else {

            //disable button if sensor is not available
            accelerometerBtn.setEnabled(false);
        }

        if (sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) != null) {

            //set listener for button
            magnetometerBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //add intent that takes use to the sensor's activity if the button pressed
                    Intent magnetometerIntent = new Intent(MainActivity.this, Magnetometer.class);
                    startActivity(magnetometerIntent);
                }
            });
        } else {

            //disable button if sensor is not available
            magnetometerBtn.setEnabled(false);
        }
    }
}