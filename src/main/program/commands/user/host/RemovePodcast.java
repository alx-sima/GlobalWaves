package main.program.commands.user.host;

import fileio.input.commands.CommandWithNameInput;
import java.util.Objects;
import main.program.databases.Library;
import main.program.databases.UserDatabase;
import main.program.entities.audio.collections.Podcast;
import main.program.entities.users.creators.Host;

public final class RemovePodcast extends HostCommand {

    private final String name;

    public RemovePodcast(final CommandWithNameInput input) {
        super(input);
        name = input.getName();
    }

    @Override
    protected String returnExecutionMessage(final Host host) {
        Library library = Library.getInstance();
        UserDatabase database = UserDatabase.getInstance();

        Podcast podcast = library.getPodcasts().stream()
            .filter(p -> p.getName().equals(name)).findFirst().orElse(null);

        if (podcast == null) {
            return user + " doesn't have a podcast with the given name.";
        }

        if (database.getUsers().stream().map(user -> user.getPlayer().getPlayingAt(timestamp))
            .filter(Objects::nonNull)
            .anyMatch(currentFile -> currentFile.getOwner().equals(user))) {
            return user + " can't delete this podcast.";
        }
        library.getPodcasts().remove(podcast);
        return user + " deleted the podcast successfully.";
    }
}
