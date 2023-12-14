package main.program.commands.user.admin;

import fileio.input.commands.CommandInput;
import fileio.output.builders.AlbumResultBuilder;
import fileio.output.CommandResult;
import java.util.List;
import lombok.Getter;
import main.entities.audio.collections.Album;
import main.entities.users.UserDatabase;
import main.program.commands.Command;

@Getter
public final class ShowAlbums extends Command {

    private final AlbumResultBuilder resultBuilder = new AlbumResultBuilder().withCommand(this);

    public ShowAlbums(final CommandInput input) {
        super(input);
    }

    @Override
    public CommandResult execute() {
        List<Album> albums = UserDatabase.getInstance().getAlbums().stream()
            .filter(album -> album.getOwner().equals(user)).toList();

        return resultBuilder.withAlbums(albums).build();
    }
}
