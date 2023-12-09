package main.entities.audio.files;

import fileio.input.SongInput;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import main.entities.audio.SearchableAudio;
import main.entities.audio.collections.SongSource;
import main.entities.audio.queues.Queue;
import main.entities.audio.queues.RepeatMode;
import main.entities.audio.queues.SongQueue;

/**
 * A song, which can be searched or played.
 */
public final class Song extends AudioFile implements SearchableAudio, SongSource {

    private final String album;
    private final List<String> tags;
    private final String lyrics;
    private final String genre;
    private final int releaseYear;
    @Getter
    private final String artist;
    @Getter
    @Setter
    private int likes = 0;

    public Song(final Song song) {
        super(song.getName(), song.getDuration(), song.artist);
        album = song.album;
        tags = song.tags;
        lyrics = song.lyrics;
        genre = song.genre;
        releaseYear = song.releaseYear;
        artist = song.artist;
        likes = song.likes;
    }

    public Song(final SongInput input) {
        super(input.getName(), input.getDuration(), input.getArtist());
        album = input.getAlbum();
        tags = input.getTags();
        lyrics = input.getLyrics();
        genre = input.getGenre();
        releaseYear = input.getReleaseYear();
        artist = input.getArtist();
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
            case "name" -> getName().startsWith(parameter);
            case "album" -> album.equals(parameter);
            case "tags" -> tags.contains(parameter);
            case "lyrics" -> lyrics.toLowerCase().contains(parameter.toLowerCase());
            case "genre" -> genre.equalsIgnoreCase(parameter);
            case "releaseYear" -> compareReleaseYear(parameter);
            case "artist" -> artist.equals(parameter);
            default -> false;
        };
    }

    @Override
    public Queue createQueue() {
        return new SongQueue(this, 1, false);
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
}
