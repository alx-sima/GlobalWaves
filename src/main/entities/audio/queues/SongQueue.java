package main.entities.audio.queues;

import lombok.Getter;
import main.entities.audio.collections.SongSource;
import main.entities.audio.files.AudioFile;
import main.entities.audio.files.Song;
import main.entities.users.UserDatabase;
import main.program.Program;

/**
 * A play queue consisting of songs (generated by a song or a playlist).
 */
public final class SongQueue extends Queue {

    private final SongSource songSource;
    @Getter
    private final int size;

    public SongQueue(final SongSource songSource, final int size, final boolean canShuffle) {
        super(canShuffle);
        this.songSource = songSource;
        this.size = size;
        currentlyPlaying = getCurrentSong();
        Program.getInstance().getDatabase().getBusyUsers().add(currentlyPlaying.getOwner());
    }

    @Override
    protected AudioFile getNextFile() {
        if (repeatMode == RepeatMode.REPEAT_CURRENT || repeatMode == RepeatMode.REPEAT_INFINITE) {
            return getCurrentSong();
        }

        if (repeatMode == RepeatMode.REPEAT_ONCE) {
            repeatMode = RepeatMode.NO_REPEAT;
            return getCurrentSong();
        }

        if (shuffler == null || shuffler.getIndexMapping(playIndex) != songSource.size() - 1) {
            playIndex++;
            if (playIndex < songSource.size()) {
                return getCurrentSong();
            }
        }

        if (repeatMode == RepeatMode.REPEAT_ALL) {
            playIndex = 0;
            return getCurrentSong();
        }

        return null;
    }

    @Override
    public void enableShuffle(final int seed) {
        if (super.isShuffle()) {
            shuffler = new Shuffler(seed, size);

            // Get the current song's index in the newly shuffled order.
            playIndex = shuffler.getIndexOf(playIndex);
        }
    }

    @Override
    public void disableShuffle() {
        // Get the current song's index in the original order.
        if (super.isShuffled()) {
            playIndex = shuffler.getIndexMapping(playIndex);
        }

        super.disableShuffle();
    }

    @Override
    public RepeatMode changeRepeatMode() {
        repeatMode = songSource.getNextRepeatMode(repeatMode);
        return repeatMode;
    }

    private int getSongIndex(final int index) {
        return shuffler != null ? shuffler.getIndexMapping(index) : index;
    }

    @Override
    protected AudioFile getFilePlaying() {
        return getCurrentSong();
    }

    @Override
    public Song getCurrentSong() {
        return songSource.get(getSongIndex(playIndex));
    }

    @Override
    public AudioFile prev() {
        if (playTime == 0 && playIndex != 0) {
            playIndex--;
            currentlyPlaying = getCurrentSong();
        }

        playTime = 0;
        return currentlyPlaying;
    }
}
