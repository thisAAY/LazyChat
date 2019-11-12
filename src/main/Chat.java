package main;

import connection.Client;
import connection.Server;
import connection.listener.MessageListener;
import connection.scan.Scanner;
import connection.scan.UniversalSpeaker;
import data.model.Device;
import utils.Constants;
import utils.Mode;

public class Chat implements MessageListener {

    public static void main(String[] args) throws InterruptedException {
        new Chat();
    }

    private Server server;
    private Client client;

    private Mode mode;

    private Chat() throws InterruptedException {
        java.util.Scanner sc = new java.util.Scanner(System.in);
        System.out.println("Please choose mode [SERVER,CLIENT,BOTH]");
        String input = sc.nextLine().toUpperCase();
        if (input.equals("SERVER")) {
            mode = Mode.SERVER;
            createServer();
        } else if (input.equals("CLIENT")) {
            mode = Mode.CLIENT;
            createClient();
        } else if (input.equals("BOTH")) {
            mode = Mode.BOTH;
            createServer();
            Thread.sleep(1000);
            createClient();
        } else
            System.out.println("Did'nt identify the input, the application is being closed");

        waitForInput();
    }

    private void createServer() {
        server = new Server(this);
        server.start();
        System.out.println("Server is started and listening on: " + Constants.PORT);
        UniversalSpeaker universalSpeaker = new UniversalSpeaker(); // ip sender
        universalSpeaker.speak();
    }

    private void createClient() {
        java.util.Scanner sc = new java.util.Scanner(System.in);
        System.out.println("Enter 'auto' to search for devices to make connect or enter the ip manually");
        String input = sc.nextLine().toLowerCase();
        if (input.equals("auto")) {
            Scanner scanner = new Scanner((ip) -> {
                client = new Client(ip, this);
                client.connect();
            });
            scanner.scan();
        } else {
            client = new Client(input, this);
            client.connect();
        }
    }

    @Override
    public void onMessageArriveToServer(Device device, String msg) {
        System.out.printf("%s: %s\n", device.getName(), msg);
        if (msg.equals("BYE")) {
            server.stop();
            client.stop();
        }
    }

    @Override
    public void onMessageArriveToClient(Device device, String msg) {
        System.out.printf("%s: %s\n", device.getName(), msg);

    }


    private void waitForInput() {
        new Thread(() -> {
            java.util.Scanner sc = new java.util.Scanner(System.in);
            String line = null;
            while ((line = sc.nextLine()) != null) {
                if (mode == Mode.SERVER)
                    server.write(line);
                if (mode == Mode.CLIENT)
                    client.write(line);
            }
        }).start();
    }
}
