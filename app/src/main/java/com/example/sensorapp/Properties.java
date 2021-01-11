package com.example.sensorapp;

public class Properties {
    private static Properties mInstance = null;
    private static boolean isBroadcast;
    private static boolean isDataSource; //false = local, true = outsourced
    public static SensorData sensorDataLocal;
    public static SensorData sensorDataBroadcast;

    public static boolean getIsBroadcast() {
        return isBroadcast;
    }

    public static boolean getIsDataSource() {
        return isDataSource;
    }

    public static void setIsBroadcast(boolean value) {
        isBroadcast = value;
    }

    public static void setIsDataSource(boolean value) {
        isDataSource = value;
    }

    public static void setSensorDataLocal(SensorData data) {
        sensorDataLocal = data;
    }

    public static void setSensorDataReceived(SensorData data) {
        sensorDataBroadcast = data;
    }

    protected Properties() {
        sensorDataLocal = new SensorData();
        sensorDataBroadcast= new SensorData();
    }

    public static synchronized Properties getInstance() {
        if (null == mInstance) {
            mInstance = new Properties();
        }
        return mInstance;
    }
}
