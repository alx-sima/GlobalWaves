package main.program.commands;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import fileio.input.commands.CommandInput;
import fileio.output.CommandResult;
import lombok.Getter;
import main.program.Program;
import main.entities.users.User;

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
    @JsonInclude(Include.NON_NULL)
    protected final String user;
    protected final int timestamp;

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
     * Get the user that executed the command.
     */
    protected User getCaller() {
        return Program.getInstance().getDatabase().getUser(user);
    }

    /**
     * Execute the command.
     *
     * @return The result of the execution.
     */
    public abstract CommandResult execute();
}
