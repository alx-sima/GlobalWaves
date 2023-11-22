package main.audio.collections;

import static main.audio.queues.RepeatMode.NO_REPEAT;
import static main.audio.queues.RepeatMode.REPEAT_ALL;
import static main.audio.queues.RepeatMode.REPEAT_CURRENT;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import main.audio.Searchable;
import main.audio.files.Song;
import main.audio.queues.Queue;
import main.audio.queues.RepeatMode;
import main.audio.queues.SongQueue;
import main.program.User;

/**
 * A collection of songs, that can be played and followed (if public).
 */
public final class Playlist implements Searchable, SongSource {

    @Getter
    private final String name;
    @Getter
    private final User user;
    private final List<Song> songs = new ArrayList<>();
    @Getter
    private final int creationTimestamp;
    @Getter
    @Setter
    private boolean isPrivate = false;
    @Getter
    @Setter
    private int followers = 0;

    public Playlist(final String name, final User user, final int creationTimestamp) {
        this.name = name;
        this.user = user;
        this.creationTimestamp = creationTimestamp;
    }

    /**
     * Get the names of the songs of the playlist.
     */
    public List<String> getSongNames() {
        return songs.stream().map(Song::getName).toList();
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
    public boolean matchFilter(final String filter, final String parameter) {
        return switch (filter) {
            case "name" -> name.startsWith(parameter);
            case "owner" -> user.getUsername().equals(parameter);
            default -> false;
        };
    }

    @Override
    public Queue createQueue() {
        return new SongQueue(this, songs.size(), true);
    }

    @Override
    public Playlist getPlaylist() {
        return this;
    }

    @Override
    public int size() {
        return songs.size();
    }

    @Override
    public Song get(final int index) {
        if (index >= 0 && index < songs.size()) {
            return songs.get(index);
        }

        return null;
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
}
