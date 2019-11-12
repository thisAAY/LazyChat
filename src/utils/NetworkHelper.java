package utils;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class NetworkHelper {
    public static String getIp() throws IOException {
//        String ip = "";
//        try(final DatagramSocket socket = new DatagramSocket()){
//            socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
//            ip = socket.getLocalAddress().getHostAddress();
//        }
//        return ip;
        return InetAddress.getLocalHost().getHostAddress();
    }
    public static String getBroadcastIp() throws IOException
    {
        String ip = getIp();
        String[] subs = ip.split("\\.");
        String broadcastIp = "";
        for(int i = 0;i< subs.length -1;i++)
            broadcastIp += subs[i]+".";
        broadcastIp += 255;
        return broadcastIp;
    }
}
