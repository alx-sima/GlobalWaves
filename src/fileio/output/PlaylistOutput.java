package fileio.output;

import java.util.List;
import lombok.Getter;
import main.entities.audio.collections.Playlist;

@Getter
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
}
