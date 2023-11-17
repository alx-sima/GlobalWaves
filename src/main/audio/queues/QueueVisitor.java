package main.audio.queues;

import main.audio.collections.Podcast;

public interface QueueVisitor {

    /**
     * Visit a podcast.
     */
    default void visit(Podcast podcast) {
    }

    /**
     * Visit a queue.
     */
    default void visit(SongQueue queue) {
    }
}
