package fileio.output;

import main.program.commands.Command;

public abstract class CommandResult extends Command {

    public CommandResult(final Command command) {
        super(command);
    }

    /**
     * Do nothing. (there's no need to execute a result, as it already is an output of a command)
     *
     * @return itself.
     */
    @Override
    public CommandResult execute() {
        return this;
    }
}
