package fileio.output;

import main.program.Player;
import main.audio.files.AudioFile;

public final class StatusOutput {

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

        name = "";
        remainedTime = 0;
        paused = true;
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
