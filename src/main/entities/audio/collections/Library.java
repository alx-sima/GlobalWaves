package main.entities.audio.collections;

import fileio.input.LibraryInput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import main.entities.users.artist.Event;
import main.entities.users.artist.Merch;
import main.entities.audio.files.Song;

/**
 * The program's database of songs.
 */
@Getter
public final class Library {

    private final List<Song> songs;
    private final List<Podcast> podcasts;
    private final List<Playlist> publicPlaylists = new ArrayList<>();
    private final Map<String, Album> albums = new HashMap<>();
    private final Map<String, Event> events = new HashMap<>();
    private final Map<String, Merch> merch = new HashMap<>();

    public Library(final LibraryInput input) {
        songs = input.getSongs().stream().map(Song::new).toList();
        podcasts = input.getPodcasts().stream().map(Podcast::new).toList();
    }
}
