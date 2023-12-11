package main.entities.audio.collections;

import fileio.input.EpisodeInput;
import fileio.input.PodcastInput;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import main.entities.audio.SearchableAudio;
import main.entities.audio.files.AudioFile;
import main.entities.audio.files.Episode;
import main.entities.audio.queues.Queue;
import main.entities.audio.queues.visitors.QueueVisitor;
import main.entities.audio.queues.RepeatMode;

/**
 * A podcast is a collection of episodes which can be played, and keeps track of play progress.
 */
@Getter
public final class Podcast extends Queue implements SearchableAudio {

    private final String name;
    private final String owner;
    private final List<Episode> episodes;

    public Podcast(final PodcastInput input) {
        super(false);
        name = input.getName();
        owner = input.getOwner();
        episodes = input.getEpisodes().stream()
            .map(episodeInput -> new Episode(episodeInput, owner)).toList();
        currentlyPlaying = episodes.get(0);
    }

    public Podcast(final String owner, final String name, final List<EpisodeInput> episodes) {
        super(false);
        this.owner = owner;
        this.name = name;
        this.episodes = episodes.stream().map(episodeInput -> new Episode(episodeInput, owner))
            .toList();
        currentlyPlaying = this.episodes.get(0);
    }

    public Podcast(final Podcast podcast) {
        super(false);
        owner = podcast.owner;
        name = podcast.name;
        episodes = podcast.episodes;
        currentlyPlaying = podcast.currentlyPlaying;
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
    public Queue createQueue(final Map<String, Queue> playHistory) {
        Queue previousPlay = playHistory.get(name);
        if (previousPlay != null) {
            return previousPlay;
        }

        Podcast podcast = new Podcast(this);
        playHistory.put(name, podcast);
        return podcast;
    }

    @Override
    protected AudioFile getNextFile() {
        if (repeatMode == RepeatMode.REPEAT_INFINITE) {
            return episodes.get(playIndex);
        } else if (repeatMode == RepeatMode.REPEAT_ONCE) {
            repeatMode = RepeatMode.NO_REPEAT;
            return getFilePlaying();
        }

        playIndex++;
        if (playIndex >= episodes.size() && (repeatMode == RepeatMode.NO_REPEAT)) {
            return null;

        }

        return getFilePlaying();
    }

    @Override
    public RepeatMode changeRepeatMode() {
        return switch (repeatMode) {
            case NO_REPEAT -> RepeatMode.REPEAT_ONCE;
            case REPEAT_ONCE -> RepeatMode.REPEAT_INFINITE;
            case REPEAT_INFINITE -> RepeatMode.NO_REPEAT;
            default -> null;
        };
    }

    @Override
    protected AudioFile getFilePlaying() {
        return episodes.get(playIndex);
    }

    @Override
    public boolean skip(final int deltaTime) {
        if (playTime + deltaTime < 0) {
            playTime = 0;
            prev();
            return true;
        }

        addTimeIncrement(deltaTime);
        return true;
    }

    @Override
    public void accept(final QueueVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return name + ":\n\t" + episodes + "\n";
    }
}
