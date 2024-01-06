package main.entities.users;

import fileio.input.UserInput;
import fileio.output.wrapped.UserWrapped;
import fileio.output.wrapped.WrappedOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import lombok.Getter;
import lombok.Setter;
import main.entities.audio.collections.Playlist;
import main.entities.audio.files.Episode;
import main.entities.audio.files.Song;
import main.entities.pages.HomePage;
import main.entities.pages.Page;
import main.program.Library;
import main.program.Player;
import main.program.Searchbar;

/**
 * A user of the application, with their own playlists and liked songs.
 */
public class User {

    private static final double PREMIUM_CREDIT = 1e6;

    @Getter
    protected final String username;
    private final int age;
    private final String city;
    @Getter
    private final Player player = new Player();
    @Getter
    private final Searchbar searchbar = new Searchbar();
    @Getter
    private final List<Playlist> playlists = new ArrayList<>();
    @Getter
    private final List<Song> likedSongs = new ArrayList<>();
    @Getter
    private final List<Playlist> followedPlaylists = new ArrayList<>();
    private final Wrapped wrapped = new Wrapped();
    @Getter
    @Setter
    private Page currentPage = new HomePage(this);
    @Getter
    private boolean isOnline = true;
    private final Map<Song, Integer> freeListenedSongs = new HashMap<>();
    private final Map<Song, Integer> premiumListenedSongs = new HashMap<>();
    @Getter
    @Setter
    private boolean isPremium = false;

    public User(final String username, final int age, final String city) {
        this.username = username;
        this.age = age;
        this.city = city;
    }

    public User(final UserInput input) {
        username = input.getUsername();
        age = input.getAge();
        city = input.getCity();
    }

    /**
     * Set the user's online state.
     *
     * @param online    the new online state.
     * @param timestamp the timestamp of 'now'.
     */
    public void setOnline(final boolean online, final int timestamp) {
        isOnline = online;
        player.setOnline(isOnline, timestamp);
    }

    /**
     * Get the user's playlist with the given `playlistId`.
     *
     * @param playlistId the index of the playlist.
     * @return null if the playlist does not exist.
     */
    public Playlist getPlaylist(final int playlistId) {
        if (playlistId >= playlists.size()) {
            return null;
        }

        return playlists.get(playlistId);
    }

    private Playlist getPlaylist(final String playListName) {
        for (Playlist playlist : playlists) {
            if (playlist.getName().equals(playListName)) {
                return playlist;
            }
        }

        return null;
    }

    /**
     * Create a playlist for this user. This fails if a playlist with the same name already exists.
     *
     * @param playListName the name of the playlist.
     * @param timestamp    when the playlist is created.
     * @return whether this operation succeeded or not.
     */
    public boolean createPlaylist(final String playListName, final int timestamp) {
        Library library = Library.getInstance();

        if (getPlaylist(playListName) != null) {
            return false;
        }

        Playlist playlist = new Playlist(playListName, this, timestamp);
        library.getPublicPlaylists().add(playlist);
        playlists.add(playlist);
        return true;
    }

    /**
     * Add the `song` to the liked list of this user. If it was already liked, remove it instead.
     *
     * @param song the song to be added/removed.
     * @return true if `song` was liked after the operation.
     */
    public boolean like(final Song song) {
        if (likedSongs.remove(song)) {
            song.setLikes(song.getLikes() - 1);
            return false;
        }

        likedSongs.add(song);
        song.setLikes(song.getLikes() + 1);
        return true;
    }

    /**
     * Follow or unfollow a playlist.
     *
     * @param playlist the playlist to be followed.
     * @return true if the playlist was followed after the operation.
     */
    public boolean follow(final Playlist playlist) {
        if (followedPlaylists.contains(playlist)) {
            followedPlaylists.remove(playlist);
            playlist.setFollowers(playlist.getFollowers() - 1);
            return false;
        }

        followedPlaylists.add(playlist);
        playlist.setFollowers(playlist.getFollowers() + 1);
        return true;
    }

    /**
     * Record a listen of a song.
     */
    public void addListen(final Song song) {
        CreatorWrapped.increment(wrapped.topSongs, song.getName());
        CreatorWrapped.increment(wrapped.topArtists, song.getArtist().getName());
        CreatorWrapped.increment(wrapped.topGenres, song.getGenre());
        CreatorWrapped.increment(wrapped.topAlbums, song.getAlbum().getName());

        if (isPremium) {
            CreatorWrapped.increment(premiumListenedSongs, song);
        } else {
            CreatorWrapped.increment(freeListenedSongs, song);
        }
    }

    /**
     * Record a listen of an episode.
     */
    public void addListen(final Episode episode) {
        CreatorWrapped.increment(wrapped.topEpisodes, episode.getName());
    }

    /**
     * Split the premium credit to the songs listened while the subscription was active.
     *
     * @param timestamp when the split happens.
     */
    public void splitPremiumMoney(final int timestamp) {
        player.updateTime(timestamp);

        int totalSongs = premiumListenedSongs.values().stream().reduce(0, Integer::sum);

        for (Entry<Song, Integer> mapEntry : premiumListenedSongs.entrySet()) {
            Song song = mapEntry.getKey();
            int songListens = mapEntry.getValue();

            song.addRevenue(PREMIUM_CREDIT / totalSongs * songListens);
        }

        premiumListenedSongs.clear();
    }

    /**
     * Get the user's *wrapped*.
     */
    public WrappedOutput getWrapped() {
        return new UserWrapped(wrapped);
    }

    /**
     * Print the user's name.
     */
    @Override
    public String toString() {
        return username;
    }

    @Getter
    public static final class Wrapped implements WrappedStats {

        private final Map<String, Integer> topArtists = new HashMap<>();
        private final Map<String, Integer> topGenres = new HashMap<>();
        private final Map<String, Integer> topSongs = new HashMap<>();
        private final Map<String, Integer> topAlbums = new HashMap<>();
        private final Map<String, Integer> topEpisodes = new HashMap<>();
    }
}
