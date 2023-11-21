package main.audio.files;

import fileio.input.EpisodeInput;

/**
 * An episode, which is part of a podcast.
 */
public class Episode extends AudioFile {

    private final String description;

    public Episode(final EpisodeInput input) {
        super(input.getName(), input.getDuration());
        description = input.getDescription();
    }
}
