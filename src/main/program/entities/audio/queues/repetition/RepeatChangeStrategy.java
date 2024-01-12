package main.program.entities.audio.queues.repetition;

/**
 * The way to change between repeat modes in a queue.
 */
@FunctionalInterface
public interface RepeatChangeStrategy {

    /**
     * Given a repeat mode, get the following one.
     *
     * @param currentRepeatMode the current repeat mode.
     * @return the next repeat mode, based on the strategy.
     */
    RepeatMode getNextRepeatMode(RepeatMode currentRepeatMode);
}
