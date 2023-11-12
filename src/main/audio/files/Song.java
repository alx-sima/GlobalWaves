package main.audio.files;

import fileio.input.SongInput;
import main.audio.Searchable;

import java.util.List;

public class Song extends AudioFile implements Searchable {
    private final String album;
    private final List<String> tags;
    private final String lyrics;
    private final String genre;
    private final int releaseYear;
    private final String artist;

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
    public boolean matchFilter(String filter, String parameter) {
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
}
