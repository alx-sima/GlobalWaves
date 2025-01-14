package main.program.entities.audio.collections;

import fileio.input.SongInput;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import main.program.entities.audio.SearchableAudio;
import main.program.entities.audio.files.Song;
import main.program.entities.audio.queues.PodcastQueue;
import main.program.entities.audio.queues.Queue;
import main.program.entities.audio.queues.SongQueue;
import main.program.entities.audio.queues.repetition.PlaylistRepeatStrategy;
import main.program.entities.audio.queues.visitors.SongSourceVisitor;
import main.program.entities.users.User;
import main.program.entities.users.creators.Artist;

/**
 * An album, created by an artist, which contains a list of songs.
 */
@Getter
public final class Album implements SearchableAudio, SongSource {

    private final Artist owner;
    private final String name;
    private final int releaseYear;
    private final String description;
    private final List<Song> songs;

    public Album(final Artist owner, final String name, final int releaseYear,
        final String description, final List<SongInput> songs, final int creationTime) {
        this.owner = owner;
        this.name = name;
        this.releaseYear = releaseYear;
        this.description = description;
        this.songs = songs.stream().map(input -> new Song(input, this, owner, creationTime))
            .toList();
    }

    /**
     * Get the total number of likes this album has.
     */
    public int getLikes() {
        return songs.stream().map(Song::getLikes).reduce(0, Integer::sum);
    }

    @Override
    public boolean matchFilter(final String filter, final String parameter) {
        return switch (filter) {
            case "name" -> name.startsWith(parameter);
            case "owner" -> owner.getName().startsWith(parameter);
            case "description" -> description.startsWith(parameter);
            default -> false;
        };
    }

    @Override
    public Queue createQueue(final User user, final Map<String, PodcastQueue> podcastHistory) {
        return new SongQueue(user, this, new PlaylistRepeatStrategy());
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

    @Override
    public String toString() {
        return name;
    }
}
