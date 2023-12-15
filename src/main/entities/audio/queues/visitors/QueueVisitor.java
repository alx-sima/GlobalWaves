package main.entities.audio.queues.visitors;

import main.entities.audio.collections.Podcast;
import main.entities.audio.queues.SongQueue;

/**
 * A visitor that visits queues.
 */
public interface QueueVisitor {

    /**
     * Visit a podcast.
     */
    default void visit(Podcast podcast) {
    }

    /**
     * Visit a song queue.
     */
    default void visit(SongQueue queue) {
    }
}
