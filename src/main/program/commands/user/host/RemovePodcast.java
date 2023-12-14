package main.program.commands.user.host;

import fileio.input.commands.CommandInputWithName;
import fileio.output.builders.ResultBuilder;
import java.util.Objects;
import main.entities.audio.collections.Podcast;
import main.entities.users.UserDatabase;
import main.entities.users.host.Host;
import main.program.Library;

public final class RemovePodcast extends HostCommand {

    private final String name;

    public RemovePodcast(final CommandInputWithName input) {
        super(input);
        name = input.getName();
    }

    @Override
    protected ResultBuilder execute(final Host host) {
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
