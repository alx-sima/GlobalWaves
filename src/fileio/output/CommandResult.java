package fileio.output;

import fileio.input.commands.CommandInput;
import main.program.commands.Command;

/**
 * A result of a command execution
 */
public abstract class CommandResult extends Command {

    protected CommandResult(final Command command) {
        super(command);
    }

    protected CommandResult(final CommandInput input) {
        super(input);
    }

    /**
     * Do nothing. (there's no need to execute a result, as it already is an output of a command)
     *
     * @return null.
     */
    @Override
    public CommandResult execute() {
        return null;
    }
}
