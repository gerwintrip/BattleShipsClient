package nl.gerwint.listener;

public interface IListener {
    /**
     * Called when a message is received
     * @param message The message is in the format of a command.
     *                e.g. "HIT~10~12~3"
     *                @see nl.gerwint.Client#sendMessage(String)
     */
    void onMessageReceived(String message);

}
