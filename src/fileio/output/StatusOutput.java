package fileio.output;

import main.program.Player;
import main.audio.collections.RepeatMode;
import main.audio.files.AudioFile;

public final class StatusOutput {

    private final String name;
    private final int remainedTime;
    private final String repeat;
    private final boolean shuffle;
    private final boolean paused;

    public StatusOutput(final Player player, final int timestamp) {
        // TODO: refactor
        if (player == null) {
            name = "";
            remainedTime = 0;
            paused = true;
            repeat = RepeatMode.NO_REPEAT.toString();
        } else {
            AudioFile nowPlaying = player.getPlayingAt(timestamp);
            if (nowPlaying != null) {
                name = nowPlaying.getName();
                remainedTime = player.remainingTimeAt(timestamp);
                paused = player.isPaused();
            } else {
                name = "";
                remainedTime = 0;
                paused = true;
            }

            repeat = player.getRepeatMode().toString();
        }

        shuffle = false;
    }

    public String getName() {
        return name;
    }

    public int getRemainedTime() {
        return remainedTime;
    }

    public boolean isShuffle() {
        return shuffle;
    }

    public boolean isPaused() {
        return paused;
    }

    public String getRepeat() {
        return repeat;
    }
}
