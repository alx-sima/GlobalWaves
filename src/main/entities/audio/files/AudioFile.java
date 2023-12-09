package main.entities.audio.files;

import lombok.Getter;

/**
 * An audio file, which can be part of a play queue.
 */
@Getter
public abstract class AudioFile {

    private final String name;
    private final int duration;
    private final String owner;

    protected AudioFile(final String name, final int duration, final String owner) {
        this.name = name;
        this.duration = duration;
        this.owner = owner;
    }
}
