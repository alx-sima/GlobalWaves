package main.program.entities.audio.collections;

import fileio.input.EpisodeInput;
import fileio.input.PodcastInput;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import main.program.entities.audio.SearchableAudio;
import main.program.entities.audio.files.Episode;
import main.program.entities.audio.queues.PodcastQueue;
import main.program.entities.audio.queues.Queue;
import main.program.entities.users.User;

/**
 * A podcast is a collection of episodes which can be played, and keeps track of play progress.
 */
@Getter
public final class Podcast implements SearchableAudio {

    private final String name;
    private final String owner;
    private final List<Episode> episodes;

    public Podcast(final PodcastInput input) {
        name = input.getName();
        owner = input.getOwner();
        episodes = input.getEpisodes().stream()
            .map(Episode::new).toList();
    }

    public Podcast(final String name, final String owner, final List<EpisodeInput> episodes) {
        this.name = name;
        this.owner = owner;
        this.episodes = episodes.stream().map(Episode::new)
            .toList();
    }

    @Override
    public boolean matchFilter(final String filter, final String parameter) {
        return switch (filter) {
            case "name" -> name.startsWith(parameter);
            case "owner" -> owner.equals(parameter);
            default -> false;
        };
    }

    @Override
    public Queue createQueue(final User user, final Map<String, PodcastQueue> podcastHistory) {
        PodcastQueue previousPlay = podcastHistory.get(name);
        if (previousPlay != null) {
            previousPlay.getFilePlaying();
            return previousPlay;
        }

        PodcastQueue podcast = new PodcastQueue(this, user);
        podcastHistory.put(name, podcast);
        return podcast;
    }



    @Override
    public String toString() {
        return name + ":\n\t" + episodes + "\n";
    }
}
