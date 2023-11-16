package main.audio.collections;

import java.util.ArrayList;
import java.util.List;
import main.User;
import main.audio.Searchable;
import main.audio.files.Song;

public final class Playlist implements Searchable {

    private final String name;
    private final User user;
    private final boolean isPrivate = false;
    private final List<Song> songs = new ArrayList<>();
    private final int followers = 0;

    public Playlist(final String name, final User user) {
        this.name = name;
        this.user = user;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public int getFollowers() {
        return followers;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<Song> getContents() {
        return songs;
    }

    @Override
    public Playable createPlayable() {
        return new SongQueue(songs);
    }

    @Override
    public boolean matchFilter(final String filter, final String parameter) {
        return switch (filter) {
            case "name" -> name.startsWith(parameter);
            case "owner" -> user.getUsername().equals(parameter);
            default -> false;
        };
    }

    /**
     * Add the song to the playlist, or remove it if it was already present.
     *
     * @param song The song to be added (or removed).
     * @return true if the song was added after this operation.
     */
    public boolean addRemoveSong(final Song song) {
        if (songs.remove(song)) {
            return false;
        }

        songs.add(song);
        return true;
    }
}
