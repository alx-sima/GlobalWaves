package main.program.entities.audio;

import java.util.Map;
import main.program.entities.Searchable;
import main.program.entities.audio.collections.Playlist;
import main.program.entities.audio.queues.PodcastQueue;
import main.program.entities.audio.queues.Queue;
import main.program.entities.users.User;
import main.program.entities.users.interactions.Searchbar;

/**
 * An entry that can be searched and then loaded into the player.
 */
public interface SearchableAudio extends Searchable {

    @Override
    default String selectResultBy(User user) {
        Searchbar searchbar = user.getSearchbar();
        searchbar.selectAudioSource(this);
        return getName();
    }

    /**
     * Create a queue to be played from this search result.
     *
     * @param podcastHistory the saved history of queues played by the same user.
     * @return a queue based on the internal type.
     */
    Queue createQueue(User user, Map<String, PodcastQueue> podcastHistory);

    /**
     * Get the playlist that contains this item.
     *
     * @return null if this item isn't a playlist.
     */
    default Playlist getPlaylist() {
        return null;
    }
}
