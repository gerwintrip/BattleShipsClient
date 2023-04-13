package nl.gerwint.client;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * BattleShipsClient is a class that can be used to connect to a server.
 */
@SuppressWarnings({"EmptyMethod", "unused"})
public abstract class BattleShipsClient {

    private Client client;

    /**
     * Instantiates a new BattleShipsClient.
     * The client will try to connect to the server on localhost:55555
     * with the given username.
     *
     * @param username The username of the client. e.g. "Saph"
     */
    public BattleShipsClient(String username) {
        connect("localhost", 55555, username);
    }

    /**
     * Instantiates a new BattleShipsClient.
     * The client will try to connect to the server on the given address and port
     * with the given username.
     *
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
     *
     * @param address  The address of the server. e.g. "localhost"
     * @param port     The port of the server. e.g. 55555
     * @param username The username of the client. e.g. "Saph"
     */
    private void connect(String address, int port, String username) {
        client = new Client();
        try {
            if (!client.connect(InetAddress.getByName(address), port, username)) {
                System.out.println("ERROR: Something went wrong while connecting");
            } else {
                System.out.println("Connected to server " + address + " on port " + port);
                client.addListener(this::onEventReceived);
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
     * @see Client#sendMessage(String)
     */
    public void sendCommand(String message) {
        if (!client.sendMessage(message)) {
            System.out.println("ERROR:" + " Something went wrong while sending your message");
        }
    }

    /**
     * Closes the connection to the server.
     * This method should be called when the client is no longer needed.
     *
     * @see Client#close()
     */
    public void close() {
        client.close();
    }

    /**
     * Called when a message is received.
     * This method should be overridden.
     *
     * @param message The message is in the format of a command.
     *                e.g. "HIT~10~12~3"
     * @see nl.gerwint.client.listener.IListener#onMessage(EventType, String)
     */
    private void onEventReceived(EventType type, String message) {
        String[] split = message.split("~");
        switch (type) {
            case HELLO -> onHello(tryParse(split[1]));
            case HIT -> onHit(tryParse(split[1]), tryParse(split[2]), tryParse(split[3]));
            case MISS -> onMiss(tryParse(split[1]), tryParse(split[2]));
            case WINNER -> onWinner(tryParse(split[1]));
            case LOST -> onLost(tryParse(split[1]));
            case ERROR -> onError(split[1]);
            case EXIT -> onExit();
            case TURN -> onTurn(tryParse(split[1]));
            case NEWGAME -> onNewGame(tryParse(split[1]), tryParse(split[2]));
            case POS -> onPos(tryParse(split[1]), tryParse(split[2]));
            case PING -> onPing();
            case PONG -> onPong();
        }
    }

    /**
     * Tries to parse the given string to an integer.
     *
     * @param string The string to be parsed.
     * @return The parsed integer or -1 if the string could not be parsed.
     */
    private int tryParse(String string) {
        try {
            return Integer.parseInt(string);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /**
     * Called when the server sends a PING message.
     * This method sends a PONG message to the server.
     */
    private void onPing() {
        sendCommand("PONG");
    }

    /**
     * Called when the server sends a PONG message.
     */
    protected void onPong() {
    }

    /**
     * Called after the game starts.
     *
     * @param x Position x of a ship part
     * @param y Position y of a ship part
     */
    protected abstract void onPos(int x, int y);

    /**
     * Called after the game starts.
     *
     * @param x Width of the game board
     * @param y Height of the game board
     */
    protected abstract void onNewGame(int x, int y);

    /**
     * Called when the server sends a TURN message.
     *
     * @param playerNumber The player number of the client that has to make a move.
     */
    protected abstract void onTurn(int playerNumber);

    /**
     * Called when the server sends an EXIT message.
     */
    protected void onExit() {
    }

    /**
     * Called when the server sends a ERROR message.
     *
     * @param message The message of the error.
     */
    protected void onError(String message) {
    }

    /**
     * Called when the server sends a LOST message.
     *
     * @param PlayerNumber The player number of the client that lost.
     */
    protected void onLost(int PlayerNumber) {
    }

    /**
     * Called when the server sends a WINNER message.
     *
     * @param PlayerNumber The player number of the client that won.
     */
    protected void onWinner(int PlayerNumber) {
    }

    /**
     * Called when the server sends a MISS message.
     *
     * @param x Position x of the miss
     * @param y Position y of the miss
     */
    protected abstract void onMiss(int x, int y);

    /**
     * Called when the server sends a HIT message.
     *
     * @param x            Position x of the hit
     * @param y            Position y of the hit
     * @param playerNumber The player number of the client that was hit.
     */
    protected abstract void onHit(int x, int y, int playerNumber);

    /**
     * Called when the server sends a HELLO message.
     *
     * @param PlayerNumber The player number of the client.
     */
    protected abstract void onHello(int PlayerNumber);

    /**
     * Sends a PING command to the server.
     */
    public void ping() {
        sendCommand("PING");
    }

    /**
     * Sends a MOVE command to the server.
     *
     * @param x Position x of the torpedo
     * @param y Position y of the torpedo
     */
    public void launchTorpedo(int x, int y) {
        sendCommand("MOVE~" + x + "~" + y);
    }

    /**
     * Sends an EXIT command to the server.
     * This method should be called when the client is no longer needed.
     * This method will close the connection to the server.
     */
    public void exit() {
        sendCommand("EXIT");
    }
}