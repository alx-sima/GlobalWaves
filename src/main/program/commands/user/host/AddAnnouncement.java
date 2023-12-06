package main.program.commands.user.host;

import fileio.input.commands.AddAnnouncementInput;
import fileio.output.CommandResult;
import main.program.commands.Command;

public final class AddAnnouncement extends Command {

    private final String name;
    private final String description;

    public AddAnnouncement(final AddAnnouncementInput input) {
        super(input);
        name = input.getName();
        description = input.getDescription();
    }

    @Override
    public CommandResult execute() {
        // TODO
        return null;
    }
}
