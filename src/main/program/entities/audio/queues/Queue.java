package main.program.entities.audio.queues;

import lombok.Getter;
import main.program.databases.Library;
import main.program.entities.audio.files.AudioFile;
import main.program.entities.audio.queues.visitors.QueueVisitor;
import main.program.entities.users.User;

/**
 * A play queue, holding the files that will play in the music player.
 */
public abstract class Queue {

    protected final User user;
    @Getter
    protected RepeatMode repeatMode = RepeatMode.NO_REPEAT;
    @Getter
    protected AudioFile currentlyPlaying = null;
    protected int playTime = 0;
    protected int playIndex = 0;
    protected Shuffler shuffler = null;
    /**
     * The price of the next ad (if scheduled) or null if not.
     */
    protected Double nextAdPrice = null;

    protected Queue(final User user) {
        this.user = user;
    }

    /**
     * Get the next file.
     *
     * @return null if the queue ended.
     */
    protected abstract AudioFile getNextFile();

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

        // Skip the files that ended in the meantime.
        while (playTime >= currentlyPlaying.getDuration()) {
            playTime -= currentlyPlaying.getDuration();
            currentlyPlaying = getNextSongOrAd();

            // Check if queue ended.
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
     * Enable shuffling.
     *
     * @param newShuffler the shuffler to use.
     */
    public void enableShuffle(final Shuffler newShuffler) {
        shuffler = newShuffler;
        playIndex = shuffler.getIndexOf(playIndex);
    }

    /**
     * Disable shuffling.
     */
    public void disableShuffle() {
        // Get the current song's index in the original order.
        if (isShuffled()) {
            playIndex = shuffler.getIndexMapping(playIndex);
        }

        shuffler = null;
    }

    /**
     * Change to the next repeat mode, based on the queue that is playing.
     *
     * @return the next mode.
     */
    public abstract RepeatMode changeRepeatMode();

    protected abstract AudioFile getFilePlaying();

    private AudioFile getNextSongOrAd() {
        if (nextAdPrice != null) {
            user.splitAdMoney(nextAdPrice);
            nextAdPrice = null;

            return Library.getInstance().getAd();
        }

        return getNextFile();
    }

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
        AudioFile nextFile = getNextSongOrAd();
        currentlyPlaying = nextFile;
        playTime = 0;

        return nextFile;
    }

    /**
     * Go back to the previous file, or to the beginning of the current file if this is the first.
     *
     * @return the previous file.
     */
    public AudioFile prev() {
        if (playTime == 0 && playIndex != 0) {
            playIndex--;
            currentlyPlaying = getFilePlaying();
        }

        playTime = 0;
        return currentlyPlaying;
    }

    /**
     * Schedule an ad play after the current audio.
     */
    public void pushAd(final double price) {
        nextAdPrice = price;
    }

    /**
     * Accept a QueueVisitor.
     */
    public abstract void accept(QueueVisitor visitor);
}
