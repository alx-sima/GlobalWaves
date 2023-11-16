package main.program;

import main.audio.collections.RepeatMode;
import main.audio.queues.Queue;
import main.audio.files.AudioFile;

public final class Player {

    private Queue queue;
    private boolean isPaused = true;
    /**
     * The timestamp when the playlist has been un-paused.
     */
    private int lastUpdate;

    public Queue getQueue() {
        return queue;
    }

    /**
     * Add a new queue to the playlist and start playing it.
     *
     * @param list     the list to be added.
     * @param timestamp the moment the playlist starts.
     */
    public void addQueue(final Queue list, final int timestamp) {
        this.queue = list;
        lastUpdate = timestamp;
        isPaused = false;
    }

    /**
     * Stop the player and clear the queue.
     */
    public void clearQueue() {
        this.queue = null;
        isPaused = true;
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
        if (queue == null) {
            return null;
        }

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

    public RepeatMode getRepeatMode() {
        if (queue == null) {
            return RepeatMode.NO_REPEAT;
        }

        return queue.getRepeatMode();
    }
}