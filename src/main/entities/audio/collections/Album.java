package main.entities.audio.collections;

import static main.entities.audio.queues.RepeatMode.NO_REPEAT;
import static main.entities.audio.queues.RepeatMode.REPEAT_ALL;
import static main.entities.audio.queues.RepeatMode.REPEAT_CURRENT;

import fileio.input.SongInput;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import main.entities.audio.SearchableAudio;
import main.entities.audio.files.Song;
import main.entities.audio.queues.Queue;
import main.entities.audio.queues.RepeatMode;
import main.entities.audio.queues.SongQueue;
import main.entities.audio.queues.visitors.SongQueueVisitor;

/**
 * An album, created by an artist, which contains a list of songs.
 */
public final class Album implements SearchableAudio, SongSource {

    @Getter
    private final String owner;
    @Getter
    private final String name;
    private final int releaseYear;
    private final String description;
    @Getter
    private final List<Song> songs;

    public Album(final String owner, final String name, final int releaseYear,
        final String description, final List<SongInput> songs) {
        this.owner = owner;
        this.name = name;
        this.releaseYear = releaseYear;
        this.description = description;
        this.songs = songs.stream().map(Song::new).toList();
    }

    @Override
    public boolean matchFilter(final String filter, final String parameter) {
        return switch (filter) {
            case "name" -> name.startsWith(parameter);
            case "owner" -> owner.startsWith(parameter);
            case "description" -> description.startsWith(parameter);
            default -> false;
        };
    }

    @Override
    public Queue createQueue(final Map<String, Queue> playHistory) {
        return new SongQueue(this, songs.size(), true);
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

    @Override
    public void accept(SongQueueVisitor visitor) {
        visitor.visit(this);
    }
}
