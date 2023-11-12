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
        switch (filter) {
            case "name":
                return getName().startsWith(parameter);
            default:
                return false;
        }
    }
}
