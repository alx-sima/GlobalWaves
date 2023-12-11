package main.program.commands.user.host;

import fileio.input.commands.CommandInputWithName;
import fileio.output.CommandResult;
import fileio.output.MessageResultBuilder;
import fileio.output.ResultBuilder;
import main.entities.users.host.Announcement;
import main.program.Library;
import main.program.commands.DependentCommand;
import main.program.commands.dependencies.IsHostDependency;

public final class RemoveAnnouncement extends DependentCommand {

    private final MessageResultBuilder resultBuilder;
    private final String name;

    public RemoveAnnouncement(final CommandInputWithName input) {
        super(input);
        resultBuilder = new MessageResultBuilder(this);
        name = input.getName();
    }

    @Override
    public CommandResult checkDependencies() {
        IsHostDependency dependency = new IsHostDependency(this, resultBuilder);
        return dependency.execute();
    }

    @Override
    public ResultBuilder executeIfDependenciesMet() {
        Library library = Library.getInstance();
        Announcement announcement = library.getAnnouncements().stream()
            .filter(a -> a.getName().equals(name)).findFirst().orElse(null);
        if (announcement == null) {
            return resultBuilder.withMessage(user + " has no announcement with the given name.");
        }
        library.getAnnouncements().remove(announcement);
        return resultBuilder.withMessage(user + " has successfully deleted the announcement.");
    }
}
