package main.audio.queues;

import main.audio.collections.RepeatMode;
import main.audio.collections.SongSource;
import main.audio.files.AudioFile;
import main.audio.files.Song;

public final class SongQueue extends Queue {

    private final SongSource songSource;
    private final int size;
    private int songIndex = 0;

    public SongQueue(final SongSource songSource, final int size, final boolean canShuffle) {
        super(canShuffle);
        this.songSource = songSource;
        this.size = size;
        currentlyPlaying = getCurrentSong();
    }

    public int getSize() {
        return size;
    }

    /**
     * Switch to the next repeat mode, based on the type of the queue that is playing.
     *
     * @return The updated repeat mode.
     */
    @Override
    public RepeatMode changeRepeatMode() {
        repeatMode = songSource.getNextRepeatMode(repeatMode);
        return repeatMode;
    }

    @Override
    public AudioFile getNext() {
        if (repeatMode == RepeatMode.REPEAT_CURRENT || repeatMode == RepeatMode.REPEAT_INFINITE) {
            return getCurrentSong();
        } else if (repeatMode == RepeatMode.REPEAT_ONCE) {
            repeatMode = RepeatMode.NO_REPEAT;
            return getCurrentSong();
        }

        if (shuffler != null) {
            shuffler.advance();
        }
        songIndex++;

        Song nextSong = getCurrentSong();
        if (nextSong != null) {
            return nextSong;
        }

        if (repeatMode == RepeatMode.REPEAT_ALL) {
            songIndex = 0;
            return getCurrentSong();
        }

        return null;
    }

    @Override
    public void accept(final QueueVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * Get the song currently playing.
     *
     * @return null if the queue ended.
     */
    public Song getCurrentSong() {
        int index = songIndex;
        if (shuffler != null) {
            index = shuffler.getCurrent();
        }

        return songSource.get(index);
    }

    @Override
    public AudioFile prev() {
        if (playTime > 0 || songIndex == 0) {
            playTime = 0;
            return currentlyPlaying;
        }

        songIndex--;
        currentlyPlaying = songSource.get(songIndex);
        return currentlyPlaying;
    }

    @Override
    public boolean skip(int deltaTime) {
        return false;
    }
}
