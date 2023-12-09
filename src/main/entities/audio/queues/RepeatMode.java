package main.entities.audio.queues;

/**
 * The ways of repeating a play queue.
 */
public enum RepeatMode {
    NO_REPEAT, REPEAT_ALL, REPEAT_CURRENT, REPEAT_ONCE, REPEAT_INFINITE;

    @Override
    public String toString() {
        return switch (this) {
            case NO_REPEAT -> "No Repeat";
            case REPEAT_ALL -> "Repeat All";
            case REPEAT_CURRENT -> "Repeat Current Song";
            case REPEAT_ONCE -> "Repeat Once";
            case REPEAT_INFINITE -> "Repeat Infinite";
        };
    }
}
