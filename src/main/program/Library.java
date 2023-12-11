package main.program;

import fileio.input.LibraryInput;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import main.entities.audio.collections.Album;
import main.entities.audio.collections.Playlist;
import main.entities.audio.collections.Podcast;
import main.entities.audio.files.Song;
import main.entities.users.artist.Event;
import main.entities.users.artist.Merch;
import main.entities.users.host.Announcement;

/**
 * The program's database of songs.
 */
@Getter
public final class Library {

    private static Library instance;

    private List<Song> songs;
    private List<Podcast> podcasts;
    private List<Playlist> publicPlaylists;
    private List<Announcement> announcements;
    private List<Album> albums;
    private List<Event> events;
    private List<Merch> merch;

    private Library() {
    }

    public static Library getInstance() {
        if (instance == null) {
            instance = new Library();
        }

        return instance;
    }

    public void initializeLibrary(final LibraryInput input) {
        songs = input.getSongs().stream().map(Song::new).collect(Collectors.toList());
        podcasts = input.getPodcasts().stream().map(Podcast::new).collect(Collectors.toList());
        publicPlaylists = new ArrayList<>();
        announcements = new ArrayList<>();
        albums = new ArrayList<>();
        events = new ArrayList<>();
        merch = new ArrayList<>();
    }
}
