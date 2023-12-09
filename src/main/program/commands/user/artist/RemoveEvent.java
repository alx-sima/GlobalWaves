package main.program.commands.user.artist;

import fileio.input.commands.CommandInputWithName;
import fileio.output.CommandResult;
import fileio.output.ResultBuilder;
import main.program.commands.DependentCommand;

public final class RemoveEvent extends DependentCommand {

    private final String name;

    public RemoveEvent(final CommandInputWithName input) {
        super(input);
        name = input.getName();
    }

    @Override
    public CommandResult checkDependencies() {
        // TODO
        return null;
    }

    @Override
    public ResultBuilder executeIfDependenciesMet() {
        // TODO
        return null;
    }
}
