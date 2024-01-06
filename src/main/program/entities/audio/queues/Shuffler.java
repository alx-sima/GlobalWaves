package main.program.entities.audio.queues;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * The shuffler used by a queue generated from a playlist to reorder the songs.
 */
public class Shuffler {

    /**
     * A mapping between the index of a song (`i`) in the original queue and its index in the
     * shuffled order `(indexes[i])`.
     */
    private final List<Integer> indexes;

    public Shuffler(final int seed, final int size) {
        indexes = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            indexes.add(i);
        }

        Collections.shuffle(indexes, new Random(seed));
    }

    /**
     * Get the `index`-th song in the shuffled order.
     *
     * @param index the index in the shuffled queue.
     * @return the index of the song in the original queue.
     */
    public int getIndexMapping(final int index) {
        return indexes.get(index);
    }

    /**
     * Given the index of a song in the original queue, get its index in the shuffled queue (the
     * reverse of getIndexMapping).
     *
     * @param songIndex the index of the song in the original queue.
     * @return the index of the song in the shuffled queue or -1 if this failed.
     * @see Shuffler#getIndexMapping
     */
    public int getIndexOf(final int songIndex) {
        for (int i = 0; i < indexes.size(); i++) {
            if (indexes.get(i) == songIndex) {
                return i;
            }
        }

        return -1;
    }
}
