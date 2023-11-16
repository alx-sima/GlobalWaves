package main.audio;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import main.audio.queues.Queue;
import main.audio.files.AudioFile;

/**
 * An entry that can be searched and then loaded into the player.
 */
public interface Searchable {

    /**
     * Check if the entity (song, podcast, playlist) matches the filter and its parameter.
     *
     * @param filter    The search filter.
     * @param parameter The filter's parameter.
     * @return true if the entity matches the search.
     */
    boolean matchFilter(String filter, String parameter);

    /**
     * Create a queue to be played from this search result.
     *
     * @return a queue based on the internal type.
     */
    Queue createQueue();

    /**
     * Get the name of the file.
     */
    String getName();
}
