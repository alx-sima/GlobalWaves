package main.audio;

import main.audio.files.AudioFile;

public final class PlayerStatus {

    private final String name;
    private final int remainedTime;
    private final String repeat;
    private final boolean shuffle;
    private final boolean paused;

    public PlayerStatus(final Player player, final int timestamp) {
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

        repeat = player.getQueue().getRepeatMode().toString();
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
