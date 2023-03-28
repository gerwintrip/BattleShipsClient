package nl.gerwint.listener;

import nl.gerwint.EventType;

/**
 * Created by Saph on 23/03/2023.
 */
public interface IListener {
    /**
     * Called when a message is received
     * @param message The message is in the format of a command.
     *                e.g. "HIT~10~12~3"
     *                @see nl.gerwint.Client#sendMessage(String)
     */
    @SuppressWarnings("unused")
    void onMessage(EventType type, String message);

}
