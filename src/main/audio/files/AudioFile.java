package main.audio.files;

public abstract class AudioFile {
    private final String name;
    private final int duration;

    protected AudioFile(final String name, final int duration) {
        this.name = name;
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public int getDuration() {
        return duration;
    }
}
