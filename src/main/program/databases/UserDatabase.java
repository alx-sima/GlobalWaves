package main.program.databases;

import fileio.input.LibraryInput;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Getter;
import main.program.entities.audio.collections.Album;
import main.program.entities.users.User;
import main.program.entities.users.creators.Artist;
import main.program.entities.users.creators.Host;

/**
 * A database that contains all the users, hosts and artists.
 */
@Getter
public final class UserDatabase {

    private static UserDatabase instance;

    private List<User> users;
    private List<Artist> artists;
    private List<Host> hosts;
    private Set<Artist> monetizedArtists;

    /**
     * Get the instance of the database.
     */
    public static UserDatabase getInstance() {
        if (instance == null) {
            instance = new UserDatabase();
        }

        return instance;
    }

    /**
     * Initialize the database with the given input.
     */
    public void initializeDatabase(final LibraryInput input) {
        users = input.getUsers().stream().map(User::new).collect(Collectors.toList());
        artists = new ArrayList<>();
        monetizedArtists = new HashSet<>();
        hosts = new ArrayList<>();
    }

    /**
     * Add a new user to the database.
     */
    public void addUser(final String type, final String username, final int age,
        final String city) {
        switch (type) {
            case "user" -> users.add(new User(username, age, city));
            case "artist" -> artists.add(new Artist(username, age, city));
            case "host" -> hosts.add(new Host(username, age, city));
            default -> System.err.println("Invalid user type: " + type);
        }
    }

    /**
     * Remove a user from the database.
     *
     * @param target the username of the user.
     */
    public void removeUser(final String target) {
        users.removeIf(user -> user.getUsername().equals(target));
        artists.removeIf(artist -> artist.getUsername().equals(target));
        monetizedArtists.removeIf(artist -> artist.getUsername().equals(target));
        hosts.removeIf(host -> host.getUsername().equals(target));
    }

    /**
     * Get all the users, hosts and artists from the database.
     */
    public Stream<User> getAllUsers() {
        return Stream.concat(Stream.concat(users.stream(), artists.stream()), hosts.stream());
    }

    /**
     * Get a user from the database.
     *
     * @param username the searched username.
     */
    public User getUser(final String username) {
        return getAllUsers().filter(user -> user.getUsername().equals(username)).findFirst()
            .orElse(null);
    }

    /**
     * Check if the user exists.
     *
     * @param username the searched username.
     */
    public boolean existsUser(final String username) {
        return getUser(username) != null;
    }

    /**
     * Get all the albums from all the artists.
     */
    public List<Album> getAlbums() {
        return artists.stream().flatMap(artist -> artist.getAlbums().stream()).toList();
    }
}
