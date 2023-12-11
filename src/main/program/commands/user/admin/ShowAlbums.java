package main.program.commands.user.admin;

import fileio.input.commands.CommandInput;
import fileio.output.AlbumResult;
import fileio.output.CommandResult;
import java.util.List;
import main.entities.audio.collections.Album;
import main.program.Library;
import main.program.Program;
import main.program.commands.Command;

public final class ShowAlbums extends Command {

    public ShowAlbums(final CommandInput input) {
        super(input);
    }

    @Override
    public CommandResult execute() {
        Library library = Library.getInstance();
        List<Album> albums = library.getAlbums().stream()
            .filter(album -> album.getOwner().equals(user)).toList();

        return new AlbumResult(this, albums);
    }
}
