package main.audio;

import main.audio.collections.Playable;
import main.audio.files.AudioFile;

public final class Player {

    private final Playable queue;
    private boolean isPaused;
    /**
     * The timestamp when the playlist has been un-paused.
     */
    private int lastUpdate;

    public Player(final Playable queue, final int lastUpdate) {
        this.queue = queue;
        this.lastUpdate = lastUpdate;
    }

    public Playable getQueue() {
        return queue;
    }

    public boolean isPaused() {
        return isPaused;
    }

    /**
     * Update the elapsed time of the playlist.
     *
     * @param timestamp The timestamp of 'now'.
     */
    public void updateTime(final int timestamp) {
        if (!isPaused) {
            queue.addTimeIncrement(timestamp - lastUpdate);
            lastUpdate = timestamp;
        }
    }

    /**
     * Toggle the `paused` state of the player.
     *
     * @return True if the player is now paused.
     */
    public boolean togglePaused(final int timestamp) {
        updateTime(timestamp);

        isPaused = !isPaused;
        if (!isPaused) {
            lastUpdate = timestamp;
        }
        return isPaused;
    }

    /**
     * Get the song that is playing at the specified `timestamp`.
     *
     * @param timestamp The queried timestamp.
     * @return The song currently playing, or `null` if the queue ended.
     */
    public AudioFile getPlayingAt(final int timestamp) {
        updateTime(timestamp);
        return queue.getCurrentlyPlaying();
    }

    /**
     * Get the remaining playtime at the specified `timestamp`.
     *
     * @param timestamp The queried timestamp.
     * @return Remaining time units until the end of the queue.
     */
    public int remainingTimeAt(final int timestamp) {
        updateTime(timestamp);
        return queue.getRemainingTime();
    }
}
