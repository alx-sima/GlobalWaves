package main.program.entities.audio.queues.visitors;

import lombok.Getter;
import main.program.entities.audio.files.Song;
import main.program.entities.audio.queues.PodcastQueue;
import main.program.entities.audio.queues.SongQueue;

/**
 * A visitor that fetches the current {@link Song} playing.
 */
@Getter
public final class PlayingSongVisitor implements QueueVisitor {

    private Song playingSong;

    @Override
    public void visit(final PodcastQueue podcast) {
        // Podcasts aren't songs.
    }

    @Override
    public void visit(final SongQueue queue) {
        playingSong = queue.getFilePlaying();
    }
}
