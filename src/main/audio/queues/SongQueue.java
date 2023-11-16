package main.audio.queues;

import main.audio.collections.RepeatMode;
import main.audio.collections.SongSource;
import main.audio.files.AudioFile;
import main.audio.files.Song;

public final class SongQueue extends Queue {

    private final SongSource songSource;
    private int songIndex = 0;

    public SongQueue(final SongSource songSource) {
        this.songSource = songSource;
        currentlyPlaying = this.songSource.get(0);
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
    protected AudioFile getNext() {
        if (repeatMode == RepeatMode.REPEAT_CURRENT || repeatMode == RepeatMode.REPEAT_INFINITE) {
            return songSource.get(songIndex);
        } else if (repeatMode == RepeatMode.REPEAT_ONCE) {
            repeatMode = RepeatMode.NO_REPEAT;
            return songSource.get(songIndex);
        }

        songIndex++;
        Song nextSong = songSource.get(songIndex);
        if (nextSong != null) {
            return nextSong;
        }

        if (repeatMode == RepeatMode.REPEAT_ALL) {
            songIndex = 0;
            return songSource.get(songIndex);
        }

        return null;
    }

    @Override
    public void accept(final QueueVisitor visitor) {
        visitor.visit(this);
    }

    public Song getCurrentSong() {
        return songSource.get(songIndex);
    }
}
