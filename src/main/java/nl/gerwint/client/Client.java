package nl.gerwint.client;

import nl.gerwint.client.listener.IListener;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Client class that handles the connection to the server.
 */
public class Client implements Runnable {

    private Socket socket;
    private BufferedWriter out;
    private BufferedReader in;
    private final List<IListener> listeners = new ArrayList<>();

    /**
     * Connects to the server and starts the thread that listens for incoming messages.
     * The username is sent to the server as soon as the connection is established.
     *
     * @param address  The address of the server.
     * @param port     The port of the server.
     * @param username The username of the client.
     * @return True if the connection was established successfully, false otherwise.
     */
    public boolean connect(InetAddress address, int port, String username) {
        try {
            socket = new Socket(address, port);
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Thread c1 = new Thread(this);
            c1.start();
            sendMessage(username);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Close the connection to the server.
     * This method should be called when the client is no longer needed.
     */
    public void close() {
        try {
            sendMessage("EXIT");
            socket.close();
        } catch (IOException ignored) {

        }
    }

    /**
     * Sends a command to the server.
     *
     * @param command The command to be sent to the server.
     * @return True if the command was sent successfully, false otherwise.
     */
    public boolean sendMessage(String command) {
        try {
            if (command != null) {
                out.write(command);
                out.newLine();
                out.flush();
            }
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    /**
     * Runs the thread that listens for incoming messages.
     * This method is called when the thread is started.
     */
    @Override
    public void run() {
        try {
            String line;
            while ((line = in.readLine()) != null) {
                notifyListeners(line);
            }
        } catch (IOException e) {
            System.out.println("Connection Closed");
            close();
        }
    }

    /**
     * Adds a listener to the client. The listener will be notified when a message is received.
     * The listener must implement the IMessageListener interface.
     * <p>
     * Example:
     * <pre>
     *     {@code
     *     client.addListener(new IMessageListener() {
     *         @Override
     *         public void onMessageReceived(String message) {
     *             System.out.println(message);
     *         }
     *     });
     *     }
     * </pre>
     *
     * @param listener The listener to be added.
     *                 The listener must implement the IListener interface.
     * @see nl.gerwint.client.listener.IListener
     */
    public void addListener(IListener listener) {
        listeners.add(listener);
    }

    /**
     * Notifies all listeners that a message has been received.
     *
     * @param message The message that has been received.
     */
    public void notifyListeners(String message) {
        try {
            for (IListener listener : listeners) {
                listener.onMessage(EventType.valueOf(message.split("~")[0]), message);
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid message received: " + message);
        }
    }


}
