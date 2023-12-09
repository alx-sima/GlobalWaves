package main.entities.audio.files;

import fileio.input.EpisodeInput;

/**
 * An episode, which is part of a podcast.
 */
public class Episode extends AudioFile {

    private final String description;

    public Episode(final EpisodeInput input, final String owner) {
        super(input.getName(), input.getDuration(), owner);
        description = input.getDescription();
    }
}
