package fileio.output;

import java.util.List;
import main.audio.collections.Playlist;

public final class PlaylistOutput {
    private final String name;
    private final List<String> songs;
    private final String visibility;
    private final int followers;

    public PlaylistOutput(final Playlist playlist) {
        name = playlist.getName();
        songs = playlist.getSongNames();
        visibility = playlist.isPrivate() ? "private" : "public";
        followers = playlist.getFollowers();
    }

    public String getName() {
        return name;
    }

    public List<String> getSongs() {
        return songs;
    }

    public String getVisibility() {
        return visibility;
    }

    public int getFollowers() {
        return followers;
    }
}
