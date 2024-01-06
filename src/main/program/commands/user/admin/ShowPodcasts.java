package main.program.commands.user.admin;

import fileio.input.commands.CommandInput;
import fileio.output.CommandResult;
import fileio.output.PodcastResult;
import fileio.output.PodcastResult.Builder;
import java.util.List;
import lombok.Getter;
import main.program.entities.audio.collections.Podcast;
import main.program.databases.Library;
import main.program.commands.Command;

@Getter
public final class ShowPodcasts extends Command {

    private final PodcastResult.Builder resultBuilder = new Builder(this);

    public ShowPodcasts(final CommandInput input) {
        super(input);
    }

    @Override
    public CommandResult execute() {
        List<Podcast> podcasts = Library.getInstance().getPodcasts().stream()
            .filter(podcast -> podcast.getOwner().equals(user)).toList();

        return resultBuilder.result(podcasts).build();
    }
}
