package main.program.commands.user.host;

import fileio.input.commands.AddAnnouncementInput;
import fileio.output.CommandResult;
import fileio.output.MessageResultBuilder;
import fileio.output.ResultBuilder;
import main.entities.users.host.Announcement;
import main.program.Library;
import main.program.Program;
import main.program.commands.DependentCommand;
import main.program.commands.dependencies.IsHostDependency;

public final class AddAnnouncement extends DependentCommand {

    private final MessageResultBuilder resultBuilder;
    private final String name;
    private final String description;

    public AddAnnouncement(final AddAnnouncementInput input) {
        super(input);
        resultBuilder = new MessageResultBuilder(this);
        name = input.getName();
        description = input.getDescription();
    }

    @Override
    public CommandResult checkDependencies() {
        IsHostDependency dependency = new IsHostDependency(this, resultBuilder);
        return dependency.checkDependencies();
    }

    @Override
    public ResultBuilder executeIfDependenciesMet() {
        Library library = Program.getInstance().getLibrary();
        library.getAnnouncements().add(new Announcement(user, name, description));
        return resultBuilder.withMessage(user + " has successfully added new announcement.");
    }
}
