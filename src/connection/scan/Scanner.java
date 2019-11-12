package connection.scan;

import connection.listener.DeviceListener;
import utils.Constants;
import utils.NetworkHelper;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Scanner {

    private DeviceListener listener;

    public Scanner(DeviceListener listener) {
        this.listener = listener;
    }

    public void scan() {
        new Thread(() -> {

            try {
                System.out.println("Searching...");
                DatagramSocket socket = new DatagramSocket(Constants.SCANNING_PORT);
                byte[] buffer = new byte[1024];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                String ip = new String(packet.getData(), 0, packet.getLength());
                System.out.printf("Found server at ip: %s, Going to make the connection...\n",ip);
                listener.onDeviceFound(ip);
                socket.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }).start();
    }

    private void sendMessage(InetAddress address) throws IOException {
        byte[] buffer = NetworkHelper.getBroadcastIp().getBytes();
        DatagramPacket packet = new DatagramPacket(buffer, 0, buffer.length, address, Constants.SCANNING_PORT);
        DatagramSocket sender = new DatagramSocket();
        sender.send(packet);
        sender.close();
    }
}
