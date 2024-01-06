package main.program.entities.audio.queues.visitors;

import lombok.Getter;
import main.program.entities.audio.collections.Album;
import main.program.entities.audio.collections.Playlist;
import main.program.entities.audio.queues.Shuffler;
import main.program.entities.audio.queues.SongQueue;

/**
 * A visitor that shuffles the queue if possible.
 */
@Getter
public final class ShuffleVisitor implements QueueVisitor {

    private final int seed;
    /**
     * Whether the queue is shuffleable.
     */
    private boolean shuffleable = false;
    private Shuffler shuffler = null;

    public ShuffleVisitor(final int seed) {
        this.seed = seed;
    }

    @Override
    public void visit(final SongQueue queue) {
        SongCanShuffleVisitor visitor = new SongCanShuffleVisitor(seed, !queue.isShuffled());
        queue.getSongSource().accept(visitor);

        shuffleable = visitor.isShuffleable();
        shuffler = visitor.getShuffler();
    }

    private static final class SongCanShuffleVisitor implements SongSourceVisitor {

        private final int seed;
        /**
         * Whether to create a shuffler, or just check if the queue is shuffleable.
         */
        private final boolean createShuffler;
        @Getter
        private boolean shuffleable = false;
        @Getter
        private Shuffler shuffler = null;

        private SongCanShuffleVisitor(final int seed, final boolean createShuffler) {
            this.seed = seed;
            this.createShuffler = createShuffler;
        }

        @Override
        public void visit(final Album album) {
            if (createShuffler) {
                shuffler = new Shuffler(seed, album.size());
            }
            shuffleable = true;
        }

        @Override
        public void visit(final Playlist playlist) {
            if (createShuffler) {
                shuffler = new Shuffler(seed, playlist.size());
            }
            shuffleable = true;
        }
    }
}
