package connection;

import connection.listener.MessageListener;
import data.mapper.DeviceMapper;
import data.model.Device;
import utils.Constants;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    private ServerSocket serverSocket;

    private ArrayList<Device> devices;
    private boolean isClosed;
    private MessageListener messageListener;

    public Server(MessageListener messageListener) {
        this.messageListener = messageListener;
        devices = new ArrayList<>();
    }

    public void stop() {
        isClosed = true;
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        isClosed = false;
        startListening();
    }

    private void dispose() {
        for (Device device : devices)
            device.closeConnection();

    }

    private void startListening() {
        new Thread(() -> {
            try {
                serverSocket = new ServerSocket(Constants.PORT);
                while (!isClosed) {
                    Socket socket = serverSocket.accept();
                    Device device = DeviceMapper.getDeviceFromSocket(socket);
                    device.startReading(messageListener);
                    devices.add(device);
                    System.out.println("New Connection with: " + device);
                }
            } catch (IOException e) {
                System.out.println("connection.Server.startListening: " + e.toString());
            }
            finally {
                dispose();
            }
        }).start();
    }

    public void write(String msg)
    {
        for(Device device : devices)
            device.write(msg);
    }
    public ArrayList<Device> getDevices() {
        return devices;
    }


}
