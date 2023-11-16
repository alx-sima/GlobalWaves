package main.audio.files;

import fileio.input.SongInput;
import java.util.List;
import main.audio.Searchable;
import main.audio.collections.Playable;
import main.audio.collections.SongQueue;

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
            case "lyrics" -> lyrics.toLowerCase().contains(parameter);
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
    public Playable createPlayable() {
        return new SongQueue(List.of(this));
    }

}
