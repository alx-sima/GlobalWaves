package main.program;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;

@Getter
public final class UserDatabase {

    private final Map<String, User> users = new HashMap<>();
    private final Map<String, User> artists = new HashMap<>();

    /**
     * Add a new user to the database.
     */
    public void addUser(final User user) {
        switch (user.getType()) {
            case "user" -> users.put(user.getUsername(), user);
            case "artist" -> artists.put(user.getUsername(), user);
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
