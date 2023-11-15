package main.audio.collections;

import main.audio.Searchable;
import main.audio.files.AudioFile;

public final class Queue {

    private final Searchable audio;
    private final int duration;
    private RepeatMode repeatMode = RepeatMode.NO_REPEAT;
    private int timePassed = 0;

    public Queue(final Searchable audio) {
        this.audio = audio;

        int queueLen = 0;
        for (AudioFile file : audio.getContents()) {
            queueLen += file.getDuration();
        }
        duration = queueLen;
    }

    public Searchable getAudio() {
        return audio;
    }

    public RepeatMode getRepeatMode() {
        return repeatMode;
    }

    /**
     * Simulate the passing of `increment` seconds.
     *
     * @param increment The time duration that passed.
     */
    public void addTimeIncrement(final int increment) {
        timePassed += increment;
    }

    /**
     * Switch to the next repeat mode, based on the type of the queue that is playing.
     *
     * @return The updated repeat mode.
     */
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
