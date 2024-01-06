package main.entities.pages;

import static main.program.Program.MAX_RESULTS;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;
import main.entities.audio.collections.Playlist;
import main.entities.audio.files.Song;
import main.entities.users.User;

/**
 * A page that contains information about the user's top liked songs and followed playlists.
 */
public final class HomePage extends Page {

    public HomePage(final User user) {
        super(user);
    }

    private List<String> getLikedSongs(final User user) {
        Stream<Song> songs = user.getLikedSongs().stream()
            .sorted(Comparator.comparingInt(Song::getLikes).reversed());
        return songs.limit(MAX_RESULTS).map(Song::getName).toList();
    }

    private List<String> getFollowedPlaylists(final User user) {
        Stream<Playlist> playlists = user.getFollowedPlaylists().stream();
        return playlists.map(Playlist::getName).toList();
    }

    private List<String> getSongRecommendations(final User user) {
        Song song = user.getRecommendations().getSong();
        if (song == null) {
            return new ArrayList<>();
        }

        return new ArrayList<>(List.of(song.getName()));
    }

    private List<String> getPlaylistRecommendations(final User user) {
        Playlist playlist = user.getRecommendations().getPlaylist();
        if (playlist == null) {
            return new ArrayList<>();
        }

        return new ArrayList<>(List.of(playlist.getName()));
    }

    @Override
    public String printPage() {
        return "Liked songs:\n\t" + getLikedSongs(user) + "\n\nFollowed playlists:\n\t"
            + getFollowedPlaylists(user) + "\n\nSong recommendations:\n\t" + getSongRecommendations(
            user) + "\n\nPlaylists recommendations:\n\t" + getPlaylistRecommendations(user);
    }
}
