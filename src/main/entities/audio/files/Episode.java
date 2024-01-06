package main.entities.audio.files;

import fileio.input.EpisodeInput;
import main.entities.users.UserDatabase;
import main.entities.users.creators.Host;

/**
 * An episode, which is part of a podcast.
 */
public final class Episode extends AudioFile {

    private final String description;

    public Episode(final EpisodeInput input) {
        super(input.getName(), input.getDuration(), input.getName());
        description = input.getDescription();
    }

    @Override
    public Host getHost() {
        return UserDatabase.getInstance().getHosts().stream()
            .filter(host -> host.getName().equals(getOwner())).findFirst().orElse(null);
    }

    @Override
    public String toString() {
        return name + " - " + description;
    }
}
