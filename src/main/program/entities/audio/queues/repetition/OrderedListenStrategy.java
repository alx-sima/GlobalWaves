package main.program.entities.audio.queues.repetition;

/**
 * Repeat modes for queues that should be listened in order (single song, podcast).
 */
public final class OrderedListenStrategy implements RepeatChangeStrategy {

    @Override
    public RepeatMode getNextRepeatMode(final RepeatMode currentRepeatMode) {
        return switch (currentRepeatMode) {
            case NO_REPEAT -> RepeatMode.REPEAT_ONCE;
            case REPEAT_ONCE -> RepeatMode.REPEAT_INFINITE;
            case REPEAT_INFINITE -> RepeatMode.NO_REPEAT;
            default -> null;
        };
    }
}
