package main.program.commands.user.host;

import fileio.input.EpisodeInput;
import fileio.input.commands.AddPodcastInput;
import fileio.output.MessageResult;
import java.util.List;
import main.program.entities.audio.collections.Podcast;
import main.program.entities.users.creators.Host;
import main.program.databases.Library;

public final class AddPodcast extends HostCommand {

    private final String name;
    private final List<EpisodeInput> episodes;

    public AddPodcast(final AddPodcastInput input) {
        super(input);
        name = input.getName();
        episodes = input.getEpisodes();
    }

    @Override
    protected MessageResult execute(final Host host) {
        List<Podcast> podcasts = Library.getInstance().getPodcasts();

        if (podcasts.stream().anyMatch(podcast -> podcast.getName().equals(name))) {
            return getResultBuilder().returnMessage(
                user + " has another podcast with the same name.");
        }

        // Check for duplicate song names.
        if (episodes.stream().map(EpisodeInput::getName).distinct().count() != episodes.size()) {
            return getResultBuilder().returnMessage(
                user + " has the same song at least twice in this album.");
        }

        podcasts.add(new Podcast(name, user, episodes));
        return getResultBuilder().returnMessage(user + " has added new podcast successfully.");
    }
}
