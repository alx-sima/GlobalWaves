package main.audio.collections;

import fileio.input.LibraryInput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import main.audio.files.Song;

/**
 * The program's database of songs.
 */
@Getter
public final class Library {

    private final List<Song> songs;
    private final List<Podcast> podcasts;
    private final List<Playlist> publicPlaylists = new ArrayList<>();
    private final Map<String, Album> albums = new HashMap<>();

    public Library(final LibraryInput input) {
        songs = input.getSongs().stream().map(Song::new).toList();
        podcasts = input.getPodcasts().stream().map(Podcast::new).toList();
    }
}
