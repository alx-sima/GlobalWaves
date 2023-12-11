package main.entities.pages;

import java.util.List;
import java.util.stream.Stream;
import main.entities.audio.collections.Playlist;
import main.entities.audio.files.Song;
import main.entities.users.User;

public final class HomePage implements Page {

    /**
     * The maximum number of results to be displayed.
     */
    private static final int MAX_RESULTS = 5;

    private List<String> getLikedSongs(final User user) {
        Stream<Song> songs = user.getLikedSongs().stream();
        return songs.limit(MAX_RESULTS).map(Song::getName).toList();
    }

    private List<String> getFollowedPlaylists(final User user) {
        Stream<Playlist> playlists = user.getFollowedPlaylists().stream();
        return playlists.map(Playlist::getName).toList();
    }

    @Override
    public String printPageOfUser(final User user) {
        return "Liked songs:\n\t" + getLikedSongs(user) + "\n\nFollowed playlists:\n\t"
            + getFollowedPlaylists(user);
    }
}
