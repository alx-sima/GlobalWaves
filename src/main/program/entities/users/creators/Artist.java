package main.program.entities.users.creators;

import fileio.output.wrapped.ArtistWrapped;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import lombok.Getter;
import main.program.databases.Library;
import main.program.databases.UserDatabase;
import main.program.entities.audio.collections.Album;
import main.program.entities.audio.files.Song;
import main.program.entities.users.User;
import main.program.entities.users.creators.content.Event;
import main.program.entities.users.creators.content.Merch;
import main.program.entities.users.interactions.notifications.Notification;
import main.program.entities.users.interactions.notifications.Notifier;
import main.program.entities.users.interactions.pages.ArtistPage;

/**
 * An artist, that can add albums, events, and merch.
 */
@Getter
public final class Artist extends Creator {

    private final List<Album> albums = new ArrayList<>();
    private final List<Event> events = new ArrayList<>();
    private final List<Merch> merch = new ArrayList<>();

    private final Notifier notifier = new Notifier();
    private final Stats stats = new Stats();

    public Artist(final String username, final int age, final String city) {
        super(username, age, city);
    }

    @Override
    public String selectResultBy(final User user) {
        user.setCurrentPage(new ArtistPage(this));
        return username + "'s page";
    }

    public Stream<Song> getAllSongs() {
        return albums.stream().flatMap(album -> album.getSongs().stream());
    }

    /**
     * Get the total number of likes this artist has.
     */
    public int getLikes() {
        return albums.stream().map(Album::getLikes).reduce(0, Integer::sum);
    }

    public double getTotalRevenue() {
        return merch.stream().map(Merch::getTotalEarned).reduce(0.0d, Double::sum)
            + stats.songRevenue.values().stream().reduce(0.0d, Double::sum);
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

    @Override
    public ArtistWrapped getWrapped() {
        return new ArtistWrapped(this);
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

    /**
     * Get the artist's listeners.
     *
     * @return a mapping between the listener and the number of listens to this artist.
     */
    public Map<User, Integer> getListeners() {
        return stats.listensByUser;
    }

    public void addSongListen(final Song song, final User listener) {
        stats.albumListens.merge(song.getAlbum().getName(), 1, Integer::sum);
        stats.songListens.merge(song.getName(), 1, Integer::sum);
        stats.listensByUser.merge(listener, 1, Integer::sum);
    }

    public void addSongRevenue(final Song song, final double revenue) {
        stats.songRevenue.merge(song.getName(), revenue, Double::sum);
    }


    @Getter
    public static final class Stats {

        private final Map<String, Integer> albumListens = new HashMap<>();
        private final Map<User, Integer> listensByUser = new HashMap<>();

        private final Map<String, Integer> songListens = new HashMap<>();
        private final Map<String, Double> songRevenue = new HashMap<>();
    }
}
