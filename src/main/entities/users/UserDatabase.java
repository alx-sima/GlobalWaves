package main.entities.users;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import lombok.Getter;
import main.entities.users.artist.Artist;

@Getter
public final class UserDatabase {

    private final List<User> users = new ArrayList<>();
    private final List<Artist> artists = new ArrayList<>();
    private final List<Host> hosts = new ArrayList<>();
    private final List<String> busyUsers = new ArrayList<>();

    /**
     * Add a new user to the database.
     */
    public void addUser(final String type, final String username, final int age,
        final String city) {
        switch (type) {
            case "user" -> users.add(new User(type, username, age, city));
            case "artist" -> artists.add(new Artist(type, username, age, city));
            case "hosts" -> hosts.add(new Host(type, username, age, city));
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

    /**
     * Clear the database.
     */
    public void clear() {
        users.clear();
        artists.clear();
    }

    public Stream<User> getAllUsers() {
        return Stream.concat(Stream.concat(users.stream(), artists.stream()), hosts.stream());
    }
}
