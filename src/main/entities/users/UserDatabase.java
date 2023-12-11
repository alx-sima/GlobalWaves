package main.entities.users;

import fileio.input.LibraryInput;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Getter;
import main.entities.users.artist.Artist;
import main.entities.users.host.Host;

@Getter
public final class UserDatabase {

    private static UserDatabase instance;

    private List<User> users;
    private List<Artist> artists;
    private List<Host> hosts;

    public static UserDatabase getInstance() {
        if (instance == null) {
            instance = new UserDatabase();
        }

        return instance;
    }

    /**
     * Add a new user to the database.
     */
    public void addUser(final String type, final String username, final int age,
        final String city) {
        switch (type) {
            case "user" -> users.add(new User(type, username, age, city));
            case "artist" -> artists.add(new Artist(type, username, age, city));
            case "host" -> hosts.add(new Host(type, username, age, city));
        }
    }

    /**
     * Get a user from the database.
     *
     * @param username the searched username.
     */
    public User getUser(final String username) {
        return Stream.concat(Stream.concat(users.stream(), artists.stream()), hosts.stream())
            .filter(user -> user.getUsername().equals(username)).findFirst().orElse(null);
    }

    /**
     * Check if the user exists.
     *
     * @param username the searched username.
     */
    public boolean existsUser(final String username) {
        return getUser(username) != null;
    }

    public Stream<User> getAllUsers() {
        return Stream.concat(Stream.concat(users.stream(), artists.stream()), hosts.stream());
    }

    public void initializeDatabase(final LibraryInput input) {
        users = input.getUsers().stream().map(User::new).collect(Collectors.toList());
        artists = new ArrayList<>();
        hosts = new ArrayList<>();
    }
}
