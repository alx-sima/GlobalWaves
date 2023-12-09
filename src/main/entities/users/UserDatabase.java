package main.entities.users;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import main.entities.users.artist.Artist;

@Getter
public final class UserDatabase {

    private final Map<String, User> users = new HashMap<>();
    private final Map<String, Artist> artists = new HashMap<>();
    private final Map<String, Host> hosts = new HashMap<>();

    /**
     * Add a new user to the database.
     */
    public void addUser(final String type, final String username, final int age,
        final String city) {
        switch (type) {
            case "user" -> users.put(username, new User(type, username, age, city));
            case "artist" -> artists.put(username, new Artist(type, username, age, city));
            case "hosts" -> hosts.put(username, new Host(type, username, age, city));
        }
    }

    /**
     * Get a user from the database.
     *
     * @param username the searched username.
     */
    public User getUser(final String username) {
        if (users.containsKey(username)) {
            return users.get(username);
        } else {
            return artists.get(username);
        }
    }

    /**
     * Check if the user exists.
     *
     * @param username the searched username.
     */
    public boolean existsUser(final String username) {
        return users.containsKey(username) || artists.containsKey(username);
    }

    /**
     * Clear the database.
     */
    public void clear() {
        users.clear();
        artists.clear();
    }
}
