package main.program.commands;

import fileio.input.commands.CommandInput;
import fileio.output.CommandResult;
import fileio.output.ResultBuilder;

public abstract class DependentCommand extends Command {

    protected DependentCommand(final Command command) {
        super(command);
    }

    protected DependentCommand(final CommandInput input) {
        super(input);
    }

    @Override
    public final CommandResult execute() {
        return checkDependencies();
    }

    /**
     * Checks if the dependencies are met, then run the command.
     */
    public abstract CommandResult checkDependencies();

    /**
     * Executes the command if the dependencies are met.
     */
    public abstract ResultBuilder executeIfDependenciesMet();
}
