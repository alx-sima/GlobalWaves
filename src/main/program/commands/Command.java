package main.program.commands;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import fileio.input.CommandInput;
import fileio.output.CommandResult;
import lombok.Getter;
import main.program.Program;
import main.program.User;

/**
 * A command that can be executed by a user, returning a result.
 */
@Getter
public abstract class Command {

    /**
     * The maximum number of results to be displayed.
     */
    protected static final int MAX_RESULTS = 5;
    protected final int timestamp;
    private final String command;
    @JsonInclude(Include.NON_NULL)
    private final String user;

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
    protected User getCallee() {
        return Program.getInstance().getUsers().get(user);
    }

    /**
     * Execute the command.
     *
     * @return The result of the execution.
     */
    public abstract CommandResult execute();
}
