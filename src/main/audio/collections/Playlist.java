package main.audio.collections;

import static main.audio.collections.RepeatMode.NO_REPEAT;
import static main.audio.collections.RepeatMode.REPEAT_ALL;
import static main.audio.collections.RepeatMode.REPEAT_CURRENT;

import java.util.ArrayList;
import java.util.List;
import main.audio.files.AudioFile;
import main.program.User;
import main.audio.Searchable;
import main.audio.queues.Queue;
import main.audio.queues.SongQueue;
import main.audio.files.Song;

public final class Playlist implements Searchable, SongSource {

    private final String name;
    private final User user;
    private final boolean isPrivate = false;
    private final List<Song> songs = new ArrayList<>();
    private final int followers = 0;

    public Playlist(final String name, final User user) {
        this.name = name;
        this.user = user;
    }

    @Override
    public int size() {
        return songs.size();
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
    public Queue createQueue() {
        return new SongQueue(this, songs.size(), true);
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

    @Override
    public RepeatMode getNextRepeatMode(final RepeatMode repeatMode) {
        return switch (repeatMode) {
            case NO_REPEAT -> REPEAT_ALL;
            case REPEAT_ALL -> REPEAT_CURRENT;
            case REPEAT_CURRENT -> NO_REPEAT;
            default -> null;
        };
    }

    @Override
    public Song get(final int index) {
        if (index < songs.size()) {
            return songs.get(index);
        }

        return null;
    }

    /**
     * Get the names of the songs of the playlist.
     */
    public List<String> getSongNames() {
        return songs.stream().map(Song::getName).toList();
    }
}
