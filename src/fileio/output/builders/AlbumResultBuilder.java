package fileio.output.builders;

import fileio.output.AlbumResult;
import fileio.output.CommandResult;
import java.util.List;
import main.entities.audio.collections.Album;
import main.program.commands.Command;

public final class AlbumResultBuilder extends ResultBuilder {

    private final AlbumResult result = new AlbumResult();

    /**
     * Set the result's list of albums.
     *
     * @return the updated builder.
     */
    public AlbumResultBuilder withAlbums(final List<Album> albums) {
        result.setResult(albums);
        return this;
    }

    @Override
    public AlbumResultBuilder withCommand(final Command command) {
        result.setCommand(command.getCommand());
        result.setUser(command.getUser());
        result.setTimestamp(command.getTimestamp());
        return this;
    }

    @Override
    public AlbumResultBuilder withMessage(final String message) {
        return this;
    }

    @Override
    public CommandResult build() {
        return result;
    }
}
