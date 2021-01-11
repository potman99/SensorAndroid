package com.example.sensorapp;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;

public class Magnetometer extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor magnetometer;
    private TextView xValue;
    private TextView yValue;
    private TextView zValue;

    public void setData(float[] data){
        xValue.setText(getResources().getString(R.string.magnetometer_x_value, data[0]));
        yValue.setText(getResources().getString(R.string.magnetometer_y_value, data[1]));
        zValue.setText(getResources().getString(R.string.magnetometer_z_value, data[2]));
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(Properties.getIsBroadcast()) {
            Properties.sensorData.setMagnetometer(sensorEvent.values);
        }
        if(Properties.getIsDataSource()) {
            setData(Properties.sensorData.getMagnetometer());
        } else {
            setData(sensorEvent.values);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magnetometer);

        //retrieve widgets
        xValue = findViewById(R.id.xValue);
        yValue = findViewById(R.id.yValue);
        zValue = findViewById(R.id.zValue);

        //define instances
        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(magnetometer != null) {

            sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        sensorManager.unregisterListener(this);

    }
}
