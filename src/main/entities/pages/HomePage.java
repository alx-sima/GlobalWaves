package main.entities.pages;

import java.util.List;
import main.entities.users.User;

public final class HomePage extends LikedContentPage {

    protected List<String> getLikedSongs(User user) {
        return super.getLikedSongs(user).stream().limit(5).toList();
    }

    protected List<String> getFollowedPlaylists(User user) {
        return super.getFollowedPlaylists(user).stream().limit(5).toList();
    }
}
