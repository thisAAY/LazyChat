package data.mapper;

import data.model.ClientDevice;
import data.model.Device;

import java.net.Socket;

public class DeviceMapper {
    public static Device getDeviceFromSocket(Socket socket)
    {
        return new Device(socket,socket.getInetAddress().getHostName(),socket.getInetAddress().getHostAddress());
    }
    public static ClientDevice getClientDeviceFromSocket(Socket socket)
    {
        return new ClientDevice(socket,socket.getInetAddress().getHostName(),socket.getInetAddress().getHostAddress());
    }
}
