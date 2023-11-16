package main.audio.collections;

public interface PlayableVisitor {

    /**
     * Visit a podcast.
     */
    void visit(Podcast podcast);

    /**
     * Visit a queue.
     */
    void visit(SongQueue queue);
}
