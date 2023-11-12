package main.audio.files;

import fileio.input.SongInput;

import java.util.List;
import java.util.Map;

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

    private boolean matchFilter(String filter, String parameter) {
        switch (filter) {
            case "name":
                return name.startsWith(parameter);
            default:
                return false;
        }
    }

    /**
     * Check if this song matches the search filters.
     *
     * @param filters a map of filters and their parameters.
     * @return if this song matches.
     */
    public boolean matches(Map<String, String> filters) {
        for (Map.Entry<String, String> filter : filters.entrySet()) {
            if (!matchFilter(filter.getKey(), filter.getValue())) {
                return false;
            }
        }

        return true;
    }
}
