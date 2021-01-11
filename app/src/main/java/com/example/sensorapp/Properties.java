package com.example.sensorapp;

public class Properties {
    private static Properties mInstance= null;
    private static boolean isBroadcast;
    private static boolean isDataSource; //false = local, true = outsourced
    public static SensorData sensorData;

    public static boolean getIsBroadcast(){
        return isBroadcast;
    }
    public static boolean getIsDataSource(){
        return isDataSource;
    }
    public static void setIsBroadcast(boolean value) {
        isBroadcast = value;
    }
    public static void setIsDataSource(boolean value) {
        isDataSource = value;
    }
    public static void setSensorData(SensorData data) { sensorData = data; }

    protected Properties(){
        sensorData = new SensorData();
    }

    public static synchronized Properties getInstance() {
        if(null == mInstance){
            mInstance = new Properties();
        }
        return mInstance;
    }
}
