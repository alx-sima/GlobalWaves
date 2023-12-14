package fileio.output;

import lombok.Getter;
import lombok.Setter;
import main.entities.audio.files.AudioFile;
import main.program.Player;

@Getter
@Setter
public final class StatusResult extends CommandResult {

    private StatusOutput stats;

    @Getter
    public static final class StatusOutput {

        private final String name;
        private final int remainedTime;
        private final String repeat;
        private final boolean shuffle;
        private final boolean paused;

        public StatusOutput(final Player player, final int timestamp) {
            AudioFile nowPlaying = player.getPlayingAt(timestamp);
            repeat = player.getRepeatMode().toString();

            if (nowPlaying != null) {
                name = nowPlaying.getName();
                remainedTime = player.remainingTimeAt(timestamp);
                paused = player.isPaused();
                shuffle = player.getQueue().isShuffled();
                return;
            }

            // Print default values if nothing is playing.
            name = "";
            remainedTime = 0;
            paused = true;
            shuffle = false;
        }
    }
}
