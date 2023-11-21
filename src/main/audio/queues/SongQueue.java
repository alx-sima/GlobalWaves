package main.audio.queues;

import lombok.Getter;
import main.audio.collections.RepeatMode;
import main.audio.collections.SongSource;
import main.audio.files.AudioFile;
import main.audio.files.Song;

public final class SongQueue extends Queue {

    private final SongSource songSource;
    @Getter
    private final int size;
    private int songIndex = 0;

    public SongQueue(final SongSource songSource, final int size, final boolean canShuffle) {
        super(canShuffle);
        this.songSource = songSource;
        this.size = size;
        currentlyPlaying = getCurrentSong();
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

        if (shuffler == null) {
            songIndex++;
            if (songIndex < songSource.size()) {
                return getCurrentSong();
            }
        } else {
            if (shuffler.getIndexMapping(songIndex) != songSource.size() - 1) {
                songIndex++;
                if (songIndex < songSource.size()) {
                    return getCurrentSong();
                }
            }
        }

        if (repeatMode == RepeatMode.REPEAT_ALL) {
            songIndex = 0;
            return getCurrentSong();
        }

        return null;
    }

    /**
     * Get the song currently playing.
     *
     * @return null if the queue ended.
     */
    @Override
    public Song getCurrentSong() {
        int index = songIndex;
        if (shuffler != null) {
            index = shuffler.getIndexMapping(index);
        }

        return songSource.get(index);
    }

    @Override
    public AudioFile prev() {
        if (playTime == 0 && songIndex != 0) {
            songIndex--;
            currentlyPlaying = getCurrentSong();
        }

        playTime = 0;
        return currentlyPlaying;
    }

    @Override
    public void enableShuffle(final int seed) {
        if (super.isShuffle()) {
            shuffler = new Shuffler(seed, size);
            // Get the current song's index in the newly shuffled order.
            songIndex = shuffler.getIndexOf(songIndex);
        }
    }

    @Override
    public void disableShuffle() {
        // Get the current song's index in the original order.
        if (super.isShuffled()) {
            songIndex = shuffler.getIndexMapping(songIndex);
        }

        super.disableShuffle();
    }
}
