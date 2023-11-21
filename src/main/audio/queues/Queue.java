package main.audio.queues;

import lombok.Getter;
import main.audio.files.AudioFile;
import main.audio.files.Song;

public abstract class Queue {

    @Getter
    private final boolean isShuffle;
    @Getter
    protected RepeatMode repeatMode = RepeatMode.NO_REPEAT;
    @Getter
    protected AudioFile currentlyPlaying = null;
    protected int playTime = 0;
    protected Shuffler shuffler = null;

    public Queue(final boolean isShuffle) {
        this.isShuffle = isShuffle;
    }

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
     * Get the remaining time of the current file.
     */
    public int getRemainingTime() {
        return currentlyPlaying.getDuration() - playTime;
    }

    /**
     * Check if playlist is shuffled.
     */
    public boolean isShuffled() {
        return shuffler != null;
    }

    /**
     * Try to enable shuffling (if this queue supports it).
     *
     * @param seed the seed of the shuffler.
     */
    public void enableShuffle(final int seed) {
    }

    /**
     * Disable shuffling.
     */
    public void disableShuffle() {
        shuffler = null;
    }

    /**
     * Get the next file.
     *
     * @return null if the queue ended.
     */
    public abstract AudioFile getNext();

    /**
     * Change to the next repeat mode.
     *
     * @return the next mode.
     */
    public abstract RepeatMode changeRepeatMode();

    /**
     * Skip `deltaTime` seconds (if the queue supports it).
     *
     * @return true if the skip succeeded.
     */
    public boolean skip(final int deltaTime) {
        return false;
    }

    /**
     * Advance to the next file.
     *
     * @return the next file, or null if the queue has ended.
     */
    public AudioFile next() {
        AudioFile nextFile = getNext();
        currentlyPlaying = nextFile;
        playTime = 0;

        return nextFile;
    }

    /**
     * Go back to the previous file, or to the beginning of the current file if this is the first.
     *
     * @return the previous file.
     */
    public abstract AudioFile prev();

    public Song getCurrentSong() {
        return null;
    }
}
