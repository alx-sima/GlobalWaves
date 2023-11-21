package main.audio.collections;

import main.audio.files.Song;
import main.audio.queues.RepeatMode;

/**
 * A collection of songs that can be indexed and has different ways to be repeated.
 */
public interface SongSource {

    /**
     * Get the number of songs contained.
     */
    int size();

    /**
     * Get the `index`-th song of the collection, if it exists.
     *
     * @return null, if the song doesn't exist.
     */
    Song get(int index);

    /**
     * Get the next way to repeat the collection, based on its internal type.
     *
     * @param repeatMode the current repeat mode.
     */
    RepeatMode getNextRepeatMode(RepeatMode repeatMode);
}
