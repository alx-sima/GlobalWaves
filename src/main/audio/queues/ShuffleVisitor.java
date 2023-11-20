package main.audio.queues;

public final class ShuffleVisitor implements QueueVisitor {

    private final int seed;

    public ShuffleVisitor(final int seed) {
        this.seed = seed;
    }

    @Override
    public void visit(final SongQueue queue) {
        queue.enableShuffle(seed);
    }
}
