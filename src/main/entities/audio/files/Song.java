package main.entities.audio.files;

import fileio.input.SongInput;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import main.entities.audio.SearchableAudio;
import main.entities.audio.collections.Album;
import main.entities.audio.collections.SongSource;
import main.entities.audio.queues.Queue;
import main.entities.audio.queues.RepeatMode;
import main.entities.audio.queues.SongQueue;
import main.entities.audio.queues.visitors.SongSourceVisitor;
import main.entities.users.User;
import main.entities.users.artist.Artist;

/**
 * A song, which can be searched or played.
 */
public final class Song extends AudioFile implements SearchableAudio, SongSource {

    @Getter
    private final int creationTime;
    @Getter
    private final Album album;
    private final List<String> tags;
    private final String lyrics;
    @Getter
    private final String genre;
    private final int releaseYear;
    @Getter
    private final Artist artist;
    @Getter
    @Setter
    private int likes = 0;
    @Getter
    private double totalEarned = 0.0d;

    public Song(final SongInput input, final Album album, final Artist artist,
        final int creationTime) {
        super(input.getName(), input.getDuration(), input.getArtist());
        this.creationTime = creationTime;
        this.album = album;
        this.artist = artist;
        tags = input.getTags();
        lyrics = input.getLyrics();
        genre = input.getGenre();
        releaseYear = input.getReleaseYear();
    }

    private boolean compareReleaseYear(final String parameter) {
        String yearString = parameter.substring(1);
        int referenceYear = Integer.parseInt(yearString);

        if (parameter.startsWith("<")) {
            return releaseYear < referenceYear;
        }
        return releaseYear > referenceYear;
    }

    @Override
    public boolean matchFilter(final String filter, final String parameter) {
        return switch (filter) {
            case "name" -> getName().toLowerCase().startsWith(parameter.toLowerCase());
            case "album" -> album.getName().equals(parameter);
            case "tags" -> tags.contains(parameter);
            case "lyrics" -> lyrics.toLowerCase().contains(parameter.toLowerCase());
            case "genre" -> genre.equalsIgnoreCase(parameter);
            case "releaseYear" -> compareReleaseYear(parameter);
            case "artist" -> artist.getName().equals(parameter);
            default -> false;
        };
    }

    @Override
    public Queue createQueue(final User user, final Map<String, Queue> playHistory) {
        return new SongQueue(user, this, 1);
    }

    @Override
    public int size() {
        return 1;
    }

    @Override
    public Song get(final int index) {
        if (index == 0) {
            return this;
        }

        return null;
    }

    @Override
    public RepeatMode getNextRepeatMode(final RepeatMode repeatMode) {
        return switch (repeatMode) {
            case NO_REPEAT -> RepeatMode.REPEAT_ONCE;
            case REPEAT_ONCE -> RepeatMode.REPEAT_INFINITE;
            case REPEAT_INFINITE -> RepeatMode.NO_REPEAT;
            default -> null;
        };
    }

    @Override
    public void accept(final SongSourceVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * Increase the revenue earned by this song.
     */
    private void addRevenue(final double revenue) {
        totalEarned += revenue;
    }

    @Override
    public String toString() {
        return name;
    }
}
