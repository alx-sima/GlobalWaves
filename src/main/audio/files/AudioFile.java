package main.audio.files;

public class AudioFile {
    private String name;
    private int duration;

     protected AudioFile(final String name, final int duration) {
        this.name = name;
        this.duration = duration;
    }
}
