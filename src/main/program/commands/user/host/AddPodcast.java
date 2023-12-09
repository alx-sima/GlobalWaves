package main.program.commands.user.host;

import fileio.input.EpisodeInput;
import fileio.input.commands.AddPodcastInput;
import fileio.output.CommandResult;
import fileio.output.ResultBuilder;
import java.util.List;
import main.program.commands.DependentCommand;

public final class AddPodcast extends DependentCommand {

    private final String name;
    private final List<EpisodeInput>episodes;

    public AddPodcast(final AddPodcastInput input) {
        super(input);
        name = input.getName();
        episodes = input.getEpisodes();
    }

    @Override
    public CommandResult checkDependencies() {
        // TODO
        return null;
    }

    @Override
    public ResultBuilder executeIfDependenciesMet() {
        // TODO
        return null;
    }
}
