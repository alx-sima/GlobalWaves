package fileio.output;

import lombok.Getter;
import main.entities.audio.files.AudioFile;
import main.program.Player;
import main.program.commands.Command;

@Getter
public final class StatusResult extends MessageResult {

    private final StatusOutput stats;

    private StatusResult(final Builder builder) {
        super(builder);
        stats = builder.stats;
    }

    @Getter
    public static final class StatusOutput {

        private final String name;
        private final int remainedTime;
        private final String repeat;
        private final boolean shuffle;
        private final boolean paused;

        private StatusOutput(final Player player, final int timestamp) {
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

    public static final class Builder extends ResultBuilder {

        private StatusOutput stats;

        public Builder(final Command cmd) {
            super(cmd);
        }

        /**
         * Set the status, by checking the `player` at a given `timestamp`.
         */
        public Builder player(final Player player, final int timestamp) {
            stats = new StatusOutput(player, timestamp);
            return this;
        }

        @Override
        public MessageResult build() {
            return new StatusResult(this);
        }
    }
}
