package main.commands;

public class Command {
    protected String command;
    protected String user;
    protected int timestamp;

    public Command(final String command, final String user, final int timestamp) {
        this.command = command;
        this.user = user;
        this.timestamp = timestamp;
    }
}
