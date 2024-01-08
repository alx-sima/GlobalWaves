package main.program.entities.users.creators;

import fileio.output.wrapped.ArtistWrapped;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;
import lombok.Getter;
import main.program.entities.audio.collections.Album;
import main.program.entities.audio.files.Song;
import main.program.entities.users.interactions.wrapped.CreatorWrapped;
import main.program.entities.users.interactions.pages.ArtistPage;
import main.program.entities.users.User;
import main.program.databases.UserDatabase;
import main.program.entities.users.creators.content.Event;
import main.program.entities.users.creators.content.Merch;
import main.program.databases.Library;
import main.program.entities.users.interactions.notifications.Notification;
import main.program.entities.users.interactions.notifications.Notifier;

/**
 * An artist, that can add albums, events, and merch.
 */
@Getter
public final class Artist extends Creator {

    private final List<Album> albums = new ArrayList<>();
    private final List<Event> events = new ArrayList<>();
    private final List<Merch> merch = new ArrayList<>();
    private final Notifier notifier = new Notifier();

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
            + getAllSongs().map(Song::getTotalEarned).reduce(0.0d, Double::sum);
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
        Map<User, Integer> fans = new HashMap<>();
        Iterator<Entry<User, Integer>> iterator = getAllSongs().flatMap(
            song -> song.getListeners().entrySet().stream()).iterator();

        while (iterator.hasNext()) {
            Entry<User, Integer> entry = iterator.next();
            CreatorWrapped.add(fans, entry.getKey(), entry.getValue());
        }
        return fans;
    }


    @Getter
    public static final class Stats {

        private final Map<String, Integer> albumsMap = new HashMap<>();
        private final Map<String, Integer> songsMap = new HashMap<>();
        private final Map<String, Integer> fansMap = new HashMap<>();

        public Stats(final Artist artist) {
            Iterator<Song> songs = artist.getAllSongs().iterator();

            while (songs.hasNext()) {
                Song song = songs.next();

                int totalListens = song.getNumberOfListens();
                if (totalListens == 0) {
                    continue;
                }

                CreatorWrapped.add(albumsMap, song.getAlbum().getName(), totalListens);
                CreatorWrapped.add(songsMap, song.getName(), totalListens);

                for (Entry<User, Integer> entry : song.getListeners().entrySet()) {
                    CreatorWrapped.add(fansMap, entry.getKey().toString(), entry.getValue());
                }
            }
        }
    }
}
