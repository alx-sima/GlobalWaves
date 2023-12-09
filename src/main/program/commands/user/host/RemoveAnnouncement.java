package main.program.commands.user.host;

import fileio.input.commands.CommandInputWithName;
import fileio.output.CommandResult;
import fileio.output.ResultBuilder;
import main.program.commands.DependentCommand;

public final class RemoveAnnouncement extends DependentCommand {

    private final String name;

    public RemoveAnnouncement(final CommandInputWithName input) {
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
