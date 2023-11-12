package main.commands;

public abstract class Command {
    private final String command;
    private final String user;
    private final int timestamp;

    public Command(final String command, final String user, final int timestamp) {
        this.command = command;
        this.user = user;
        this.timestamp = timestamp;
    }

    public String getCommand() {
        return command;
    }

    public String getUser() {
        return user;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public abstract Result execute();
}
