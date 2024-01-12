package main.program.entities.audio.queues.repetition;

import static main.program.entities.audio.queues.repetition.RepeatMode.NO_REPEAT;
import static main.program.entities.audio.queues.repetition.RepeatMode.REPEAT_ALL;
import static main.program.entities.audio.queues.repetition.RepeatMode.REPEAT_CURRENT;

/**
 * Repeat modes for playlists and albums.
 */
public final class PlaylistRepeatStrategy implements RepeatChangeStrategy {

    @Override
    public RepeatMode getNextRepeatMode(final RepeatMode currentRepeatMode) {
        return switch (currentRepeatMode) {
            case NO_REPEAT -> REPEAT_ALL;
            case REPEAT_ALL -> REPEAT_CURRENT;
            case REPEAT_CURRENT -> NO_REPEAT;
            default -> null;
        };
    }
}
