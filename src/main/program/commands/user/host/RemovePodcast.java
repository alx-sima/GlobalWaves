package main.program.commands.user.host;

import fileio.input.commands.CommandInputWithName;
import fileio.output.CommandResult;
import main.program.commands.Command;

public final class RemovePodcast extends Command {

    private final String name;

    public RemovePodcast(final CommandInputWithName input) {
        super(input);
        name = input.getName();
    }

    @Override
    public CommandResult execute() {
        // TODO
        return null;
    }
}
