package main.program.entities.audio.queues;

import java.util.List;
import main.program.entities.audio.collections.Podcast;
import main.program.entities.audio.files.AudioFile;
import main.program.entities.audio.files.Episode;
import main.program.entities.audio.queues.repetition.OrderedListenStrategy;
import main.program.entities.audio.queues.repetition.RepeatMode;
import main.program.entities.audio.queues.visitors.QueueVisitor;
import main.program.entities.users.User;

public final class PodcastQueue extends Queue {

    private final List<Episode> episodes;

    public PodcastQueue(final Podcast podcast, final User user) {
        super(user, new OrderedListenStrategy());
        episodes = podcast.getEpisodes();
        currentlyPlaying = getFilePlaying();
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
    public Episode getFilePlaying() {
        Episode nowPlaying = episodes.get(playIndex);
        nowPlaying.addListen(user);
        user.addListen(nowPlaying);
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
