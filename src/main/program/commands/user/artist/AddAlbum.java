package main.program.commands.user.artist;

import fileio.input.SongInput;
import fileio.input.commands.AddAlbumInput;
import fileio.output.CommandResult;
import java.util.List;
import main.program.commands.Command;

public final class AddAlbum extends Command {

    private final String name;
    private final int releaseYear;
    private final String description;
    private final List<SongInput> songs;

    public AddAlbum(final AddAlbumInput input) {
        super(input);
        name = input.getName();
        releaseYear = input.getReleaseYear();
        description = input.getDescription();
        songs = input.getSongs();
    }

    @Override
    public CommandResult execute() {
        // TODO
        return null;
    }
}
