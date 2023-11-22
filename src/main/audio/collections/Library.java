package main.audio.collections;

import fileio.input.LibraryInput;
import java.util.ArrayList;
import java.util.List;
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

    public Library(final LibraryInput input) {
        songs = input.getSongs().stream().map(Song::new).toList();
        podcasts = input.getPodcasts().stream().map(Podcast::new).toList();
    }
}
