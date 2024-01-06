package main.entities.audio;

import java.util.Map;
import main.entities.Searchable;
import main.entities.audio.collections.Playlist;
import main.entities.audio.queues.PodcastQueue;
import main.entities.audio.queues.Queue;
import main.entities.users.User;
import main.program.Searchbar;

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
