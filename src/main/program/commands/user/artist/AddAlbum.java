package main.program.commands.user.artist;

import fileio.input.SongInput;
import fileio.input.commands.AddAlbumInput;
import fileio.output.CommandResult;
import fileio.output.MessageResult;
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
        Program program = Program.getInstance();
        Library library = program.getLibrary();
        if (library.getAlbums().containsKey(name)) {
            return new MessageResult(this, user + " has another album with the same name.");
        }

        // Check for duplicate song names.
        if (songs.stream().map(SongInput::getName).distinct().count() != songs.size()) {
            return new MessageResult(this,
                user + " has the same song at least twice in this album.");
        }

        Album album = new Album(user, name, releaseYear, description, songs);
        library.getAlbums().put(name, album);
        return new MessageResult(this, user + " has added new album successfully.");
    }
}
