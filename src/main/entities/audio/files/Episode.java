package main.entities.audio.files;

import fileio.input.EpisodeInput;
import lombok.Getter;
import main.entities.users.host.Host;

/**
 * An episode, which is part of a podcast.
 */
public final class Episode extends AudioFile {

    @Getter
    private final Host host;
    private final String description;

    public Episode(final EpisodeInput input, final Host host) {
        super(input.getName(), input.getDuration(), host.getName());
        this.host = host;
        description = input.getDescription();
    }

    @Override
    public String toString() {
        return name + " - " + description;
    }
}
