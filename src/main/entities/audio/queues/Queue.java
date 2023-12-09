package main.entities.audio.queues;

import lombok.Getter;
import main.entities.audio.files.AudioFile;
import main.entities.audio.files.Song;
import main.entities.users.UserDatabase;
import main.program.Program;

/**
 * A play queue, holding the files that will play in the music player.
 */
public abstract class Queue {

    @Getter
    private final boolean isShuffle;
    @Getter
    protected RepeatMode repeatMode = RepeatMode.NO_REPEAT;
    @Getter
    protected AudioFile currentlyPlaying = null;
    protected int playTime = 0;
    protected Shuffler shuffler = null;
    protected int playIndex = 0;

    public Queue(final boolean isShuffle) {
        this.isShuffle = isShuffle;
    }

    /**
     * Set the file that is currently playing.
     */
    protected void setCurrentlyPlaying(final AudioFile currentlyPlaying) {
        UserDatabase database = Program.getInstance().getDatabase();

        if (this.currentlyPlaying != null) {
            database.getBusyUsers().remove(this.currentlyPlaying.getOwner());
        }

        this.currentlyPlaying = currentlyPlaying;
        if (currentlyPlaying != null) {
            database.getBusyUsers().add(currentlyPlaying.getOwner());
        }
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
            setCurrentlyPlaying(getNextFile());

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
     * Change to the next repeat mode, based on the queue that is playing.
     *
     * @return the next mode.
     */
    public abstract RepeatMode changeRepeatMode();

    protected abstract AudioFile getFilePlaying();

    /**
     * Get the song that is currently playing.
     *
     * @return none, if the file playing is not a song.
     */
    public Song getCurrentSong() {
        return null;
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
        AudioFile nextFile = getNextFile();
        setCurrentlyPlaying(nextFile);
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
            setCurrentlyPlaying(getFilePlaying());
        }

        playTime = 0;
        return currentlyPlaying;
    }
}
