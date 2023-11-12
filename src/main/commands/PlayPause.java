package main.commands;

import main.Program;
import main.audio.Player;

public final class PlayPause extends Command {
    public PlayPause(final String command, final String user, final int timestamp) {
        super(command, user, timestamp);
    }

    @Override
    public Result execute() {
        Program instance = Program.getInstance();
        Player player = instance.getPlayer();

        if (player.getQueue().isEmpty()) {
            return new Result(this, "Please load a source before attempting to pause or resume " +
                    "playback");
        }

        if (player.togglePaused()) {
            return new Result(this, "Playback resumed successfully.");
        }
        return new Result(this, "Playback paused successfully");
    }
}
