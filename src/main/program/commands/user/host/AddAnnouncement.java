package main.program.commands.user.host;

import fileio.input.commands.AddAnnouncementInput;
import fileio.output.CommandResult;
import fileio.output.MessageResultBuilder;
import fileio.output.ResultBuilder;
import java.util.List;
import main.entities.users.host.Announcement;
import main.program.Library;
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
        List<Announcement> announcements = Library.getInstance().getAnnouncements();
        announcements.add(new Announcement(user, name, description));
        return resultBuilder.withMessage(user + " has successfully added new announcement.");
    }
}
