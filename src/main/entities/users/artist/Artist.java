package main.entities.users.artist;

import fileio.output.wrapped.ArtistWrapped;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import main.entities.Searchable;
import main.entities.audio.collections.Album;
import main.entities.audio.files.Song;
import main.entities.pages.ArtistPage;
import main.entities.users.CreatorWrapped;
import main.entities.users.User;
import main.entities.users.UserDatabase;

/**
 * An artist, that can add albums, events, and merch.
 */
public final class Artist extends User implements Searchable {

    @Getter
    private final List<Album> albums = new ArrayList<>();
    @Getter
    private final List<Event> events = new ArrayList<>();
    @Getter
    private final List<Merch> merch = new ArrayList<>();
    private final Wrapped wrapped = new Wrapped();

    public Artist(final String username, final int age, final String city) {
        super(username, age, city);
    }

    @Override
    public boolean matchFilter(final String filter, final String parameter) {
        if (filter.equals("name")) {
            return username.startsWith(parameter);
        }
        return false;
    }

    @Override
    public String getName() {
        return username;
    }

    @Override
    public String selectResultBy(final User user) {
        user.setCurrentPage(new ArtistPage(this));
        return username + "'s page";
    }

    /**
     * Record a listen by `listener` to the `song`.
     */
    public void addListen(final User listener, final Song song) {
        // If a song by the artist is listened, track the artist for monetization.
        UserDatabase.getInstance().getMonetizedArtists().add(this);

        wrapped.addListen(listener, song);
        listener.addListen(song);
    }

    @Override
    public ArtistWrapped getWrapped() {
        return new ArtistWrapped(wrapped);
    }

    @Getter
    public static final class Wrapped implements CreatorWrapped<Song> {

        private final Map<String, Integer> topAlbums;
        private final Map<String, Integer> topSongs;
        private final Map<String, Integer> topFans;
        private final int listeners;

        private Wrapped() {
            topAlbums = new HashMap<>();
            topSongs = new HashMap<>();
            topFans = new HashMap<>();
            listeners = 0;
        }

        @Override
        public void addListen(final User listener, final Song song) {
            CreatorWrapped.increment(topFans, listener.getUsername());
            CreatorWrapped.increment(topSongs, song.getName());
            CreatorWrapped.increment(topAlbums, song.getAlbum().getName());
        }
    }
}
