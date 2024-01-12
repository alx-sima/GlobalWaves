package main.program.commands.user.host;

import fileio.input.EpisodeInput;
import fileio.input.commands.CommandWithNameInput;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import main.program.commands.Command;
import main.program.databases.Library;
import main.program.entities.audio.collections.Podcast;
import main.program.entities.users.creators.Host;

public final class AddPodcast extends HostCommand {

    private final String name;
    private final List<EpisodeInput> episodes;

    public AddPodcast(final Input input) {
        super(input);
        name = input.getName();
        episodes = input.getEpisodes();
    }

    @Override
    protected String returnExecutionMessage(final Host host) {
        List<Podcast> podcasts = Library.getInstance().getPodcasts();

        if (podcasts.stream().anyMatch(podcast -> podcast.getName().equals(name))) {
            return user + " has another podcast with the same name.";
        }

        // Check for duplicate song names.
        if (episodes.stream().map(EpisodeInput::getName).distinct().count() != episodes.size()) {
            return user + " has the same song at least twice in this album.";
        }

        podcasts.add(new Podcast(name, user, episodes));
        return user + " has added new podcast successfully.";
    }

    @Getter
    @Setter
    public static final class Input extends CommandWithNameInput {

        private List<EpisodeInput> episodes;

        @Override
        public Command createCommand() {
            return new AddPodcast(this);
        }
    }
}
