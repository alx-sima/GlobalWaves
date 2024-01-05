package main.entities.audio.queues.visitors;

import main.entities.audio.queues.PodcastQueue;
import main.entities.audio.queues.SongQueue;

/**
 * A visitor that visits queues.
 */
public interface QueueVisitor {

    /**
     * Visit a podcast.
     */
    default void visit(PodcastQueue podcast) {
    }

    /**
     * Visit a song queue.
     */
    default void visit(SongQueue queue) {
    }
}
