package main.program.commands.search;

import fileio.input.CommandInput;
import fileio.output.CommandResult;
import fileio.output.ShowPreferredSongsResult;
import java.util.Comparator;
import java.util.List;
import main.audio.collections.Playlist;
import main.program.Program;
import main.program.commands.Command;

public final class GetTop5Playlists extends Command {

    public GetTop5Playlists(CommandInput input) {
        super(input);
    }

    @Override
    public CommandResult execute() {
        Program instance = Program.getInstance();
        List<Playlist> publicPlaylists = instance.getPublicPlaylists();

        List<String> top = publicPlaylists.stream().sorted(
                Comparator.comparingInt(Playlist::getFollowers).reversed()
                    .thenComparingInt(Playlist::getCreationTimestamp)).limit(5).map(Playlist::getName)
            .toList();

        return new ShowPreferredSongsResult(this, top);
    }
}
