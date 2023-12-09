package main.program.commands.user.host;

import fileio.input.commands.AddAnnouncementInput;
import fileio.output.CommandResult;
import fileio.output.ResultBuilder;
import main.program.commands.DependentCommand;

public final class AddAnnouncement extends DependentCommand {

    private final String name;
    private final String description;

    public AddAnnouncement(final AddAnnouncementInput input) {
        super(input);
        name = input.getName();
        description = input.getDescription();
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
