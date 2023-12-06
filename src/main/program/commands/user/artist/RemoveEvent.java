package main.program.commands.user.artist;

import fileio.input.commands.CommandInputWithName;
import fileio.output.CommandResult;
import main.program.commands.Command;

public final class RemoveEvent extends Command {

    private final String name;

    public RemoveEvent(final CommandInputWithName input) {
        super(input);
        name = input.getName();
    }

    @Override
    public CommandResult execute() {
        // TODO
        return null;
    }
}
