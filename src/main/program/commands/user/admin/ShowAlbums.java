package main.program.commands.user.admin;

import fileio.input.commands.CommandInput;
import fileio.output.AlbumResult;
import fileio.output.CommandResult;
import java.util.List;
import main.audio.collections.Album;
import main.audio.collections.Library;
import main.program.Program;
import main.program.commands.Command;

public final class ShowAlbums extends Command {

    public ShowAlbums(final CommandInput input) {
        super(input);
    }

    @Override
    public CommandResult execute() {
        Program program = Program.getInstance();
        Library library = program.getLibrary();
        List<Album> albums = library.getAlbums().values().stream()
            .filter(album -> album.getOwner().equals(user)).toList();

        return new AlbumResult(this, albums);
    }
}
