package main.commands;

import fileio.input.CommandInput;
import main.Program;
import main.User;

public abstract class Command {
    private final String command;
    private final String user;
    private final int timestamp;

    protected Command(final CommandInput input) {
        command = input.getCommand();
        user = input.getUsername();
        timestamp = input.getTimestamp();
    }

    protected Command(final Command command) {
        this.command = command.command;
        this.user = command.user;
        this.timestamp = command.timestamp;
    }

    /**
     * Get the command name.
     *
     * @return The command name.
     */
    public String getCommand() {
        return command;
    }

    /**
     * Get the user that executes the command.
     *
     * @return The username.
     */
    public String getUser() {
        return user;
    }

    /**
     * Get the timestamp when the command was executed.
     *
     * @return The timestamp.
     */
    public int getTimestamp() {
        return timestamp;
    }

    /**
     * Get the user that called this command.
     *
     * @return A refference to the user.
     */
    public User getCallee() {
        Program instance = Program.getInstance();

        for (User u : instance.getUsers()) {
            if (u.getUsername().equals(user)) {
                return u;
            }
        }

        return null;
    }

    /**
     * Execute the command.
     *
     * @return The result of the execution.
     */
    public abstract Result execute();
}
