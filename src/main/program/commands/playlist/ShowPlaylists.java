package main.program.commands.playlist;

import fileio.input.commands.CommandInput;
import fileio.output.CommandResult;
import fileio.output.builders.PlaylistResultBuilder;
import java.util.List;
import lombok.Getter;
import main.entities.audio.collections.Playlist;
import main.program.commands.Command;

@Getter
public final class ShowPlaylists extends Command {

    private final PlaylistResultBuilder resultBuilder = new PlaylistResultBuilder().withCommand(
        this);

    public ShowPlaylists(final CommandInput input) {
        super(input);
    }

    @Override
    public CommandResult execute() {
        List<Playlist> playlists = getCaller().getPlaylists();
        return resultBuilder.withPlaylists(playlists).build();
    }
}

