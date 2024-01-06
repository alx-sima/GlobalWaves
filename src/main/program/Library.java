package main.program;

import fileio.input.LibraryInput;
import fileio.input.PodcastInput;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import main.entities.audio.collections.Playlist;
import main.entities.audio.collections.Podcast;
import main.entities.audio.files.Song;
import main.entities.users.UserDatabase;
import main.entities.users.host.Host;

/**
 * The program's database of songs.
 */
@Getter
public final class Library {

    private static Library instance;

    @Getter
    private Song ad;
    @Setter
    private List<Song> songs;
    private List<Podcast> podcasts;
    private List<Playlist> publicPlaylists;

    private Library() {
    }

    /**
     * Get the instance of the library.
     */
    public static Library getInstance() {
        if (instance == null) {
            instance = new Library();
        }

        return instance;
    }

    /**
     * Initialize the library from the input.
     */
    public void initializeLibrary(final LibraryInput input) {
        songs = new ArrayList<>();
        podcasts = new ArrayList<>();

        if (input.getSongs().isEmpty()) {
            System.err.println("Ad not present.");
            return;
        }

        ad = new Song(input.getSongs().get(0), null, null, 0);

        for (PodcastInput podcastInput : input.getPodcasts()) {
            Host host = UserDatabase.getInstance().getOrAddHost(podcastInput.getOwner());
            podcasts.add(new Podcast(podcastInput, host));
        }

        publicPlaylists = new ArrayList<>();
    }
}
