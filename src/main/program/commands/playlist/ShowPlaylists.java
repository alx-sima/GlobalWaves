package main.program.commands.playlist;

import fileio.input.commands.CommandInput;
import fileio.output.CommandResult;
import fileio.output.PlaylistResult;
import fileio.output.PlaylistResult.Builder;
import java.util.List;
import lombok.Getter;
import main.entities.audio.collections.Playlist;
import main.program.commands.Command;

@Getter
public final class ShowPlaylists extends Command {

    private final PlaylistResult.Builder resultBuilder = new Builder(this);

    public ShowPlaylists(final CommandInput input) {
        super(input);
    }

    @Override
    public CommandResult execute() {
        List<Playlist> playlists = getCaller().getPlaylists();
        return resultBuilder.result(playlists).build();
    }
}

