package main.entities.audio.queues;

import lombok.Getter;
import main.entities.audio.collections.SongSource;
import main.entities.audio.files.AudioFile;
import main.entities.audio.files.Song;
import main.entities.audio.queues.visitors.QueueVisitor;
import main.entities.users.User;
import main.entities.users.UserDatabase;

/**
 * A play queue consisting of songs (generated by a song or a playlist).
 */
@Getter
public final class SongQueue extends Queue {

    private final SongSource songSource;
    private final int size;

    public SongQueue(final User user, final SongSource songSource, final int size) {
        super(user);
        this.songSource = songSource;
        this.size = size;
        currentlyPlaying = getCurrentSong();
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

        playIndex++;
        if (playIndex < songSource.size()) {
            return getCurrentSong();
        }

        if (repeatMode == RepeatMode.REPEAT_ALL) {
            playIndex = 0;
            return getCurrentSong();
        }

        return null;
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
        Song nowPlaying = songSource.get(getSongIndex(playIndex));
        nowPlaying.addListen(user);
        user.addListen(nowPlaying);

        UserDatabase.getInstance().getMonetizedArtists().add(nowPlaying.getArtist());
        return nowPlaying;
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

    /**
     * Accept a QueueVisitor.
     */
    @Override
    public void accept(final QueueVisitor visitor) {
        visitor.visit(this);
    }
}
