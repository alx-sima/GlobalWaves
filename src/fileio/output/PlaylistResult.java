package fileio.output;

import java.util.List;
import lombok.Getter;
import main.entities.audio.collections.Playlist;

@Getter
public final class PlaylistResult extends CommandResult {

    private List<PlaylistOutput> result;

    public void setResult(final List<Playlist> result) {
        this.result = result.stream().map(PlaylistOutput::new).toList();
    }

    @Getter
    private static final class PlaylistOutput {

        private final String name;
        private final List<String> songs;
        private final String visibility;
        private final int followers;

        private PlaylistOutput(final Playlist playlist) {
            name = playlist.getName();
            songs = playlist.getSongNames();
            visibility = playlist.isPrivate() ? "private" : "public";
            followers = playlist.getFollowers();
        }
    }
}
