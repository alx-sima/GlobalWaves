package main.entities.audio.queues;

import java.util.List;
import main.entities.audio.collections.Podcast;
import main.entities.audio.files.AudioFile;
import main.entities.audio.files.Episode;
import main.entities.audio.queues.visitors.QueueVisitor;
import main.entities.users.User;

public final class PodcastQueue extends Queue {

    private final List<Episode> episodes;

    public PodcastQueue(final Podcast podcast, final User user) {
        super(user);
        episodes = podcast.getEpisodes();
        currentlyPlaying = episodes.get(0);
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
    protected Episode getFilePlaying() {
        Episode nowPlaying = episodes.get(playIndex);
        if (nowPlaying.getHost() != null) {
            nowPlaying.getHost().addListen(user, nowPlaying);
        }
        return nowPlaying;
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
}
