package main.program.commands.user.host;

import fileio.input.commands.CommandInputWithName;
import fileio.output.CommandResult;
import fileio.output.MessageResultBuilder;
import fileio.output.ResultBuilder;
import java.util.Objects;
import main.entities.audio.collections.Podcast;
import main.entities.users.UserDatabase;
import main.program.Library;
import main.program.commands.DependentCommand;
import main.program.commands.dependencies.IsHostDependency;

public final class RemovePodcast extends DependentCommand {

    private final MessageResultBuilder resultBuilder;
    private final String name;

    public RemovePodcast(final CommandInputWithName input) {
        super(input);
        resultBuilder = new MessageResultBuilder(this);
        name = input.getName();
    }

    @Override
    public CommandResult checkDependencies() {
        IsHostDependency dependency = new IsHostDependency(this, resultBuilder);
        return dependency.checkDependencies();
    }

    @Override
    public ResultBuilder executeIfDependenciesMet() {
        Library library = Library.getInstance();
        UserDatabase database = UserDatabase.getInstance();

        Podcast podcast = library.getPodcasts().stream()
            .filter(p -> p.getName().equals(name)).findFirst().orElse(null);

        if (podcast == null) {
            return resultBuilder.withMessage(user + " doesn't have a podcast with the given name.");
        }

        if (database.getUsers().stream().map(user -> user.getPlayer().getPlayingAt(timestamp))
            .filter(Objects::nonNull)
            .anyMatch(currentFile -> currentFile.getOwner().equals(user))) {
            return resultBuilder.withMessage(user + " can't delete this podcast.");
        }
        library.getPodcasts().remove(podcast);
        return resultBuilder.withMessage(user + " deleted the podcast successfully.");
    }
}
