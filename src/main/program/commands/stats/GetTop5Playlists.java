package main.program.commands.stats;

import fileio.input.commands.CommandInput;
import fileio.output.CommandResult;
import fileio.output.StatsResult;
import java.util.Comparator;
import java.util.List;
import main.audio.collections.Playlist;
import main.program.Program;
import main.program.commands.Command;

public final class GetTop5Playlists extends Command {

    public GetTop5Playlists(final CommandInput input) {
        super(input);
    }

    @Override
    public CommandResult execute() {
        Program instance = Program.getInstance();
        List<Playlist> publicPlaylists = instance.getPublicPlaylists();

        // Compare first by number of followers, then by age (timestamp of creation).
        Comparator<Playlist> comparator = Comparator.comparingInt(Playlist::getFollowers).reversed()
            .thenComparingInt(Playlist::getCreationTimestamp);

        List<String> top = publicPlaylists.stream().sorted(comparator).limit(MAX_RESULTS)
            .map(Playlist::getName).toList();

        return new StatsResult(this, top);
    }
}
