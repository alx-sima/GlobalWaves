package main.program;

import fileio.input.UserInput;
import lombok.Getter;
import main.audio.collections.Playlist;

import java.util.ArrayList;
import java.util.List;
import main.audio.files.AudioFile;
import main.audio.files.Song;

public final class User {

    @Getter
    private final String username;
    private final int age;
    private final String city;
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
     * @param playListName The name of the playlist.
     * @return Whether this operation succeeded or not.
     */
    public boolean createPlaylist(final String playListName) {
        Program instance = Program.getInstance();

        if (getPlaylist(playListName) != null) {
            return false;
        }

        Playlist playlist = new Playlist(playListName, this);
        instance.getPublicPlaylists().add(playlist);
        playlists.add(playlist);
        return true;
    }

    /**
     * Add the `file` to the liked list of this user. If it was already liked, remove it instead.
     *
     * @param file The file to be added/removed.
     * @return true if `file` wasn't liked before.
     */
    public boolean like(final AudioFile file) {
        if (likedSongs.remove(file)) {
            return false;
        }

        likedSongs.add(file);
        return true;
    }

    /**
     * Try to add or remove the song in the playlist.
     *
     * @param song       the song to be added.
     * @param playlistId the playlist's id.
     * @return true if the song was added after the operation.
     */
    public boolean addRemoveSongInPlaylist(final Song song, final int playlistId)
        throws IndexOutOfBoundsException {
        if (playlistId >= playlists.size()) {
            throw new IndexOutOfBoundsException();
        }
        Playlist playlist = playlists.get(playlistId);

        return playlist.addRemoveSong(song);
    }

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
