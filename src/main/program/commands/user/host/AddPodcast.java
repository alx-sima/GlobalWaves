package main.program.commands.user.host;

import fileio.input.EpisodeInput;
import fileio.input.commands.AddPodcastInput;
import fileio.output.CommandResult;
import java.util.List;
import main.program.commands.Command;

public final class AddPodcast extends Command {

    private final String name;
    private final List<EpisodeInput>episodes;

    public AddPodcast(final AddPodcastInput input) {
        super(input);
        name = input.getName();
        episodes = input.getEpisodes();
    }

    @Override
    public CommandResult execute() {
        // TODO
        return null;
    }
}
