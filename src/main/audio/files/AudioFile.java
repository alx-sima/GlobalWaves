package main.audio.files;

public class AudioFile {
    protected String name;
    protected int duration;

    protected AudioFile(final String name, final int duration) {
        this.name = name;
        this.duration = duration;
    }

    public String getName() {
        return name;
    }
}
