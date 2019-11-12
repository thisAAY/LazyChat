package data.model;

import connection.listener.MessageListener;

import java.net.Socket;

public class ClientDevice extends Device{
    public ClientDevice(Socket socket, String name, String ip) {
        super(socket, name, ip);
    }

    @Override
    public void informListener(MessageListener listener, String message) {
        listener.onMessageArriveToClient(this,message);
    }
}
