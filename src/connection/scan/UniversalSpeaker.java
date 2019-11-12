package connection.scan;

import utils.Constants;
import utils.NetworkHelper;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UniversalSpeaker {
    private boolean isClosed = false;

    public void speak() {
        new Thread(() -> {
            while (true) {
                try {
                    DatagramSocket socket = new DatagramSocket();
                    socket.setBroadcast(true);
                    byte[] buffer = NetworkHelper.getIp().getBytes();
                    DatagramPacket packet = new DatagramPacket(buffer,
                            0,
                            buffer.length,
                            InetAddress.getByName(NetworkHelper.getBroadcastIp()),
                            Constants.SCANNING_PORT);
                    socket.send(packet);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }
}
