package main.program.commands;

import fileio.input.commands.CommandInput;
import fileio.output.CommandResult;
import fileio.output.ResultBuilder;
import lombok.Getter;
import main.program.commands.exceptions.InvalidOperation;
import main.program.databases.UserDatabase;
import main.program.entities.users.User;

/**
 * A command that can be executed by a user, returning a result.
 */
@Getter
public abstract class Command {

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
    protected final User getCaller() {
        return UserDatabase.getInstance().getUser(user);
    }

    /**
     * Execute the command.
     *
     * @return The result of the execution, be it valid or an error.
     */
    public final CommandResult run() {
        try {
            return execute();
        } catch (InvalidOperation e) {
            return getResultBuilder().returnMessage(e.getMessage());
        }
    }

    /**
     * Execute the command.
     *
     * @return The result of the execution, if valid.
     * @throws InvalidOperation If the command cannot be executed; Contains the error message.
     */
    protected abstract CommandResult execute() throws InvalidOperation;
}
