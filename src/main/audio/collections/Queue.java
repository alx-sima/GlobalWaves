package main.audio.collections;

import main.audio.Searchable;
import main.audio.files.AudioFile;

public class Queue {

    private final Searchable audio;
    private final int duration;
    private RepeatMode repeatMode = RepeatMode.NO_REPEAT;
    private int timePassed = 0;

    public Queue(Searchable audio) {
        this.audio = audio;

        int duration = 0;
        for (AudioFile file : audio.getContents()) {
            duration += file.getDuration();
        }
        this.duration = duration;
    }

    public RepeatMode getRepeatMode() {
        return repeatMode;
    }

    public void addTimeIncrement(int increment) {
        timePassed += increment;
    }

    public RepeatMode changeRepeatMode() {
        repeatMode = audio.nextRepeatMode(repeatMode);
        return repeatMode;
    }

    public int getRemainingTime() {
        return duration - timePassed;
    }

    public AudioFile getPlayingSong() {
        return audio.getSongAt(timePassed);
    }
}
