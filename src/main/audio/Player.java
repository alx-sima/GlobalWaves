package main.audio;

import main.audio.files.AudioFile;
import main.audio.files.Song;

import java.util.ArrayList;
import java.util.List;

public final class Player {

    private List<AudioFile> queue = new ArrayList<>();
    private boolean isPaused;

    public Player() {
    }

    public List<AudioFile> getQueue() {
        return queue;
    }

    public void setQueue(List<AudioFile> queue) {
        this.queue = queue;
    }

    public boolean isPaused() {
        return isPaused;
    }

    public void setPaused(boolean paused) {
        isPaused = paused;
    }

    /**
     * Toggle the `paused` state of the player.
     *
     * @return The new state of the player.
     */
    public boolean togglePaused() {
        isPaused = !isPaused;
        return isPaused;
    }
}
