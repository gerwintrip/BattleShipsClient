package nl.gerwint;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Main {

    private static boolean exit = false;
    private static Scanner scanner;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        System.out.println("Please enter server address:");
        InetAddress address = null;
        try {
            address = InetAddress.getByName(scanner.nextLine());
        } catch (UnknownHostException e) {
            System.out.println("ERROR: Unknown Host");
            System.exit(1);
        }
        System.out.println("Please enter port number:");
        int port = Integer.parseInt(scanner.nextLine());
        Client client = new MyClient();
        Listener listener = new Listener();
        client.addListener(listener);
        if (!client.connect(address, port)) {
            System.out.println("ERROR: Something went wrong while connecting");
        } else {
            System.out.println("Connected to server " + address + " on port " + port);
            while (!exit) {
                //System.out.println("Please enter a new command:");
                String command = scanner.nextLine();
                if (!command.equals("quit")) {
                    if (!client.sendCommand(command)) {
                        System.out.println("ERROR:" +
                                " Something went wrong while sending your command");
                        exit = true;
                    }
                } else {
                    exit = true;
                }
            }
            client.removeListener(listener);
            client.close();
        }
    }
}