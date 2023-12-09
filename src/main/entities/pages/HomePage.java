package main.entities.pages;

import java.util.List;
import main.entities.users.User;

public final class HomePage extends LikedContentPage {

    /**
     * The maximum number of results to be displayed.
     */
    private static final int MAX_RESULTS = 5;

    protected List<String> getLikedSongs(User user) {
        return super.getLikedSongs(user).stream().limit(MAX_RESULTS).toList();
    }

    protected List<String> getFollowedPlaylists(User user) {
        return super.getFollowedPlaylists(user).stream().limit(MAX_RESULTS).toList();
    }
}
