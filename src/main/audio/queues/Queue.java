package main.audio.queues;

import main.audio.collections.RepeatMode;
import main.audio.files.AudioFile;

public abstract class Queue {

    protected RepeatMode repeatMode = RepeatMode.NO_REPEAT;
    protected AudioFile currentlyPlaying = null;
    protected int playTime = 0;

    /**
     * Get the next file.
     *
     * @return null if the queue ended.
     */
    protected abstract AudioFile getNext();

    /**
     * Simulate the passing of `increment` seconds.
     *
     * @param increment The time duration that passed.
     */
    public void addTimeIncrement(final int increment) {
        if (currentlyPlaying == null) {
            return;
        }

        playTime += increment;

        while (playTime >= currentlyPlaying.getDuration()) {
            playTime -= currentlyPlaying.getDuration();
            currentlyPlaying = getNext();

            if (currentlyPlaying == null) {
                playTime = 0;
                return;
            }
        }
    }

    /**
     * Get the currently playing file.
     */
    public AudioFile getCurrentlyPlaying() {
        return currentlyPlaying;
    }

    /**
     * Get the repeating mode of the queue.
     *
     * @return
     */
    public RepeatMode getRepeatMode() {
        return repeatMode;
    }

    /**
     * Get the remaining time of the current file.
     */
    public int getRemainingTime() {
        return currentlyPlaying.getDuration() - playTime;
    }

    /**
     * Change to the next repeat mode.
     *
     * @return the next mode.
     */
    public abstract RepeatMode changeRepeatMode();

    /**
     * Accept a PlayableVisitor.
     */
    public abstract void accept(QueueVisitor visitor);
}