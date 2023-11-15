package fileio.output;

import java.util.List;
import main.audio.collections.Playlist;
import main.commands.Command;

public final class ShowPlaylistsResult extends CommandResult {

    private final List<Playlist> result;

    public ShowPlaylistsResult(final Command command, final List<Playlist> playlists) {
        super(command);
        result = playlists;
    }

    public List<Playlist> getResult() {
        return result;
    }
}
