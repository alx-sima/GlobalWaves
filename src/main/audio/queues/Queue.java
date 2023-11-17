package main.audio.queues;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import main.audio.collections.RepeatMode;
import main.audio.files.AudioFile;

class Shuffler {

    private final List<Integer> indexes;
    private int index;

    public Shuffler(final int seed, final int size) {
        indexes = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            indexes.add(i);
        }

        Collections.shuffle(indexes, new Random(seed));
    }

    /**
     * Get the current song's index in the order generated by the shuffler.
     *
     * @return an out-of-bounds index if the queue has ended.
     */
    public int getCurrent() {
        if (index < indexes.size()) {
            return indexes.get(index);
        }

        return indexes.size();
    }

    /**
     * Advance to the next song in the generated order.
     */
    public void advance() {
        if (indexes.get(index) == indexes.size() - 1) {
            index = indexes.size();
        }
        index++;
    }
}

public abstract class Queue {

    private final boolean isShuffle;
    protected RepeatMode repeatMode = RepeatMode.NO_REPEAT;
    protected AudioFile currentlyPlaying = null;
    protected int playTime = 0;
    protected Shuffler shuffler = null;

    public Queue(final boolean isShuffle) {
        this.isShuffle = isShuffle;
    }

    /**
     * Check if queue can be shuffled.
     */
    public boolean isShuffle() {
        return isShuffle;
    }

    /**
     * Get the currently playing file.
     */
    public AudioFile getCurrentlyPlaying() {
        return currentlyPlaying;
    }

    /**
     * Get the repeating mode of the queue.
     */
    public RepeatMode getRepeatMode() {
        return repeatMode;
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
     * @param size this queue's number of songs.
     */
    public void enableShuffle(final int seed, final int size) {
        if (isShuffle) {
            shuffler = new Shuffler(seed, size);
        }
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
    protected abstract AudioFile getNext();

    /**
     * Change to the next repeat mode.
     *
     * @return the next mode.
     */
    public abstract RepeatMode changeRepeatMode();

    /**
     * Accept a QueueVisitor.
     */
    public abstract void accept(QueueVisitor visitor);

}
