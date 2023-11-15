package main;

import fileio.input.UserInput;
import main.audio.collections.Playlist;

import java.util.ArrayList;
import java.util.List;

public final class User {

    private final String username;
    private final int age;
    private final String city;
    private final List<Playlist> playlists = new ArrayList<>();

    public User(final UserInput input) {
        username = input.getUsername();
        age = input.getAge();
        city = input.getCity();
    }

    public String getUsername() {
        return username;
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
}
