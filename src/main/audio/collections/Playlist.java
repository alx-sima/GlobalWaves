package main.audio.collections;

import static main.audio.collections.RepeatMode.NO_REPEAT;
import static main.audio.collections.RepeatMode.REPEAT_ALL;
import static main.audio.collections.RepeatMode.REPEAT_CURRENT;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import main.audio.Searchable;
import main.audio.files.Song;
import main.audio.queues.Queue;
import main.audio.queues.SongQueue;
import main.program.User;

public final class Playlist implements Searchable, SongSource {

    private final String name;
    @Getter
    private final User user;
    private final List<Song> songs = new ArrayList<>();
    @Getter
    @Setter
    private boolean isPrivate = false;
    @Getter
    @Setter
    private int followers = 0;

    public Playlist(final String name, final User user) {
        this.name = name;
        this.user = user;
    }

    @Override
    public int size() {
        return songs.size();
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

    @Override
    public Playlist getPlaylist() {
        return this;
    }

    /**
     * Get the names of the songs of the playlist.
     */
    public List<String> getSongNames() {
        return songs.stream().map(Song::getName).toList();
    }
}
