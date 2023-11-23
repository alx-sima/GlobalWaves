package main.audio.collections;

import fileio.input.PodcastInput;
import java.util.List;
import lombok.Getter;
import main.audio.Searchable;
import main.audio.files.AudioFile;
import main.audio.files.Episode;
import main.audio.queues.Queue;
import main.audio.queues.RepeatMode;

/**
 * A podcast is a collection of episodes which can be played, and keeps track of play progress.
 */
public final class Podcast extends Queue implements Searchable {

    @Getter
    private final String name;
    private final String owner;
    private final List<Episode> episodes;

    public Podcast(final PodcastInput input) {
        super(false);
        name = input.getName();
        owner = input.getOwner();
        episodes = input.getEpisodes().stream().map(Episode::new).toList();
        currentlyPlaying = episodes.get(0);
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
    public Queue createQueue() {
        return this;
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
        if (playIndex >= episodes.size()) {
            if (repeatMode == RepeatMode.NO_REPEAT) {
                return null;
            }
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
}
