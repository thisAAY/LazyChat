package connection;

import connection.listener.MessageListener;
import data.mapper.DeviceMapper;
import data.model.ClientDevice;
import utils.Constants;

import java.io.IOException;
import java.net.Socket;

public class Client {
    private String ip;

    private ClientDevice device;
    private MessageListener messageListener;
    public Client(String serverIp,MessageListener messageListener)
    {
        this.ip = serverIp;
        this.messageListener = messageListener;
    }

    public void stop()
    {
        device.closeConnection();
    }
    public void connect()
    {
        buildConnection();
    }
    private void buildConnection()
    {
        try {
            Socket socket = new Socket(ip, Constants.PORT);
            device = DeviceMapper.getClientDeviceFromSocket(socket);
            device.startReading(messageListener);
            System.out.println("Client: " + "Connection build with: " + device);
        } catch (IOException e) {
            System.err.println("Connection: mostly rejected no server is listening");
        }

    }
    public void write(String message)
    {
        device.write(message);
    }

}
