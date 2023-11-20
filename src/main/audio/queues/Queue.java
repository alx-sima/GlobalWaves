package main.audio.queues;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import lombok.Getter;
import main.audio.collections.RepeatMode;
import main.audio.files.AudioFile;

class Shuffler {

    /**
     * A mapping between the index of a song (`i`) in the original queue and its index in the
     * shuffled order `(indexes[i])`.
     */
    private final List<Integer> indexes;

    Shuffler(final int seed, final int size) {
        indexes = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            indexes.add(i);
        }

        Collections.shuffle(indexes, new Random(seed));
    }

    /**
     * Get the `index`-th song in the shuffled order.
     *
     * @param index the index in the shuffled queue.
     * @return the index of the song in the original queue.
     */
    public int getIndexMapping(final int index) {
        return indexes.get(index);
    }

    /**
     * Given the index of a song in the original queue, get its index in the shuffled queue (the
     * reverse of getIndexMapping).
     *
     * @param songIndex the index of the song in the original queue.
     * @return the index of the song in the shuffled queue or -1 if this failed.
     * @see Shuffler#getIndexMapping
     */
    public int getIndexOf(final int songIndex) {
        for (int i = 0; i < indexes.size(); i++) {
            if (indexes.get(i) == songIndex) {
                return i;
            }
        }

        return -1;
    }
}

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
     * Accept a QueueVisitor.
     */
    public abstract void accept(QueueVisitor visitor);

    /**
     * Skip `deltaTime` seconds (if the queue supports it).
     *
     * @return true if the skip succeeded.
     */
    public abstract boolean skip(int deltaTime);

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
}
