package main.entities.pages;

import java.util.List;
import java.util.stream.Stream;
import main.entities.audio.collections.Playlist;
import main.entities.audio.files.Song;
import main.entities.users.User;

/**
 * A page that contains information about the user's liked songs and followed playlists.
 */
public final class LikedContentPage extends Page {

    public LikedContentPage(final User user) {
        super(user);
    }

    private List<String> getLikedSongs(final User user) {
        Stream<Song> songs = user.getLikedSongs().stream();
        return songs.map(song -> song.getName() + " - " + song.getArtist()).toList();
    }

    private List<String> getFollowedPlaylists(final User user) {
        Stream<Playlist> playlists = user.getFollowedPlaylists().stream();
        return playlists.map(
            playlist -> playlist.getName() + " - " + playlist.getUser().getUsername()).toList();
    }

    @Override
    public String printPage() {
        return "Liked songs:\n\t" + getLikedSongs(user) + "\n\nFollowed playlists:\n\t"
            + getFollowedPlaylists(user);
    }

}
