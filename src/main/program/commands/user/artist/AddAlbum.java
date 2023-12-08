package main.program.commands.user.artist;

import fileio.input.SongInput;
import fileio.input.commands.AddAlbumInput;
import fileio.output.CommandResult;
import fileio.output.MessageResultBuilder;
import java.util.List;
import main.audio.collections.Album;
import main.audio.collections.Library;
import main.program.Program;
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
        MessageResultBuilder resultBuilder = new MessageResultBuilder(this);

        Program program = Program.getInstance();
        Library library = program.getLibrary();
        if (library.getAlbums().containsKey(name)) {
            resultBuilder.withMessage(user + " has another album with the same name.");
            return resultBuilder.build();
        }

        // Check for duplicate song names.
        if (songs.stream().map(SongInput::getName).distinct().count() != songs.size()) {
            resultBuilder.withMessage(user + " has the same song at least twice in this album.");
            return resultBuilder.build();
        }

        Album album = new Album(user, name, releaseYear, description, songs);
        library.getAlbums().put(name, album);
        resultBuilder.withMessage(user + " has added new album successfully.");
        return resultBuilder.build();
    }
}
