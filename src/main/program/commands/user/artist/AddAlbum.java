package main.program.commands.user.artist;

import fileio.input.SongInput;
import fileio.input.commands.AddAlbumInput;
import fileio.output.CommandResult;
import fileio.output.MessageResultBuilder;
import fileio.output.ResultBuilder;
import java.util.List;
import main.entities.audio.collections.Album;
import main.program.Library;
import main.program.Program;
import main.program.commands.DependentCommand;
import main.program.commands.dependencies.IsArtistDependency;

public final class AddAlbum extends DependentCommand {

    private final MessageResultBuilder resultBuilder;
    private final String name;
    private final int releaseYear;
    private final String description;
    private final List<SongInput> songs;

    public AddAlbum(final AddAlbumInput input) {
        super(input);
        resultBuilder = new MessageResultBuilder(this);
        name = input.getName();
        releaseYear = input.getReleaseYear();
        description = input.getDescription();
        songs = input.getSongs();
    }

    @Override
    public CommandResult checkDependencies() {
        IsArtistDependency dependency = new IsArtistDependency(this, resultBuilder);
        return dependency.execute();
    }

    @Override
    public ResultBuilder executeIfDependenciesMet() {
        Program program = Program.getInstance();
        Library library = program.getLibrary();
        if (library.getAlbums().stream().anyMatch(album -> album.getName().equals(name))) {
            return resultBuilder.withMessage(user + " has another album with the same name.");
        }

        // Check for duplicate song names.
        if (songs.stream().map(SongInput::getName).distinct().count() != songs.size()) {
            return resultBuilder.withMessage(
                user + " has the same song at least twice in this album.");
        }

        Album album = new Album(user, name, releaseYear, description, songs);
        library.getAlbums().add( album);
        return resultBuilder.withMessage(user + " has added new album successfully.");
    }
}
