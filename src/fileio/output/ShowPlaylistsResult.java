package fileio.output;

import java.util.List;
import lombok.Getter;
import main.entities.audio.collections.Playlist;
import main.program.commands.Command;

@Getter
public final class ShowPlaylistsResult extends CommandResult {

    private final List<PlaylistOutput> result;

    public ShowPlaylistsResult(final Command command, final List<Playlist> playlists) {
        super(command);
        result = playlists.stream().map((PlaylistOutput::new)).toList();
    }
}
