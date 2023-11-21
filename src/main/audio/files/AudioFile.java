package main.audio.files;

import lombok.Getter;

/**
 * An audio file, which is .
 */
@Getter
public abstract class AudioFile {

    private final String name;

    private final int duration;

    protected AudioFile(final String name, final int duration) {
        this.name = name;
        this.duration = duration;
    }

}
