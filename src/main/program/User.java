package main.program;

import fileio.input.UserInput;
import lombok.Getter;
import main.audio.collections.Playlist;

import java.util.ArrayList;
import java.util.List;
import main.audio.files.AudioFile;
import main.audio.files.Song;

/**
 * A user of the application, with their own playlists and liked songs.
 */
public final class User {

    @Getter
    private final String username;
    private final int age;
    private final String city;
    @Getter
    private final Player player = new Player();
    @Getter
    private final List<Playlist> playlists = new ArrayList<>();
    @Getter
    private final List<AudioFile> likedSongs = new ArrayList<>();
    private final List<Playlist> followedPlaylists = new ArrayList<>();

    public User(final UserInput input) {
        username = input.getUsername();
        age = input.getAge();
        city = input.getCity();
    }

    /**
     * Get the user's playlist with the given `playlistId`.
     *
     * @param playlistId the index of the playlist.
     * @return null if the playlist does not exist.
     */
    public Playlist getPlaylist(final int playlistId) {
        if (playlistId >= playlists.size()) {
            return null;
        }

        return playlists.get(playlistId);
    }

    private Playlist getPlaylist(final String playListName) {
        for (Playlist playlist : playlists) {
            if (playlist.getName().equals(playListName)) {
                return playlist;
            }
        }

        return null;
    }

    /**
     * Create a playlist for this user. This fails if a playlist with the same name already exists.
     *
     * @param playListName the name of the playlist.
     * @param timestamp    when the playlist is created.
     * @return whether this operation succeeded or not.
     */
    public boolean createPlaylist(final String playListName, final int timestamp) {
        Program program = Program.getInstance();

        if (getPlaylist(playListName) != null) {
            return false;
        }

        Playlist playlist = new Playlist(playListName, this, timestamp);
        program.getLibrary().getPublicPlaylists().add(playlist);
        playlists.add(playlist);
        return true;
    }

    /**
     * Add the `song` to the liked list of this user. If it was already liked, remove it instead.
     *
     * @param song The song to be added/removed.
     * @return true if `song` was liked after the operation.
     */
    public boolean like(final Song song) {
        if (likedSongs.remove(song)) {
            song.setLikes(song.getLikes() - 1);
            return false;
        }

        likedSongs.add(song);
        song.setLikes(song.getLikes() + 1);
        return true;
    }

    /**
     * Follow or unfollow a playlist.
     *
     * @param playlist the playlist to be followed.
     * @return true if the playlist was followed after the operation.
     */
    public boolean follow(final Playlist playlist) {
        if (followedPlaylists.contains(playlist)) {
            followedPlaylists.remove(playlist);
            playlist.setFollowers(playlist.getFollowers() - 1);
            return false;
        }

        followedPlaylists.add(playlist);
        playlist.setFollowers(playlist.getFollowers() + 1);
        return true;
    }
}
