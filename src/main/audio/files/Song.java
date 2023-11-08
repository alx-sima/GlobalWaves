package main.audio.files;

import fileio.input.SongInput;

import java.util.List;

public class Song extends AudioFile {
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
}
