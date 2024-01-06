package main.program.commands.user.admin;

import fileio.input.commands.CommandInput;
import fileio.output.AlbumResult;
import fileio.output.AlbumResult.Builder;
import fileio.output.CommandResult;
import java.util.List;
import lombok.Getter;
import main.program.entities.audio.collections.Album;
import main.program.databases.UserDatabase;
import main.program.commands.Command;

@Getter
public final class ShowAlbums extends Command {


    private final AlbumResult.Builder resultBuilder = new Builder(this);

    public ShowAlbums(final CommandInput input) {
        super(input);
    }

    @Override
    public CommandResult execute() {
        List<Album> albums = UserDatabase.getInstance().getAlbums().stream()
            .filter(album -> album.getOwner().getUsername().equals(user)).toList();

        return resultBuilder.result(albums).build();
    }
}
