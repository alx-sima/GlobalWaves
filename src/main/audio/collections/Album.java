package main.audio.collections;

import fileio.input.SongInput;
import java.util.List;
import lombok.Getter;
import main.audio.Searchable;
import main.audio.files.Song;
import main.audio.queues.Queue;
import main.audio.queues.SongQueue;

/**
 * An album, created by an artist, which contains a list of songs.
 */
public final class Album implements Searchable {

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
            case "name" -> name.startsWith(filter);
            case "owner" -> owner.startsWith(filter);
            case "description" -> description.startsWith(filter);
            default -> false;
        };
    }

    @Override
    public Queue createQueue() {
        // TODO
        return null;
    }
}
