package main.audio.collections;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import main.audio.files.AudioFile;
import main.audio.files.Song;

public final class SongQueue extends Playable {

    private final List<Song> audios;
    private final Function<RepeatMode, RepeatMode> repeatModeToggle;
    private int songIndex = 0;

    public SongQueue(List<Song> songs, Function<RepeatMode, RepeatMode> repeatModeToggle) {
        this.audios = new ArrayList<>(songs);
        this.repeatModeToggle = repeatModeToggle;
        currentlyPlaying = audios.get(0);
    }

    /**
     * Switch to the next repeat mode, based on the type of the queue that is playing.
     *
     * @return The updated repeat mode.
     */
    @Override
    public RepeatMode changeRepeatMode() {
        repeatMode = repeatModeToggle.apply(repeatMode);
        return repeatMode;
    }

    @Override
    protected AudioFile getNext() {
        switch (repeatMode) {
            case REPEAT_CURRENT, REPEAT_INFINITE -> {
                return audios.get(songIndex);
            }
            case REPEAT_ONCE -> {
                repeatMode = RepeatMode.NO_REPEAT;
                return audios.get(songIndex);
            }
        }

        songIndex++;
        if (songIndex >= audios.size()) {
            switch (repeatMode) {
                case NO_REPEAT -> {
                    return null;
                }
                case REPEAT_ALL -> songIndex = 0;
            }
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
