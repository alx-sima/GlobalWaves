package main.entities.audio.queues.visitors;

import lombok.Getter;
import main.entities.audio.collections.Podcast;
import main.entities.audio.queues.SongQueue;

public final class QueueVisitor {

    private final String owner;
    @Getter
    private boolean isOwned = false;

    public QueueVisitor(final String owner) {
        this.owner = owner;
    }

    /**
     * Visit a song queue.
     */
    public void visit(final SongQueue queue) {
        SongSourceVisitor visitor = new SongSourceVisitor(owner);
        queue.getSongSource().accept(visitor);
        isOwned = visitor.isOwned();
    }

    /**
     * Visit a podcast.
     */
    public void visit(final Podcast podcast) {
        isOwned = owner.equals(podcast.getOwner());
    }
}
