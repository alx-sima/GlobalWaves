package main.program.commands;

import fileio.input.commands.CommandInput;
import fileio.output.CommandResult;
import fileio.output.ResultBuilder;

/**
 * A command that can be run only if the caller is online.
 */
public abstract class OnlineCommand extends Command {

    protected OnlineCommand(final CommandInput input) {
        super(input);
    }

    /**
     * Check if the user is online, then execute the command.
     *
     * @return the result of the command, or an error if the user was offline.
     */
    @Override
    public final CommandResult execute() {
        if (!getCaller().isOnline()) {
            ResultBuilder result = createResultBuilder();
            return result.withMessage(user + " is offline.").build();
        }

        return executeWhenOnline();
    }

    /**
     * Create a result builder for the command.
     */
    protected abstract ResultBuilder createResultBuilder();

    /**
     * The command to be executed if the user is online.
     *
     * @return the result of the command.
     */
    protected abstract CommandResult executeWhenOnline();
}
