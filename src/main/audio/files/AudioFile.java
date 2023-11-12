package main.audio.files;

public abstract class AudioFile {
    private final String name;
    private final int duration;

    protected AudioFile(final String name, final int duration) {
        this.name = name;
        this.duration = duration;
    }

    /**
     * Get the name of the file.
     *
     * @return The name.
     */
    public String getName() {
        return name;
    }

    /**
     * Get the duration of the audio.
     *
     * @return The duration.
     */
    public int getDuration() {
        return duration;
    }
}
