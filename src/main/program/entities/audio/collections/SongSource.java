package main.program.entities.audio.collections;

import main.program.entities.audio.files.Song;
import main.program.entities.audio.queues.visitors.SongSourceVisitor;

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
     * Accept a SongSourceVisitor.
     */
    void accept(SongSourceVisitor visitor);
}
