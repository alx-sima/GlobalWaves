package main.audio.files;

import fileio.input.SongInput;
import java.util.List;
import main.audio.Searchable;
import main.audio.collections.SongSource;
import main.audio.queues.Queue;
import main.audio.collections.RepeatMode;
import main.audio.queues.SongQueue;

public final class Song extends AudioFile implements Searchable, SongSource {

    private final String album;
    private final List<String> tags;
    private final String lyrics;
    private final String genre;
    private final int releaseYear;
    private final String artist;

    public Song(final Song song) {
        super(song.getName(), song.getDuration());
        album = song.album;
        tags = song.tags;
        lyrics = song.lyrics;
        genre = song.genre;
        releaseYear = song.releaseYear;
        artist = song.artist;
    }

    public Song(final SongInput input) {
        super(input.getName(), input.getDuration());
        album = input.getAlbum();
        tags = input.getTags();
        lyrics = input.getLyrics();
        genre = input.getGenre();
        releaseYear = input.getReleaseYear();
        artist = input.getArtist();
    }

    @Override
    public boolean matchFilter(final String filter, final String parameter) {
        return switch (filter) {
            case "name" -> getName().startsWith(parameter);
            case "album" -> album.equals(parameter);
            case "tags" -> tags.contains(parameter);
            case "lyrics" -> lyrics.contains(parameter) || lyrics.toLowerCase().contains(parameter)
                || lyrics.contains(parameter.toLowerCase());
            case "genre" -> genre.equalsIgnoreCase(parameter);
            case "releaseYear" -> {
                int referenceYear = Integer.parseInt(parameter.substring(1));
                if (parameter.startsWith("<")) {
                    yield releaseYear < referenceYear;
                }
                yield releaseYear > referenceYear;
            }
            case "artist" -> artist.equals(parameter);
            default -> false;
        };
    }

    @Override
    public Queue createQueue() {
        return new SongQueue(this, 1, false);
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
}
