package main.entities.audio;

import main.entities.Searchable;
import main.entities.audio.collections.Playlist;
import main.entities.audio.queues.Queue;
import main.program.Program;
import main.program.Searchbar;
import main.entities.users.User;

/**
 * An entry that can be searched and then loaded into the player.
 */
public interface SearchableAudio extends Searchable {

    @Override
    default void selectResultBy(User user) {
        Searchbar searchbar = Program.getInstance().getSearchbar();
        searchbar.selectAudioSource(this);
    }

    /**
     * Create a queue to be played from this search result.
     *
     * @return a queue based on the internal type.
     */
    Queue createQueue();

    /**
     * Get the playlist that contains this item.
     *
     * @return null if this item isn't a playlist.
     */
    default Playlist getPlaylist() {
        return null;
    }
}
