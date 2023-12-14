package main.program.commands.stats;

import fileio.input.commands.CommandInput;
import fileio.output.CommandResult;
import fileio.output.builders.StatsResultBuilder;
import java.util.Comparator;
import java.util.List;
import lombok.Getter;
import main.entities.audio.collections.Playlist;
import main.program.Library;
import main.program.commands.Command;

@Getter
public final class GetTop5Playlists extends Command {

    private final StatsResultBuilder resultBuilder = new StatsResultBuilder().withCommand(this);

    public GetTop5Playlists(final CommandInput input) {
        super(input);
    }

    @Override
    public CommandResult execute() {
        List<Playlist> publicPlaylists = Library.getInstance().getPublicPlaylists();

        // Compare first by number of followers, then by age (timestamp of creation).
        Comparator<Playlist> comparator = Comparator.comparingInt(Playlist::getFollowers).reversed()
            .thenComparingInt(Playlist::getCreationTimestamp);

        List<String> top = publicPlaylists.stream().sorted(comparator).limit(MAX_RESULTS)
            .map(Playlist::getName).toList();

        return resultBuilder.withResult(top).build();
    }
}
