package main.audio.queues;

import main.audio.collections.Podcast;

public interface QueueVisitor {

    /**
     * Visit a podcast.
     */
    void visit(Podcast podcast);

    /**
     * Visit a queue.
     */
    void visit(SongQueue queue);
}
