package nl.gerwint;

import nl.gerwint.listener.IListener;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class BattleShipsClient {

    private static Client client;

    @SuppressWarnings("EmptyMethod")
    public static void main(String[] args) {
    }

    /**
     * Connects to the server and starts the thread that listens for incoming messages.
     * The username is sent to the server as soon as the connection is established.
     *
     * @param address  The address of the server. e.g. "localhost"
     * @param port     The port of the server. e.g. 55555
     * @param username The username of the client. e.g. "Saph"
     */
    @SuppressWarnings("unused")
    public static void connect(String address, int port, String username) {
        client = new Client();
        try {
            if (!client.connect(InetAddress.getByName(address), port, username)) {
                System.out.println("ERROR: Something went wrong while connecting");
            } else {
                System.out.println("Connected to server " + address + " on port " + port);
            }
        } catch (UnknownHostException e) {
            System.out.println("ERROR: Unknown host");
        }
    }

    /**
     * Sends a message to the server.
     *
     * @param message The message to be sent to the server.
     *                The message should be in the format of a command.
     *                e.g. "MOVE~10~12"
     * @see nl.gerwint.Client#sendMessage(String)
     */
    @SuppressWarnings("unused")
    public static void sendCommand(String message) {
        if (!client.sendMessage(message)) {
            System.out.println("ERROR:" + " Something went wrong while sending your message");
        }
    }

    /**
     * Adds a listener to the client.
     * The listener will be notified when a message is received from the server.
     *
     * @param listener The listener to be added.
     *                 The listener should implement the IListener interface.
     * @see nl.gerwint.listener.IListener
     */
    @SuppressWarnings("unused")
    public static void addListener(IListener listener) {
        client.addListener(listener);
    }

    /**
     * Closes the connection to the server.
     * This method should be called when the client is no longer needed.
     *
     * @see nl.gerwint.Client#close()
     */
    @SuppressWarnings("unused")
    public static void close() {
        client.close();
    }


}