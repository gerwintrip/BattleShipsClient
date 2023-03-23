package nl.gerwint;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * BattleShipsClient is a class that can be used to connect to a server.
 */
public abstract class BattleShipsClient {

    private static Client client;

    /**
     * Instantiates a new BattleShipsClient.
     * The client will try to connect to the server on localhost:55555
     * with the given username.
     * @param username The username of the client. e.g. "Saph"
     */
    public BattleShipsClient(String username) {
        connect("localhost", 55555, username);
    }

    /**
     * Instantiates a new BattleShipsClient.
     * The client will try to connect to the server on the given address and port
     * with the given username.
     * @param address  The address of the server. e.g. "localhost"
     * @param port     The port of the server. e.g. 55555
     * @param username The username of the client. e.g. "Saph"
     */
    public BattleShipsClient(String address, int port, String username) {
        connect(address, port, username);
    }

    /**
     * Connects to the server and starts the thread that listens for incoming messages.
     * The username is sent to the server as soon as the connection is established.
     * @param address  The address of the server. e.g. "localhost"
     * @param port     The port of the server. e.g. 55555
     * @param username The username of the client. e.g. "Saph"
     */
    @SuppressWarnings("unused")
    private void connect(String address, int port, String username) {
        client = new Client();
        try {
            if (!client.connect(InetAddress.getByName(address), port, username)) {
                System.out.println("ERROR: Something went wrong while connecting");
            } else {
                System.out.println("Connected to server " + address + " on port " + port);
                client.addListener(this::onMessageReceived);
            }
        } catch (UnknownHostException e) {
            System.out.println("ERROR: Unknown host");
        }
    }

    /**
     * Sends a message to the server.
     * @param message The message to be sent to the server.
     *                The message should be in the format of a command.
     *                e.g. "MOVE~10~12"
     * @see nl.gerwint.Client#sendMessage(String)
     */
    @SuppressWarnings("unused")
    public void sendCommand(String message) {
        if (!client.sendMessage(message)) {
            System.out.println("ERROR:" + " Something went wrong while sending your message");
        }
    }

    /**
     * Closes the connection to the server.
     * This method should be called when the client is no longer needed.
     * @see nl.gerwint.Client#close()
     */
    @SuppressWarnings("unused")
    public void close() {
        client.close();
    }

    /**
     * Called when a message is received.
     * This method should be overridden.
     * @param message The message is in the format of a command.
     *                e.g. "HIT~10~12~3"
     * @see nl.gerwint.listener.IListener#onMessage(String)
     */
    public abstract void onMessageReceived(String message);

}