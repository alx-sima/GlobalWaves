package main.audio;

import main.audio.files.AudioFile;

import java.util.List;

public final class Player {
    private final List<AudioFile> queue;
    private final int duration;
    private final int repeat = 0;
    private boolean isPaused;
    private boolean isShuffled;
    /**
     * Time the current playlist has been playing.
     */
    private int elapsedTime = 0;
    /**
     * The timestamp when the playlist has been un-paused.
     */
    private int lastUpdate;

    public Player(final List<AudioFile> queue, final int lastUpdate) {
        this.queue = queue;
        this.lastUpdate = lastUpdate;
        duration = queue.stream().mapToInt(AudioFile::getDuration).sum();
    }

    public int getRepeat() {
        return repeat;
    }

    public List<AudioFile> getQueue() {
        return queue;
    }

    public boolean isPaused() {
        return isPaused;
    }

    public boolean isShuffled() {
        return isShuffled;
    }

    /**
     * Update the elapsed time of the playlist.
     *
     * @param timestamp The timestamp of 'now'.
     */
    public void updateTime(final int timestamp) {
        if (!isPaused) {
            elapsedTime += (timestamp - lastUpdate);
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

        int queueDuration = 0;
        for (AudioFile audio : queue) {
            queueDuration += audio.getDuration();
            if (queueDuration > elapsedTime) {
                return audio;
            }
        }

        return null;
    }

    /**
     * Get the remaining playtime at the specified `timestamp`.
     *
     * @param timestamp The queried timestamp.
     * @return Remaining time units until the end of the queue.
     */
    public int remainingTimeAt(final int timestamp) {
        updateTime(timestamp);

        return duration - elapsedTime;
    }
}
