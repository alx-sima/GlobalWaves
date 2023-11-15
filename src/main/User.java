package main;

import fileio.input.UserInput;
import main.audio.collections.Playlist;

import java.util.ArrayList;
import java.util.List;
import main.audio.files.AudioFile;
import main.audio.files.Song;

public final class User {

    private final String username;
    private final int age;
    private final String city;
    private final List<Playlist> playlists = new ArrayList<>();
    private final List<AudioFile> likedSongs = new ArrayList<>();

    public User(final UserInput input) {
        username = input.getUsername();
        age = input.getAge();
        city = input.getCity();
    }

    public String getUsername() {
        return username;
    }

    public List<AudioFile> getLikedSongs() {
        return likedSongs;
    }

    public List<Playlist> getPlaylists() {
        return playlists;
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
        if (getPlaylist(playListName) != null) {
            return false;
        }

        playlists.add(new Playlist(playListName, this));
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
     * @param song
     * @param playlistId
     * @return true if the song was added after the operation.
     */
    public boolean addRemoveSongInPlaylist(final Song song, final int playlistId) {
        if (playlistId >= playlists.size()) {
            return false;
        }
        Playlist playlist = playlists.get(playlistId);

        return playlist.addRemoveSong(song);
    }
}
