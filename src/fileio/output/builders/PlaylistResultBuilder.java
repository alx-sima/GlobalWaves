package fileio.output.builders;

import fileio.output.CommandResult;
import fileio.output.PlaylistResult;
import java.util.List;
import main.entities.audio.collections.Playlist;
import main.program.commands.Command;

public final class PlaylistResultBuilder extends ResultBuilder {

    private final PlaylistResult result = new PlaylistResult();

    /**
     * Set the result's list of playlists.
     *
     * @return the updated builder.
     */
    public PlaylistResultBuilder withPlaylists(final List<Playlist> playlists) {
        result.setResult(playlists);
        return this;
    }

    @Override
    public PlaylistResultBuilder withCommand(final Command command) {
        result.setCommand(command.getCommand());
        result.setUser(command.getUser());
        result.setTimestamp(command.getTimestamp());
        return this;
    }

    @Override
    public PlaylistResultBuilder withMessage(final String message) {
        return this;
    }

    @Override
    public CommandResult build() {
        return result;
    }
}
