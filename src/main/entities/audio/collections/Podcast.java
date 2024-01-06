package main.entities.audio.collections;

import fileio.input.EpisodeInput;
import fileio.input.PodcastInput;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import main.entities.audio.SearchableAudio;
import main.entities.audio.files.Episode;
import main.entities.audio.queues.PodcastQueue;
import main.entities.audio.queues.Queue;
import main.entities.users.User;
import main.entities.users.creators.Host;

/**
 * A podcast is a collection of episodes which can be played, and keeps track of play progress.
 */
@Getter
public final class Podcast implements SearchableAudio {

    private final String name;
    private final Host host;
    private final List<Episode> episodes;

    public Podcast(final PodcastInput input, final Host host) {
        this.host = host;
        name = input.getName();
        episodes = input.getEpisodes().stream()
            .map(episodeInput -> new Episode(episodeInput, host)).toList();
    }

    public Podcast(final String name, final Host host, final List<EpisodeInput> episodes) {
        this.name = name;
        this.host = host;
        this.episodes = episodes.stream().map(episodeInput -> new Episode(episodeInput, host))
            .toList();
    }

    @Override
    public boolean matchFilter(final String filter, final String parameter) {
        return switch (filter) {
            case "name" -> name.startsWith(parameter);
            case "owner" -> host.getUsername().equals(parameter);
            default -> false;
        };
    }

    @Override
    public Queue createQueue(final User user, final Map<String, Queue> playHistory) {
        Queue previousPlay = playHistory.get(name);
        if (previousPlay != null) {
            return previousPlay;
        }

        PodcastQueue podcast = new PodcastQueue(this, user);
        playHistory.put(name, podcast);
        return podcast;
    }



    @Override
    public String toString() {
        return name + ":\n\t" + episodes + "\n";
    }
}
