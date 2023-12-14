package main.program.commands.user.host;

import fileio.input.EpisodeInput;
import fileio.input.commands.AddPodcastInput;
import fileio.output.builders.ResultBuilder;
import java.util.List;
import main.entities.audio.collections.Podcast;
import main.entities.users.host.Host;
import main.program.Library;

public final class AddPodcast extends HostCommand {

    private final String name;
    private final List<EpisodeInput> episodes;

    public AddPodcast(final AddPodcastInput input) {
        super(input);
        name = input.getName();
        episodes = input.getEpisodes();
    }

    @Override
    protected ResultBuilder execute(final Host host) {
        List<Podcast> podcasts = Library.getInstance().getPodcasts();

        if (podcasts.stream().anyMatch(podcast -> podcast.getName().equals(name))) {
            return getResultBuilder().withMessage(
                user + " has another podcast with the same name.");
        }

        // Check for duplicate song names.
        if (episodes.stream().map(EpisodeInput::getName).distinct().count() != episodes.size()) {
            return getResultBuilder().withMessage(
                user + " has the same song at least twice in this album.");
        }

        podcasts.add(new Podcast(user, name, episodes));
        return getResultBuilder().withMessage(user + " has added new podcast successfully.");
    }
}
