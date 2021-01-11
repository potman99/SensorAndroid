package com.example.sensorapp;

import java.io.Serializable;

public class SensorData implements Serializable {
    private float[] accelerometer;
    private float[] magnetometer;

    public float[] getAccelerometer() {
        return accelerometer;
    }

    public void setAccelerometer(float[] accelerometer) {
        this.accelerometer = accelerometer;
    }

    public float[] getMagnetometer() {
        return magnetometer;
    }

    public void setMagnetometer(float[] magnetometer) {
        this.magnetometer = magnetometer;
    }

    public SensorData(){
        this.accelerometer = new float[3];
        this.magnetometer = new float[3];
    }
}
