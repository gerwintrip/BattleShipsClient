package nl.gerwint;

public class Listener {

    public void messageReceived(String message) {
        if (message != null) {
            System.out.println(message);
        }
    }
}
