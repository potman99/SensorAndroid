package com.example.sensorapp;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;

public class DataBroadcast {
    private static final String TAG = "Broadcast";
    private Client client;

    public DataBroadcast() {
        this.client = new Client();
    }

    private Timer timer;
    public void start() {
        if (timer != null) {
            return;
        }
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void run() {
                client.sendObject(Properties.sensorData);
            }
        }, 0, 1000);
    }

    public void stop() {
        timer.cancel();
        timer = null;
    }

    private class Client {
        private DatagramSocket socket;
        private InetAddress address;
        private int port = 8888;

        public Client() {
            openSocket();
        }

        private void openSocket() {
            if(socket == null || socket.isClosed()){
                try {
                    socket = new DatagramSocket();
                    socket.setBroadcast(true);
                    address = InetAddress.getByName("255.255.255.255");
                } catch (UnknownHostException e) {
                    Log.d(TAG, "Error: " + e.getLocalizedMessage());
                } catch (SocketException e) {
                    Log.d(TAG, "Error: " + e.getLocalizedMessage());
                }
            }
        }

        private void closeSocket(){
            if(socket.isConnected()){
                socket.close();
            }
        }

        private void sendPacket(DatagramPacket packet) {
            try {
                socket.send(packet);
            } catch (IOException e) {
                Log.d(TAG, "Error: " + e.getLocalizedMessage());
            }
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        public void sendObject(Object object){
            try {
                openSocket();

                ByteArrayOutputStream bStream = new ByteArrayOutputStream();
                ObjectOutput oo = new ObjectOutputStream(bStream);
                oo.writeObject(object);
                oo.close();
                byte[] serializedObject = bStream.toByteArray();

                DatagramPacket packet = new DatagramPacket(serializedObject, serializedObject.length, this.address, this.port);
                sendPacket(packet);
                Log.v(TAG, "Client: Sent object to " + this.address + ":" + this.port + "[" + LocalDateTime.now() + "]");

                closeSocket();
            } catch (IOException e) {
                Log.d(TAG, "Error: " + e.getLocalizedMessage());
            }
        }
    }
}
