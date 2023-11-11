package main.commands;

public class Search extends Command {
    private String type;

    public Search(final String command, final String user, final int timestamp, final String type) {
        super(command, user, timestamp);
        this.type = type;
    }
}
