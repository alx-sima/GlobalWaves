package main.program.commands.user.admin;

import fileio.input.commands.CommandInput;
import fileio.output.CommandResult;
import fileio.output.builders.PodcastResultBuilder;
import java.util.List;
import lombok.Getter;
import main.entities.audio.collections.Podcast;
import main.program.Library;
import main.program.commands.Command;

@Getter
public final class ShowPodcasts extends Command {

    private final PodcastResultBuilder resultBuilder = new PodcastResultBuilder().withCommand(this);

    public ShowPodcasts(final CommandInput input) {
        super(input);
    }

    @Override
    public CommandResult execute() {
        List<Podcast> podcasts = Library.getInstance().getPodcasts().stream()
            .filter(podcast -> podcast.getOwner().equals(user)).toList();

        return resultBuilder.withPodcasts(podcasts).build();
    }
}
