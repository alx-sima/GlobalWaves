package main.program.commands;

import fileio.input.commands.CommandInput;
import fileio.output.CommandResult;
import fileio.output.builders.ResultBuilder;
import lombok.Getter;
import main.entities.users.User;
import main.entities.users.UserDatabase;

/**
 * A command that can be executed by a user, returning a result.
 */
@Getter
public abstract class Command {

    /**
     * The maximum number of results to be displayed.
     */
    protected static final int MAX_RESULTS = 5;
    protected final String command;
    protected final String user;
    protected final int timestamp;

    protected Command(final CommandInput input) {
        command = input.getCommand();
        user = input.getUsername();
        timestamp = input.getTimestamp();
    }

    /**
     * Get the result builder for the command.
     *
     * @return The result builder, initialized with the base command fields (command, user,
     * timestamp).
     */
    protected abstract ResultBuilder getResultBuilder();

    /**
     * Get the user that executed the command.
     */
    protected User getCaller() {
        return UserDatabase.getInstance().getUser(user);
    }

    /**
     * Execute the command.
     *
     * @return The result of the execution.
     */
    public abstract CommandResult execute();
}
