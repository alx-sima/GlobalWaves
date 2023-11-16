package main.audio.collections;

import java.util.ArrayList;
import java.util.List;
import main.audio.files.AudioFile;
import main.audio.files.Song;

public final class SongQueue extends Playable {

    private final List<Song> audios;
    private int songIndex = 0;

    public SongQueue(List<Song> songs) {
        this.audios = new ArrayList<>(songs);
        currentlyPlaying = audios.get(0);
    }

    /**
     * Switch to the next repeat mode, based on the type of the queue that is playing.
     *
     * @return The updated repeat mode.
     */
    @Override
    public RepeatMode changeRepeatMode() {
        return switch (repeatMode) {
            case NO_REPEAT -> RepeatMode.REPEAT_ALL;
            case REPEAT_ALL -> RepeatMode.REPEAT_CURRENT;
            case REPEAT_CURRENT -> RepeatMode.NO_REPEAT;
            default -> null;
        };
    }

    @Override
    protected AudioFile getNext() {
        songIndex++;
        if (songIndex >= audios.size()) {
            return null;
        }

        return audios.get(songIndex);
    }

    @Override
    public void accept(PlayableVisitor visitor) {
        visitor.visit(this);
    }

    public Song getCurrentSong() {
        return audios.get(songIndex);
    }
}
