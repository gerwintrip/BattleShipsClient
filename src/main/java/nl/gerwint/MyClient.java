package nl.gerwint;

public class MyClient extends Client{

    @Override
    public String commandReceived(String command) {
        return command;
    }
}
