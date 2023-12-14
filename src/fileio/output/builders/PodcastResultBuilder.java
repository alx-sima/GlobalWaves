package fileio.output.builders;

import fileio.output.CommandResult;
import fileio.output.PodcastResult;
import java.util.List;
import main.entities.audio.collections.Podcast;
import main.program.commands.Command;

public final class PodcastResultBuilder extends ResultBuilder {

    private final PodcastResult result = new PodcastResult();

    /**
     * Set the result's list of podcasts.
     *
     * @return the updated builder.
     */
    public PodcastResultBuilder withPodcasts(final List<Podcast> podcasts) {
        result.setResult(podcasts);
        return this;
    }

    @Override
    public PodcastResultBuilder withCommand(final Command command) {
        result.setCommand(command.getCommand());
        result.setUser(command.getUser());
        result.setTimestamp(command.getTimestamp());
        return this;
    }

    @Override
    public PodcastResultBuilder withMessage(final String message) {
        return this;
    }

    @Override
    public CommandResult build() {
        return result;
    }
}
