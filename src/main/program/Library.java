package main.program;

import fileio.input.LibraryInput;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import main.entities.audio.collections.Album;
import main.entities.audio.collections.Playlist;
import main.entities.audio.collections.Podcast;
import main.entities.audio.files.Song;
import main.entities.users.artist.Event;
import main.entities.users.artist.Merch;

/**
 * The program's database of songs.
 */
@Getter
public final class Library {

    private final List<Song> songs;
    private final List<Podcast> podcasts;
    private final List<Playlist> publicPlaylists = new ArrayList<>();
    private final List<Album> albums = new ArrayList<>();
    private final List<Event> events = new ArrayList<>();
    private final List<Merch> merch = new ArrayList<>();

    public Library(final LibraryInput input) {
        songs = input.getSongs().stream().map(Song::new).toList();
        podcasts = input.getPodcasts().stream().map(Podcast::new).toList();
    }
}
