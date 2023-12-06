package main.program.commands.user.host;

import fileio.input.commands.CommandInputWithName;
import fileio.output.CommandResult;
import main.program.commands.Command;

public final class RemoveAnnouncement extends Command {

    private final String name;

    public RemoveAnnouncement(final CommandInputWithName input) {
        super(input);
        name = input.getName();
    }

    @Override
    public CommandResult execute() {
        // TODO
        return null;
    }
}
