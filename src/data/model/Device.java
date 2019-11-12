package data.model;

import connection.listener.MessageListener;

import java.io.*;
import java.net.Socket;
import java.util.Objects;

public class Device {
    private Socket socket;
    private String name,ip;
    private PrintWriter writer;
    private BufferedReader reader;
    private boolean isClosed;
    public Device(Socket socket,String name,String ip)
    {
        this.socket = socket;
        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.name = name;
        this.ip = ip;
    }


    public void write(String msg)
    {
        if(!socket.isClosed()) {
            writer.println(msg);
            writer.flush();
        }
    }
    public void startReading(MessageListener listener)
    {
        new Thread(() -> {
            String line = null;
            try {
                while (!isClosed && (line = reader.readLine()) != null) {
                    informListener(listener,line);
                }
            }
            catch (IOException e)
            {

            }
        }).start();

    }
    public void informListener(MessageListener listener,String message)
    {
        listener.onMessageArriveToServer(this,message);
    }
    public void closeConnection()
    {
        try {
            isClosed = true;
            reader.close();
            writer.close();
            socket.close();

        } catch (IOException e) {
            System.err.println("connection.Server.dispose: error closing streams and socket: " + e.toString());
        }
    }
    public Socket getSocket() {
        return socket;
    }

    public String getName() {
        return name;
    }


    public String getIp() {
        return ip;
    }

    @Override
    public String toString()
    {
        return String.format("Hostname: %s, Ip: %s",name,ip);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Device device = (Device) o;
        return Objects.equals(ip, device.ip);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ip);
    }



}
