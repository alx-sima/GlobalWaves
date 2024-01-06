package main.entities.audio.files;

import fileio.input.EpisodeInput;

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
    public String toString() {
        return name + " - " + description;
    }
}
