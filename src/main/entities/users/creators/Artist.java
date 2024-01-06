package main.entities.users.creators;

import fileio.output.wrapped.ArtistWrapped;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import main.entities.audio.collections.Album;
import main.entities.audio.files.Song;
import main.entities.pages.ArtistPage;
import main.entities.users.User;
import main.entities.users.UserDatabase;
import main.entities.users.creators.content.Event;
import main.entities.users.creators.content.Merch;
import main.program.Library;
import main.program.notifications.Notification;
import main.program.notifications.Notifier;

/**
 * An artist, that can add albums, events, and merch.
 */
public final class Artist extends Creator {

    @Getter
    private final List<Album> albums = new ArrayList<>();
    @Getter
    private final List<Event> events = new ArrayList<>();
    @Getter
    private final List<Merch> merch = new ArrayList<>();
    private final Wrapped wrapped = new Wrapped();
    @Getter
    private final Notifier notifier = new Notifier();

    public Artist(final String username, final int age, final String city) {
        super(username, age, city);
    }

    @Override
    public String selectResultBy(final User user) {
        user.setCurrentPage(new ArtistPage(this));
        return username + "'s page";
    }

    /**
     * Add a new album to the artist.
     *
     * @param album the new album.
     */
    public void addAlbum(final Album album) {
        Library.getInstance().getSongs().addAll(album.getSongs());
        albums.add(album);

        notifier.updateSubscribers(
            new Notification("New Album", "New Album from " + username + "."));
    }

    /**
     * Add a new event to the artist.
     */
    public void addEvent(final Event event) {
        events.add(event);

        notifier.updateSubscribers(
            new Notification("New Event", "New Event from " + username + "."));
    }

    /**
     * Add a new merch to the artist.
     */
    @Override
    public void addMerch(final Merch merchandise) {
        merch.add(merchandise);

        notifier.updateSubscribers(
            new Notification("New Merchandise", "New Merchandise from " + username + "."));
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

    @Override
    public boolean buyMerch(final User buyer, final String merchName) {
        Merch selectedMerch = merch.stream().filter(m -> m.getName().equals(merchName)).findAny()
            .orElse(null);
        if (selectedMerch == null) {
            return false;
        }

        selectedMerch.buyMerch();
        buyer.addMerch(selectedMerch);

        // Track this artist as monetized.
        UserDatabase.getInstance().getMonetizedArtists().add(this);

        return true;
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
