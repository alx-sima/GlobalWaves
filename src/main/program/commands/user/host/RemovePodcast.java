package main.program.commands.user.host;

import fileio.input.commands.CommandInputWithName;
import fileio.output.MessageResult;
import java.util.Objects;
import main.program.entities.audio.collections.Podcast;
import main.program.databases.UserDatabase;
import main.program.entities.users.creators.Host;
import main.program.databases.Library;

public final class RemovePodcast extends HostCommand {

    private final String name;

    public RemovePodcast(final CommandInputWithName input) {
        super(input);
        name = input.getName();
    }

    @Override
    protected MessageResult execute(final Host host) {
        Library library = Library.getInstance();
        UserDatabase database = UserDatabase.getInstance();

        Podcast podcast = library.getPodcasts().stream()
            .filter(p -> p.getName().equals(name)).findFirst().orElse(null);

        if (podcast == null) {
            return resultBuilder.returnMessage(
                user + " doesn't have a podcast with the given name.");
        }

        if (database.getUsers().stream().map(user -> user.getPlayer().getPlayingAt(timestamp))
            .filter(Objects::nonNull)
            .anyMatch(currentFile -> currentFile.getOwner().equals(user))) {
            return resultBuilder.returnMessage(user + " can't delete this podcast.");
        }
        library.getPodcasts().remove(podcast);
        return resultBuilder.returnMessage(user + " deleted the podcast successfully.");
    }
}
