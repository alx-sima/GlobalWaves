package main.audio.files;

import fileio.input.SongInput;
import main.audio.Searchable;

import java.util.List;
import main.audio.SearchableVisitor;
import main.audio.collections.RepeatMode;

public final class Song extends AudioFile implements Searchable {

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
    public List<Song> getContents() {
        return List.of(this);
    }

    @Override
    public boolean matchFilter(final String filter, final String parameter) {
        return switch (filter) {
            case "name" -> getName().startsWith(parameter);
            case "album" -> album.equals(parameter);
            case "tags" -> tags.contains(parameter);
            case "lyrics" -> lyrics.contains(parameter);
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
    public AudioFile getSongAt(final int timePassed) {
        if (timePassed <= getDuration()) {
            return this;
        }
        return null;
    }

    @Override
    public RepeatMode nextRepeatMode(final RepeatMode mode) {
        return switch (mode) {
            case NO_REPEAT -> RepeatMode.REPEAT_ONCE;
            case REPEAT_ONCE -> RepeatMode.REPEAT_INFINITE;
            case REPEAT_INFINITE -> RepeatMode.NO_REPEAT;
            default -> null;
        };
    }

    @Override
    public void accept(SearchableVisitor visitor) {
        visitor.visit(this);
    }
}
