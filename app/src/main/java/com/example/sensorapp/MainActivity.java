package com.example.sensorapp;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private final DataBroadcast dataBroadcast = new DataBroadcast();
    private final DataReceive dataReceive = new DataReceive();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Properties.getInstance();

        setContentView(R.layout.activity_main);

        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Button accelerometerBtn = findViewById(R.id.accelerometer);
        Button magnetometerBtn = findViewById(R.id.magnetometer);
        Switch broadcastSwitch = findViewById(R.id.broadcastSwitch);
        Switch dataSourceSwitch = findViewById(R.id.deviceSwitch);

        broadcastSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Properties.setIsBroadcast(isChecked);
            if (isChecked) {
                dataBroadcast.start();
            }
            if(!isChecked) {
                dataBroadcast.stop();
            }
            Log.v("Switch Broadcast State=", "" + isChecked);
        });

        dataSourceSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Properties.setIsDataSource(isChecked);
            if (isChecked) {
                dataReceive.start();
            }if(!isChecked){
                dataReceive.stop();
            }
            Log.v("Data Source State=", "" + isChecked);
        });

        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            //set listener for button
            accelerometerBtn.setOnClickListener(view -> {
                //add intent that takes use of the sensor's activity if the button pressed
                Intent accelerometerIntent = new Intent(MainActivity.this, Accelerometer.class);
                startActivity(accelerometerIntent);
            });
        } else {
            //disable button if sensor is not available
            accelerometerBtn.setEnabled(false);
        }

        if (sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) != null) {
            //set listener for button
            magnetometerBtn.setOnClickListener(view -> {
                //add intent that takes use to the sensor's activity if the button pressed
                Intent magnetometerIntent = new Intent(MainActivity.this, Magnetometer.class);
                startActivity(magnetometerIntent);
            });
        } else {
            //disable button if sensor is not available
            magnetometerBtn.setEnabled(false);
        }
    }
}