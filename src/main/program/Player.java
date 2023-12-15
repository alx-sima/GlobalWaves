package main.program;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import main.entities.audio.SearchableAudio;
import main.entities.audio.files.AudioFile;
import main.entities.audio.queues.Queue;
import main.entities.audio.queues.RepeatMode;

/**
 * User's player that can play songs and podcasts.
 */
public final class Player {

    private final Map<String, Queue> playHistory = new HashMap<>();

    @Getter
    private Queue queue;
    @Getter
    private boolean isPaused = true;
    private boolean isOnline = true;
    /**
     * The timestamp when the playlist has been un-paused.
     */
    private int lastUpdate;

    /**
     * Add a new queue to the playlist and start playing it.
     *
     * @param audio     the loaded audio source.
     * @param timestamp the moment the playlist starts.
     */
    public void addQueue(final SearchableAudio audio, final int timestamp) {
        queue = audio.createQueue(playHistory);
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

    /**
     * Update the elapsed time of the playlist.
     *
     * @param timestamp The timestamp of 'now'.
     */
    public void updateTime(final int timestamp) {
        if (queue != null && isOnline && !isPaused) {
            queue.addTimeIncrement(timestamp - lastUpdate);

            if (queue.getCurrentlyPlaying() == null) {
                clearQueue();
            }
        }
        lastUpdate = timestamp;
    }

    /**
     * Set the `paused` state of the player.
     *
     * @param paused    the new state.
     * @param timestamp the timestamp when this action occurs.
     */
    public void setPaused(final boolean paused, final int timestamp) {
        updateTime(timestamp);
        isPaused = paused;
        if (!isPaused) {
            lastUpdate = timestamp;
        }
    }

    /**
     * Set the online status of the user that owns the player.
     *
     * @param online    the new online status.
     * @param timestamp the timestamp when this action occurs.
     */
    public void setOnline(final boolean online, final int timestamp) {
        updateTime(timestamp);
        isOnline = online;
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
        // The queue might have ended in the meantime.
        if (queue == null) {
            return null;
        }
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

    /**
     * Get the repeating mode of the queue that is currently playing.
     *
     * @return NO_REPEAT (default) if nothing is playing.
     */
    public RepeatMode getRepeatMode() {
        if (queue == null) {
            return RepeatMode.NO_REPEAT;
        }

        return queue.getRepeatMode();
    }
}
