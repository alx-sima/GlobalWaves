package main.program.commands;

import fileio.input.CommandInput;
import fileio.output.CommandResult;

/**
 * A command that can be executed by a user, returning a result.
 */
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
     */
    public String getCommand() {
        return command;
    }

    /**
     * Get the user that executes the command.
     */
    public String getUser() {
        return user;
    }

    /**
     * Get the timestamp when the command was executed.
     */
    public int getTimestamp() {
        return timestamp;
    }

    /**
     * Execute the command.
     *
     * @return The result of the execution.
     */
    public abstract CommandResult execute();
}
