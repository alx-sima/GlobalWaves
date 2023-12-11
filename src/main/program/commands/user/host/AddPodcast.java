package main.program.commands.user.host;

import fileio.input.EpisodeInput;
import fileio.input.commands.AddPodcastInput;
import fileio.output.CommandResult;
import fileio.output.MessageResultBuilder;
import fileio.output.ResultBuilder;
import java.util.List;
import main.entities.audio.collections.Podcast;
import main.program.Library;
import main.program.Program;
import main.program.commands.DependentCommand;
import main.program.commands.dependencies.IsHostDependency;

public final class AddPodcast extends DependentCommand {

    private final MessageResultBuilder resultBuilder;
    private final String name;
    private final List<EpisodeInput> episodes;

    public AddPodcast(final AddPodcastInput input) {
        super(input);
        resultBuilder = new MessageResultBuilder(this);
        name = input.getName();
        episodes = input.getEpisodes();
    }

    @Override
    public CommandResult checkDependencies() {
        IsHostDependency dependency = new IsHostDependency(this, resultBuilder);
        return dependency.checkDependencies();
    }

    @Override
    public ResultBuilder executeIfDependenciesMet() {
        Library library = Program.getInstance().getLibrary();

        if (library.getPodcasts().stream().anyMatch(podcast -> podcast.getName().equals(name))) {
            return resultBuilder.withMessage(user + " has another podcast with the same name.");
        }

        // Check for duplicate song names.
        if (episodes.stream().map(EpisodeInput::getName).distinct().count() != episodes.size()) {
            return resultBuilder.withMessage(
                user + " has the same song at least twice in this album.");
        }

        library.getPodcasts().add(new Podcast(user, name, episodes));
        return resultBuilder.withMessage(user + " has added new podcast successfully.");
    }
}
