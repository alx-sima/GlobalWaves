package main.program.commands.user.artist;

import fileio.input.commands.CommandInputWithName;
import fileio.output.CommandResult;
import fileio.output.MessageResultBuilder;
import fileio.output.ResultBuilder;
import main.entities.audio.collections.Album;
import main.program.Library;
import main.program.Program;
import main.program.commands.DependentCommand;
import main.program.commands.dependencies.IsArtistDependency;

public final class RemoveAlbum extends DependentCommand {

    private final MessageResultBuilder resultBuilder;
    private final String name;

    public RemoveAlbum(final CommandInputWithName input) {
        super(input);
        resultBuilder = new MessageResultBuilder(this);
        name = input.getName();
    }

    @Override
    public CommandResult checkDependencies() {
        IsArtistDependency dependency = new IsArtistDependency(this, resultBuilder);
        return dependency.execute();
    }

    @Override
    public ResultBuilder executeIfDependenciesMet() {
        Library library = Program.getInstance().getLibrary();
        Album album = library.getAlbums().stream()
            .filter(a -> a.getName().equals(name)).findFirst().orElse(null);

        if (album == null) {
            return resultBuilder.withMessage(user + " doesn't have an album with the given name.");
        }

        if (Program.getInstance().getDatabase().getUsers().stream().flatMap(
                user -> user.getPlaylists().stream()
                    .flatMap(playlist -> playlist.getSongs().stream()))
            .anyMatch(song -> album.getSongs().contains(song))) {
            return resultBuilder.withMessage(user + " can't delete this album.");
        }

        library.getAlbums().remove(album);
        return resultBuilder.withMessage(user + " deleted the album successfully.");
    }
}
