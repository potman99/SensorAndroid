package com.example.sensorapp;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.time.LocalDateTime;

public class DataReceive {
    private static final String TAG = "Receive";
    private Server server;

    public DataReceive() {
    }

    public void start() {
        server = new Server();
        server.start();
    }

    public void stop(){
        server.stopServer();
    }

    private class Server extends Thread {
        private DatagramSocket socket;
        private byte[] buf = new byte[151];
        private volatile boolean running = true;

        public Server() {
        }

        private void openSocket() {
            if(socket == null || socket.isClosed()){
                try {
                    socket = new DatagramSocket(null);
                    socket.setReuseAddress(true);
                    socket.bind(new InetSocketAddress(8888));
                } catch (SocketException e) {
                    Log.d(TAG, "Error: " + e.getLocalizedMessage());
                }
            }
        }

        private void closeSocket(){
            if(socket != null || socket.isConnected()){
                socket.close();
            }
        }

        public void stopServer(){
            running = false;
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void run() {
            super.run();
            running = true;
            openSocket();
            while (running) {
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                ObjectInputStream iStream;
                try {
                    socket.receive(packet);
                    iStream = new ObjectInputStream(new ByteArrayInputStream(packet.getData()));
                    SensorData sensorData = (SensorData) iStream.readObject();
                    iStream.close();
                    Properties.setSensorData(sensorData);
                    Log.v(TAG, "Server: Received sensor data from " + packet.getAddress() + ":" + packet.getPort() + "[" + LocalDateTime.now() + "]");
                } catch (IOException | ClassNotFoundException e) {
                    Log.d(TAG, "Error: " + e.getLocalizedMessage());
                }
            }
            closeSocket();
        }
    }
}
