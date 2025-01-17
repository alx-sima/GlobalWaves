package main.program.entities.audio.collections;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import main.program.entities.audio.SearchableAudio;
import main.program.entities.audio.files.Song;
import main.program.entities.audio.queues.PodcastQueue;
import main.program.entities.audio.queues.Queue;
import main.program.entities.audio.queues.SongQueue;
import main.program.entities.audio.queues.repetition.PlaylistRepeatStrategy;
import main.program.entities.audio.queues.visitors.SongSourceVisitor;
import main.program.entities.users.User;
import main.program.entities.users.interactions.notifications.Notifier;

/**
 * A collection of songs, that can be played and followed (if public).
 */
@Getter
public final class Playlist implements SearchableAudio, SongSource {

    private final String name;
    private final User user;
    private final List<Song> songs;
    private final int creationTimestamp;
    @Setter
    private boolean isPrivate = false;
    @Setter
    private int followers = 0;
    @Getter
    private final Notifier notifier = new Notifier();

    public Playlist(final String name, final User user, final int creationTimestamp,
        final List<Song> songs) {
        this.name = name;
        this.user = user;
        this.creationTimestamp = creationTimestamp;
        this.songs = songs;
    }

    public Playlist(final String name, final User user, final int creationTimestamp) {
        this(name, user, creationTimestamp, new ArrayList<>());
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
    public Queue createQueue(final User listener, final Map<String, PodcastQueue> podcastHistory) {
        return new SongQueue(listener, this, new PlaylistRepeatStrategy());
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
    public void accept(final SongSourceVisitor visitor) {
        visitor.visit(this);
    }
}
